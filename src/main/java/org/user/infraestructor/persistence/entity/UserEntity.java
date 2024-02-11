package org.user.infraestructor.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String name;
    private String email;
    private String password;

    @ElementCollection
    private List<Phone> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    @Builder.Default
    private boolean isactive = true;

    @Embeddable
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
