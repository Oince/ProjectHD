package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.controller.dto.BoardDto;
import oince.projecthd.controller.dto.BoardHomeDto;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.Comment;
import oince.projecthd.domain.Member;
import oince.projecthd.domain.ThumbsupTable;
import oince.projecthd.mapper.BoardMapper;
import oince.projecthd.mapper.CommentMapper;
import oince.projecthd.mapper.MemberMapper;
import oince.projecthd.mapper.ThumpsupTableMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        log.info("board[{}] created", board.getBoardId());
        return board.getBoardId();
    }

    @Transactional
    public BoardDto getBoard (int boardId) {

        boardMapper.increaseViews(boardId);

        Board board = boardMapper.findById(boardId);
        if (board == null) {
            log.info("board[{}] not exist", boardId);
            return null;
        }
        Member member = memberMapper.findById(board.getMemberId());

        int numberOfComment = commentMapper.numberOfComment(boardId);
        return new BoardDto(board, member.getName(), numberOfComment);
    }

    public List<BoardHomeDto> getBoards() {
        List<Board> boards = boardMapper.findAll();
        List<BoardHomeDto> res = new ArrayList<>();
        for (Board board : boards) {
            String name = memberMapper.findById(board.getMemberId()).getName();
            res.add(new BoardHomeDto(board, name, commentMapper.numberOfComment(board.getBoardId())));
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
        log.info("board[{}] updated", board.getBoardId());
    }

    @Transactional
    public void deleteBoard(int boardId) {
        boardMapper.deleteBoard(boardId);
        log.info("board[{}] deleted", boardId);
    }

    @Transactional
    public int thumbsUp(int boardId, int memberId) {
        ThumbsupTable byId = thumpsupTableMapper.findById(boardId, memberId);
        if (byId != null) {
            log.info("member[{}] already thumbsup board[{}]", memberId, boardId);
            return 400;
        }

        boardMapper.increaseThumbsup(boardId);
        ThumbsupTable thumbsupTable = new ThumbsupTable(boardId, memberId);
        thumpsupTableMapper.addNewThumbsup(thumbsupTable);
        log.info("member[{}] completed thumbsup board[{}]", memberId, boardId);
        return 200;
    }

    public Board findById(int boardId) {
        return boardMapper.findById(boardId);
    }
}
