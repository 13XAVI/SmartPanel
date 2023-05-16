package com.SmartPannel.Repositories;

import com.SmartPannel.userData.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface userRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);

}
