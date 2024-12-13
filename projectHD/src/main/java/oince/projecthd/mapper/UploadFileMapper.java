package oince.projecthd.mapper;

import oince.projecthd.domain.UploadFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadFileMapper {

    void insertFile(UploadFile file);

    UploadFile selectFile(String fileName);

}
