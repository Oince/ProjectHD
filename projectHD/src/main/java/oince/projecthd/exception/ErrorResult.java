package oince.projecthd.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResult {
    private String method;
    private String url;
    private Integer code;
    private String message;
}
