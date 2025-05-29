package lt.asirtu.manoFilmuSvetaine.dto;

import jakarta.validation.constraints.NotBlank;

public class MovieRequestDTO {

    
    private String name;

    
    private String description;

    private Double rating;

    private Integer category;

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public  Integer getCategory() {
        return category;
    }

    public void setCategory( Integer category) {
        this.category = category;
    }
}
