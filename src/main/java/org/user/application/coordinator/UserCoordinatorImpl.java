package org.user.application.coordinator;


import org.springframework.stereotype.Service;
import org.user.application.domain.entities.User;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.request.UserRequest;
import org.user.infraestructor.web.dto.UserDTO;
import org.user.infraestructor.mapper.UserMapper;
import org.user.application.domain.service.UserService;

import java.time.LocalDateTime;
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
   public FullUserDTO getUser(UUID id) throws Exception {
      return userMapper.UserToFullUser(userService.findById(id));
   }

   @Override
   public UserDTO createUser(UserRequest userRequest) throws Exception {
      User user = userMapper.userRequestToUser(userRequest);
      User response = userService.save(user);

      return userMapper.userToUserDTO(response);
   }

   @Override
   public UserDTO updateUser(UserRequest userRequest, UUID id) throws Exception {
      User user = userMapper.userRequestToUser(userRequest);
      User response = userService.update(user, id, LocalDateTime.now());

      return userMapper.userToUserDTO(response);
   }

   @Override
   public void deleteUser(UUID id) throws Exception {
       userService.delete(id);
   }
}
