package com.carlosdv93.udemy.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosdv93.udemy.cursomc.domain.ItemPedido;
import com.carlosdv93.udemy.cursomc.domain.PagamentoComBoleto;
import com.carlosdv93.udemy.cursomc.domain.Pedido;
import com.carlosdv93.udemy.cursomc.enums.EstadoPagamento;
import com.carlosdv93.udemy.cursomc.repositories.ItemPedidoRepository;
import com.carlosdv93.udemy.cursomc.repositories.PagamentoRepository;
import com.carlosdv93.udemy.cursomc.repositories.PedidoRepository;
import com.carlosdv93.udemy.cursomc.repositories.ProdutoRepository;
import com.carlosdv93.udemy.cursomc.resources.BoletoService;
import com.carlosdv93.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objecto não Encontrado: " + id + "\nTipo: " + Pedido.class.getName());
		}
		return obj;
	}

	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setData(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getData());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoRepository.findOne(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.save(obj.getItens());
		//emailService.sendConfirmationOrderEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
}
