package qas.auth.auth.dao;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.demo.db.tables.Users;
import org.jooq.demo.db.tables.records.UsersRecord;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import qas.auth.auth.dao.iDao.IUserDao;
import qas.auth.auth.dto.RoleDto;
import qas.auth.auth.dto.UserDto;

import java.util.List;

@Component
public class UserDao implements IUserDao {
    private final DSLContext dslContext;
    private final UserRoleDao userRoleDao;

    public UserDao(DSLContext dslContext, UserRoleDao userRoleDao) {
        this.dslContext = dslContext;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserDto findByUserName(String name) {
        UsersRecord usersRecord = dslContext.selectFrom(Users.USERS)
                .where(Users.USERS.LOGIN.eq(name)).fetchAny();
        List<RoleDto> usersRoles = userRoleDao.getUsersRoles(usersRecord.getId());
        UserDto userDto = new UserDto(usersRecord);
        userDto.setRoles(usersRoles);
        return userDto;
    }

    @Override
    public void updateToken(UserDto userDto, String token) {
        userDto.setToken(token);
        dslContext.update(Users.USERS).set(Users.USERS.TOKEN, userDto.getToken())
                .where(Users.USERS.ID.eq(userDto.getId())).execute();
    }

    @Override
    public void addUser(UserDto userDto) {
        dslContext.insertInto(Users.USERS, Users.USERS.LOGIN, Users.USERS.PASSWORD, Users.USERS.STATUS)
                .values(userDto.getLogin(), userDto.getPassword(), "ACTIVE").execute();
    }

    @Override
    public UserDto getUserById(long id) {
        UsersRecord usersRecord = dslContext.selectFrom(Users.USERS).where(Users.USERS.ID.eq(id)).fetchAny();
        return new UserDto(usersRecord);
    }

    @Override
    public boolean checkLogin(String login) {
        Record1<String> stringRecord1 = dslContext.select(Users.USERS.LOGIN).from(Users.USERS).where(Users.USERS.LOGIN.eq(login)).fetchAny();
        if (stringRecord1 == null) {
            throw new BadCredentialsException("user doesn't exist");
        }
        return true;

    }
}
