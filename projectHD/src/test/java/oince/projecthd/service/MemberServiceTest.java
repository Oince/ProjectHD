package oince.projecthd.service;

import oince.projecthd.controller.dto.SignupDto;
import oince.projecthd.domain.Member;
import oince.projecthd.mapper.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void signup() {
        SignupDto member = new SignupDto("test12", "test12", "name");

        String code = memberService.signup(member);
        Member findMember = memberService.findByLoginId(member.getLoginId());

        assertThat(code).isEqualTo("ok");
        assertThat(findMember).isNotNull();
        assertThat(member.getLoginId()).isEqualTo(findMember.getLoginId());

    }

    @Test
    @DisplayName("회원가입 실패: 중복 아이디")
    void signupFail() {
        SignupDto member1 = new SignupDto("test123", "test12", "name");
        SignupDto member2 = new SignupDto("test123", "test123", "name1");

        memberService.signup(member1);
        String code = memberService.signup(member2);

        assertThat(code).isEqualTo("duplicate");
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        Integer memberId = memberService.login("test1", "test1");
        Member loginMember = memberService.findById(memberId);

        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getMemberId()).isEqualTo(memberId);
        assertThat(loginMember.getLoginId()).isEqualTo("test1");
        assertThat(loginMember.getPassword()).isEqualTo("test1");
    }

    @Test
    @DisplayName("로그인 실패: 아이디 없음")
    void loginFail1() {
        Integer memberId = memberService.login("test123", "test1");

        assertThat(memberId).isNull();
    }

    @Test
    @DisplayName("로그인 실패: 비밀번호 틀림")
    void loginFail() {
        Integer memberId = memberService.login("test1", "test1312");

        assertThat(memberId).isNull();
    }
}