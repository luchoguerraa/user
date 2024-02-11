package org.user.application.mapper;

import org.springframework.stereotype.Service;
import org.user.application.Phone;
import org.user.application.UserRequest;
import org.user.infraestructor.entity.UserEntity;
import org.user.application.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserMapper {


    public UserEntity userRequestToUserEntity(UserRequest userRequest) {

        LocalDateTime creationDate = LocalDateTime.now();

        UserEntity user = new UserEntity();
        user.setIsactive(true);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setCreated(creationDate);
        user.setModified(creationDate);
        user.setLastLogin(creationDate);
        user.setToken(UUID.randomUUID().toString());

        List<UserEntity.Phone> phoneList = new ArrayList<>();

        for (Phone phone : userRequest.getPhones()) {
            UserEntity.Phone phoneEntity = new UserEntity.Phone();
            phoneEntity.setCitycode(phone.getCitycode());
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setContrycode(phone.getContrycode());
            phoneList.add(phoneEntity);
        }
        user.setPhones(phoneList);
        return user;
    }

    public UserEntity userRequestToUserEntityUpdate(UserRequest userRequest) {

        UserEntity user = new UserEntity();
        user.setIsactive(true);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        List<UserEntity.Phone> phoneList = new ArrayList<>();

        for (Phone phone : userRequest.getPhones()) {
            UserEntity.Phone phoneEntity = new UserEntity.Phone();
            phoneEntity.setCitycode(phone.getCitycode());
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setContrycode(phone.getContrycode());
            phoneList.add(phoneEntity);
        }
        user.setPhones(phoneList);
        return user;
    }

    public UserDTO userEntityToUserDTO(UserEntity entity) {

      return  UserDTO.builder()
                .id(entity.getId().toString())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .lastLogin(entity.getLastLogin())
                .token(entity.getToken())
              .isactive(entity.isIsactive())
                .build();
    }
}