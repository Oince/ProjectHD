package oince.projecthd.service;

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
        Member member = new Member(null, "test12", "test12", "name");

        memberService.signup(member);
        Member findMember = memberService.findById(member.getMemberId());

        assertThat(findMember).isNotNull();
        assertThat(member.getMemberId()).isEqualTo(findMember.getMemberId());

    }

    @Test
    @DisplayName("회원가입 실패: 중복 아이디")
    void signupFail() {
        Member member1 = new Member(null, "test123", "test12", "name");
        Member member2 = new Member(null, "test123", "test123", "name1");

        memberService.signup(member1);
        String code = memberService.signup(member2);

        assertThat(code).isEqualTo("duplicate");
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        Member member = memberService.login("test1", "test1");

        assertThat(member).isNotNull();
        assertThat(member.getLoginId()).isEqualTo("test1");
        assertThat(member.getPassword()).isEqualTo("test1");
        assertThat(member.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("로그인 실패: 아이디 없음")
    void loginFail1() {
        Member member = memberService.login("test123", "test1");

        assertThat(member).isNull();
    }

    @Test
    @DisplayName("로그인 실패: 비밀번호 틀림")
    void loginFail() {
        Member member = memberService.login("test1", "test1312");

        assertThat(member).isNull();
    }
}