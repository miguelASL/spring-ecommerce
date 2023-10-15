package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrdenService {
    List<Orden> findAll();
    Orden save(Orden orden);
    String generarNumeroOrden();
}
