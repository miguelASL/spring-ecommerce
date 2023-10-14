package com.curso.ecommerce.controllers;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class); //Logger para mostrar mensajes en consola
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UploadFileService upload;

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
    public String save(Producto producto,@RequestParam("img") MultipartFile file) {
        LOGGER.info("Guardando producto: {}");
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);

        //imagen
        if (producto.getId() == null){
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }else {
            if (!file.isEmpty()) { // editamos el producto pero no cambiamos la imagen
                Producto p = new Producto();
                p = ProductoService.get(producto.getId()).get();
                producto.setImagen(p.getImagen());
            } else { // editamos el producto y cambiamos la imagen
                String nombreImagen = upload.saveImage(file);
                producto.setImagen(nombreImagen);
            }
        }
        productoService.save(producto);
        return "redirect:/productos/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = ProductoService.get(id);

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
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p = new Producto();
        p = ProductoService.get(producto.getId()).get();
        if (file.isEmpty()){
            producto.setImagen(p.getImagen());
        } else{ //Cuando se edita tambien la imagen
            if (p.getImagen().equals("default.jpg")) {
                upload.deleteImage(p.getImagen());
            }
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(producto.getUsuario());
        ProductoService.update(producto);
        return "redirect:/productos";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Producto p = new Producto();
        p = ProductoService.get(id).get();
        if (p.getImagen().equals("default.jpg")) {
            upload.deleteImage(p.getImagen());
        }
        productoService.delete(id);
        return "redirect:/productos";
    }
}
