package lt.asirtu.manoFilmuSvetaine.services;

import lt.asirtu.manoFilmuSvetaine.dao.CategoryRepository;
import lt.asirtu.manoFilmuSvetaine.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        Optional<Category> result = categoryRepository.findById(id);
        Category category = null;
        if (result.isPresent()) {
            category = result.get();
        } else {
            throw new RuntimeException("Can't find category, id - " + id);
        }
        return category;
    }

    @Transactional
    public Category save(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category already exists, id - " + category.getId());
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
