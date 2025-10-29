package com.agi.hack.service;

import com.agi.hack.dto.FuncionarioDTO.FuncionarioRequestDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.enums.StatusFuncionario;
import com.agi.hack.exception.FuncionarioNotFound;
import com.agi.hack.exception.ResourceNotFoundException;
import com.agi.hack.mapper.FuncionarioMapper;
import com.agi.hack.model.Cargo;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Funcionario;
import com.agi.hack.model.Setor;
import com.agi.hack.repository.CargoRepository;
import com.agi.hack.repository.FuncionarioRepository;
import com.agi.hack.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;
    private final CargoRepository cargoRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final EquipamentoService equipamentoService;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            SetorRepository setorRepository,
            CargoRepository cargoRepository,
            FuncionarioMapper funcionarioMapper,
            EquipamentoService equipamentoService
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
        this.cargoRepository = cargoRepository;
        this.funcionarioMapper = funcionarioMapper;
        this.equipamentoService = equipamentoService;
    }

    // --- MÉTODOS AUXILIARES ---

    private Setor buscarSetorPorId(Long idSetor) {
        return setorRepository.findById(idSetor)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado com ID: " + idSetor));
    }

    private Cargo buscarCargoPorId(Long idCargo) {
        return cargoRepository.findById(idCargo)
                .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado com ID: " + idCargo));
    }

    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO dto) {
        Setor setor = buscarSetorPorId(dto.getIdSetor());
        Cargo cargo = buscarCargoPorId(dto.getIdCargo());

        Funcionario funcionario = funcionarioMapper.toEntity(dto);

        funcionario.setSetor(setor);
        funcionario.setCargo(cargo);
        funcionario.setStatus(StatusFuncionario.ATIVO);

        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toResponseDTO(savedFuncionario);
    }

    public FuncionarioResponseDTO criarEAssociarEquipamento(FuncionarioRequestDTO requestDTO) {
        FuncionarioResponseDTO responseDTo = criarFuncionario(requestDTO);
        equipamentoService.atribuirEquipamento(responseDTo);
        return responseDTo;
    }

    // --- CRUD: LISTAGEM GERAL ---

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll().stream()
                .map(funcionarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- CRUD: BUSCA POR ID ---

    @Transactional(readOnly = true)
    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + id));
        return funcionarioMapper.toResponseDTO(funcionario);
    }

    // --- CRUD: ATUALIZAÇÃO ---

    @Transactional
    public FuncionarioResponseDTO atualizarFuncionario(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado para atualização com ID: " + id));

        Setor novoSetor = buscarSetorPorId(dto.getIdSetor());
        Cargo novoCargo = buscarCargoPorId(dto.getIdCargo());

        // Atualiza campos
        funcionarioExistente.setNome(dto.getNome());
        funcionarioExistente.setCpf(dto.getCpf());
        funcionarioExistente.setEmail(dto.getEmail());
        funcionarioExistente.setSetor(novoSetor);
        funcionarioExistente.setCargo(novoCargo);

        if (dto.getStatus() != null) {
            funcionarioExistente.setStatus(dto.getStatus());
        }

        Funcionario updatedFuncionario = funcionarioRepository.save(funcionarioExistente);
        return funcionarioMapper.toResponseDTO(updatedFuncionario);
    }

    @Transactional
    public FuncionarioResponseDTO desativarFuncionario(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + id));

        if (funcionario.getStatus() != StatusFuncionario.INATIVO)
        {
            funcionario.setStatus(StatusFuncionario.INATIVO);
            Funcionario saved = funcionarioRepository.save(funcionario);
             return funcionarioMapper.toResponseDTO(saved);
        } else {
            throw new IllegalStateException("O funcionário com ID " + id + " já está INATIVO e não pode ser desativado novamente.");
        }
    }

    public FuncionarioResponseDTO mudarOcupacao( long idFuncionario,long idCargo, Long idSetor){
         Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow(()-> new FuncionarioNotFound("Funcionário não encontrado"));
         Setor setor = setorRepository.findById(idSetor).orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));
         Cargo cargo = cargoRepository.findById(idCargo).orElseThrow(()-> new ResourceNotFoundException("Cargo não encontrado"));
         funcionario.setSetor(setor);
         funcionario.setCargo(cargo);
         Funcionario saved = funcionarioRepository.save(funcionario);
         return funcionarioMapper.toResponseDTO(saved);
    }
}