package oince.projecthd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import oince.projecthd.controller.dto.CommentCreationDto;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer boardId;
    private Integer memberId;
    private Integer parentComment;
    private String content;
    private LocalDateTime date;

    public Comment(CommentCreationDto dto, Integer memberId) {
        this.boardId = dto.getBoardId();
        this.parentComment = dto.getParentComment();
        this.content = dto.getContent();
        this.memberId = memberId;
        this.date = LocalDateTime.now().withNano(0);
    }
}
