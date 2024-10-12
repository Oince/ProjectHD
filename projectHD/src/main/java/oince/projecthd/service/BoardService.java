package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.domain.Board;
import oince.projecthd.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardMapper boardMapper;

    @Transactional
    public void addBoard(Board board) {
        boardMapper.addNewBoard(board);
    }

    @Transactional
    public Board getBoard (int boardId) {
        Board board = boardMapper.findById(boardId);
        board.setViews(board.getViews() + 1);
        boardMapper.viewsCount(boardId);

        return board;
    }

    public List<Board> getBoards() {
        return null;
    }

    public int getCountOfComment(int boardId) {
        return 0;
    }


}
