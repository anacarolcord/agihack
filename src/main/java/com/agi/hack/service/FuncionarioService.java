package com.agi.hack.service;

import com.agi.hack.dto.FuncionarioDTO.FuncionarioRequestDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.enums.StatusFuncionario;
import com.agi.hack.mapper.FuncionarioMapper;
import com.agi.hack.model.Cargo;
import com.agi.hack.model.Funcionario;
import com.agi.hack.model.Setor;
import com.agi.hack.repository.CargoRepository;
import com.agi.hack.repository.FuncionarioRepository;
import com.agi.hack.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            SetorRepository setorRepository,
            CargoRepository cargoRepository,
            FuncionarioMapper funcionarioMapper
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
        this.cargoRepository = cargoRepository;
        this.funcionarioMapper = funcionarioMapper;
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

    // --- CRUD: CRIAÇÃO ---

    @Transactional
    public FuncionarioResponseDTO criarFuncionario(FuncionarioRequestDTO dto) {
        Setor setor = buscarSetorPorId(dto.getIdSetor());
        Cargo cargo = buscarCargoPorId(dto.getIdCargo());

        Funcionario funcionario = funcionarioMapper.toEntity(dto);

        funcionario.setSetor(setor);
        funcionario.setCargo(cargo);

        if (dto.getStatus() == null) {
            funcionario.setStatus(StatusFuncionario.ATIVO);
        }

        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toResponseDTO(savedFuncionario);
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

    // --- CRUD: SOFT-DELETE/DESATIVAÇÃO (CORRIGIDO) ---

    @Transactional
    public void desativarFuncionario(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + id));

        // CORREÇÃO: Removemos a checagem por DEMITIDO, verificando apenas se já está INATIVO
        if (funcionario.getStatus() != StatusFuncionario.INATIVO)
        {
            // Alteramos o status para INATIVO
            funcionario.setStatus(StatusFuncionario.INATIVO);
            funcionarioRepository.save(funcionario);
        } else {
            // CORREÇÃO: Mensagem de erro reflete o único status final, INATIVO
            throw new IllegalStateException("O funcionário com ID " + id + " já está INATIVO e não pode ser desativado novamente.");
        }
    }
}