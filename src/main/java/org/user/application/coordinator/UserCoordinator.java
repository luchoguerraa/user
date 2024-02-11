package org.user.application.coordinator;

import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.web.dto.UserDTO;


public interface UserCoordinator {

    UserDTO getUser(String id) throws Exception;
    UserDTO createUser(UserRequest user) throws Exception;
    UserDTO updateUser(UserRequest user, String id) throws Exception;
    void deleteUser(String id) throws Exception;

}
