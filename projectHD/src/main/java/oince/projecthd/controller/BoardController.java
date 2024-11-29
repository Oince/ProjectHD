package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.controller.dto.BoardDto;
import oince.projecthd.controller.dto.BoardHomeDto;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.Member;
import oince.projecthd.service.BoardService;
import oince.projecthd.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> postBoards(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                        @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        if (memberId == null) {
            log.info("not login");
            return ResponseEntity.status(401).build();
        }

        int boardId = boardService.addBoard(boardCreationDto, memberId);

        return ResponseEntity.created(URI.create("/boards/" + boardId)).build();

    }

    @GetMapping
    public ResponseEntity<List<BoardHomeDto>> getBoards() {
        List<BoardHomeDto> boards = boardService.getBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable(value = "boardId") int boardId) {

        BoardDto res = boardService.getBoard(boardId);
        if (res == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> putBoard(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                      @PathVariable(value = "boardId") Integer boardId,
                                      @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        Board board = boardService.findById(boardId);
        if (board == null) {
            log.info("board[{}] not exist", boardId);
            return ResponseEntity.badRequest().build();
        }
        if (memberId == null) {
            log.info("not login");
            return ResponseEntity.status(401).build();
        }
        if (board.getMemberId() != memberId) {
            log.info("member[{}] don't have update permission board[{}]", memberId, boardId);
            return ResponseEntity.status(403).build();
        }

        boardService.updateBoard(boardCreationDto, boardId);
        return ResponseEntity.created(URI.create("/boards/" + boardId)).build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable(value = "boardId") Integer boardId,
                                         @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        Board board = boardService.findById(boardId);
        if (board == null) {
            log.info("board[{}] not exist", boardId);
            return ResponseEntity.badRequest().build();
        }
        if (memberId == null) {
            log.info("not login");
            return ResponseEntity.status(401).build();
        }
        if (board.getMemberId() != memberId) {
            log.info("member[{}] don't have delete permission board[{}]", memberId, boardId);
            return ResponseEntity.status(403).build();
        }

        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardId}/thumbsup")
    public ResponseEntity<?> postThumbsup(@PathVariable(value = "boardId") Integer boardId,
                                          @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        if (memberId == null) {
            log.info("not login");
            return ResponseEntity.status(401).build();
        } else if (boardService.findById(boardId) == null) {
            log.info("board[{}] not exist", boardId);
            return ResponseEntity.status(400).build();
        }

        int code = boardService.thumbsUp(boardId, memberId);

        if (code == 400) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
