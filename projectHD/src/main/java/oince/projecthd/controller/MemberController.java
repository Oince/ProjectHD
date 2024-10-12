package oince.projecthd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.LoginReq;
import oince.projecthd.controller.dto.SignupReq;
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
    public ResponseEntity<?> postSignup(@Valid @RequestBody SignupReq signupReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Member newMember = new Member(null, signupReq.getLoginId(), signupReq.getPassword(), signupReq.getName());
        String code = memberService.signup(newMember);

        if (code.equals("duplicate")) {
            return ResponseEntity.badRequest().build();
        }
        log.info("new member={}", newMember);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@Valid @RequestBody LoginReq loginReq, HttpServletRequest request) {

        Member member = memberService.login(loginReq.getLoginId(), loginReq.getPassword());
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
