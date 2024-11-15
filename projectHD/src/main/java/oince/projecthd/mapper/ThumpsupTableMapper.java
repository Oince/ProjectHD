package oince.projecthd.mapper;

import oince.projecthd.domain.ThumbsupTable;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThumpsupTableMapper {

    ThumbsupTable findById(int boardId, int memberId);

    void addNewThumbsup(ThumbsupTable thumbsup);
}
