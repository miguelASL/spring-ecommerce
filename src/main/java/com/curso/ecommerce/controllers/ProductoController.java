package com.curso.ecommerce.controllers;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class); //Logger para mostrar mensajes en consola
    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String show(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "productos/index";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto) {
        LOGGER.info("Guardando producto: {}");
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        productoService.save(producto);
        return "redirect:/productos/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.get(id);

        LOGGER.info("Editando producto: {}", producto);
        model.addAttribute("producto", producto);
/*        if (optionalProducto.isPresent()) {
            producto = optionalProducto.get();
            model.addAttribute("producto", producto);*/
            return "productos/edit";
        }
/*        return null;
    }*/
    @PostMapping("/update")
    public String update(Producto producto){
        ProductoService.update(producto);
        return "redirect:/productos";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        productoService.delete(id);
        return "redirect:/productos";
    }
}
