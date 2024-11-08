package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.controller.dto.BoardDto;
import oince.projecthd.controller.dto.BoardHomeDto;
import oince.projecthd.domain.Board;
import oince.projecthd.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardMapper boardMapper;

    @Transactional
    public int addBoard(BoardCreationDto boardCreationDto, int memberId) {
        Board board = new Board(boardCreationDto, memberId);
        boardMapper.addNewBoard(board);
        log.info("board creation={}", board.getBoardId());
        return board.getBoardId();
    }

    @Transactional
    public BoardDto getBoard (int boardId) {

        boardMapper.increaseViews(boardId);

        Board board = boardMapper.findById(boardId);
        if (board == null) {
            return null;
        }

        //임시로 0넣음 추후에 수정 필요
        return new BoardDto(board, 0);
    }

    public List<BoardHomeDto> getBoards() {
        List<Board> boards = boardMapper.findAll();
        List<BoardHomeDto> res = new LinkedList<>();
        for (Board board : boards) {
            //여기도 임시로 0넣음
            res.add(new BoardHomeDto(board, 0));
        }

        return res;
    }

    @Transactional
    public void updateBoard(BoardCreationDto boardCreationDto, int boardId) {
        Board board = boardMapper.findById(boardId);

        board.setTitle(boardCreationDto.getTitle());
        board.setUrl(boardCreationDto.getUrl());
        board.setItemName(boardCreationDto.getItemName());
        board.setPrice(boardCreationDto.getPrice());
        board.setDeliveryPrice(boardCreationDto.getDeliveryPrice());
        board.setCategory(boardCreationDto.getCategory());
        board.setContent(boardCreationDto.getContent());

        boardMapper.updateBoard(board);
        log.info("board update={}", board.getBoardId());
    }

    @Transactional
    public void deleteBoard(int boardId) {
        boardMapper.deleteBoard(boardId);
        log.info("board delete={}", boardId);
    }

    public Board findById(int boardId) {
        return boardMapper.findById(boardId);
    }



}
