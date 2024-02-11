package org.user.infraestructor.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.user.infraestructor.web.request.UserRequest;
import org.user.application.coordinator.UserCoordinator;
import org.user.infraestructor.common.CommonAttribute;
import org.user.infraestructor.web.dto.UserDTO;
import org.user.infraestructor.web.controller.UserController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserCoordinator userPresenter;

    @Mock
    private CommonAttribute commonAttribute;

    @InjectMocks
    private UserController userController;

    @Test
    void save_ValidUserRequest_ReturnsOkResponse() throws Exception {
        UserRequest userRequest = new UserRequest();
        when(commonAttribute.validateEmail(any())).thenReturn(true);
        when(commonAttribute.validatePassword(any())).thenReturn(true);
        when(userPresenter.createUser(any())).thenReturn(UserDTO.builder().build());

        ResponseEntity<UserDTO> response = userController.save(userRequest);

        verify(userPresenter, times(1)).createUser(userRequest);
        verify(commonAttribute, times(1)).validateEmail(any());
        verify(commonAttribute, times(1)).validatePassword(any());
        assert(response.getStatusCode().is2xxSuccessful());
    }


    @Test
    void find_ValidId_ReturnsOkResponse() throws Exception {
        when(userPresenter.getUser(any())).thenReturn(UserDTO.builder().build());

        ResponseEntity<UserDTO> response = userController.find("1");

        verify(userPresenter, times(1)).getUser("1");
        assert(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void delete_ValidId_ReturnsOkResponse() throws Exception {
        ResponseEntity<String> response = userController.delete("1");

        verify(userPresenter, times(1)).deleteUser("1");
        assert(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void update_ValidUserRequest_ReturnsOkResponse() throws Exception {
        UserRequest userRequest = new UserRequest();
        when(commonAttribute.validateEmail(any())).thenReturn(true);
        when(commonAttribute.validatePassword(any())).thenReturn(true);
        when(userPresenter.updateUser(any(), any())).thenReturn(UserDTO.builder().build());

        ResponseEntity<UserDTO> response = userController.update(userRequest, "1");

        verify(userPresenter, times(1)).updateUser(userRequest, "1");
        verify(commonAttribute, times(1)).validateEmail(any());
        verify(commonAttribute, times(1)).validatePassword(any());
        assert(response.getStatusCode().is2xxSuccessful());
    }


}
