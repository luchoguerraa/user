package org.user.application.coordinator;

import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.web.dto.UserDTO;

import java.util.UUID;


public interface UserCoordinator {

    FullUserDTO getUser(UUID ID) throws Exception;
    UserDTO createUser(UserRequest user) throws Exception;
    UserDTO updateUser(UserRequest user, UUID ID) throws Exception;
    void deleteUser(UUID ID) throws Exception;

}
