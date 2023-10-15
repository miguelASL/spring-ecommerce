package com.curso.ecommerce.controllers;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IDetalleOrdenService;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IProductoService;
import com.curso.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService IProductoService;
    // Para almacenar los productos que se agregan al carrito
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    // Datos de la orden
    Orden orden = new Orden();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("productos", IProductoService.findAll());
        return "usuario/home";
    }

    @GetMapping("/productohome/{id}")
    public String productHome(@PathVariable Integer id, Model model) {
        log.info("Mostrando producto con id: {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = com.curso.ecommerce.service.IProductoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/carrito")
    public String carrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalle = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> productoOptional = com.curso.ecommerce.service.IProductoService.get(id);
        assert Objects.requireNonNull(productoOptional).isPresent();
        log.info("Mostrando producto con id: {}", productoOptional.get());
        log.info("Cantidad: {}", cantidad);
        producto = productoOptional.get();

        detalle.setCantidad(cantidad);
        detalle.setPrecio(producto.getPrecio());
        detalle.setNombre(producto.getNombre());
        detalle.setPrecio(producto.getPrecio());
        detalle.setProducto(producto);

        //Validar si el producto ya existe en el carrito
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
        if (!ingresado) {
            detalles.add(detalle);
        }

        sumaTotal = detalles.stream().mapToDouble(DetalleOrden::getTotal).sum();
        return "usuario/carrito";
    }

    // Quita un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProducto(@PathVariable Integer id, Model model) {
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>(); // Lista para almacenar los productos que no se eliminan
        for (DetalleOrden detalle : detalles) {
            if (detalle.getProducto().getId() != id) {
                ordenesNueva.add(detalle);
            }
        }
        // ponemos la nueva lista en la lista original
        detalles = ordenesNueva;
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(DetalleOrden::getTotal).sum();
        return "usuario/carrito";
    }

    @GetMapping("/getCarrito")
    public String getCarrito(Model model) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
/*        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(DetalleOrden::getTotal).sum();*/
        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model) {
        Usuario usuario = usuarioService.findById(1).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorder";
    }

    // Guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        // Usuario
        Usuario usuario = usuarioService.findById(1).get();
        orden.setUsuario(usuario);
        ordenService.save(orden);

        // Detalle de la orden
        for (DetalleOrden dt : detalles) {
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        // Limpiar el carrito
        orden = new Orden();
        detalles.clear();
        return "redirect:/";
    }
}