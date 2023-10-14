package com.curso.ecommerce.controllers;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;
    // Para almacenar los productos que se agregan al carrito
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    // Datos de la orden
    Orden orden = new Orden();
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }
    @GetMapping("/productohome/{id}")
    public String productHome(@PathVariable Integer id, Model model){
        log.info("Mostrando producto con id: {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = ProductoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/car")
    public String carrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalle = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> productoOptional = ProductoService.get(id);
        assert Objects.requireNonNull(productoOptional).isPresent();
        log.info("Mostrando producto con id: {}", productoOptional.get());
        log.info("Cantidad: {}", cantidad);
        return "usuario/carrito";
    }
}