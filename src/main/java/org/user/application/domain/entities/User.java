package org.user.application.domain.entities;

import lombok.Builder;
import lombok.Data;
import org.user.infraestructor.persistence.entity.UserEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class User {

    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String password;

    private List<User.Phone> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isactive;

    public static class Phone {
        private String number;
        private String citycode;
        private String contrycode;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getContrycode() {
            return contrycode;
        }

        public void setContrycode(String contrycode) {
            this.contrycode = contrycode;
        }
    }
}

