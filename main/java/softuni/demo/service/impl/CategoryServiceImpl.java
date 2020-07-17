package softuni.demo.service.impl;

import org.springframework.stereotype.Service;
import softuni.demo.model.entity.Category;
import softuni.demo.model.entity.CategoryName;
import softuni.demo.repository.CategoryRepository;
import softuni.demo.service.CategoryService;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void init() {
        if(categoryRepository.count() == 0){
            Arrays.stream(CategoryName.values()).forEach(categoryName -> {
                this.categoryRepository.saveAndFlush(new Category(categoryName,
                        String.format("Description for %s",categoryName.name())));
            });
        }

    }

    @Override
    public Category findByName(CategoryName name) {
        return this.categoryRepository.findByName(name);
    }
}
