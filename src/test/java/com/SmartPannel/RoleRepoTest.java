package com.SmartPannel;

import com.SmartPannel.Repositories.roleRepository;
import com.SmartPannel.Repositories.userRepository;
import com.SmartPannel.userData.Model.Roles;
import com.SmartPannel.userData.Model.Users;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleRepoTest {
    @Autowired private roleRepository repo;
    @Autowired private userRepository userRepo;



    @Test
    public void TestCreateRole(){
        Roles admin = new Roles(1L,"ROLE_ADMIN");
        Roles customer = new Roles(2L,"ROLE_CUSTOMER");
        Roles Distributor = new Roles (3L,"ROLE_DISTRIBUTOR");

        repo.saveAll(List.of(admin,customer,Distributor));
        Long numberOfRows = repo.count();

        assertEquals(3,numberOfRows);

    }
    @Test
    public  void  testListRole(){
      List <Roles> ListRole= repo.findAll();
        assertNotNull(ListRole);
        assertNotNull(ListRole);
        Long numberOfRows = repo.count();
        assertEquals(3,numberOfRows);
    }



}
