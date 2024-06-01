package ru.sber;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.entities.Category;
import ru.sber.entities.enums.ECategory;
import ru.sber.repositories.CategoryRepository;
import ru.sber.services.CategoryServiceImp;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImpTest {

    @Autowired
    private CategoryServiceImp categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void testGetEmptyListCategory() {
        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<Category> categories = categoryService.getListCategory();

        Assert.assertTrue(categories.isEmpty());
    }

    @Test
    public void testGetListCategory() {
        Category category = new Category();
        category.setCategory(ECategory.ROLLS);

        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> categories = categoryService.getListCategory();

        Assert.assertTrue(categories.contains(category));
    }
}
