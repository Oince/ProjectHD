package oince.projecthd.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreationDto {

    @NotNull
    private Integer boardId;

    private Integer parentComment;

    @NotBlank
    @Size(max = 500)
    private String content;
}
