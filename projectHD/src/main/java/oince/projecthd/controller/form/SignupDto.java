package oince.projecthd.controller.form;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    @NotBlank
    @Size(min = 5, max = 30)
    private String loginId;
    @NotBlank
    @Size(min = 5, max = 20)
    private String password;
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}
