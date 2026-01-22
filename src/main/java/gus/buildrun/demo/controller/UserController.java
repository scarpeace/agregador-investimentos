package gus.buildrun.demo.controller;

import gus.buildrun.demo.controller.dto.*;
import gus.buildrun.demo.entity.Account;
import gus.buildrun.demo.entity.User;
import gus.buildrun.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody CreateUserDto createUserDto) {

        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable("userId") String userId) {

        var foundUser = userService.findUserById(userId);

        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUser() {

        var allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UpdateUserDto updateUserDto) {

        var updatedUser = userService.updateUser(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("userId") String userId) {

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Account> createAccount(
            @PathVariable("userId") String userId,
            @RequestBody CreateAccountDto createAccountDto) {

        Account accountCreated = userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok(accountCreated);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAccount(@PathVariable String userId) {

        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

}
