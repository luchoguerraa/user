package org.user.application.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user.application.Phone;
import org.user.application.UserRequest;
import org.user.infraestructor.entity.UserEntity;
import org.user.application.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void userRequestToUserEntity_Success() {
        // Arrange
        UserRequest userRequest = createUserRequest();

        // Act
        UserEntity userEntity = userMapper.userRequestToUserEntity(userRequest);

        // Assert
        assertNotNull(userEntity);
        assertEquals(userRequest.getName(), userEntity.getName());
        assertEquals(userRequest.getEmail(), userEntity.getEmail());
        assertEquals(userRequest.getPassword(), userEntity.getPassword());
        assertEquals(userRequest.getPhones().size(), userEntity.getPhones().size());
        assertNotNull(userEntity.getToken());
        assertTrue(userEntity.isIsactive());
    }

    @Test
    void userRequestToUserEntityUpdate_Success() {
        // Arrange
        UserRequest userRequest = createUserRequest();

        // Act
        UserEntity userEntity = userMapper.userRequestToUserEntityUpdate(userRequest);

        // Assert
        assertNotNull(userEntity);
        assertEquals(userRequest.getName(), userEntity.getName());
        assertEquals(userRequest.getEmail(), userEntity.getEmail());
        assertEquals(userRequest.getPassword(), userEntity.getPassword());
        assertEquals(userRequest.getPhones().size(), userEntity.getPhones().size());
    }

    @Test
    void userEntityToUserDTO_Success() {
        // Arrange
        UserEntity userEntity = createUserEntity();

        // Act
        UserDTO userDTO = userMapper.userEntityToUserDTO(userEntity);

        // Assert
        assertNotNull(userDTO);
        assertEquals(userEntity.getId().toString(), userDTO.getId());
        assertEquals(userEntity.getCreated(), userDTO.getCreated());
        assertEquals(userEntity.getModified(), userDTO.getModified());
        assertEquals(userEntity.getLastLogin(), userDTO.getLastLogin());
        assertEquals(userEntity.getToken(), userDTO.getToken());
        assertEquals(userEntity.isIsactive(), userDTO.isIsactive());
    }

    private UserRequest createUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("password");

        Phone phone = new Phone();
        phone.setCitycode("123");
        phone.setNumber("555-1234");
        phone.setContrycode("+1");

        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        userRequest.setPhones(phones);

        return userRequest;
    }

    private UserEntity createUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setModified(LocalDateTime.now());
        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setToken(UUID.randomUUID().toString());
        userEntity.setIsactive(true);

        return userEntity;
    }
}
