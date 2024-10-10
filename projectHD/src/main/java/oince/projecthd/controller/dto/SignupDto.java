package oince.projecthd.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {
    @NotBlank
    @Size(min = 5, max = 30)
    private String loginId;
    @NotBlank
    @Size(min = 5, max = 30)
    private String password;
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}
