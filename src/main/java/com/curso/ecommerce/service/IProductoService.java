package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IProductoService {
    public Producto save(Producto producto);

    public static Optional<Producto> get(Integer id) {
        return null;
    }

    public static void update(Producto producto) {

    }

    public void delete(Integer id);
    public List<Producto> findAll();
}
