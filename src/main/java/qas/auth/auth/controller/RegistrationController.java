package qas.auth.auth.controller;

import org.jooq.tools.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import qas.auth.auth.dto.RegObj;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.dto.UserForDBc;
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

        HttpEntity<UserForDBc> request = new HttpEntity<>(userForDBc, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8080/user", HttpMethod.POST, request,
                        ResponseEntity.class);

        return new ResponseEntity(HttpStatus.OK);
    }
}
