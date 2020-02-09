package qas.auth.auth.services.iService;


import qas.auth.auth.dto.UserDto;

public interface IUserService {
    UserDto findByUserName(String userName);
    void login(UserDto user, String token);
    void register(UserDto userDto);
    UserDto getUserById(long id);
    boolean checkLogin(String login);
}
