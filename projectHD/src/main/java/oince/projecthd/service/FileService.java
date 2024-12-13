package oince.projecthd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.domain.UploadFile;
import oince.projecthd.exception.NotFoundException;
import oince.projecthd.mapper.UploadFileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final UploadFileMapper uploadFileMapper;

    @Value("${file.dir}")
    private String fileDir;

    public UploadFile getFile(String fileName) {

        UploadFile uploadFile = uploadFileMapper.selectFile(fileName);
        if (uploadFile == null) {
            throw new NotFoundException("not exist file");
        }
        return uploadFile;
    }

    @Transactional
    public UploadFile saveFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String storeFileName = generateStoreFileName(originalFilename);
        try {
            file.transferTo(new File(fileDir + storeFileName));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 오류");
        }


        UploadFile uploadFile = new UploadFile(storeFileName, originalFilename);
        uploadFileMapper.insertFile(uploadFile);

        return uploadFile;
    }

    public String getFullPth(String path) {
        return fileDir + path;
    }

    private String generateStoreFileName(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
}
