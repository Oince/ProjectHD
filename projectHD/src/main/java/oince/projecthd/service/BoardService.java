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
        boardMapper.increaseViews(boardId);

        return boardMapper.findById(boardId);
    }

    public List<Board> getBoards() {
        return null;
    }

    public Integer getNumberOfComment(int boardId) {
        return 0;
    }


}
