package qas.auth.auth.dto;

import lombok.Data;
import org.jooq.demo.db.tables.records.RolesRecord;

@Data
public class RoleDto {
    private long id;
    private String name;

    public RoleDto(RolesRecord roleRecord) {
        this.id = roleRecord.getId();
        this.name = roleRecord.getName();
    }
}
