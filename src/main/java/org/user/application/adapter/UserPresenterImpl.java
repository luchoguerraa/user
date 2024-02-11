package org.user.application.adapter;


import org.springframework.stereotype.Component;
import org.user.application.UserRequest;
import org.user.application.dto.UserDTO;
import org.user.application.mapper.UserMapper;
import org.user.application.service.UserService;
import org.user.infraestructor.entity.UserEntity;

import java.util.UUID;

@Component
public class UserPresenterImpl implements UserPresenter {
   private final UserService userService;
   private  final UserMapper userMapper;

   public UserPresenterImpl(UserService userService, UserMapper userMapper) {
      this.userService = userService;
      this.userMapper = userMapper;
   }

   @Override
   public UserDTO getUser(String id) throws Exception {
      UserEntity response = userService.findById(UUID.fromString(id));
      return userMapper.userEntityToUserDTO(response);
   }

   @Override
   public UserDTO createUser(UserRequest userRequest) throws Exception {

      UserEntity user = userMapper.userRequestToUserEntity(userRequest);
      UserEntity response = userService.save(user);

      return userMapper.userEntityToUserDTO(response);
   }

   @Override
   public UserDTO updateUser(UserRequest userRequest, String id) throws Exception {

      UserEntity user = userMapper.userRequestToUserEntityUpdate(userRequest);
      UserEntity response = userService.update(user, id);
      return userMapper.userEntityToUserDTO(response);
   }

   @Override
   public void deleteUser(String id) throws Exception {
       userService.delete(UUID.fromString(id));
   }
}
