package com.SmartPannel.pannelData.Controller;

import com.SmartPannel.pannelData.Model.panelSpecification;
import com.SmartPannel.pannelData.panelService.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.time.temporal.ValueRange;
import java.util.List;



@RestController
@RequestMapping
@CrossOrigin("*")
public class pannelController {
    @Autowired
    private ProductService service;

    @GetMapping("/HomePage")
    public ModelAndView viewHomePage(Model model, @RequestHeader("Authorization") String authorization) {
        ModelAndView modelAndView = new ModelAndView("Home");
        model.addAttribute("token", authorization);
        return modelAndView;
    }





    @GetMapping("/ListProducts")
    @ResponseBody
    @Cacheable(value = "panelSpecification")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR","ROLE_ADMIN"})
    public ResponseEntity list(Model model) {
        List<panelSpecification> panels = service.listAll();
        model.addAttribute("panels", panels);
        return ResponseEntity.ok(panels);
    }

    @PostMapping("/products")
    @CachePut(value = "panelSpecification",key = "#panel")
    @RolesAllowed({"ROLE_ADMIN","ROLE_DISTRIBUTOR"})
    public ResponseEntity<?> create(@RequestBody @Valid panelSpecification panel, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        panelSpecification savedPanel = service.save(panel);
        URI productURI = URI.create(request.getContextPath() + "/products/" + savedPanel.getPanelId());

        return ResponseEntity.created(productURI).body(savedPanel);

    }


    @GetMapping("/product/{panelId}")
    @Cacheable(value = "panelSpecification", key = "#panelId")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR", "ROLE_ADMIN"})
    public ResponseEntity<?> getProductById(@PathVariable("panelId") Long panelId) {
        panelSpecification panel = service.get(panelId);
        if (panel == null) {
            String message = "Panel not found for ID: " + panelId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(panel);
    }


    @PutMapping("/product/{panelId}")
    @CachePut(value = "panelSpecification", key = "#panelId")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_DISTRIBUTOR"})
    public ResponseEntity<?> updatePanel(
            @Valid @PathVariable("panelId") Long panelId,
            @RequestBody panelSpecification updatedPanel) {

        panelSpecification panel = service.get(panelId);
        if (panel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        panel.setType(updatedPanel.getType());
        panel.setMaxpowerOutput(updatedPanel.getMaxpowerOutput());
        panel.setDimensions(updatedPanel.getDimensions());
        panel.setWeight(updatedPanel.getWeight());
        panel.setWarrant(updatedPanel.getWarrant());

        service.save(panel);
        return ResponseEntity.ok(panel);
    }

    @CacheEvict(value = "panelSpecification",key = "#panelId")
    @DeleteMapping("/product/{panelId}")
    @RolesAllowed({"ROLE_ADMIN","ROLE_DISTRIBUTOR"})
    public ResponseEntity<?> Delete (@Valid @PathVariable("panelId")  Long panelId) {
        panelSpecification panel = service.get(panelId);
        if (panel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.delete(panelId);
        return ResponseEntity.status(HttpStatus.OK).body("Panel Deleted Successfully");
    }
    @GetMapping("/products/find/")
    @Cacheable(value = "panelSpecification",key = "#keyword")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_DISTRIBUTOR", "ROLE_ADMIN"})
    public ResponseEntity<List<panelSpecification>> searchProducts(@PathVariable("keyword") String keyword) {
        List<panelSpecification> searchResults = service.search(keyword);
        return ResponseEntity.ok(searchResults);
    }



}
