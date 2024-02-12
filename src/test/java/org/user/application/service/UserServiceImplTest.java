package org.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.user.application.domain.entities.User;
import org.user.application.domain.service.UserServiceImpl;
import org.user.infraestructor.mapper.UserMapper;
import org.user.infraestructor.persistence.repository.UserEntityRepository;
import org.user.infraestructor.persistence.entity.UserEntity;
import org.user.infraestructor.web.exception.UserException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_Success() throws Exception {

        User user = new User();
        user.setEmail("test@example.com");

        when(userEntityRepository.findAllEmailByEmail(user.getEmail())).thenReturn(null);
        when(userMapper.userToUserEntity(user)).thenReturn(new UserEntity());


        userService.save(user);

        verify(userEntityRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void save_UserAlreadyExists_Exception() {

        User user = new User();
        user.setEmail("existing@example.com");

        when(userEntityRepository.findAllEmailByEmail(user.getEmail())).thenReturn("existing");

        assertThrows(UserException.class, () -> userService.save(user));
    }

    @Test
    void findById_UserExists_Success() throws Exception {

        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUser(userEntity)).thenReturn(new User());

        User resultUser = userService.findById(userId);

        assertNotNull(resultUser);
    }

    @Test
    void findById_UserNotFound_Exception() {

        UUID userId = UUID.randomUUID();

        when(userEntityRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.findById(userId));
    }

    @Test
    void delete_UserExists_Success() throws Exception {

        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();

        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.delete(userId);

        verify(userEntityRepository, times(1)).delete(userEntity);
    }

    @Test
    void delete_UserNotFound_Exception() {
        UUID userId = UUID.randomUUID();

        when(userEntityRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.delete(userId));
    }

    @Test
    void update_UserExists_Success() throws Exception {

        String userId = UUID.randomUUID().toString();
        User user = new User();
        UserEntity existingUserEntity = new UserEntity();

        when(userEntityRepository.getById(UUID.fromString(userId))).thenReturn(existingUserEntity);
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(existingUserEntity);
        when(userMapper.userEntityToUser(existingUserEntity)).thenReturn(user);


        User resultUser = userService.update(user, userId);

        assertNotNull(resultUser);
    }

    @Test
    void update_UserNotFound_Exception() {

        String userId = UUID.randomUUID().toString();
        User user = new User();

        when(userEntityRepository.getById(UUID.fromString(userId))).thenThrow(new RuntimeException("Entity not found"));

        assertThrows(Exception.class, () -> userService.update(user, userId));
    }
}

