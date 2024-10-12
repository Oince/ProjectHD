package oince.projecthd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginReq {

    @NotBlank
    @Size(min = 5, max = 30)
    private String loginId;

    @NotBlank
    @Size(min = 5, max = 30)
    private String password;

}
