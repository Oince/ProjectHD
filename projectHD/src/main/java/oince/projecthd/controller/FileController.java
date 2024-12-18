package oince.projecthd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.annotation.LoginCheck;
import oince.projecthd.controller.dto.FileDto;
import oince.projecthd.domain.Board;
import oince.projecthd.domain.UploadFile;
import oince.projecthd.exception.ErrorResult;
import oince.projecthd.exception.NotFoundException;
import oince.projecthd.service.BoardService;
import oince.projecthd.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @ExceptionHandler
    public ResponseEntity<ErrorResult> notFoundEx(NotFoundException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        ErrorResult errorResult = new ErrorResult(method, requestURI, 404, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws MalformedURLException {
        UploadFile file = fileService.getFile(fileName);
        String path = fileService.getFullPth(file.getStoreFileName());
        UrlResource resource = new UrlResource("file:" + path);

        String encode = UriUtils.encode(file.getUploadFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encode + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

    @PostMapping("/files")
    @LoginCheck
    public ResponseEntity<?> postFile(@ModelAttribute @Valid FileDto fileDto,
                                      @SessionAttribute(name = "loginMember", required = false) Integer memberId) {

        MultipartFile file = fileDto.getFile();

        if (file.isEmpty()) {
            log.info("file is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UploadFile uploadFile = fileService.saveFile(file);
        return ResponseEntity.created(URI.create("/" + uploadFile.getStoreFileName())).build();
    }
}
