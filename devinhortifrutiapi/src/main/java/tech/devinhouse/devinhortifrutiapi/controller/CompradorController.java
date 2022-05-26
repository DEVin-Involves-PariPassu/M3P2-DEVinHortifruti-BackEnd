package tech.devinhouse.devinhortifrutiapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import tech.devinhouse.devinhortifrutiapi.service.CompradorService;

@RestController
@RequestMapping("/comprador")
public class CompradorController {

    @Autowired
    CompradorService compradorService;

}
