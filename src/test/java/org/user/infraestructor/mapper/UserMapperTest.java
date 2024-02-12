package org.user.infraestructor.mapper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.user.application.domain.entities.User;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.persistence.entity.UserEntity;
import org.user.infraestructor.web.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void userToUserEntity_Success() {
        // Arrange
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setPhones(new ArrayList<>());

        // Act
        UserEntity userEntity = userMapper.userToUserEntity(user);

        // Assert
        assertNotNull(userEntity);
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertTrue(userEntity.isIsactive());
        assertNotNull(userEntity.getToken());
        assertEquals(user.getPhones().size(), userEntity.getPhones().size());
    }

    @Test
    void userRequestToUser_Success() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Alice");
        userRequest.setEmail("alice@example.com");
        userRequest.setPassword("password");
        userRequest.setPhones(new ArrayList<>());

        // Act
        User user = userMapper.userRequestToUser(userRequest);

        // Assert
        assertNotNull(user);
        assertEquals(userRequest.getName(), user.getName());
        assertEquals(userRequest.getEmail(), user.getEmail());
        assertEquals(userRequest.getPassword(), user.getPassword());
        assertTrue(user.isIsactive());
        assertEquals(userRequest.getPhones().size(), user.getPhones().size());
    }

    @Test
    void userEntityToUser_Success() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Bob");
        userEntity.setEmail("bob@example.com");
        userEntity.setPassword("password");
        userEntity.setPhones(new ArrayList<>());

        // Act
        User user = userMapper.userEntityToUser(userEntity);

        // Assert
        assertNotNull(user);
        assertEquals(userEntity.getName(), user.getName());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getPassword(), user.getPassword());
        assertTrue(user.isIsactive());
        assertEquals(userEntity.getPhones().size(), user.getPhones().size());
    }

    @Test
    void UserToFullUser_Success() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsactive(true);
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setPassword("password");
        user.setToken(UUID.randomUUID().toString());
        user.setPhones(new ArrayList<>());

        // Act
        FullUserDTO fullUserDTO = userMapper.UserToFullUser(user);

        // Assert
        assertNotNull(fullUserDTO);
        assertEquals(user.getId(), fullUserDTO.getId());
        assertEquals(user.getCreated(), fullUserDTO.getCreated());
        assertEquals(user.getModified(), fullUserDTO.getModified());
        assertEquals(user.getLastLogin(), fullUserDTO.getLastLogin());
        assertTrue(fullUserDTO.isIsactive());
        assertEquals(user.getName(), fullUserDTO.getName());
        assertEquals(user.getEmail(), fullUserDTO.getEmail());
        assertEquals(user.getPassword(), fullUserDTO.getPassword());
        assertEquals(user.getToken(), fullUserDTO.getToken());
        assertEquals(user.getPhones().size(), fullUserDTO.getPhones().size());
    }

    @Test
    void userToUserDTO_Success() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsactive(true);
        user.setToken(UUID.randomUUID().toString());
        user.setPhones(new ArrayList<>());

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertNotNull(userDTO);
        assertEquals(user.getId().toString(), userDTO.getId());
        assertEquals(user.getCreated(), userDTO.getCreated());
        assertEquals(user.getModified(), userDTO.getModified());
        assertEquals(user.getLastLogin(), userDTO.getLastLogin());
        assertTrue(userDTO.isIsactive());
        assertEquals(user.getToken(), userDTO.getToken());
    }
}
