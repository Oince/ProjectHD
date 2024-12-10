package oince.projecthd.mapper;

import oince.projecthd.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    Comment findById(int commentId);

    List<Comment> findByBoardId(int boardId);

    void addNewComment(Comment comment);

    void deleteComment(int commentId);

    int numberOfComment(int boardId);
}
