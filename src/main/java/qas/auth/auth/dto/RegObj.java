package qas.auth.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegObj {
    private String username;
    private String password;
    private String fio;
    private String email;
    private int telegramChatId;
}
