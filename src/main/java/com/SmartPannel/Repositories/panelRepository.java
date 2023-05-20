package com.SmartPannel.Repositories;

import com.SmartPannel.pannelData.Model.panelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface panelRepository extends JpaRepository <panelSpecification,Long> {
    @Query("SELECT p.type, p.maxpowerOutput, p.dimensions, p.weight, p.price, p.warrant " +
            "FROM panelSpecification p " +
            "WHERE p.type LIKE %:keyword%")

    List<panelSpecification> findByKeyword(@Param("keyword")String keyword );
}
