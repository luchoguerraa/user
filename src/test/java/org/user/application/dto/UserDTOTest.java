package org.user.application.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDTOTest {

    @Test
    void userDTOBuilder_Success() {
        // Arrange
        String id = "123";
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime modified = LocalDateTime.now().plusHours(1);
        LocalDateTime lastLogin = LocalDateTime.now().plusHours(2);
        String token = "token123";
        boolean isActive = true;

        // Act
        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .created(created)
                .modified(modified)
                .lastLogin(lastLogin)
                .token(token)
                .isactive(isActive)
                .build();

        // Assert
        assertNotNull(userDTO);
        assertEquals(id, userDTO.getId());
        assertEquals(created, userDTO.getCreated());
        assertEquals(modified, userDTO.getModified());
        assertEquals(lastLogin, userDTO.getLastLogin());
        assertEquals(token, userDTO.getToken());
        assertEquals(isActive, userDTO.isIsactive());
    }
}
