package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationReq;
import oince.projecthd.controller.dto.BoardRes;
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
    public ResponseEntity<?> postBoards(@Valid @RequestBody BoardCreationReq boardCreationReq,
                                       @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Board board = new Board(boardCreationReq, loginMember.getMemberId());
        boardService.addBoard(board);

        return ResponseEntity.created(URI.create("/boards/" + board.getBoardId())).build();

    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardRes> getBoard(@PathVariable Integer boardId) {
        Board board = boardService.getBoard(boardId);
        if(board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Member writer = memberService.findById(board.getMemberId());
        int commentCount = boardService.getCountOfComment(boardId);

        BoardRes res = new BoardRes(board, writer.getName(), commentCount);
        return ResponseEntity.ok(res);
    }
}
