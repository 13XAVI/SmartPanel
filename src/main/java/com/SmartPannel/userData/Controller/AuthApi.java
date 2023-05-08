package com.SmartPannel.userData.Controller;

import com.SmartPannel.userData.JWT.jwtTokenApi;
import com.SmartPannel.userData.Model.UserResponse;
import com.SmartPannel.userData.Model.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthApi {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    jwtTokenApi tokenApi;

    @GetMapping("/auth/login")
    public String login(Model model) {
        model.addAttribute("userRequest", new Users());
        return "redirect:loginHandle";
    }
    @PostMapping("/auth/login")
    public ResponseEntity <?> loginHandle(@RequestBody  @Valid Users request) {
        System.out.println("Inside loginHandle method");
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
}
