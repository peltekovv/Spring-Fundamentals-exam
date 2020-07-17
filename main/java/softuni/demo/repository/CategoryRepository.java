package softuni.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.demo.model.entity.Category;
import softuni.demo.model.entity.CategoryName;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(CategoryName categoryName);
}
