package org.user.application.adapter;

import org.user.application.UserRequest;
import org.user.application.dto.UserDTO;


public interface UserPresenter {

    UserDTO getUser(String id) throws Exception;
    UserDTO createUser(UserRequest user) throws Exception;
    UserDTO updateUser(UserRequest user, String id) throws Exception;
    void deleteUser(String id) throws Exception;

}
