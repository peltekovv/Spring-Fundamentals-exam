package softuni.demo.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.demo.model.binding.ProductAddBindingModel;
import softuni.demo.model.service.ProductServiceModel;
import softuni.demo.service.ProductService;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;

        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel
            , BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("product-add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel
            , BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {


        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:add");
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            return modelAndView;
        }

        this.productService.addProduct(modelMapper.map(productAddBindingModel, ProductServiceModel.class));

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, ModelAndView modelAndView) {
        this.productService.deleteProductById(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/deleteall")
    public ModelAndView deleteall(ModelAndView modelAndView) {
        this.productService.deleteAll();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
