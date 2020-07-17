package softuni.demo.service;

import softuni.demo.model.service.ProductServiceModel;
import softuni.demo.model.view.ProductViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductViewModel> findAllItems();
    List<ProductViewModel> findAllDrink();
    List<ProductViewModel> findAllFood();
    List<ProductViewModel> findAllHousehold();
    List<ProductViewModel> findAllOther();

    void deleteProductById (Long id);


    BigDecimal getTotalCost();

    void deleteAll();
}
