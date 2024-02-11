package org.user.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.user.application.UserRequest;
import org.user.application.adapter.UserPresenter;
import org.user.application.common.CommonAttribute;
import org.user.application.dto.UserDTO;
import org.user.application.exception.AttributeException;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserPresenter userPresenter;
   // private final CommonAttribute commonAtributte;
    public UserController(UserPresenter userPresenter/*, CommonAttribute commonAtributte*/) {
        this.userPresenter = userPresenter;
      //  this.commonAtributte = commonAtributte;
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserRequest userRequest) throws Exception {
       // validatePassword(userRequest.getPassword());
       // validateEmail(userRequest.getEmail());
        return ResponseEntity.ok(userPresenter.createUser(userRequest));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(userPresenter.getUser(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
        userPresenter.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserRequest userRequest,
                                          @PathVariable String id ) throws Exception {
       // validatePassword(userRequest.getPassword());
       // validateEmail(userRequest.getEmail());
        UserDTO response = userPresenter.updateUser(userRequest, id);
        return ResponseEntity.ok(response);
    }
   /* private void validateEmail(String email) {
       if(!commonAtributte.validateEmail(email)){
           throw new AttributeException("El correo no cumple con el formato.", HttpStatus.BAD_REQUEST);
       }
     }

     private void validatePassword(String pass) {
         if(!commonAtributte.validatePassword(pass)){
             throw new AttributeException("La password no cumple con el formato.", HttpStatus.BAD_REQUEST);
         }
     }*/
    }