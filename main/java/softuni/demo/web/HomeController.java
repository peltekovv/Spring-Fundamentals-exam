package softuni.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.demo.service.ProductService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            modelAndView.setViewName("index");
        }else{
            modelAndView.setViewName("home");
            modelAndView.addObject("totalCost",productService.getTotalCost());
            modelAndView.addObject("drink",productService.findAllDrink());
            modelAndView.addObject("food",productService.findAllFood());
            modelAndView.addObject("household",productService.findAllHousehold());
            modelAndView.addObject("other",productService.findAllOther());
        }
        return modelAndView;
    }
}
