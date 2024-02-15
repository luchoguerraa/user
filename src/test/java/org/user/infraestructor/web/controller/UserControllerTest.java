package org.user.infraestructor.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.exception.AttributeException;
import org.user.infraestructor.web.request.UserRequest;
import org.user.application.coordinator.UserCoordinator;
import org.user.infraestructor.common.CommonAttribute;
import org.user.infraestructor.web.dto.UserDTO;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final UUID ID = UUID.randomUUID();

    @Mock
    private UserCoordinator userCoordinator;

    @Mock
    private CommonAttribute commonAttribute;

    @InjectMocks
    private UserController userController;

    @Test
    void save_ValidUserRequest_ReturnsOkResponse() throws Exception {
        UserRequest userRequest = new UserRequest();
        when(commonAttribute.validateEmail(any())).thenReturn(true);
        when(commonAttribute.validatePassword(any())).thenReturn(true);
        when(userCoordinator.createUser(any())).thenReturn(UserDTO.builder().build());

        ResponseEntity<UserDTO> response = userController.save(userRequest);

        verify(userCoordinator, times(1)).createUser(userRequest);
        verify(commonAttribute, times(1)).validateEmail(any());
        verify(commonAttribute, times(1)).validatePassword(any());
        assert(response.getStatusCode().is2xxSuccessful());
    }


    @Test
    void find_ValidId_ReturnsOkResponse() throws Exception {

        when(userCoordinator.getUser(any())).thenReturn(new FullUserDTO());

        ResponseEntity<FullUserDTO> response = userController.find(ID.toString());

        verify(userCoordinator, times(1)).getUser(ID);
        assert(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void delete_ValidId_ReturnsOkResponse() throws Exception {

        ResponseEntity<String> response = userController.delete(ID.toString());

        verify(userCoordinator, times(1)).deleteUser(ID);
        assert(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void update_ValidUserRequest_ReturnsOkResponse() throws Exception {
        UserRequest userRequest = new UserRequest();
        when(commonAttribute.validateEmail(any())).thenReturn(true);
        when(commonAttribute.validatePassword(any())).thenReturn(true);
        when(userCoordinator.updateUser(any(), any())).thenReturn(UserDTO.builder().build());

        ResponseEntity<UserDTO> response = userController.update(userRequest, ID.toString());

        verify(userCoordinator, times(1)).updateUser(userRequest, ID);
        verify(commonAttribute, times(1)).validateEmail(any());
        verify(commonAttribute, times(1)).validatePassword(any());
        assert(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void validateEmail_ValidEmailFormat_ExceptionThrown() {

        UserRequest request = new UserRequest();
        request.setEmail("usuario@dominio.com");
        request.setPassword("hunter2");
        when(commonAttribute.validatePassword(any())).thenReturn(true);

        AttributeException exception = assertThrows(AttributeException.class,
                () -> userController.save(request));

        assertEquals("El correo no cumple con el formato.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void validateID_ValidIDFormat_ExceptionThrown() {

        String id = "id";
        AttributeException exception = assertThrows(AttributeException.class,
                () -> userController.find(id));

        assertEquals("El ID no cumple con el formato UUID", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void validatePassword_ValidPasswordFormat_ExceptionThrown() {

        UserRequest request = new UserRequest();
        request.setPassword("123");
        AttributeException exception = assertThrows(AttributeException.class,
                () -> userController.save(request));

        assertEquals("La password no cumple con el formato.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }


}
