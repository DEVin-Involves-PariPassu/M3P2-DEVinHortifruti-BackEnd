package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Long adicionaProduto(ProdutoDTO produtoDTO){
        validaNomeProduto(produtoDTO);
        validaDescricao(produtoDTO);
        validaUrlFoto(produtoDTO);
        validaPrecoProduto(produtoDTO);
        validaProdutoAtivo(produtoDTO);
        Produto produto = new Produto();

        produto.setId(produtoDTO.getId());
        produto.setName(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setUrlFoto(produtoDTO.getUrlFoto());
        produto.setPrecoSugerido(produtoDTO.getPrecoAtual());
        produto.setAtivo(produtoDTO.getActive());

        produtoRepository.save(produto);
        return produto.getId();
    }

    private void validaNomeProduto(ProdutoDTO produtoDTO){
        if (produtoDTO.getNome() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é requerido");
        }
        Optional<Produto> produto =  produtoRepository.findAllByNome(produtoDTO.getNome());
        if (!produto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O nome do produto já existe");
        }
    }

    private void validaDescricao(ProdutoDTO produtoDTO){
        if (produtoDTO.getDescricao() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A descrição é requerida");
        }
    }

    private void validaUrlFoto(ProdutoDTO produtoDTO){
        if (produtoDTO.getUrlFoto() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A URL da Foto é requerida");
        }
    }

    private void validaPrecoProduto(ProdutoDTO produtoDTO){
        if (produtoDTO.getPrecoAtual().compareTo(BigDecimal.ZERO) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor não pode ser negativo");
        }
    }

    private void validaProdutoAtivo(ProdutoDTO produtoDTO){
        if (produtoDTO.getActive() != true){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto está inativo");
        }
    }

}
