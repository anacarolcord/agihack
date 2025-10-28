package com.agi.hack.service;

import com.agi.hack.dto.CargoDTO.CargoDTO;
import com.agi.hack.dto.EquipamentoDTO.EquipamentoRequestDTO;
import com.agi.hack.dto.EquipamentoDTO.EquipamentoResponseDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioRequestDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.dto.SetorDTO.SetorRequestDTO;
import com.agi.hack.dto.SetorDTO.SetorResponseDTO;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.enums.StatusPedido;
import com.agi.hack.exception.ResourceNotFoundException;
import com.agi.hack.model.Cargo;
import com.agi.hack.model.Equipamento;
import com.agi.hack.model.Funcionario;
import com.agi.hack.model.Setor;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.FuncionarioRepository;
import jakarta.annotation.Resource;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;
    private final FuncionarioRepository funcionarioRepository;

    public EquipamentoService (EquipamentoRepository repository, FuncionarioRepository funcionarioRepository){
        this.repository=repository;
        this.funcionarioRepository=funcionarioRepository;
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

        return dto;
    }

    public EquipamentoResponseDTO salvar(EquipamentoRequestDTO dados){

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
        equipamento.setFuncionario(dados.getFuncionario());

        repository.save(equipamento);

        return  toResponseDto(equipamento);
    }

    public List<EquipamentoResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    public EquipamentoResponseDTO buscarId(Long id){
        Equipamento equipamento = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipamento nao encontrado"));

        return toResponseDto(equipamento);
    }

    public EquipamentoResponseDTO atualizar(Long id, EquipamentoRequestDTO dados){
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
        equipamento.setFuncionario(dados.getFuncionario());

        Equipamento atualizado = repository.save(equipamento);

        return toResponseDto(atualizado);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

    public void atribuirEquipamento(FuncionarioResponseDTO funcionario){

        if(funcionarioRepository.existsById(funcionario.getIdFuncionario())) {

            if (funcionario.getSetor().getNomeSetor().equals("TI") || funcionario.getSetor().getNomeSetor().equals("RH")
                    || funcionario.getSetor().getNomeSetor().equals("VENDAS")) {
                equipamentoPorSetor(funcionario.getSetor());

            }
        }else throw new ResourceNotFoundException("Funcionario nao encontrado");


    }

    public void equipamentoPorSetor(SetorResponseDTO setor){

        EquipamentoResponseDTO equipamento = new EquipamentoResponseDTO();


        if (setor.getNomeSetor().equals("TI")){
           equipamento.setNome(ListaEquipamento.NOTEBOOK, ListaEquipamento.MOUSE,ListaEquipamento.HEADSET,ListaEquipamento.ROTEADOR);
           equipamento.setSetor(setor);
        }
        if(setor.getNomeSetor().equals("VENDAS")){
            equipamento.setNome(ListaEquipamento.TABLET,ListaEquipamento.IMPRESSORA,
                    ListaEquipamento.DESKTOP,ListaEquipamento.MONITOR,ListaEquipamento.MOUSE,ListaEquipamento.TECLADO);
            equipamento.setSetor(setor);
        }
        if(setor.getNomeSetor().equals("RH")){
            equipamento.setNome(ListaEquipamento.IMPRESSORA,ListaEquipamento.DESKTOP,ListaEquipamento.MONITOR,ListaEquipamento.MOUSE,
                    ListaEquipamento.TECLADO, ListaEquipamento.WEBCAM);
            equipamento.setSetor(setor);
        }
    }

    public void alterarStatusEquipamento(EquipamentoResponseDTO dto){

        if(!dto.getManutencao().getStatusManutencao().equals(StatusManutencao.CONCLUIDA)){
            dto.setStatus(StatusEquipamento.MANUTENCAO);
        } else if (dto.getFuncionario().getStatus().isAtivo()) {
            dto.setStatus(StatusEquipamento.EM_USO);
        } else if (dto.getPedido().getStatus().equals("PENDENTE") || dto.getPedido().getStatus().equals("APROVADO")) {
            dto.setStatus(StatusEquipamento.PROCESSANDO_COMPRA);
        } else if (dto.getManutencao().getStatusManutencao().equals(StatusManutencao.FALHA)) {
            dto.setStatus(StatusEquipamento.SUCATA);
        }else{
            dto.setStatus(StatusEquipamento.DISPONIVEL);
        }

    }




}
