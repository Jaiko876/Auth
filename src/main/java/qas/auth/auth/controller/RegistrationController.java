package qas.auth.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import qas.auth.auth.dto.RegObj;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.services.UserService;


@RestController
@RequestMapping("/")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegObj regObj) {
        UserDto userDto = new UserDto();
        userDto.setLogin(regObj.getUsername());
        userDto.setPassword(regObj.getPassword());
        userService.register(userDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
