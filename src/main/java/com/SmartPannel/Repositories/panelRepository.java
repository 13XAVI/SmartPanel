package com.SmartPannel.Repositories;

import com.SmartPannel.pannelData.Model.panelSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface panelRepository extends JpaRepository <panelSpecification,Long> {
}
