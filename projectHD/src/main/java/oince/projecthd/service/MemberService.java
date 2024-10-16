package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.dto.SignupDto;
import oince.projecthd.domain.Member;
import oince.projecthd.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public String signup(SignupDto signupDto) {

        Member newMember = new Member(null, signupDto.getLoginId(), signupDto.getPassword(), signupDto.getName());

        Member findMember = memberMapper.findByLoginId(newMember.getLoginId());
        if (findMember != null) {
            return "duplicate";
        }

        memberMapper.addNewMember(newMember);
        log.info("new member={}", newMember);

        return "ok";
    }

    public Integer login(String loginId, String password) {
        Member member = memberMapper.findByLoginId(loginId);
        if (member == null || !member.getPassword().equals(password)) {
            return null;
        }
        return member.getMemberId();
    }

    public Member findById(int id) {
        return memberMapper.findById(id);
    }

    public Member findByLoginId(String loginId) {
        return memberMapper.findByLoginId(loginId);
    }

}
