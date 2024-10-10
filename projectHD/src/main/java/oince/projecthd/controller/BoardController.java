package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.Member;
import oince.projecthd.service.BoardService;
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

    @PostMapping
    public ResponseEntity<?> postBoard(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                       @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Board board = new Board(boardCreationDto, loginMember.getMemberId());
        boardService.addBoard(board);

        return ResponseEntity.created(URI.create("/boards/" + board.getBoardId())).build();

    }
}
