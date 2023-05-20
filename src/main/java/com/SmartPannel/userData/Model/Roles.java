package com.SmartPannel.userData.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;


    public Roles (Long id){
         super();
        this.id=id;
    }
     public  Roles (String name){
       super();
       this.name = name;
     }


}
