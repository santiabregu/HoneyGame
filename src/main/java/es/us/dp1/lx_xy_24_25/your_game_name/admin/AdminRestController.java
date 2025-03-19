package es.us.dp1.lx_xy_24_25.your_game_name.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
 
@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin", description = "Mini API for the management of Admin")
public class AdminRestController {
    
    @Autowired
    AdminService as;

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> findById(@PathVariable(name = "adminId") Integer adminId){
        return new ResponseEntity<>(as.findAdminById(adminId),HttpStatus.OK);
    }
}
