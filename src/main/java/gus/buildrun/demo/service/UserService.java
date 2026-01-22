package gus.buildrun.demo.service;

import gus.buildrun.demo.controller.dto.*;
import gus.buildrun.demo.entity.Account;
import gus.buildrun.demo.entity.BillingAddress;
import gus.buildrun.demo.entity.Stock;
import gus.buildrun.demo.entity.User;
import gus.buildrun.demo.repository.AccountRepository;
import gus.buildrun.demo.repository.BillingAddressRepository;
import gus.buildrun.demo.repository.StockRepository;
import gus.buildrun.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepo;
    final AccountRepository accountRepo;
    final BillingAddressRepository billingAddressRepo;
    final StockRepository stockRepo;

    public UserService(UserRepository userRepo, AccountRepository accountRepo, BillingAddressRepository billingAddressRepo, StockRepository stockRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.billingAddressRepo = billingAddressRepo;
        this.stockRepo = stockRepo;
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

    public List<UserResponseDto> getAllUsers(){
        return  userRepo.findAll()
                .stream()
                .map(usr -> new UserResponseDto(
                        usr.getUserId().toString(),
                        usr.getUsername(),
                        usr.getEmail(),
                        usr.getAccounts()))
                .toList();
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

    public Account createAccount(String userId, CreateAccountDto createAccountDto){
        var id = UUID.fromString(userId);

        var foundUser = userRepo.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Account account = new Account(
                null,
                createAccountDto.description(),
                foundUser,
                null,
                new ArrayList<>()
                );

        var accountCreated = accountRepo.save(account);

        BillingAddress billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                createAccountDto.street(),
                createAccountDto.number(),
                accountCreated);

        billingAddressRepo.save(billingAddress);

        return accountCreated;
    }

    public List<AccountResponseDto> listAccounts(String userId){
        var foundUser = userRepo.findById(UUID.fromString(userId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found in the DATABASE"));

        return foundUser.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                .toList();

    }


}
