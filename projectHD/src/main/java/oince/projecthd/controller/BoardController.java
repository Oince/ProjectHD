package oince.projecthd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.annotation.LoginCheck;
import oince.projecthd.controller.dto.BoardCreationDto;
import oince.projecthd.controller.dto.BoardDto;
import oince.projecthd.controller.dto.BoardHomeDto;
import oince.projecthd.domain.Board;
import oince.projecthd.exception.NotFoundException;
import oince.projecthd.exception.PermissionException;
import oince.projecthd.service.BoardService;
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
    @LoginCheck
    public ResponseEntity<?> postBoards(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                        @SessionAttribute(name = "loginMember", required = false) Integer memberId) {


        int boardId = boardService.addBoard(boardCreationDto, memberId);

        return ResponseEntity.created(URI.create("/boards/" + boardId)).build();

    }

    @GetMapping
    public ResponseEntity<List<BoardHomeDto>> getBoards(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        if(page <= 0)
            page = 1;

        List<BoardHomeDto> boards = boardService.getBoards(page);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable(value = "boardId") int boardId) {

        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @PutMapping("/{boardId}")
    @LoginCheck
    public ResponseEntity<?> putBoard(@Valid @RequestBody BoardCreationDto boardCreationDto,
                                      @PathVariable(value = "boardId") Integer boardId,
                                      @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        Board board = boardService.findById(boardId);
        if (board == null) {
            throw new NotFoundException("존재하지 않는 게시글입니다.");
        }
        if (board.getMemberId() != memberId) {
            log.info("member[{}] don't have update permission board[{}]", memberId, boardId);
            throw new PermissionException("수정 권한이 없습니다.");
        }

        boardService.updateBoard(boardCreationDto, boardId);
        return ResponseEntity.created(URI.create("/boards/" + boardId)).build();
    }

    @DeleteMapping("/{boardId}")
    @LoginCheck
    public ResponseEntity<?> deleteBoard(@PathVariable(value = "boardId") Integer boardId,
                                         @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        Board board = boardService.findById(boardId);
        if (board == null) {
            log.info("board[{}] not exist", boardId);
            throw new NotFoundException("존재하지 않는 게시글입니다.");
        }
        if (board.getMemberId() != memberId) {
            log.info("member[{}] don't have delete permission board[{}]", memberId, boardId);
            throw new PermissionException("삭제 권한이 없습니다.");
        }

        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardId}/thumbsup")
    @LoginCheck
    public ResponseEntity<?> postThumbsup(@PathVariable(value = "boardId") Integer boardId,
                                          @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        if (boardService.findById(boardId) == null) {
            log.info("board[{}] not exist", boardId);
            throw new NotFoundException("존재하지 않는 게시글입니다.");
        }

        boardService.thumbsUp(boardId, memberId);

        return ResponseEntity.ok().build();
    }
}
