package org.user.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user.application.domain.entities.User;
import org.user.application.domain.service.UserServiceImpl;
import org.user.infraestructor.mapper.UserMapper;
import org.user.infraestructor.persistence.entity.UserEntity;
import org.user.infraestructor.persistence.repository.UserEntityRepository;
import org.user.infraestructor.web.exception.UserException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUserNotExists() {

        User userToSave = new User();
        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setId(userToSave.getId());

        Mockito.when(userEntityRepository.findAllEmailByEmail(userToSave.getEmail())).thenReturn(null);
        Mockito.when(userEntityRepository.save(Mockito.any(UserEntity.class))).thenReturn(savedUserEntity);
        Mockito.when(userMapper.userToUserEntity(Mockito.any(User.class))).thenReturn(new UserEntity());
        Mockito.when(userMapper.userEntityToUser(Mockito.any(UserEntity.class))).thenReturn(userToSave);

        User result = userService.save(userToSave);

        assertEquals(savedUserEntity.getId(), UUID.fromString(result.getId().toString()));
    }

    @Test
    void testSaveUserExists() {
        User userToSave = new User();
        Mockito.when(userEntityRepository.findAllEmailByEmail(userToSave.getEmail())).thenReturn("existing@example.com");

        UserException exception = assertThrows(UserException.class, () -> {
            userService.save(userToSave);
        });

        assertEquals("Ya existe el correo registrado", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void testFindByIdUserExists() {

        UUID userId = UUID.randomUUID();
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(userId);
        User existingUser = new User();
        existingUser.setId(userId);
        Mockito.when(userEntityRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        Mockito.when(userMapper.userEntityToUser(existingUserEntity)).thenReturn(existingUser);

        User result = userService.findById(userId);

        assertEquals(existingUser, result);
    }

    @Test
    void testFindByIdUserNotExists() {

        UUID userId = UUID.randomUUID();
        Mockito.when(userEntityRepository.findById(userId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            userService.findById(userId);
        });

        assertEquals("No existe el usuario con Id :" + userId, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testDeleteUserExists() throws Exception {
        UUID userId = UUID.randomUUID();
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(userId);
        Mockito.when(userEntityRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));

        userService.delete(userId);
    }

    @Test
    void testDeleteUserNotExists() {

        UUID userId = UUID.randomUUID();
        Mockito.when(userEntityRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.delete(userId);
        });

        assertEquals("Error eliminando al usuario con Id :" + userId, exception.getMessage());
    }

    @Test
    void testUpdateUserExists() {

        LocalDateTime modified = LocalDateTime.now();
        UUID userId = UUID.randomUUID();
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(userId);
        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(userId);
        updatedUserEntity.setModified(modified);
        User updatedUser = new User();
        updatedUser.setId(userId);
        Mockito.when(userEntityRepository.getById(userId)).thenReturn(existingUserEntity);
        Mockito.when(userMapper.userToUserEntity(updatedUser)).thenReturn(updatedUserEntity);
        Mockito.when(userEntityRepository.save(updatedUserEntity)).thenReturn(updatedUserEntity);
        Mockito.when(userMapper.userEntityToUser(updatedUserEntity)).thenReturn(updatedUser);

        User result = userService.update(updatedUser, userId, modified);

        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
    }
}
