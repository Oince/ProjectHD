package oince.projecthd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank
    @Size(min = 5, max = 30)
    private String loginId;

    @NotBlank
    @Size(min = 5, max = 30)
    private String password;

}
