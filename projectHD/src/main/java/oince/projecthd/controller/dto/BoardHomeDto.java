package oince.projecthd.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oince.projecthd.domain.Board;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardHomeDto {

    private int boardId;
    private int memberId;
    private String title;
    private int numberOfComment;
    private int price;
    private int deliveryPrice;
    private String category;
    private String date;
    private int thumbsup;

    public BoardHomeDto(Board board, Integer numberOfComment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        this.boardId = board.getBoardId();
        this.memberId = board.getMemberId();
        this.title = board.getTitle();
        this.numberOfComment = numberOfComment;
        this.price = board.getPrice();
        this.deliveryPrice = board.getDeliveryPrice();
        this.category = board.getCategory().toString();
        this.date = board.getDate().format(formatter);
        this.thumbsup = board.getThumbsup();
    }
}
