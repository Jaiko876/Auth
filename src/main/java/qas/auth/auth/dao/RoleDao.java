package qas.auth.auth.dao;

import org.jooq.DSLContext;
import org.jooq.demo.db.tables.Roles;
import org.jooq.demo.db.tables.records.RolesRecord;
import org.springframework.stereotype.Component;
import qas.auth.auth.dao.iDao.IRoleDao;
import qas.auth.auth.dto.RoleDto;

@Component
public class RoleDao implements IRoleDao {
    private static final String USER = "user";
    private DSLContext dslContext;

    public RoleDao(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public RoleDto findByName(String name) {
        RolesRecord roleRecord = dslContext.selectFrom(Roles.ROLES)
                .where(Roles.ROLES.NAME.eq(name)).fetchAny();
        return new RoleDto(roleRecord);
    }

    @Override
    public RoleDto findById(long id) {
        RolesRecord roleRecord = dslContext.selectFrom(Roles.ROLES)
                .where(Roles.ROLES.ID.eq(id)).fetchAny();
        return new RoleDto(roleRecord);
    }

    @Override
    public RoleDto getDefaultRole() {
        RolesRecord record = dslContext.selectFrom(Roles.ROLES)
                .where(Roles.ROLES.NAME.eq(USER)).fetchAny();
        return new RoleDto(record);
    }
}
