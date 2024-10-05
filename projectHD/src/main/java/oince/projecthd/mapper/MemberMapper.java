package oince.projecthd.mapper;

import oince.projecthd.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member findById(int id);

    Member findByLoginId(String loginId);

    void addNewMember(Member member);
}
