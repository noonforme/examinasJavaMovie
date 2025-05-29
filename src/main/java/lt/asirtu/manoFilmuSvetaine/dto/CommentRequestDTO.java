package lt.asirtu.manoFilmuSvetaine.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequestDTO {

    
    private String text;

    
    private Integer movie;

    public  String getText() {
        return text;
    }

    public void setText( String text) {
        this.text = text;
    }

    public  Integer getMovie() {
        return movie;
    }

    public void setMovie( Integer movie) {
        this.movie = movie;
    }
}
