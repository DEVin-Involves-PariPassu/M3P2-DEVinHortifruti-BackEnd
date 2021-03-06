package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import tech.devinhouse.devinhortifrutiapi.service.exception.AccessDeniedException;

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

    @Autowired
    UsuarioService usuarioService;

    @Transactional
    public Produto listarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Não existe Produto com o id "+id)
        );
    }

    public Page<Produto> listarProdutos(String nome, Integer pagina, Integer tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina -1, tamanhoPagina);

        return produtoRepository.findAll(Specification.where(SpecificationsProduto.nome(nome)), pageable);
    }

    public Long adicionaProduto(ProdutoDTO produtoDTO){

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setUrlFoto(produtoDTO.getUrlFoto());
        produto.setPrecoSugerido(produtoDTO.getPrecoSugerido());
        produto.setIsAtivo(produtoDTO.getIsAtivo());
        produtoRepository.save(produto);
        return produto.getId();
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

    @Transactional
    public Produto getProduto(Long id) {
        return produtoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Produto não encontardo"));
    }

    public void verificaAdmin(String auth) {
        if (!usuarioService.usuarioEhAdmin(auth)) {
            throw new AccessDeniedException("Acesso negado");
        }
    }

}
