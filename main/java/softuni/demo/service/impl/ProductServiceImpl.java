package softuni.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.demo.model.entity.Product;
import softuni.demo.model.service.ProductServiceModel;
import softuni.demo.model.view.ProductViewModel;
import softuni.demo.repository.ProductRepository;
import softuni.demo.service.CategoryService;
import softuni.demo.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        System.out.println();
        product.setCategory(categoryService.findByName(productServiceModel.getCategory()));

        this.productRepository.saveAndFlush(product);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductViewModel> findAllItems() {
        return this.productRepository.findAll()
                .stream()
                .map(i -> {
                    ProductViewModel productViewModel = this.modelMapper.map(i, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", i.getCategory().getName().name().toLowerCase()));

                    return productViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findAllDrink() {
        return this.productRepository.findAll()
                .stream()
                .filter(i -> i.getCategory().getName().name().equals("DRINK"))
                .map(i -> {
                    ProductViewModel productViewModel = this.modelMapper.map(i, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", i.getCategory().getName().name().toLowerCase()));

                    return productViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findAllFood() {
        return this.productRepository.findAll()
                .stream()
                .filter(i -> i.getCategory().getName().name().equals("FOOD"))
                .map(i -> {
                    ProductViewModel productViewModel = this.modelMapper.map(i, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", i.getCategory().getName().name().toLowerCase()));

                    return productViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findAllHousehold() {
        return this.productRepository.findAll()
                .stream()
                .filter(i -> i.getCategory().getName().name().equals("HOUSEHOLD"))
                .map(i -> {
                    ProductViewModel productViewModel = this.modelMapper.map(i, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", i.getCategory().getName().name().toLowerCase()));

                    return productViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findAllOther() {
        return this.productRepository.findAll()
                .stream()
                .filter(i -> i.getCategory().getName().name().equals("OTHER"))
                .map(i -> {
                    ProductViewModel productViewModel = this.modelMapper.map(i, ProductViewModel.class);
                    productViewModel.setImgUrl(String.format("/img/%s.jpg", i.getCategory().getName().name().toLowerCase()));

                    return productViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalCost() {
        AtomicReference<BigDecimal> totalcost = new AtomicReference<>(new BigDecimal(0));

                this.productRepository.findAll().forEach(e -> {
                    BigDecimal add = totalcost.get().add(e.getPrice());
                    totalcost.set(add);
                });


        return totalcost.get();
    }

    @Override
    public void deleteAll() {
        this.productRepository.deleteAll();
    }


}
