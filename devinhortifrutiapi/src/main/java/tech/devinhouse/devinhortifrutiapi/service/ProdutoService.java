package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
        produto.setPrecoSugerido(produtoDTO.getPrecoSugerido());
        produto.setAtivo(produtoDTO.getAtivo());

        produtoRepository.save(produto);
        return produto.getId();
    }

    private void validaNomeProduto(ProdutoDTO produtoDTO){
        if (produtoDTO.getNome() == null || produtoDTO.getNome().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é requerido.");
        }
        Optional<Produto> optionalProduto = this.produtoRepository.findAllByNome(produtoDTO.getNome());

        if (optionalProduto.isPresent()) {
            throw new EntityExistsException("Este nome de produto já existe.");
        }
    }

    private void validaDescricao(ProdutoDTO produtoDTO){
        if (produtoDTO.getDescricao() == null || produtoDTO.getDescricao().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A descrição é requerida.");
        }
    }

    private void validaUrlFoto(ProdutoDTO produtoDTO){
        if (produtoDTO.getUrlFoto() == null || produtoDTO.getUrlFoto().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A URL da foto é requerida.");
        }
    }

    private void validaPrecoProduto(ProdutoDTO produtoDTO){
        if (produtoDTO.getPrecoSugerido() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço é requerido.");
        }
        if (produtoDTO.getPrecoSugerido().compareTo(BigDecimal.ZERO) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor não pode ser negativo.");
        }
    }

    private void validaProdutoAtivo(ProdutoDTO produtoDTO){
        if (!produtoDTO.getAtivo()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto está inativo.");
        }
    }

    public void atualizar(Long id_produto,
                            ProdutoDTO produtoDTO) {
        Produto produto = atualizarProduto(id_produto, produtoDTO);

        this.produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id_produto, ProdutoDTO produtoDTO){
        validaNomeProduto(produtoDTO);
        validaDescricao(produtoDTO);
        validaUrlFoto(produtoDTO);
        validaPrecoProduto(produtoDTO);
        validaProdutoAtivo(produtoDTO);

       Produto produto = this.produtoRepository.findById(id_produto).orElseThrow(() ->
                new EntityNotFoundException("Não há produto com este Id."));

        produto.setId(produtoDTO.getId());
        produto.setName(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setUrlFoto(produtoDTO.getUrlFoto());
        produto.setPrecoSugerido(produtoDTO.getPrecoSugerido());
        produto.setAtivo(produtoDTO.getAtivo());

        return produto;
    }

}
