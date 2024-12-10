package oince.projecthd.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oince.projecthd.domain.Comment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer commentId;
    private Integer boardId;
    private Integer memberId;
    private String name;
    private Integer parentComment;
    private String content;
    private LocalDateTime date;

    public CommentDto(Comment comment, String name) {
        this.commentId = comment.getCommentId();
        this.boardId = comment.getBoardId();
        this.memberId = comment.getMemberId();
        this.name = name;
        this.parentComment = comment.getParentComment();
        this.content = comment.getContent();
        this.date = comment.getDate();
    }
}
