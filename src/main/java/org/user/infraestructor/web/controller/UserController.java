package org.user.infraestructor.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.user.infraestructor.web.dto.FullUserDTO;
import org.user.infraestructor.web.request.UserRequest;
import org.user.application.coordinator.UserCoordinator;
import org.user.infraestructor.common.CommonAttribute;
import org.user.infraestructor.web.dto.UserDTO;
import org.user.infraestructor.web.exception.AttributeException;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserCoordinator userCoordinator;
    private final CommonAttribute commonAttribute;
    public UserController(UserCoordinator userPresenter, CommonAttribute commonAtributte) {
        this.userCoordinator = userPresenter;
        this.commonAttribute = commonAtributte;
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserRequest userRequest) throws Exception {
        validatePassword(userRequest.getPassword());
        validateEmail(userRequest.getEmail());
        return ResponseEntity.ok(userCoordinator.createUser(userRequest));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<FullUserDTO> find(@PathVariable String id) throws Exception {
        validateID(id);
        return ResponseEntity.ok(userCoordinator.getUser(UUID.fromString(id)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
        validateID(id);
        userCoordinator.deleteUser(UUID.fromString(id));
        return ResponseEntity.ok("Usuario eliminado con exito");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserRequest userRequest,
                                          @PathVariable String id ) throws Exception {
        validateID(id);
        validatePassword(userRequest.getPassword());
        validateEmail(userRequest.getEmail());
        UserDTO response = userCoordinator.updateUser(userRequest, UUID.fromString(id));
        return ResponseEntity.ok(response);
    }
    private void validateEmail(String email) {
       if(!commonAttribute.validateEmail(email)){
           throw new AttributeException("El correo no cumple con el formato.", HttpStatus.BAD_REQUEST);
       }
     }

     private void validatePassword(String pass) {
         if(!commonAttribute.validatePassword(pass)){
             throw new AttributeException("La password no cumple con el formato.", HttpStatus.BAD_REQUEST);
         }
     }

    private void validateID(String id) {
        try {
            UUID.fromString(id);
        } catch (Exception e) {
            throw new AttributeException("El ID no cumple con el formato UUID",HttpStatus.BAD_REQUEST);
        }
    }
    }