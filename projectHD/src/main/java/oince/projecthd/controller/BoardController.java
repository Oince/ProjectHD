package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.controller.dto.BoardDto;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.Member;
import oince.projecthd.service.BoardService;
import oince.projecthd.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> postBoards(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                       @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Board board = new Board(boardCreationDto, loginMember.getMemberId());
        boardService.addBoard(board);

        return ResponseEntity.created(URI.create("/boards/" + board.getBoardId())).build();

    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable Integer boardId) {
        Board board = boardService.getBoard(boardId);
        if(board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Member writer = memberService.findById(board.getMemberId());
        int numberOfComment = boardService.getNumberOfComment(boardId);

        BoardDto res = new BoardDto(board, writer.getName(), numberOfComment);
        return ResponseEntity.ok(res);
    }
}
