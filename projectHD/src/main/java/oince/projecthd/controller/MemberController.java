package oince.projecthd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.LoginDto;
import oince.projecthd.controller.dto.MemberIdDto;
import oince.projecthd.controller.dto.SignupDto;
import oince.projecthd.domain.Member;
import oince.projecthd.exception.NotLoginException;
import oince.projecthd.interceptor.SessionConst;
import oince.projecthd.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> postSignup(@Valid @RequestBody SignupDto signupDto) {
        memberService.signup(signupDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<MemberIdDto> postLogin(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {

        Member member = memberService.login(loginDto.getLoginId(), loginDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getMemberId());
        return ResponseEntity.ok(new MemberIdDto(member.getMemberId(), member.getName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> postLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new NotLoginException("로그인하지 않았습니다.");
        }

        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_MEMBER);
        session.invalidate();
        log.info("member[{}] logout", memberId);
        return ResponseEntity.ok().build();
    }
}
