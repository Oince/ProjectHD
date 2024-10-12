package oince.projecthd.domain;

import lombok.*;
import oince.projecthd.controller.dto.BoardCreationReq;

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

    public Board(BoardCreationReq boardCreationReq, Integer memberId) {
        this.boardId = null;
        this.memberId = memberId;
        this.title = boardCreationReq.getTitle();
        this.date = LocalDateTime.now().withNano(0);
        this.views = 0;
        this.thumbsup = 0;
        this.url = boardCreationReq.getUrl();
        this.itemName = boardCreationReq.getItemName();
        this.price = boardCreationReq.getPrice();
        this.deliveryPrice = boardCreationReq.getDeliveryPrice();
        this.category = boardCreationReq.getCategory();
        this.content = boardCreationReq.getContent();
    }

}
