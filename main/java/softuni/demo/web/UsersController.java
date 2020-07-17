package softuni.demo.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.demo.model.binding.UserLoginBindingModel;
import softuni.demo.model.binding.UserRegisterBindingModel;
import softuni.demo.model.service.UserServiceModel;
import softuni.demo.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel,
                              BindingResult bindingResult,ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@Valid @ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                     ModelAndView modelAndView, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel",userLoginBindingModel);
            modelAndView.setViewName("redirect:login");
            return modelAndView;
        }

        UserServiceModel user = this.userService.findByUsername(userLoginBindingModel.getUsername());

        if (user == null || !user.getPassword().equals(userLoginBindingModel.getPassword())) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel",userLoginBindingModel);
            redirectAttributes.addFlashAttribute("notFound", true);
            modelAndView.setViewName("redirect:login");
            return modelAndView;
        }

        httpSession.setAttribute("user", user);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                 BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            modelAndView.setViewName("redirect:register");

        } else {
            this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:/";
    }
}
