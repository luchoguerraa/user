package org.user.application.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.user.application.domain.entities.User;
import org.user.infraestructor.mapper.UserMapper;
import org.user.infraestructor.persistence.repository.UserEntityRepository;
import org.user.infraestructor.persistence.entity.UserEntity;
import org.user.infraestructor.web.exception.UserException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private  final UserMapper userMapper;
    public UserServiceImpl(UserEntityRepository userEntityRepository,
                           UserMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }

    public User save(User user) {

        String email = userEntityRepository.findAllEmailByEmail(user.getEmail());

        if(email == null) {
            UserEntity response = userEntityRepository.save(userMapper.userToUserEntity(user));
            return userMapper.userEntityToUser(response);
        } else {
            throw new UserException("Ya existe el correo registrado", HttpStatus.CONFLICT);
        }

    }


    public User findById(UUID userUuID) {
        Optional<UserEntity> response = userEntityRepository.findById(userUuID);
        if (response.isPresent()){
            return userMapper.userEntityToUser(response.get());
        }
        throw new UserException("No existe el usuario con Id :" + userUuID, HttpStatus.NOT_FOUND);
    }

    public void delete( UUID userUuID) throws Exception {

            Optional<UserEntity> userToDelete = userEntityRepository.findById(userUuID);
            if(userToDelete.isPresent()) {
                userEntityRepository.delete(userToDelete.get());
            } else {
                throw new Exception("Error eliminando al usuario con Id :" + userUuID);
            }
    }

    public User update(User user, UUID id, LocalDateTime modified) {

        UserEntity response = userEntityRepository.getById(id);
        UserEntity responseMapper = userMapper.userToUserEntity(user);

        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setCreated(response.getCreated());
        response.setLastLogin(response.getLastLogin());
        response.setModified(modified);
        response.setPhones(responseMapper.getPhones());

        UserEntity responseUpdate = userEntityRepository.save(response);

        return userMapper.userEntityToUser(responseUpdate);
    }
}
