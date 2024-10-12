package oince.projecthd.controller.dto;

import lombok.Data;
import oince.projecthd.domain.Board;

import java.time.format.DateTimeFormatter;

@Data
public class BoardRes {
    private int boardId;
    private int memberId;
    private String writer;
    private String title;
    private int numberOfComment;
    private int price;
    private int deliveryPrice;
    private String category;
    private String date;
    private int thumbsup;
    private String itemName;
    private int views;
    private String url;
    private String content;

    public BoardRes(Board board, String writer, int numberOfComment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        this.boardId = board.getBoardId();
        this.memberId = board.getMemberId();
        this.writer = writer;
        this.title = board.getTitle();
        this.numberOfComment = numberOfComment;
        this.price = board.getPrice();
        this.deliveryPrice = board.getDeliveryPrice();
        this.category = board.getCategory().toString();
        this.date = board.getDate().format(formatter);
        this.thumbsup = board.getThumbsup();
        this.itemName = board.getItemName();
        this.views = board.getViews();
        this.url = board.getUrl();
        this.content = board.getContent();
    }
}
