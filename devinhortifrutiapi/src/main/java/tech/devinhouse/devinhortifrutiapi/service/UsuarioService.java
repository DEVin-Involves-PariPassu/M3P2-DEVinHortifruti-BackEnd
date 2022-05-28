package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void patchPermissao(Long idUser, String nomeFeature, String tipoPermissao) {
        Usuario usuarioEntity = UsuarioRepository.findById(idUser).orElseThrow(() ->
                new EntityNotFoundException("Usuário não encontrado."));

       
       
     
    }

    public Long salvar(UsuarioDto usuario) {
        Usuario newUser = validateUser(usuario);
        UsuarioRepository.save(newUser);
        return newUser.getId();
    }

    public void atualizar(Long idUser, UsuarioDto usuarioDto) {
        Usuario usuario = updateUser(idUser, usuarioDto);

        UsuarioRepository.save(usuario);
    }

    public Usuario updateUser(Long idUser, UsuarioDto usuarioDto) {
        isUniqueNameUser(usuarioDto);
        isUniqueLoginUser(usuarioDto);
        LocalDate dtNascimento = verificationDate(usuarioDto);
        verificationAge(dtNascimento);

        Usuario usuario = UsuarioRepository.findById(idUser).orElseThrow(
                () -> new EntityNotFoundException("Id de usuário inexistente.")
        );
        usuario.setLogin(usuarioDto.getLogin());
        usuario.setSenha(usuarioDto.getSenha());
        usuario.setNome(usuarioDto.getNome());
        usuario.setDtNascimento(dtNascimento);

        return usuario;
    }

    private Usuario validateUser(UsuarioDto usuario) {

        existsNome(usuario);
        existsLogin(usuario);
        existsSenha(usuario);
        existsDtNascimento(usuario);
        isUniqueNameUser(usuario);
        isUniqueLoginUser(usuario);
        LocalDate usuarioAge = verificationDate(usuario);
        verificationAge(usuarioAge);
        isFeaturesEmpty(usuario);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Usuario newUser = new Usuario();
        newUser.setLogin(usuario.getLogin());
        newUser.setSenha(encoder.encode(usuario.getSenha()));
        newUser.setNome(usuario.getNome());
        newUser.setDtNascimento(usuarioAge);
        newUser = UsuarioRepository.save(newUser);

        return newUser;
    }

    private void existsNome(UsuarioDto usuario) {
        if (usuario.getNome() == null) {
            throw new RequiredFieldMissingException("O campo Nome é obrigatório");
        }
    }

    private void existsLogin(UsuarioDto usuario) {
        if (usuario.getLogin() == null) {
            throw new RequiredFieldMissingException("O campo Login é obrigatório");
        }
    }

    private void existsSenha(UsuarioDto usuario) {
        if (usuario.getSenha() == null) {
            throw new RequiredFieldMissingException("O campo Senha é obrigatório");
        }
    }

    private void existsDtNascimento(UsuarioDto usuario) {
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

    private LocalDate verificationDate(UsuarioDto usuarioDto) throws DateTimeParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtNascimento = LocalDate.parse(usuarioDto.getDtNascimento(), dateTimeFormatter);

        return dtNascimento;
    }

    private LocalDate verificationDate(String dtNascimentoStr) throws DateTimeParseException {
        if (dtNascimentoStr == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dtNascimento = LocalDate.parse(dtNascimentoStr, dateTimeFormatter);

        return dtNascimento;
    }

    private void isUniqueLoginUser(UsuarioDto usuario) {
        Optional<Usuario> optionalUser = UsuarioRepository.findUsuarioByLogin(usuario.getLogin());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("Já existe um usuário cadastrado com este login: " + usuario.getLogin());
        }
    }

    private void isUniqueNameUser(UsuarioDto usuario) {
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
