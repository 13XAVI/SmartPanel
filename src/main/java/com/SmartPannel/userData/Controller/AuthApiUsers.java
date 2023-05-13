package com.SmartPannel.userData.Controller;

import com.SmartPannel.Repositories.roleRepository;
import com.SmartPannel.Repositories.userRepository;
import com.SmartPannel.pannelData.Model.panelSpecification;
import com.SmartPannel.userData.JWT.jwtTokenApi;
import com.SmartPannel.userData.Model.Roles;
import com.SmartPannel.userData.Model.UserResponse;
import com.SmartPannel.userData.Model.Users;
import com.SmartPannel.pannelData.panelService.ProductService;
import com.SmartPannel.userData.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AuthApiUsers {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    jwtTokenApi tokenApi;
    @Autowired
    private UserService userService;
    @Autowired
    private userRepository userRepo;
    @Autowired
    private roleRepository roleRepo;
    @Autowired
    private userRepository repo;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/auth/login")
    public String login(Model model) {
        model.addAttribute("userRequest", new Users());
        return "redirect:loginHandle";
    }
    @PostMapping("/auth/login")

    public ResponseEntity <?> loginHandle(@RequestBody  Users request) {
        try {
//            System.out.println("Email: " + request.getEmail() + ", Password: " + userRequest.getPassword());
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            Users user = (Users) authentication.getPrincipal();
            String accessToken = tokenApi.generateAccToken(user);
            UserResponse  response = new UserResponse(user.getEmail(),accessToken);

            ModelAndView mav = new ModelAndView("userResponse");
            mav.addObject("userResponse", response);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException exception) {
            ModelAndView mav = new ModelAndView("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


        @PostMapping("/auth/Register")
        public ResponseEntity<Users> saveUser(@Valid @RequestBody Users users) {
            try {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encoded = encoder.encode(users.getPassword());
                System.out.println(users.getPassword());
                users.setPassword(encoded);

                Roles customer = new Roles(2L,"ROLE_CUSTOMER");
                users.getRoles().add(customer);
                userService.saveUser(users);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(users.getUsername());
                message.setSubject("Welcome to Smart Panel!");
                message.setText("Dear " + users.getUsername() + ",\n\nThank you for registering with us. We look forward to serving you.\n\nBest regards,\nThe Hotel Team");
                //mailSender.send(message);
                return ResponseEntity.status(HttpStatus.CREATED).body(users);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    @PostMapping("/grantRole")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> grantRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        Users user = repo.findById(userId).orElse(null);
        Roles role = roleRepo.findById(roleId).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.badRequest().body("Invalid user or role ID");
        }
        user.addRole(role);
        repo.save(user);

        return ResponseEntity.ok("Role granted successfully to the user");
    }


    @GetMapping("/Users/get/{UserId}")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR", "ROLE_ADMIN"})
    public ResponseEntity<?> getUserById(@PathVariable("UserId") Long UserId) {
        Optional<Users> users = userService.getUser(UserId);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }


    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/Users/Update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id") Long id, @Valid @RequestBody Users users) {
        try {
            Optional<Users> existingUser = repo.findById(id);
            if (existingUser.isPresent()) {
                Users userToUpdate = existingUser.get();

                userToUpdate.setName(users.getUsername());
                userToUpdate.setEmail(users.getEmail());
                userToUpdate.setPassword(users.getPassword());

                Roles customer = new Roles("ROLE_CUSTOMER");
                users.getRoles().add(customer);
                repo.save(userToUpdate);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(users.getUsername());
                message.setSubject("Welcome to Smart Panel!");
                message.setText("Dear " + users.getUsername() + ",\n\nThank you for registering with us. We look forward to serving you.\n\nBest regards,\nThe Smart Panel Team");
                //mailSender.send(message);
                return ResponseEntity.ok(userToUpdate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/Users/Delete/{UserId}")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR", "ROLE_ADMIN"})
    public ResponseEntity<?> DeleteUser(@PathVariable("UserId") Long UserId) {
        Optional<Users> users = userService.getUser(UserId);
        userService.deleteUser(UserId);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
    }

    @GetMapping("/Users/List")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR", "ROLE_ADMIN"})
    public ResponseEntity ListAll( ) {
        List<Users> Results = userService.ListAll();
        return ResponseEntity.ok(Results);
    }



    @GetMapping("/find/{userId}")
    public ResponseEntity<Users> getUser(@PathVariable Long userId) {
        Optional<Users> user = userService.getUser(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
