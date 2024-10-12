package oince.projecthd.service;

import oince.projecthd.controller.dto.BoardCreationReq;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.CategoryName;
import oince.projecthd.mapper.BoardMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardMapper boardMapper;

    @Test
    @DisplayName("게시글 생성")
    void addBoard() {
        int memberId = 1;
        BoardCreationReq boardCreationReq = new BoardCreationReq("test", "https://test.com", "item", 1000, 1000, CategoryName.FOOD, "test content");
        Board board = new Board(boardCreationReq, memberId);

        boardService.addBoard(board);

        Board findBoard = boardMapper.findById(board.getBoardId());

        assertThat(findBoard).usingRecursiveComparison().isEqualTo(board);
    }

}