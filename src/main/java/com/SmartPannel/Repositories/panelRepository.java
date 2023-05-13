package com.SmartPannel.Repositories;

import com.SmartPannel.pannelData.Model.panelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface panelRepository extends JpaRepository <panelSpecification,Long> {
    @Query("SELECT p.type, p.maxpowerOutput, p.dimensions, p.weight, p.price, p.warrant\n" +
            "FROM panelSpecification p\n" +
            "WHERE p.type LIKE :keyword\n")
    List<panelSpecification> findByKeyword(String keyword);
}
