package qas.auth.auth.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import qas.auth.auth.dto.RegObj;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.dto.transferObjects.RoleTransfer;
import qas.auth.auth.dto.transferObjects.UserForDBc;
import qas.auth.auth.security.jwt.JwtTokenProvider;
import qas.auth.auth.services.UserService;


@RestController
@RequestMapping("/")
public class RegistrationController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public RegistrationController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegObj regObj) {

        UserDto userDto = new UserDto();
        userDto.setLogin(regObj.getUsername());
        userDto.setPassword(regObj.getPassword());
        userService.register(userDto);
        UserDto byUserName = userService.findByUserName(regObj.getUsername());
        String token = jwtTokenProvider.createToken(byUserName);
        userService.login(byUserName, token);

        UserForDBc userForDBc = new UserForDBc((int) byUserName.getId(), regObj.getFio(), regObj.getUsername(),"",
                regObj.getEmail(),regObj.getTelegramChatId());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity headerEntity = new HttpEntity(headers);
        HttpEntity<UserForDBc> request = new HttpEntity<>(userForDBc, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseEntity> exchange = restTemplate.exchange("http://localhost:8080/user", HttpMethod.POST, request,
                ResponseEntity.class);

        RoleTransfer roleTransfer = new RoleTransfer();
        roleTransfer.setId_role(4);
        roleTransfer.setName("Сотрудник");
        HttpEntity<RoleTransfer> roleTransferHttpEntity = new HttpEntity<>(roleTransfer, headers);


        /*ResponseEntity<RoleTransfer> roleTransferResponseEntity = restTemplate.exchange("http://localhost:8080/role"
                , HttpMethod.GET, headerEntity, RoleTransfer.class);*/
        ResponseEntity<ResponseEntity> AnotherExchange = restTemplate.exchange("http://localhost:8080/user/role/{id}"
                , HttpMethod.POST, roleTransferHttpEntity, ResponseEntity.class, userForDBc.getId_user());
        System.out.println(exchange.getBody());

        return new ResponseEntity(HttpStatus.OK);
    }
}
