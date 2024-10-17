package oince.projecthd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oince.projecthd.domain.CategoryName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreationDto {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 200)
    private String url;

    @NotBlank
    @Size(max = 100)
    private String itemName;

    @NotNull
    private int price;

    @NotNull
    private int deliveryPrice;

    @NotNull
    private CategoryName category;

    @NotBlank
    @Size(max = 1000)
    private String content;
}
