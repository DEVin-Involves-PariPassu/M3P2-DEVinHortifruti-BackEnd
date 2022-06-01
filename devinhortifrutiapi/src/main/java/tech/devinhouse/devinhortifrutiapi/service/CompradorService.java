package tech.devinhouse.devinhortifrutiapi.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tech.devinhouse.devinhortifrutiapi.dto.CompradorDTO;
import tech.devinhouse.devinhortifrutiapi.model.Comprador;
import tech.devinhouse.devinhortifrutiapi.repository.CompradorRepository;
import tech.devinhouse.devinhortifrutiapi.service.exception.RequiredFieldMissingException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CompradorService {

    @Autowired
    CompradorRepository compradorRepository;

    @Transactional
    public Long salvar(CompradorDTO compradorDTO){
        Comprador comprador = validateAndConvertDto(compradorDTO);
        comprador = compradorRepository.save(comprador);
        return comprador.getId();
    }

    public Comprador findById(Long id_comprador) {
        return this.compradorRepository.findById(id_comprador).orElseThrow(() ->
                new EntityNotFoundException("Não existe Comprador com este ID"));
    }

    private Comprador validateAndConvertDto(CompradorDTO compradorDTO){
        existsNome(compradorDTO);
        existsCpf(compradorDTO);
        existsEmail(compradorDTO);
        existsTelefone(compradorDTO);
        isUniqueCpf(compradorDTO);
        isUniqueEmail(compradorDTO);
        Comprador comprador = new Comprador();
        comprador.setNome(compradorDTO.getNome());
        comprador.setCpf(compradorDTO.getCpf());
        comprador.setEmail(compradorDTO.getEmail());
        comprador.setTelefone(compradorDTO.getTelefone());
        return comprador;
    }

    private void existsNome(CompradorDTO compradorDTO){
        if(compradorDTO.getNome() == null){
            throw new RequiredFieldMissingException("Campo Nome é obrigatório!");
        }
    }

    private void existsCpf(CompradorDTO compradorDTO){
        if(compradorDTO.getCpf() == null){
            throw new RequiredFieldMissingException("Campo CPF é obrigatório!");
        }
    }

    private void existsEmail(CompradorDTO compradorDTO){
        if(compradorDTO.getEmail() == null){
            throw new RequiredFieldMissingException("Campo E-mail é obrigatório!");
        }
    }

    private void existsTelefone(CompradorDTO compradorDTO){
        if(compradorDTO.getTelefone() == null){
            throw new RequiredFieldMissingException("Campo Telefone é obrigatório!");
        }
    }

    private void isUniqueCpf(CompradorDTO compradorDTO){
        Optional<Comprador> optionalComprador = compradorRepository.findByCpf(compradorDTO.getCpf());
        if(optionalComprador.isPresent()){
            throw new EntityExistsException("O CPF " + compradorDTO.getCpf() + " já possui cadastro!");
        }
    }

    private void isUniqueEmail(CompradorDTO compradorDTO){
        Optional<Comprador> optionalComprador = compradorRepository.findByEmail(compradorDTO.getEmail());
        if(optionalComprador.isPresent()){
            throw new EntityExistsException("O e-mail " + compradorDTO.getEmail() + " já possui cadastro!");
        }
    }

    @Transactional
    public Long updateDoPut(Long id_comprador,
                            CompradorDTO compradorDTO) {
        Comprador comprador = validationsPut(id_comprador, compradorDTO);

        if(compradorDTO.getNome() != null)
            comprador.setNome(compradorDTO.getNome());
        if(compradorDTO.getTelefone() !=null)
            comprador.setTelefone(compradorDTO.getTelefone());

        return id_comprador;
    }

    public Comprador validationsPut(Long id_comprador,
                                    CompradorDTO compradorDTO) {
        Comprador comprador = findById(id_comprador);

        if(compradorDTO.getCpf() != null && compradorDTO.getEmail() != null) {
            if (!compradorDTO.getCpf().isBlank() && !compradorDTO.getEmail().isBlank()) {
                isUniqueOrTheSame(compradorDTO, id_comprador, comprador);
            }
        }

        return comprador;
    }

    public Comprador getComprador(String cpf) {
        Optional<Comprador> compradorOpt = this.compradorRepository.findByCpf(cpf);
        if (compradorOpt.isEmpty()) {
            throw new EntityNotFoundException("Comprador não encontrado.");
        }
        return compradorOpt.get();
    }

    public void isUniqueOrTheSame(CompradorDTO compradorDTO, Long id_comprador, Comprador comprador) {
        Optional<Comprador> optionalCpf = this.compradorRepository.findByCpf(compradorDTO.getCpf());
        Optional<Comprador> optionalEmail = this.compradorRepository.findByEmail(compradorDTO.getEmail());

        if(optionalCpf.isPresent() && id_comprador != this.compradorRepository.findByCpf(compradorDTO.getCpf()).get().getId()) {
            throw new EntityExistsException("Já existe Comprador com o CPF " + compradorDTO.getCpf());
        }
        if(optionalCpf.isEmpty()) {
            comprador.setCpf(compradorDTO.getCpf());
        }
        if(!optionalEmail.isEmpty() && optionalEmail.isPresent() && id_comprador != this.compradorRepository.findByEmail(compradorDTO.getEmail()).get().getId()) {
            throw new EntityExistsException("Já existe Comprador com o E-mail " + compradorDTO.getEmail());
        }
        if(optionalEmail.isEmpty()) {
            comprador.setEmail(compradorDTO.getEmail());
        }
    }
}
