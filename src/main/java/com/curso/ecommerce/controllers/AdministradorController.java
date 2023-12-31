package com.curso.ecommerce.controllers;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IProductoService IProductoService;

    @GetMapping("/home")
    public String home(Model model){
        List<Producto> productos = IProductoService.findAll();
        model.addAttribute("productos", productos);
        return "administrador/home";
    }
}
