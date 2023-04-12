package com.elisio.cursomc.service;


import com.elisio.cursomc.domain.Cliente;
import com.elisio.cursomc.domain.ItemPedido;
import com.elisio.cursomc.domain.PagamentoComBoleto;
import com.elisio.cursomc.domain.Pedido;
import com.elisio.cursomc.enums.EstadoPagamento;
import com.elisio.cursomc.repository.*;
import com.elisio.cursomc.security.UserSS;
import com.elisio.cursomc.security.UserService;
import com.elisio.cursomc.service.validation.exceptions.AuthorizationException;
import com.elisio.cursomc.service.validation.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;


    public Pedido findById(Long id) {
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id: " +
                        id + " Tipo: " + Pedido.class.getName()));
    }


    @Transactional
    public Pedido insert(Pedido obj) {

        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.findById(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }

        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.fendById(ip.getProuto().getId()));
            ip.setPreco(produtoService.fendById(ip.getProuto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());

        System.out.println(obj);

        //Esse manda a msg e texto
        //emailService.sendOrderConfirmationEmail(obj);

        //Este manda msg com html no corpo
        emailService.sendOrderConfirmationHtmlEmail(obj);

        return obj;
    }


    //Retorna os pedidos apenas do cliente que esta logado
    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso Negado");
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.findById(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }

}
