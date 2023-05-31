package com.SmartPannel.pannelData.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class panelSpecification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long panelId;
    private String type;
    private String maxpowerOutput;
    private String dimensions;
    private String weight;
    private String price;
    private String warrant;
    private String panelAddress;

}
