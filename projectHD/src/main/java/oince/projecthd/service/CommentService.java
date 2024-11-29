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
            log.info("parentComment[{}] not exist", comment.getCommentId());
            return 400;
        }

        commentMapper.addNewComment(comment);
        log.info("comment[{}] created", comment.getCommentId());
        return comment.getCommentId();
    }

    @Transactional
    public int deleteComment(int memberId, int commentId) {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            log.info("comment[{}] not exist", commentId);
            return 400;
        }
        if (comment.getMemberId() != memberId) {
            log.info("member[{}] don't have permission to comment[{}]", memberId, commentId);
            return 403;
        }

        log.info("comment[{}] deleted", commentId);
        commentMapper.deleteComment(commentId);
        return 200;
    }

    public Comment getComment(int commentId) {
        return commentMapper.findById(commentId);
    }
}
