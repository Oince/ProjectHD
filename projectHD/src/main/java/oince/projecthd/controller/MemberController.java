package oince.projecthd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.LoginDto;
import oince.projecthd.controller.dto.SignupDto;
import oince.projecthd.domain.Member;
import oince.projecthd.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        Member newMember = new Member(null, signupDto.getLoginId(), signupDto.getPassword(), signupDto.getName());
        String code = memberService.signup(newMember);

        if (code.equals("duplicate")) {
            return ResponseEntity.badRequest().build();
        }
        log.info("new member={}", newMember);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {

        Member member = memberService.login(loginDto.getLoginId(), loginDto.getPassword());
        if (member == null) {
            return ResponseEntity.badRequest().build();
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member);
            log.info("login member={}", member);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> postLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute("loginMember");

        if (session != null) {
            session.invalidate();
            log.info("logout member={}", member);
        }
        return ResponseEntity.ok().build();
    }
}
