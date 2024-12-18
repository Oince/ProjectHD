package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.annotation.LoginCheck;
import oince.projecthd.controller.dto.CommentCreationDto;
import oince.projecthd.controller.dto.CommentDto;
import oince.projecthd.domain.Comment;
import oince.projecthd.service.CommentService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<CommentDto>> getComments(@RequestParam int boardId) {
        List<CommentDto> comments = commentService.getComments(boardId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable(value = "commentId") int commentId) {
        CommentDto comment = commentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    @LoginCheck
    public ResponseEntity<?> postComments(@Valid @RequestBody CommentCreationDto commentCreationDto,
                                          @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        int commentId = commentService.addComment(commentCreationDto, memberId);
        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }

    @DeleteMapping("/{commentId}")
    @LoginCheck
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Integer commentId,
                                           @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        commentService.deleteComment(memberId, commentId);

        return ResponseEntity.ok().build();
    }
}
