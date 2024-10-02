package oince.projecthd.mapper;

import oince.projecthd.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    List<Member> testSelect();

}
