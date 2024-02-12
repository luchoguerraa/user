package org.user.application.coordinator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user.application.domain.entities.User;
import org.user.application.domain.service.UserService;
import org.user.infraestructor.mapper.UserMapper;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.dto.UserDTO;
import org.user.infraestructor.web.request.UserRequest;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCoordinatorImplTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserCoordinatorImpl userCoordinator;

    @Test
    void getUser_Success() throws Exception {

        UUID userId = UUID.randomUUID();
        User mockedUser = new User();
        FullUserDTO expectedDto = new FullUserDTO();

        when(userService.findById(userId)).thenReturn(mockedUser);
        when(userMapper.UserToFullUser(mockedUser)).thenReturn(expectedDto);

        FullUserDTO resultDto = userCoordinator.getUser(userId.toString());

        verify(userService, times(1)).findById(userId);
        verify(userMapper, times(1)).UserToFullUser(mockedUser);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void createUser_Success() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        User user = new User();
        UserDTO expectedDto = UserDTO.builder().build();

        when(userMapper.userRequestToUser(userRequest)).thenReturn(user);
        when(userService.save(user)).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(expectedDto);

        // Act
        UserDTO resultDto = userCoordinator.createUser(userRequest);

        // Assert
        verify(userMapper, times(1)).userRequestToUser(userRequest);
        verify(userService, times(1)).save(user);
        verify(userMapper, times(1)).userToUserDTO(user);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void updateUser_Success() throws Exception {

        LocalDateTime modified = LocalDateTime.now();
        UserRequest userRequest = new UserRequest();
        User user = new User();
        user.setCreated(modified);
        String userId = "someId";
        UserDTO expectedDto = UserDTO.builder().build();
        when(userMapper.userRequestToUser(userRequest)).thenReturn(user);
        when(userService.update(Mockito.any(User.class), Mockito.any(String.class), Mockito.any(LocalDateTime.class))).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(expectedDto);

        UserDTO resultDto = userCoordinator.updateUser(userRequest, userId);

        verify(userMapper, times(1)).userRequestToUser(userRequest);
        verify(userService, times(1)).update(Mockito.any(User.class), Mockito.any(String.class), Mockito.any(LocalDateTime.class));
        verify(userMapper, times(1)).userToUserDTO(user);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void deleteUser_Success() throws Exception {
        String userId = UUID.randomUUID().toString();

        userCoordinator.deleteUser(userId);

        verify(userService, times(1)).delete(UUID.fromString(userId));
    }
}