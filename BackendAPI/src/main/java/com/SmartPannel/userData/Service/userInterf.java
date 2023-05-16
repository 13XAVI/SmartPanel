package com.SmartPannel.userData.Service;

import com.SmartPannel.userData.Model.Roles;
import com.SmartPannel.userData.Model.Users;

import java.util.List;
import java.util.Optional;

public interface userInterf {

     Users saveUser(Users users);
     Roles saveRole(Roles role);
     List<Users> ListAll ();
     public void addRole(Long userId, String roleName);
     Optional<Users> getUser (Long id);
     void  deleteUser(Long id);

}
