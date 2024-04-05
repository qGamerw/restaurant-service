package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.Category;
import ru.sber.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }
}
