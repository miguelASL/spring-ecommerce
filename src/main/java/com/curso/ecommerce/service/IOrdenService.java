package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;
import org.springframework.stereotype.Service;

@Service
public interface IOrdenService {
    Orden save(Orden orden);
}
