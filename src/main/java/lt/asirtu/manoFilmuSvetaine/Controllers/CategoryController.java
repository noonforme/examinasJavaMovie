package lt.asirtu.manoFilmuSvetaine.Controllers;

import lt.asirtu.manoFilmuSvetaine.dto.CategoryRequestDTO;
import lt.asirtu.manoFilmuSvetaine.dto.CatergoryResponseDTO;
import lt.asirtu.manoFilmuSvetaine.entity.Category;
import lt.asirtu.manoFilmuSvetaine.entity.User;
import lt.asirtu.manoFilmuSvetaine.mapper.CategoryMapper;
import lt.asirtu.manoFilmuSvetaine.security.services.UserDetailsImpl;
import lt.asirtu.manoFilmuSvetaine.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatergoryResponseDTO> addCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        Category category = CategoryMapper.toEntity(dto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = new User(userDetails.getUsername(), userDetails.getPassword());
        user.setId(userDetails.getId());
        category.setUser(user);
        Category categorySaved = categoryService.save(category);

        return ResponseEntity.ok(CategoryMapper.toResponse(categorySaved));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> findAll() {
        List<CatergoryResponseDTO> categories = categoryService.findAll().stream().map(CategoryMapper::toResponse).toList();
        Map<String, Object> response = Map.of("status", "success",
                "results", categories.size(),
                "data", categories);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CatergoryResponseDTO> getCategory(@PathVariable Integer id) {
        Optional<Category> result = Optional.ofNullable(categoryService.findById(id));
        return result.map(category -> ResponseEntity.ok(CategoryMapper.toResponse(category))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        categoryService.deleteById(id);
        return ResponseEntity.ok("Deleted category, id - " + id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatergoryResponseDTO> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequestDTO dto) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Category updatedCategory = CategoryMapper.toEntity(dto);
        updatedCategory.setId(id);
        Category saveCategory = categoryService.save(updatedCategory);
        return ResponseEntity.ok(CategoryMapper.toResponse(saveCategory));
    }
}
