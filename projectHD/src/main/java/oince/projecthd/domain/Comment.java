package oince.projecthd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
