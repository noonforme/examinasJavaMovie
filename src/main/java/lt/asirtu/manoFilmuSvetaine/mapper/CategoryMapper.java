package lt.asirtu.manoFilmuSvetaine.mapper;

import lt.asirtu.manoFilmuSvetaine.dto.CategoryRequestDTO;
import lt.asirtu.manoFilmuSvetaine.dto.CatergoryResponseDTO;
import lt.asirtu.manoFilmuSvetaine.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    public static CatergoryResponseDTO toResponse(Category category) {
        CatergoryResponseDTO dto = new CatergoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setUser(category.getUser().getId());
        return dto;
    }
}
