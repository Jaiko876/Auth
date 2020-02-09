package qas.auth.auth.dao.iDao;

import qas.auth.auth.dto.RoleDto;

public interface IRoleDao {
    RoleDto findByName(String name);
    RoleDto findById(long id);
    RoleDto getDefaultRole();
}
