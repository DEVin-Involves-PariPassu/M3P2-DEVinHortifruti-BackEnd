package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import tech.devinhouse.devinhortifrutiapi.dto.UsuarioDTO;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import tech.devinhouse.devinhortifrutiapi.repository.UsuarioRepository;
import tech.devinhouse.devinhortifrutiapi.service.exception.RequiredFieldMissingException;
import tech.devinhouse.devinhortifrutiapi.service.exception.UserIsUnderAgeException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


public class UsuarioService {

    @Autowired
    private UsuarioRepository UsuarioRepository;   

    public List<Usuario> listar(String nome, String dtNascimentoMinStr, String dtNascimentoMaxStr) {
        LocalDate dtNascimentoMin = verificationDate(dtNascimentoMinStr);
        LocalDate dtNascimentoMax = verificationDate(dtNascimentoMaxStr);
        return UsuarioRepository.findAll(
                Specification.where(
                        SpecificationsUsuario.nome(nome).and(
                                SpecificationsUsuario.dtNascimentoMin(dtNascimentoMin).and(
                                        SpecificationsUsuario.dtNascimentoMax(dtNascimentoMax)
                                )
                        )
                )
        );
    }

    public void patchPermissao(Long idUser, String tipoPermissao) {
        Usuario usuario = UsuarioRepository.findById(idUser).orElseThrow(() ->
                new EntityNotFoundException("Usuário não encontrado."));
    }

    public Long salvar(UsuarioDTO usuarioDTO) {
        Usuario newUser = validateUser(usuarioDTO);
        UsuarioRepository.save(newUser);
        return newUser.getId();
    }

    public void atualizar(Long idUser, UsuarioDTO usuarioDTO) {
        Usuario usuario = updateUser(idUser, usuarioDTO);

        UsuarioRepository.save(usuario);
    }

    public Usuario updateUser(Long idUser, UsuarioDTO usuarioDTO) {
        isUniqueNameUser(usuarioDTO);
        isUniqueLoginUser(usuarioDTO);
        LocalDate dtNascimento = verificationDate(String.valueOf(usuarioDTO));
        verificationAge(dtNascimento);

        Usuario usuario = UsuarioRepository.findById(idUser).orElseThrow(
                () -> new EntityNotFoundException("Id de usuário inexistente.")
        );
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setDtNascimento(dtNascimento);

        return usuario;
    }

    private Usuario validateUser(UsuarioDTO usuarioDTO) {

        existsNome(usuarioDTO);
        existsLogin(usuarioDTO);
        existsDtNascimento(usuarioDTO);
        isUniqueNameUser(usuarioDTO);
        isUniqueLoginUser(usuarioDTO);
        LocalDate usuarioAge = verificationDate(String.valueOf(usuarioDTO));
        verificationAge(usuarioAge);


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario newUser = new Usuario();
        newUser.setLogin(usuarioDTO.getLogin());
        newUser.setNome(usuarioDTO.getNome());
        newUser.setDtNascimento(usuarioAge);
        newUser = UsuarioRepository.save(newUser);

        return newUser;
    }

    private void existsNome(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getNome() == null) {
            throw new RequiredFieldMissingException("O campo Nome é obrigatório");
        }
    }

    private void existsLogin(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getLogin() == null) {
            throw new RequiredFieldMissingException("O campo Login é obrigatório");
        }
    }

    private void existsDtNascimento(UsuarioDTO usuario) {
        if (usuario.getDtNascimento() == null) {
            throw new RequiredFieldMissingException("O campo dtNascimento é obrigatório");
        }
    }

    private void verificationAge(LocalDate dtNascimento) {
        Period idade = Period.between(dtNascimento, LocalDate.now());

        if (idade.getYears() < 18) {
            throw new UserIsUnderAgeException("O usuário deve possuir mais de 18 anos.");
        }
    }

    private LocalDate verificationDate(String dtNascimentoStr) throws DateTimeParseException {
        if (dtNascimentoStr == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtNascimento = LocalDate.parse(dtNascimentoStr, dateTimeFormatter);

        return dtNascimento;
    }

    private void isUniqueLoginUser(UsuarioDTO usuario) {
        Optional<Usuario> optionalUser = UsuarioRepository.findUsuarioByLogin(usuario.getLogin());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("Já existe um usuário cadastrado com este login: " + usuario.getLogin());
        }
    }

    private void isUniqueNameUser(UsuarioDTO usuario) {
        Optional<Usuario> optionalUser = UsuarioRepository.findUsuarioByNome(usuario.getNome());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("Já existe este nome de usuário cadastrado: " + usuario.getNome());
        }
    }

    @Transactional
    public void delete(Long id){
        Usuario usuarioEntity = UsuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " was not found."));

        UsuarioRepository.delete(usuarioEntity);
    }

    @Transactional
    public Usuario findById(Long id){
        return UsuarioRepository.findUsuarioById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " was not found." )
        );
    }
}
