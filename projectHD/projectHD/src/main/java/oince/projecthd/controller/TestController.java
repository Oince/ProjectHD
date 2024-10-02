package oince.projecthd.controller;

import lombok.RequiredArgsConstructor;
import oince.projecthd.domain.Member;
import oince.projecthd.mapper.TestMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestMapper testMapper;

    @GetMapping("/test")
    public List<Member> test() {
        return testMapper.testSelect();
    }
}
