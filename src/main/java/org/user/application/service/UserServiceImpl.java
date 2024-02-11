package org.user.application.service;

import org.springframework.stereotype.Service;
import org.user.domain.repository.UserEntityRepository;
import org.user.infraestructor.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    public UserServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public UserEntity save(UserEntity user) throws Exception {

        String response = userEntityRepository.findAllEmailByEmail(user.getEmail());

        if(response == null) {
            return userEntityRepository.save(user);
        }
        throw  new Exception("Ya existe el correo registrado");
    }

    public UserEntity findById(UUID userUuID) throws Exception {
        Optional<UserEntity> response = userEntityRepository.findById(userUuID);
        if (response.isPresent()){
            return response.get();
        }
        throw new Exception("No existe el usuario con Id :" + userUuID);
    }

    public void delete( UUID userUuID) throws Exception {
        try {
            UserEntity userToDelete = this.findById(userUuID);
            userEntityRepository.delete(userToDelete);
        } catch (Exception e) {
            throw new Exception("Error a actualizar el usuario con Id :" + userUuID.toString());
        }
    }

    public UserEntity update(UserEntity user, String id) throws Exception {

        UserEntity response = this.findById(UUID.fromString(id));

        UUID idResponse = response.getId();
        user.setId(idResponse);
        user.setCreated(response.getCreated());
        user.setLastLogin(response.getLastLogin());
        user.setModified(LocalDateTime.now());
        user.setToken(response.getToken());

        UserEntity responseUpdate = userEntityRepository.save(user);

        return responseUpdate;
    }


}
