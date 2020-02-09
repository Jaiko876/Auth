package qas.auth.auth.dto;

import lombok.Data;

@Data
public class ValidatedToken {
    private boolean validated;
    private String message;
}
