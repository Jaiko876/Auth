package qas.auth.auth.dao;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.demo.db.tables.UserRoles;
import org.jooq.demo.db.tables.records.UserRolesRecord;
import org.springframework.stereotype.Component;
import qas.auth.auth.dao.iDao.IUserRoleDao;
import qas.auth.auth.dto.RoleDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleDao implements IUserRoleDao {
    private DSLContext dslContext;
    private RoleDao roleDao;

    public UserRoleDao(DSLContext dslContext, RoleDao roleDao) {
        this.dslContext = dslContext;
        this.roleDao = roleDao;
    }

    @Override
    public void setUserRole(long userId, long roleId) {
        dslContext.insertInto(UserRoles.USER_ROLES,
                UserRoles.USER_ROLES.USER_ID, UserRoles.USER_ROLES.ROLE_ID)
                .values(userId, roleId).execute();
    }

    @Override
    public List<RoleDto> getUsersRoles(long userId) {
        List<RoleDto> roleDtos = new ArrayList<>();
        Result<UserRolesRecord> rolesRecords = dslContext.selectFrom(UserRoles.USER_ROLES)
                .where(UserRoles.USER_ROLES.USER_ID.eq(userId)).fetch();
        for (UserRolesRecord roleRecord: rolesRecords) {
            roleDtos.add(roleDao.findById(roleRecord.getRoleId()));
        }
        return roleDtos;
    }
}
