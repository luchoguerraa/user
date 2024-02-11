package org.user.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.user.infraestructor.persistence.repository.UserEntityRepository;
import org.user.infraestructor.persistence.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_NewUser_SuccessfullySaved() throws Exception {
        // Arrange
        UserEntity newUser = new UserEntity();
        newUser.setEmail("new@example.com");

        when(userEntityRepository.findAllEmailByEmail(newUser.getEmail())).thenReturn(null);
        when(userEntityRepository.save(newUser)).thenReturn(newUser);

        // Act
        UserEntity savedUser = userService.save(newUser);

        // Assert
        assertNotNull(savedUser);
        assertEquals(newUser.getEmail(), savedUser.getEmail());
        verify(userEntityRepository, times(1)).findAllEmailByEmail(newUser.getEmail());
        verify(userEntityRepository, times(1)).save(newUser);
    }

    @Test
    void save_ExistingUser_ExceptionThrown() {
        // Arrange
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("existing@example.com");

        when(userEntityRepository.findAllEmailByEmail(existingUser.getEmail())).thenReturn("existing@example.com");

        // Act & Assert
        assertThrows(Exception.class, () -> userService.save(existingUser));
        verify(userEntityRepository, times(1)).findAllEmailByEmail(existingUser.getEmail());
        verify(userEntityRepository, never()).save(existingUser);
    }

    // Similar tests for findById, delete, and update methods
}
