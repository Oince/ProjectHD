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
    public String signup(String loginId, String password, String name) {

        if (isDuplicateLoginId(loginId)) {
            return "duplicate";
        }

        Member member = new Member(null, loginId, password, name);
        memberMapper.addNewMember(member);
        log.info("member.memberId={}", member.getMemberId());
        return "ok";
    }

    private boolean isDuplicateLoginId(String loginId) {
        Member findMember = memberMapper.findByLoginId(loginId);
        return findMember != null;
    }
}
