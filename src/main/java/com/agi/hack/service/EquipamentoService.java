package com.agi.hack.service;

import com.agi.hack.dto.EquipamentoDTO.EquipamentoRequestDTO;
import com.agi.hack.dto.EquipamentoDTO.EquipamentoResponseDTO;
import com.agi.hack.model.Equipamento;
import com.agi.hack.repository.EquipamentoRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;

    public EquipamentoService (EquipamentoRepository repository){
        this.repository=repository;
    }

    public EquipamentoResponseDTO toResponseDto(Equipamento equipamento){
        EquipamentoResponseDTO dto = new EquipamentoResponseDTO();

        dto.setIdEquipamento(equipamento.getIdEquipamento());
        dto.setNome(equipamento.getNome());
        dto.setCustoAquisicao(equipamento.getCustoAquisicao());
        dto.setDataAquisicao(equipamento.getDataAquisicao());
        dto.setNumeroSerie(equipamento.getNumeroSerie());
        dto.setStatus(equipamento.getStatus());
        dto.setClassificacaoEquipamento(equipamento.getClassificacaoEquipamento());
        dto.setCategoriaEquipamento(equipamento.getCategoriaEquipamento());
        dto.setManutencao(equipamento.getManutencao());
        dto.setSetor(equipamento.getSetor());
        dto.setPedido(equipamento.getPedido());

        return dto;

    }

    private EquipamentoResponseDTO salvar(EquipamentoRequestDTO dados){
        Equipamento equipamento = new Equipamento();

        equipamento.setNome(dados.getNome());
        equipamento.setCustoAquisicao(dados.getCustoAquisicao());
        equipamento.setDataAquisicao(dados.getDataAquisicao());
        equipamento.setNumeroSerie(dados.getNumeroSerie());
        equipamento.setStatus(dados.getStatus());
        equipamento.setClassificacaoEquipamento(dados.getClassificacaoEquipamento());
        equipamento.setCategoriaEquipamento(dados.getCategoriaEquipamento());
        equipamento.setManutencao(dados.getManutencao());
        equipamento.setSetor(dados.getSetor());
        equipamento.setPedido(dados.getPedido());

        repository.save(equipamento);

        return  toResponseDto(equipamento);
    }

    private List<EquipamentoResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    private EquipamentoResponseDTO buscarId(Long id){
        Equipamento equipamento = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipamento nao encontrado"));

        return toResponseDto(equipamento);
    }

    private EquipamentoResponseDTO atualizar(Long id, EquipamentoRequestDTO dados){
        Equipamento equipamento = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipamento nao encontrado"));

        equipamento.setNome(dados.getNome());
        equipamento.setCustoAquisicao(dados.getCustoAquisicao());
        equipamento.setDataAquisicao(dados.getDataAquisicao());
        equipamento.setNumeroSerie(dados.getNumeroSerie());
        equipamento.setStatus(dados.getStatus());
        equipamento.setClassificacaoEquipamento(dados.getClassificacaoEquipamento());
        equipamento.setCategoriaEquipamento(dados.getCategoriaEquipamento());
        equipamento.setManutencao(dados.getManutencao());
        equipamento.setSetor(dados.getSetor());
        equipamento.setPedido(dados.getPedido());

        Equipamento atualizado = repository.save(equipamento);

        return toResponseDto(atualizado);
    }
}
