package simpleBank.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String cpf;
    private String password;
}
