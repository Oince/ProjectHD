package oince.projecthd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Integer boardId;
    private Integer memberId;
    private String title;
    private LocalDateTime date;
    private Integer views;
    private Integer thumbsup;
    private String url;
    private String itemName;
    private Integer price;
    private Integer deliveryPrice;
    private CategoryName category;
    private String content;

}
