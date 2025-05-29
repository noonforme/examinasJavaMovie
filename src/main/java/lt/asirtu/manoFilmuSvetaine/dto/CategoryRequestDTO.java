package lt.asirtu.manoFilmuSvetaine.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {

    @NotBlank(message = "Can't be left blank.")
    String name;

    public @NotBlank(message = "Can't be left blank.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Can't be left blank.") String name) {
        this.name = name;
    }
}
