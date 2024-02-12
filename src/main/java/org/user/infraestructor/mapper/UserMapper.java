package org.user.infraestructor.mapper;

import org.springframework.stereotype.Service;
import org.user.application.domain.entities.User;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.request.Phone;
import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.persistence.entity.UserEntity;
import org.user.infraestructor.web.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserMapper {


    public UserEntity userToUserEntity(User user) {

        LocalDateTime creationDate = LocalDateTime.now();

        UserEntity entity = new UserEntity();
        entity.setIsactive(true);
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setCreated(creationDate);
        entity.setModified(creationDate);
        entity.setLastLogin(creationDate);
        entity.setToken(UUID.randomUUID().toString());

        List<UserEntity.Phone> phoneList = new ArrayList<>();

        for (User.Phone phone : user.getPhones()) {
            UserEntity.Phone phoneEntity = new UserEntity.Phone();
            phoneEntity.setCitycode(phone.getCitycode());
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setContrycode(phone.getContrycode());
            phoneList.add(phoneEntity);
        }
        entity.setPhones(phoneList);
        return entity;
    }


    public User userRequestToUser(UserRequest request) {

        User user = new User();
        user.setIsactive(true);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        List<User.Phone> phoneList = new ArrayList<>();

        for (Phone phone : request.getPhones()) {
            User.Phone phoneEntity = new User.Phone();
            phoneEntity.setCitycode(phone.getCitycode());
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setContrycode(phone.getContrycode());
            phoneList.add(phoneEntity);
        }
        user.setPhones(phoneList);
        return user;
    }

    public User userEntityToUser(UserEntity entity) {

        User user = new User();
        user.setId(entity.getId());
        user.setIsactive(entity.isIsactive());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setCreated(entity.getCreated());
        user.setModified(entity.getModified());
        user.setLastLogin(entity.getLastLogin());
        user.setToken(entity.getToken());

        List<User.Phone> phoneList = new ArrayList<>();

        for (UserEntity.Phone phone : entity.getPhones()) {
            User.Phone phoneEntity = new User.Phone();
            phoneEntity.setCitycode(phone.getCitycode());
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setContrycode(phone.getContrycode());
            phoneList.add(phoneEntity);
        }
        user.setPhones(phoneList);
        return user;
    }

    public FullUserDTO UserToFullUser(User user) {

        FullUserDTO fullUserDTO = new FullUserDTO();

        fullUserDTO.setId(user.getId());
        fullUserDTO.setCreated(user.getCreated());
        fullUserDTO.setModified(user.getModified());
        fullUserDTO.setLastLogin(user.getLastLogin());
        fullUserDTO.setIsactive(user.isIsactive());
        fullUserDTO.setName(user.getName());
        fullUserDTO.setEmail(user.getEmail());
        fullUserDTO.setPassword(user.getPassword());
        fullUserDTO.setToken(user.getToken());

        List<FullUserDTO.Phone> phoneList = new ArrayList<>();

        for (User.Phone phone : user.getPhones()) {
            FullUserDTO.Phone phoneEntity = new FullUserDTO.Phone();
            phoneEntity.setCitycode(phone.getCitycode());
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setContrycode(phone.getContrycode());
            phoneList.add(phoneEntity);
        }

        fullUserDTO.setPhones(phoneList);

       return fullUserDTO;
    }

    public UserDTO userToUserDTO(User entity) {

        return UserDTO.builder().
                 id(entity.getId().toString()).
                created(entity.getCreated()).
                modified(entity.getModified()).
                lastLogin(entity.getLastLogin()).
                isactive(entity.isIsactive()).
                token(entity.getToken()).
                build();
    }
}