package org.user.application.domain.service;

import org.user.application.domain.entities.User;
import java.util.UUID;


public interface UserService {
    User save(User user) throws Exception;
    User findById(UUID userUuID) throws Exception;
    void delete( UUID userUuID) throws Exception;
    User update(User user, String id) throws Exception;
}
