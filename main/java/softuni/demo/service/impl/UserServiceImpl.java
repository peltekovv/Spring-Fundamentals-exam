package softuni.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.demo.model.entity.User;
import softuni.demo.model.service.UserServiceModel;
import softuni.demo.repository.UserRepository;
import softuni.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel,User.class);
        this.userRepository.saveAndFlush(user);
        return userServiceModel;
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u,UserServiceModel.class))
                .orElse(null);
    }
}
