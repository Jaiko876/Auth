package qas.auth.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import qas.auth.auth.dao.RoleDao;
import qas.auth.auth.dao.UserDao;
import qas.auth.auth.dao.UserRoleDao;
import qas.auth.auth.dto.RoleDto;
import qas.auth.auth.dto.UserDto;
import qas.auth.auth.services.iService.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public void login(UserDto user, String token) {

        userDao.updateToken(user, token);
    }

    public void register(UserDto userDto) {
        RoleDto role = roleDao.getDefaultRole();
        List<RoleDto> userRole = new ArrayList<>();
        userRole.add(role);
        userDto.setRoles(userRole);
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDao.addUser(userDto);
        UserDto userWithActualId = userDao.findByUserName(userDto.getLogin());
        userRoleDao.setUserRole(userWithActualId.getId(), role.getId());
    }

    @Override
    public UserDto getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public boolean checkLogin(String login) {
        return userDao.checkLogin(login);
    }
}
