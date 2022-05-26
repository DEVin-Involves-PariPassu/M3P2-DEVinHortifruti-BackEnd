package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tech.devinhouse.devinhortifrutiapi.repository.CompradorRepository;

@Service
public class CompradorService {

    @Autowired
    CompradorRepository compradorRepository;

}
