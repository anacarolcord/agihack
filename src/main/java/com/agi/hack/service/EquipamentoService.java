package com.agi.hack.service;

import com.agi.hack.dto.EquipamentoDTO.EquipamentoRequestDTO;
import com.agi.hack.dto.EquipamentoDTO.EquipamentoResponseDTO;
import com.agi.hack.dto.FuncionarioDTO.FuncionarioResponseDTO;
import com.agi.hack.dto.SetorDTO.SetorResponseDTO;
import com.agi.hack.enums.ListaEquipamento;
import com.agi.hack.enums.StatusEquipamento;
import com.agi.hack.enums.StatusManutencao;
import com.agi.hack.enums.StatusPedido;
import com.agi.hack.model.Funcionario;
import com.agi.hack.repository.CatalogoRepository;
import org.springframework.transaction.annotation.Transactional;
import com.agi.hack.model.Catalogo;
import com.agi.hack.exception.ResourceNotFoundException;
import com.agi.hack.model.Equipamento;
import com.agi.hack.repository.EquipamentoRepository;
import com.agi.hack.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;
    private final FuncionarioRepository funcionarioRepository;
    private final CatalogoRepository catalogoRepository;

    public EquipamentoService (EquipamentoRepository repository, FuncionarioRepository funcionarioRepository, CatalogoRepository catalogoRepository){
        this.repository=repository;
        this.funcionarioRepository=funcionarioRepository;
        this.catalogoRepository = catalogoRepository;
    }

    public EquipamentoResponseDTO toResponseDto(Equipamento equipamento){
        EquipamentoResponseDTO dto = new EquipamentoResponseDTO();

        dto.setIdEquipamento(equipamento.getIdEquipamento());

        if (equipamento.getCatalogo() != null) {
            dto.setNome(equipamento.getCatalogo().getDescricao());
            dto.setCatalogoDescricao(equipamento.getCatalogo().getDescricao());
        }

        dto.setCustoAquisicao(equipamento.getCustoAquisicao());
        dto.setDataAquisicao(equipamento.getDataAquisicao());
        dto.setNumeroSerie(equipamento.getNumeroSerie());
        dto.setStatus(equipamento.getStatus());
        dto.setCategoriaEquipamento(equipamento.getCategoriaEquipamento());

        return dto;
    }

    @Transactional
    public EquipamentoResponseDTO salvar(EquipamentoRequestDTO dados){

        Catalogo catalogo = catalogoRepository.findById(dados.getCatalogoId())
                .orElseThrow(() -> new ResourceNotFoundException("Catalogo não encontrado com ID: " + dados.getCatalogoId()));

        Equipamento equipamento = new Equipamento();

        equipamento.setCatalogo(catalogo);
        equipamento.setCustoAquisicao(dados.getCustoAquisicao());
        equipamento.setDataAquisicao(dados.getDataAquisicao());
        equipamento.setNumeroSerie(dados.getNumeroSerie());
        equipamento.setStatus(dados.getStatus());
        equipamento.setCategoriaEquipamento(dados.getCategoriaEquipamento());
        equipamento.setSetor(dados.getSetor());
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

    @Transactional
    public EquipamentoResponseDTO atualizar(Long id, EquipamentoRequestDTO dados){
        Equipamento equipamento = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipamento nao encontrado"));

        Catalogo catalogo = catalogoRepository.findById(dados.getCatalogoId())
                .orElseThrow(() -> new ResourceNotFoundException("Catalogo não encontrado com ID: " + dados.getCatalogoId()));

        equipamento.setCatalogo(catalogo);
        equipamento.setCustoAquisicao(dados.getCustoAquisicao());
        equipamento.setDataAquisicao(dados.getDataAquisicao());
        equipamento.setNumeroSerie(dados.getNumeroSerie());
        equipamento.setStatus(dados.getStatus());
        equipamento.setCategoriaEquipamento(dados.getCategoriaEquipamento());
        equipamento.setSetor(dados.getSetor());
        equipamento.setFuncionario(dados.getFuncionario());

        Equipamento atualizado = repository.save(equipamento);

        return toResponseDto(atualizado);
    }

    @Transactional
    public void deletar(Long id){

        Equipamento equipamento = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipamento nao encontrado"));

        equipamento.setStatus(StatusEquipamento.SUCATA);
        equipamento.setFuncionario(null);
        equipamento.setSetor(null);

        repository.save(equipamento);
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

    @Transactional
    public EquipamentoResponseDTO alocarEquipamento(Long equipamentoId, Long funcionarioId) {

        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado com ID: " + equipamentoId));

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        // Regra de Negócio: Só pode alocar se estiver DISPONÍVEL
        if (equipamento.getStatus() != StatusEquipamento.DISPONIVEL) {
            // (Você pode querer criar uma BadRequestException para isso)
            throw new RuntimeException("Equipamento não está disponível para alocação. Status atual: " + equipamento.getStatus());
        }

        equipamento.setFuncionario(funcionario);
        equipamento.setSetor(funcionario.getSetor());
        equipamento.setStatus(StatusEquipamento.EM_USO);

        Equipamento equipamentoAlocado = repository.save(equipamento);
        return toResponseDto(equipamentoAlocado);
    }

    @Transactional
    public EquipamentoResponseDTO devolverEquipamento(Long equipamentoId) {
        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado com ID: " + equipamentoId));

        equipamento.setFuncionario(null);
        equipamento.setStatus(StatusEquipamento.DISPONIVEL);

        Equipamento equipamentoDevolvido = repository.save(equipamento);
        return toResponseDto(equipamentoDevolvido);
    }

    @Transactional
    public EquipamentoResponseDTO enviarParaManutencao(Long equipamentoId) {
        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado com ID: " + equipamentoId));

        equipamento.setFuncionario(null);
        equipamento.setStatus(StatusEquipamento.MANUTENCAO);

        Equipamento equipamentoEmManutencao = repository.save(equipamento);
        return toResponseDto(equipamentoEmManutencao);
    }

    // Adicione estes métodos ao seu EquipamentoService.java

    /**
     * REESCRITA (Substitui 'atribuirEquipamento')
     * Orquestra a alocação do "Kit" padrão para um funcionário.
     */
    @Transactional
    public void atribuirKitParaFuncionario(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        String nomeSetor = funcionario.getSetor().getNomeSetor(); // (Assumindo que Setor tem 'getNomeSetor()')
        List<String> skusDoKit;

        // --- MUDANÇA ---
        // A lógica de "Kit" agora usa SKUs do Catálogo, não Enums.
        // Você precisa definir quais SKUs (do seu Catalogo) cada setor recebe.
        switch (nomeSetor) {
            case "TI":
                skusDoKit = List.of("SKU-NOTEBOOK-AVANCADO", "SKU-MOUSE-SEM-FIO", "SKU-HEADSET-PRO");
                break;
            case "VENDAS":
                skusDoKit = List.of("SKU-TABLET-PADRAO", "SKU-MOUSE-SEM-FIO");
                break;
            case "RH":
                skusDoKit = List.of("SKU-NOTEBOOK-PADRAO", "SKU-MOUSE-COM-FIO", "SKU-WEBCAM");
                break;
            default:
                skusDoKit = List.of("SKU-NOTEBOOK-PADRAO", "SKU-MOUSE-COM-FIO");
                break;
        }

        System.out.println("Alocando " + skusDoKit.size() + " itens para " + funcionario.getNome());

        // Para cada SKU do Kit, encontre e aloque o próximo item disponível
        for (String sku : skusDoKit) {
            try {
                alocarProximoDisponivelPorSku(sku, funcionario);
            } catch (Exception e) {
                // Loga o erro mas continua tentando alocar os outros itens
                System.err.println("Falha ao alocar item " + sku + " para " + funcionario.getNome() + ": " + e.getMessage());
            }
        }
    }

    private void alocarProximoDisponivelPorSku(String sku, Funcionario funcionario) {

        Catalogo catalogoItem = catalogoRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("SKU do Kit não encontrado no catálogo: " + sku));

        Equipamento equipamentoParaAlocar = repository.findFirstByCatalogoAndStatus(catalogoItem, StatusEquipamento.DISPONIVEL)
                .orElseThrow(() -> new RuntimeException("Sem estoque disponível para o SKU: " + sku));

        System.out.println("Alocando " + sku + " (N/S: " + equipamentoParaAlocar.getNumeroSerie() + ") para " + funcionario.getNome());

        alocarEquipamento(equipamentoParaAlocar.getIdEquipamento(), funcionario.getIdFuncionario());
    }
}
