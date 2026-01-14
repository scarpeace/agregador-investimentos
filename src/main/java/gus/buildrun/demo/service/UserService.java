package gus.buildrun.demo.service;

import gus.buildrun.demo.controller.CreateUserDto;
import gus.buildrun.demo.controller.UpdateUserDto;
import gus.buildrun.demo.entity.User;
import gus.buildrun.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UUID createUser(CreateUserDto updateUserDto){
        var userEntity = new User(null,
                updateUserDto.username(),
                updateUserDto.email(),
                updateUserDto.password(),
                null,
                null
                );

        var userSaved = userRepo.save(userEntity);

        return userSaved.getUserId();
    }

    public Optional<User> findUserById(String userId){
        return userRepo.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers(){
        return  userRepo.findAll();
    }

    public void deleteUser(String userId){
        var id = UUID.fromString(userId);
        var userExists = userRepo.existsById(id);

        if(userExists){
            userRepo.deleteById(UUID.fromString(userId));
        }

    }

    public User updateUser(String userId, UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);
        var userEntity = userRepo.findById(id);

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }

            if(updateUserDto.password() !=null){
                user.setPassword(updateUserDto.password());
            }

            return userRepo.save(user);
        }

        return null;

    }
}
