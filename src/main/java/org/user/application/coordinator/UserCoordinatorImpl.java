package org.user.application.coordinator;


import org.springframework.stereotype.Service;
import org.user.application.domain.entities.User;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.web.dto.UserDTO;
import org.user.infraestructor.mapper.UserMapper;
import org.user.application.domain.service.UserService;

import java.util.UUID;

@Service
public class UserCoordinatorImpl implements UserCoordinator {
   private final UserService userService;
   private  final UserMapper userMapper;

   public UserCoordinatorImpl(UserService userService, UserMapper userMapper) {
      this.userService = userService;
      this.userMapper = userMapper;
   }

   @Override
   public FullUserDTO getUser(String id) throws Exception {
      User response;
      try {
          response = userService.findById(UUID.fromString(id));
      } catch (Exception e) {

         throw new Exception("el ID no cumple con el formato UUID");
      }

      return userMapper.UserToFullUser(response);
   }

   @Override
   public UserDTO createUser(UserRequest userRequest) throws Exception {

      User user = userMapper.userRequestToUser(userRequest);
      User response = userService.save(user);

      return userMapper.userToUserDTO(response);
   }

   @Override
   public UserDTO updateUser(UserRequest userRequest, String id) throws Exception {

      User user = userMapper.userRequestToUser(userRequest);
      User response = userService.update(user, id);

      return userMapper.userToUserDTO(response);
   }

   @Override
   public void deleteUser(String id) throws Exception {
       userService.delete(UUID.fromString(id));
   }
}
