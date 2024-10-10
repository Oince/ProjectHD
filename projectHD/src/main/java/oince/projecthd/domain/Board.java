package oince.projecthd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import oince.projecthd.controller.dto.BoardCreationDto;

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

    public Board(BoardCreationDto boardCreationDto, Integer memberId) {
        this.boardId = null;
        this.memberId = memberId;
        this.title = boardCreationDto.getTitle();
        this.date = LocalDateTime.now().withNano(0);
        this.views = 0;
        this.thumbsup = 0;
        this.url = boardCreationDto.getUrl();
        this.itemName = boardCreationDto.getItemName();
        this.price = boardCreationDto.getPrice();
        this.deliveryPrice = boardCreationDto.getDeliveryPrice();
        this.category = boardCreationDto.getCategory();
        this.content = boardCreationDto.getContent();
    }

}
