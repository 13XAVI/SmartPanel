package com.SmartPannel.pannelData.Controller;

import com.SmartPannel.Repositories.userRepository;
import com.SmartPannel.pannelData.Model.panelSpecification;
import com.SmartPannel.userData.Model.Users;
import com.SmartPannel.userData.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@Controller
public class pannelController {
    @Autowired
    private ProductService service;

    @Autowired
    private userRepository userRepo;

    @GetMapping("/ListProducts")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR"})
    public ResponseEntity list(Model model) {
        List<panelSpecification> panels = service.listAll();
        model.addAttribute("panels", panels);
        return ResponseEntity.ok(panels);
    }

    @PostMapping("/products")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> create(@ModelAttribute("panel") @Valid panelSpecification panel, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("createProduct");
            return ResponseEntity.ok(request);
        }
        panelSpecification savedPanel = service.save(panel);
        URI productURI = URI.create(request.getContextPath() + "/products/" + savedPanel.getPanelId());
        ModelAndView modelAndView = new ModelAndView("redirect:" + productURI);
        return ResponseEntity.ok(ResponseEntity.created(productURI));
    }

    @GetMapping("/HomePage")
    public String viewHomePage(HttpSession session, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        session.setAttribute("email", email);

        model.addAttribute("email", email);
        return "Home";
    }
    @GetMapping("/Register")
    public String ShowSignupForm(Model model){
        model.addAttribute("user",new panelSpecification());
        return "SignupH";
    }

    @RequestMapping(value = "/list_users",method = RequestMethod.GET)
    public String ViewList(Model model, HttpServletRequest request){
        List<Users> listUsers = userRepo.findAll();
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();
        model.addAttribute("listUsers",listUsers);
        return "list_users";
    }
    @RequestMapping ("/list_Hotel")
    public String ViewAllList(Model model){
        List<panelSpecification> listHotel = service.listAll();
        model.addAttribute("listHotel",listHotel );
        return "list_Hotel";
    }
    @RequestMapping  ("/new")
    public String NewAllHotel( Model model){
        panelSpecification hotel = new panelSpecification();
        model.addAttribute("hotel",hotel );
        return "new_Hotel";
    }
    @RequestMapping(value = "/Save",method = RequestMethod.POST )
    public String SaveHotel(@RequestBody @Validated @ModelAttribute("hotel") panelSpecification panel , RedirectAttributes redirectAttributes , Users user){
        try {
            service.save(panel);
            redirectAttributes.addFlashAttribute("message","Data has been saved successfully");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Welcome to our hotel!");
            message.setText("Dear " + user.getUsername() + ",\n\nThank you for registering with us. We look forward to serving you.\n\nBest regards,\nThe Hotel Team");
            //mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "redirect:/list_Hotel";
        }

    }

    @RequestMapping ("/edit/{id}")
    public ModelAndView showEditHotel(@PathVariable(name ="id") Long id){
        ModelAndView mav = new ModelAndView("edit_Hotel");
        panelSpecification hotel = service.get(id);
        mav.addObject("hotel", hotel);
        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id")Long id){
        service.delete(id);
        return "list_users";
    }
}
