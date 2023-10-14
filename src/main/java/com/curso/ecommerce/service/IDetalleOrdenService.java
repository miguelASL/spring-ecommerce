package com.curso.ecommerce.service;

import com.curso.ecommerce.model.DetalleOrden;
import org.springframework.stereotype.Service;

@Service
public interface IDetalleOrdenService {
    DetalleOrden save(DetalleOrden detalleOrden);
}
