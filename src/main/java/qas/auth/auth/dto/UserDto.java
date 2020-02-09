package qas.auth.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jooq.demo.db.tables.records.UsersRecord;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String login;
    private String password;
    private String token;
    private Status status;
    @ToString.Exclude
    private List<RoleDto> roles;

    public UserDto(UsersRecord usersRecord) {
        this.id = usersRecord.getId();
        this.login = usersRecord.getLogin();
        this.password = usersRecord.getPassword();
        this.token = usersRecord.getToken();
        setStatus(usersRecord.getStatus());
    }

    private void setStatus(String status) {
        this.status = Status.valueOf(status.toUpperCase());
    }
}
