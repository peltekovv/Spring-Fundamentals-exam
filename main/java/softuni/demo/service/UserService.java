package softuni.demo.service;

import softuni.demo.model.service.UserServiceModel;

public interface UserService {
    UserServiceModel register(UserServiceModel userServiceModel);
    UserServiceModel findByUsername(String username);
}
