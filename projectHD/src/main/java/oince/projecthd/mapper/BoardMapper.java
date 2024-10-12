package oince.projecthd.mapper;

import oince.projecthd.domain.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    void addNewBoard(Board board);

    Board findById(int boardId);

    void viewsCount(int boardId);
}
