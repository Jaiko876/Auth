package qas.auth.auth.dto.transferObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForDBc {
    private Integer id_user;
    private String fio;
    private String login;
    private String password;
    private String email;
    private Integer telegram_chat_id;
}
