package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.CommentCreationDto;
import oince.projecthd.domain.Comment;
import oince.projecthd.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentMapper commentMapper;

    public List<Comment> getComments(int boardId) {
        return commentMapper.findByBoardId(boardId);
    }

    @Transactional
    public int addComment(CommentCreationDto commentCreationDto, int memberId) {
        Comment comment = new Comment(commentCreationDto, memberId);
        if (comment.getParentComment() != null && commentMapper.findById(comment.getParentComment()) == null) {
            return 400;
        }

        commentMapper.addNewComment(comment);
        log.info("comment creation={}", comment.getCommentId());
        return comment.getCommentId();
    }

    @Transactional
    public int deleteComment(int memberId, int commentId) {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            return 400;
        }
        if (comment.getMemberId() != memberId) {
            return 403;
        }
        commentMapper.deleteComment(commentId);
        return 200;
    }

    public Comment getComment(int commentId) {
        return commentMapper.findById(commentId);
    }
}
