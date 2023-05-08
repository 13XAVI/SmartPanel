package com.SmartPannel.Repositories;

import com.SmartPannel.userData.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
public interface roleRepository extends JpaRepository<Roles,Long> {
}
