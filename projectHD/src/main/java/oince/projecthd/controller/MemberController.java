package oince.projecthd.controller;

import jakarta.servlet.http.Cookie;
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
import oince.projecthd.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> postSignup(@Valid @RequestBody SignupDto signupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        String code = memberService.signup(signupDto);

        if (code.equals("duplicate")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<MemberIdDto> postLogin(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {

        Member member = memberService.login(loginDto.getLoginId(), loginDto.getPassword());
        if (member == null) {
            return ResponseEntity.badRequest().build();
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member.getMemberId());
            return ResponseEntity.ok(new MemberIdDto(member.getMemberId(), member.getName()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> postLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Integer memberId = (Integer) session.getAttribute("loginMember");
            session.invalidate();
            log.info("member[{}] logout", memberId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
