package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Repository
public class UsuarioController {
    @Autowired
    private UsuarioService service;
}
