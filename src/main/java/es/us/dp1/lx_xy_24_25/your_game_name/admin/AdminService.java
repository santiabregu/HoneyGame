package es.us.dp1.lx_xy_24_25.your_game_name.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.exceptions.ResourceNotFoundException;


@Service
public class AdminService {
    
    AdminRepository ar;


    @Autowired
    public AdminService(AdminRepository ar){
        this.ar= ar;
    }

    @Transactional(readOnly = true)
    public Admin findAdminById(Integer id){
        return ar.findById(id).orElseThrow(()-> new ResourceNotFoundException("no se ha encontrado el admin"));
    }
}
