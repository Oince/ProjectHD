package oince.projecthd.domain;

import lombok.*;
import oince.projecthd.controller.dto.BoardCreationDto;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Integer boardId;
    private Integer memberId;

    @Setter
    private String title;

    private LocalDateTime date;

    @Setter
    private Integer views;

    @Setter
    private Integer thumbsup;

    @Setter
    private String url;

    @Setter
    private String itemName;

    @Setter
    private Integer price;

    @Setter
    private Integer deliveryPrice;

    @Setter
    private CategoryName category;

    @Setter
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
