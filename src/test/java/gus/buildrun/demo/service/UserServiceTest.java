package gus.buildrun.demo.service;

import gus.buildrun.demo.controller.dto.CreateUserDto;
import gus.buildrun.demo.controller.dto.UpdateUserDto;
import gus.buildrun.demo.entity.User;
import gus.buildrun.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;

   @InjectMocks
   private UserService userService;

   @Captor
   private ArgumentCaptor<User> userArgumentCaptor;

   @Captor
   private ArgumentCaptor<UUID> uuidArgumentCaptor;
   @Nested
    class createUser{

       @Test
       @DisplayName("Should create a user with success")
       void shouldCreateUserWithSuccess(){
           //Arrange
           var user = new User(
                   UUID.randomUUID(),
                   "username",
                   "email@email.com",
                   "password",
                   Instant.now(),
                   null
           );

           doReturn(user).when(userRepo).save(userArgumentCaptor.capture());
           var input = new CreateUserDto(
                   "username",
                   "email@email.com",
                   "password"
           );

           //Act
           var output = userService.createUser(input);

           //Assert
           var userCaptured = userArgumentCaptor.getValue();

           assertNotNull(output);
           assertEquals(userCaptured.getUsername(), input.username());
           assertEquals(userCaptured.getEmail(), input.email());
           assertEquals(userCaptured.getPassword(), input.password());
           assertDoesNotThrow(()-> UUID.fromString(output.toString()),
                   "Expected output to be a UUID, but it threw an exception");

       }

       @Test
       @DisplayName("Should throw exception when error occurs")
       void shouldThrowExceptionWhenErrorOccurs(){

           //Arrange
           doThrow(new RuntimeException()).when(userRepo).save(any());
           var input = new CreateUserDto(
                   "username",
                   "email@email.com",
                   "password"
           );

           //Act & Assert
           assertThrows(RuntimeException.class, ()-> userService.createUser(input));
       }
   }

   @Nested
    class findUserById{

       @Test
       @DisplayName("Should return user with ID with success when optional is full")
       void shouldGetUserByIdWithSuccessWhenOptionalIsFull(){

           //Arrange
           var id = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
           var user = new User(
                    id,
                   "username",
                   "email@email.com",
                   "password",
                   Instant.now(),
                   Instant.now()
           );

           doReturn(Optional.of(user)).when(userRepo).findById(uuidArgumentCaptor.capture());

           var output = userService.findUserById("3fa85f64-5717-4562-b3fc-2c963f66afa6");

           assertTrue(output.isPresent(),"Expected the output to contain a value");
           var foundUser = output.get();

           assertNotNull(foundUser);
           assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
           assertEquals(user.getUsername(), foundUser.getUsername());
           assertEquals(user.getEmail(), foundUser.getEmail());
           assertEquals(user.getPassword(), foundUser.getPassword());

       }

       @Test
       @DisplayName("Should return user with ID with success when optional is empty")
       void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty(){

           //Arrange
           var userId = UUID.randomUUID();


           doReturn(Optional.empty()).when(userRepo).findById(uuidArgumentCaptor.capture());

           var output = userService.findUserById(userId.toString());

           assertTrue(output.isEmpty(),"Expected the output to be empty");
           assertEquals(userId, uuidArgumentCaptor.getValue());

       }

   }

   @Nested
    class listUsers{

       @Test
       @DisplayName("Should return users with success")
       void shouldReturnAllUsersWithSuccess(){
           //Arrange
           var id = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
           var user = new User(
                   id,
                   "username",
                   "email@email.com",
                   "password",
                   Instant.now(),
                   Instant.now()
           );

           doReturn(List.of(user)).when(userRepo).findAll();

           //Act
           var output = userService.getAllUsers();

           //Accert

           assertNotNull(output);
           assertEquals(1, output.size());
       }
   }

   @Nested
    class deleteById{

       @Test
       @DisplayName("Should delete a user with success when user exists")
       void shouldDeleteUserWhenSuccess(){
           //Arrange

           doReturn(true).when(userRepo).existsById(uuidArgumentCaptor.capture());
           doNothing().when(userRepo).deleteById(uuidArgumentCaptor.capture());
           var userId = UUID.randomUUID();

           //Act
           userService.deleteUser(userId.toString());

           //Assert
           var idList = uuidArgumentCaptor.getAllValues();
           assertEquals(userId, idList.get(0));
           assertEquals(userId, idList.get(1));

           verify(userRepo, times(1)).existsById(idList.get(0));
           verify(userRepo, times(1)).deleteById(idList.get(1));

       }

       @Test
       @DisplayName("Should not delete a user when user do not exists")
       void shouldNotDeleteUserWhenSuccess(){
           //Arrange

           doReturn(false).when(userRepo).existsById(uuidArgumentCaptor.capture());

           var userId = UUID.randomUUID();

           //Act
           userService.deleteUser(userId.toString());

           //Assert
           assertEquals(userId, uuidArgumentCaptor.getValue());


           verify(userRepo, times(1)).existsById(uuidArgumentCaptor.getValue());
           verify(userRepo, times(0)).deleteById(any());

       }

   }

   @Nested
    class updateById{

       @Test
       @DisplayName("Should update user when username and password is filled")
       void shouldUpdateUserWhenUsernameAndPasswordIsFilled(){
           //Arrange
           var id = UUID.randomUUID();
           var updateUserDto = new UpdateUserDto(
                   "newUsername",
                   "newPassword");

           var user = new User(
                   id,
                   "username",
                   "email@email.com",
                   "password",
                   Instant.now(),
                   Instant.now()
           );

           doReturn(Optional.of(user))
                   .when(userRepo).
                   findById(uuidArgumentCaptor.capture());

           doReturn(user)
                   .when(userRepo)
                   .save(userArgumentCaptor.capture());

           //Act
           userService.updateUser(user.getUserId().toString(), updateUserDto);

           //Assert
           var userCaptured = userArgumentCaptor.getValue();

           assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
           assertEquals(user.getUsername(), userCaptured.getUsername());
           assertEquals(user.getPassword(), userCaptured.getPassword());
           verify(userRepo,times(1)).findById(uuidArgumentCaptor.getValue());
           verify(userRepo, times(1)).save(user);
       }

       @Test
       @DisplayName("Should not update user when user does not exist ")
       void shouldNotUpdateUserWhenUserDoesNotExist(){
           //Arrange
           var userId = UUID.randomUUID();
           var updateUserDto = new UpdateUserDto("username","password");


           doReturn(Optional.empty())
                   .when(userRepo).
                   findById(uuidArgumentCaptor.capture());

           //Act
           var output = userService.updateUser(userId.toString(),updateUserDto);

           //Assert

           assertNull(output);
           verify(userRepo,times(1)).findById(uuidArgumentCaptor.getValue());
           verify(userRepo, times(0)).save(any());
       }
   }
}