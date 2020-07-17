package softuni.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.demo.service.CategoryService;

@Component
public class DataInit implements CommandLineRunner {

    private final CategoryService categoryService;

    public DataInit(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.init();
    }
}
