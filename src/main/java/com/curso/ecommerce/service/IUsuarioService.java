package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUsuarioService {
    Optional<Usuario> findById(Integer id);
}
