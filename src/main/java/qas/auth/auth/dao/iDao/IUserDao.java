package qas.auth.auth.dao.iDao;

import qas.auth.auth.dto.UserDto;

public interface IUserDao {
    UserDto findByUserName(String name);
    void updateToken(UserDto userDto, String token);
    void addUser(UserDto userDto);
    UserDto getUserById(long id);
    boolean checkLogin(String login);
}
