package tech.devinhouse.devinhortifrutiapi.service;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import tech.devinhouse.devinhortifrutiapi.repository.ItemVendaRepository;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;

public class ItemVendaServiceTests {

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @InjectMocks
    private ItemVendaService itemVendaService = new ItemVendaService();


}
