package oince.projecthd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> postLogin(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {

        Integer memberId = memberService.login(loginDto.getLoginId(), loginDto.getPassword());
        if (memberId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            HttpSession session = request.getSession();
            //response.addCookie(new Cookie("memberId", memberId.toString()));
            session.setAttribute("loginMember", memberId);
            log.info("login member={}", memberId);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> postLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Integer memberId = (Integer) session.getAttribute("loginMember");
            session.invalidate();
            log.info("logout member={}", memberId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/nickname/{memberId}")
    public ResponseEntity<?> getNickname(@PathVariable int memberId) {
        Member member = memberService.findById(memberId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member.getName());
    }
}
