package oince.projecthd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
    private Integer memberId;
    private String loginId;
    private String password;
    private String name;
}