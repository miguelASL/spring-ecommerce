package com.curso.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @GetMapping("/home")
    public String home(){
        return "administrador/home";
    }
}
