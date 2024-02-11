package org.user.application.coordinator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.web.dto.UserDTO;
import org.user.infraestructor.mapper.UserMapper;
import org.user.application.service.UserService;
import org.user.infraestructor.persistence.entity.UserEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPresenterImplTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserCoordinatorImpl userPresenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser_ValidId_ReturnsUserDTO() throws Exception {

        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        UserDTO expectedUserDTO =  UserDTO.builder().build();

        when(userService.findById(userId)).thenReturn(userEntity);
        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(expectedUserDTO);


        UserDTO result = userPresenter.getUser(userId.toString());

        assertEquals(expectedUserDTO, result);
        verify(userService).findById(userId);
        verify(userMapper).userEntityToUserDTO(userEntity);
    }

    @Test
    void createUser_ValidUserRequest_ReturnsUserDTO() throws Exception {

        UserRequest userRequest = new UserRequest();
        UserEntity userEntity = new UserEntity();
        UserDTO expectedUserDTO =  UserDTO.builder().build();

        when(userMapper.userRequestToUserEntity(userRequest)).thenReturn(userEntity);
        when(userService.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(expectedUserDTO);

        UserDTO result = userPresenter.createUser(userRequest);

        assertEquals(expectedUserDTO, result);
        verify(userMapper).userRequestToUserEntity(userRequest);
        verify(userService).save(userEntity);
        verify(userMapper).userEntityToUserDTO(userEntity);
    }

    @Test
    void updateUser_ValidUserRequest_ReturnsUserDTO() throws Exception {

        UserRequest userRequest = new UserRequest();
        UserEntity userEntity = new UserEntity();
        UserDTO expectedUserDTO = UserDTO.builder().build();
        String userId = "1";

        when(userMapper.userRequestToUserEntityUpdate(any())).thenReturn(userEntity);
        when(userService.update(userEntity, userId)).thenReturn(userEntity);
        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(expectedUserDTO);


        UserDTO result = userPresenter.updateUser(userRequest, userId);

        assertEquals(expectedUserDTO, result);
        verify(userMapper, times(1)).userRequestToUserEntityUpdate(userRequest);
        verify(userService, times(1)).update(userEntity, userId);
        verify(userMapper, times(1)).userEntityToUserDTO(userEntity);
    }

    @Test
    void deleteUser_ValidId_CallsUserServiceDelete() throws Exception {
        UUID userId = UUID.randomUUID();

        userPresenter.deleteUser(userId.toString());

        verify(userService).delete(userId);
    }
}
