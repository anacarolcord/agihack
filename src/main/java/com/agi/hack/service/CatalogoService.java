package com.agi.hack.service;

import com.agi.hack.enums.StatusPedido;
import com.agi.hack.exception.BadRequestException;
import com.agi.hack.exception.ResourceNotFoundException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import com.agi.hack.dto.CatalogoDTO.CatalogoRequestDTO;
import com.agi.hack.dto.CatalogoDTO.CatalogoResponseDTO;
import com.agi.hack.model.Catalogo;
import com.agi.hack.model.Pedido; // Dependência do colega
import com.agi.hack.repository.CatalogoRepository;
import com.agi.hack.repository.EquipamentoRepository; // Dependência do colega
import com.agi.hack.repository.PedidoRepository; // Dependência do colega
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final EquipamentoRepository equipamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final CatalogoRepository catalogoRepository;

    @Transactional
    public CatalogoResponseDTO criarItem(CatalogoRequestDTO dto){
        if(catalogoRepository.findBySku(dto.sku()).isPresent()){
            throw new BadRequestException("SKU ja cadastrado: " + dto.sku());
        }

        Catalogo novoItem = new Catalogo();
        novoItem.setSku(dto.sku());
        novoItem.setDescricao(dto.descrição());
        novoItem.setCategoria(dto.categoria());
        novoItem.setFabricante(dto.fabricante());
        novoItem.setEstoqueMinimo(dto.estoqueMinimo());

        Catalogo itemSalvo = catalogoRepository.save(novoItem);

        return new CatalogoResponseDTO(itemSalvo);
    }

    public List<CatalogoResponseDTO> listarTodos(){
        return catalogoRepository.findAll()
                .stream()
                .map(CatalogoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CatalogoResponseDTO buscarPorId(Long id){
        Catalogo item = catalogoRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Item do catálogo não encontrado: " + id));
        return new CatalogoResponseDTO(item);
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    public void verificarReestocagemAutomatica(){
        System.out.println("|------ RODANDO VERIFICAÇÃO DE REESTOCAGEM AUTOMÁTICA -------------------|");

        List<Catalogo> todosItens = catalogoRepository.findAll();

        for(Catalogo item : todosItens){
            long disponiveis = equipamentoRepository.countDisponiveisPorCatalogoId(item.getId());

            long emPedido = pedidoRepository.countPedidosAbertosPorCatalogoId(item.getId());

            long estoqueVirtual = disponiveis + emPedido;

            if(estoqueVirtual < item.getEstoqueMinimo()){
                System.out.println("ALERTA: Estoque baixo para " + item.getSku());

                int quantidadeParaPedir = (item.getEstoqueMinimo()*2);

                Pedido novoPedido = new Pedido();
                novoPedido.setItemSolicitado(item);
                novoPedido.setQuantidade(quantidadeParaPedir);
                novoPedido.setStatus(StatusPedido.ABERTO_AUTOMATICO);
                novoPedido.setDataPedido(LocalDateTime.now());

                pedidoRepository.save(novoPedido);
                System.out.println("GERADO PEDIDO AUTOMÁTICO de " + quantidadeParaPedir + " para " + item.getSku());
            }
        }
    }
}
