package com.SmartPannel.userData.Service;

import com.SmartPannel.Repositories.roleRepository;
import com.SmartPannel.Repositories.userRepository;
import com.SmartPannel.userData.Model.Roles;
import com.SmartPannel.userData.Model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class UserService implements userInterf{
    @Autowired
    private final userRepository userRepo;
    private final roleRepository roleRepo;


    @Override
    public Users saveUser(Users users) {
        return userRepo.save(users);
    }

    @Override
    public Roles saveRole(Roles role) {
        return roleRepo.save(role);
    }

    @Override
    public List<Users> ListAll() {

        return userRepo.findAll();
    }

    @Override
    public void addRole(Long userId, String roleName) {
        Roles role = roleRepo.findByName(roleName);
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        user.addRole(new Roles(roleName));
    }

    @Override
    public Optional<Users> getUser(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUser(Long id) {

    }
}
