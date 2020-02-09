package qas.auth.auth.dao.iDao;

import qas.auth.auth.dto.RoleDto;

import java.util.List;

public interface IUserRoleDao {
    void setUserRole(long userId, long roleId);
    List<RoleDto> getUsersRoles(long userId);
}
