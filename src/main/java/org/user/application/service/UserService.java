package org.user.application.service;

import org.user.infraestructor.entity.UserEntity;

import java.util.UUID;


public interface UserService {
    UserEntity save(UserEntity user) throws Exception;
    UserEntity findById(UUID userUuID) throws Exception;
    void delete( UUID userUuID) throws Exception;
    UserEntity update(UserEntity user, String id) throws Exception;
}
