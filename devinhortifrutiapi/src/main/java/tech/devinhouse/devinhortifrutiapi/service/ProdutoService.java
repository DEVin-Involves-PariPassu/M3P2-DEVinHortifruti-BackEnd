package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.devinhortifrutiapi.dto.ProdutoDTO;
import tech.devinhouse.devinhortifrutiapi.model.Produto;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import tech.devinhouse.devinhortifrutiapi.repository.ProdutoRepository;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public Long adicionaProduto(ProdutoDTO produtoDTO){
        validaNomeProduto(produtoDTO);
        validaDescricao(produtoDTO);
        validaUrlFoto(produtoDTO);
        validaPrecoProduto(produtoDTO);
        validaProdutoAtivo(produtoDTO);
        Produto produto = new Produto();

        produto.setId(produtoDTO.getId());
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setUrlFoto(produtoDTO.getUrlFoto());
        produto.setPrecoSugerido(produtoDTO.getPrecoSugerido());
        produto.setIsAtivo(produtoDTO.getIsAtivo());

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
        if (!produtoDTO.getIsAtivo()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto está inativo.");
        }
    }

    @Transactional
    public Long atualizar(Long id_produto,
                          ProdutoDTO produtoDTO) {
        Produto produto = atualizarProduto(id_produto, produtoDTO);

        produto.setDescricao(produtoDTO.getDescricao());
        produto.setUrlFoto(produtoDTO.getUrlFoto());
        produto.setPrecoSugerido(produtoDTO.getPrecoSugerido());
        produto.setIsAtivo(produtoDTO.getIsAtivo());

        return id_produto;
    }

    public Produto atualizarProduto(Long id_produto, ProdutoDTO produtoDTO){
        validaDescricao(produtoDTO);
        validaUrlFoto(produtoDTO);
        validaPrecoProduto(produtoDTO);

        Produto produto = this.produtoRepository.findById(id_produto).orElseThrow(() ->
                new EntityNotFoundException("Não há produto com este Id."));

        if(produtoDTO.getNome() != null && !produtoDTO.getNome().isBlank()){
            isUnicoOuOMesmo(produtoDTO, id_produto, produto);
        }

        return produto;
    }

    public void isUnicoOuOMesmo (ProdutoDTO produtoDTO, Long id_produto, Produto produto) {
        Optional<Produto> optionalProduto = this.produtoRepository.findAllByNome(produtoDTO.getNome());

        if(optionalProduto.isPresent() && !Objects.equals(id_produto, this.produtoRepository.findAllByNome(produtoDTO.getNome()).get().getId())) {
            throw new EntityExistsException("Já existe Produto com o nome " + produtoDTO.getNome() + ".");
        }
        if(optionalProduto.isEmpty()) {
            produto.setNome(produtoDTO.getNome());
        }
    }

    public Produto getProduto(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long idUsuarioLogado = (Long) authentication.getPrincipal();
        Usuario usuarioLogado = usuarioRepository.findUsuarioById(idUsuarioLogado).get();

        if (!usuarioLogado.getIsAdmin()) {
            throw new IllegalArgumentException("Usuário não é um administrador!");
        }

        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);
        if(produtoOptional.isEmpty()) {
            throw new EntityNotFoundException("Produto não Encontrado!");
        }
        return produtoOptional.get();
    }
}
