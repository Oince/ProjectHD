package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.CommentCreationDto;
import oince.projecthd.controller.dto.CommentDto;
import oince.projecthd.domain.Comment;
import oince.projecthd.domain.Member;
import oince.projecthd.exception.NotFoundException;
import oince.projecthd.mapper.CommentMapper;
import oince.projecthd.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentMapper commentMapper;
    private final MemberMapper memberMapper;

    public List<CommentDto> getComments(int boardId) {

        List<Comment> commentList = commentMapper.findByBoardId(boardId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            String name = memberMapper.findById(comment.getMemberId()).getName();
            commentDtoList.add(new CommentDto(comment, name));
        }
        return commentDtoList;
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

    public CommentDto getComment(int commentId) {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            throw new NotFoundException("not exist comment=" + commentId);
        }
        String name = memberMapper.findById(comment.getMemberId()).getName();
        return new CommentDto(comment, name);
    }
}
