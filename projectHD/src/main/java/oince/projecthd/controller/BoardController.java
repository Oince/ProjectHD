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
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> postBoards(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                        @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> putBoard(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                      @PathVariable(value = "boardId") Integer boardId,
                                      @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        Board board = boardService.findById(boardId);
        if (board == null) {
            return ResponseEntity.badRequest().build();
        }
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (board.getMemberId() != memberId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boardService.updateBoard(boardCreationDto, boardId);
        return ResponseEntity.created(URI.create("/boards/" + boardId)).build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable(value = "boardId") Integer boardId,
                                         @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        Board board = boardService.findById(boardId);
        if (board == null) {
            return ResponseEntity.badRequest().build();
        }
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (board.getMemberId() != memberId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
}
