package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.controller.dto.BoardDto;
import oince.projecthd.controller.dto.BoardHomeDto;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.Comment;
import oince.projecthd.domain.ThumbsupTable;
import oince.projecthd.mapper.BoardMapper;
import oince.projecthd.mapper.CommentMapper;
import oince.projecthd.mapper.MemberMapper;
import oince.projecthd.mapper.ThumpsupTableMapper;
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
    private final MemberMapper memberMapper;
    private final ThumpsupTableMapper thumpsupTableMapper;
    private final CommentMapper commentMapper;

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

        int numberOfComment = commentMapper.numberOfComment(boardId);
        return new BoardDto(board, numberOfComment);
    }

    public List<BoardHomeDto> getBoards() {
        List<Board> boards = boardMapper.findAll();
        List<BoardHomeDto> res = new LinkedList<>();
        for (Board board : boards) {
            res.add(new BoardHomeDto(board, commentMapper.numberOfComment(board.getBoardId())));
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

    @Transactional
    public int thumbsUp(int boardId, int memberId) {
        ThumbsupTable byId = thumpsupTableMapper.findById(boardId, memberId);
        if (byId != null) {
            return 400;
        }

        ThumbsupTable thumbsupTable = new ThumbsupTable(boardId, memberId);
        thumpsupTableMapper.addNewThumbsup(thumbsupTable);
        return 200;
    }

    public Board findById(int boardId) {
        return boardMapper.findById(boardId);
    }





}