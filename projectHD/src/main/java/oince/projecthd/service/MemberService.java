package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String signup(Member newMember) {

        Member findMember = memberMapper.findByLoginId(newMember.getLoginId());
        if (findMember != null) {
            return "duplicate";
        }

        memberMapper.addNewMember(newMember);

        return "ok";
    }

    public Member login(String loginId, String password) {
        Member member = memberMapper.findByLoginId(loginId);
        if (member == null || !member.getPassword().equals(password)) {
            return null;
        }
        return member;
    }

    public Member findById(int id) {
        return memberMapper.findById(id);
    }

    public Member findByLoginId(String loginId) {
        return memberMapper.findByLoginId(loginId);
    }

}
