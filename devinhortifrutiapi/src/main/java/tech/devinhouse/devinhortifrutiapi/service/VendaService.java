package tech.devinhouse.devinhortifrutiapi.service;

import tech.devinhouse.devinhortifrutiapi.repository.VendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;


}
