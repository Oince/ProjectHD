package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.CommentCreationDto;
import oince.projecthd.domain.Comment;
import oince.projecthd.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@RequestParam int boardId) {
        List<Comment> comments = commentService.getComments(boardId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable(value = "commentId") int commentId) {
        Comment comment = commentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<?> postComments(@Valid @RequestBody CommentCreationDto commentCreationDto,
                                          @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        if (memberId == null) {
            return ResponseEntity.status(401).build();
        }

        int code = commentService.addComment(commentCreationDto, memberId);

        if (code == 400) {
            return ResponseEntity.status(400).build();
        }
        else {
            return ResponseEntity.created(URI.create("/comments/" + code)).build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Integer commentId,
                                           @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        if (memberId == null) {
            return ResponseEntity.status(401).build();
        }

        int code = commentService.deleteComment(memberId, commentId);

        if (code == 400) {
            return ResponseEntity.badRequest().build();
        } else if (code == 403) {
            return ResponseEntity.status(403).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
