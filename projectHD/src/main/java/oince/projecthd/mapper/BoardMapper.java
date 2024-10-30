package oince.projecthd.mapper;

import oince.projecthd.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void addNewBoard(Board board);

    Board findById(int boardId);

    List<Board> findAll();

    void increaseViews(int boardId);

    void updateBoard(Board board);

    void deleteBoard(int boardId);
}
