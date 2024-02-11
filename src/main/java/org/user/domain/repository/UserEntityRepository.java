package org.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.user.infraestructor.entity.UserEntity;

import java.util.UUID;

@Service
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT A.email FROM UserEntity A WHERE lower(A.email) = lower(:alias)")
    String findAllEmailByEmail(@Param("alias") String email);

}
