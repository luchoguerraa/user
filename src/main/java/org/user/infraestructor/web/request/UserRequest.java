package org.user.infraestructor.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserRequest {
    private String name;
    @NotBlank(message = "El campo email no puede estar en blanco")
    @Pattern(regexp = "^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$", message = "El formato del email no es válido")
    private String email;
    private String password;
    private List<Phone> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

}
