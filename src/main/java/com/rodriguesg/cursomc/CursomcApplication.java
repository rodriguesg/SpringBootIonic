package com.rodriguesg.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rodriguesg.cursomc.domain.Categoria;
import com.rodriguesg.cursomc.domain.Cidade;
import com.rodriguesg.cursomc.domain.Cliente;
import com.rodriguesg.cursomc.domain.Endereco;
import com.rodriguesg.cursomc.domain.Estado;
import com.rodriguesg.cursomc.domain.ItemPedido;
import com.rodriguesg.cursomc.domain.Pagamento;
import com.rodriguesg.cursomc.domain.PagamentoBoleto;
import com.rodriguesg.cursomc.domain.PagamentoCartao;
import com.rodriguesg.cursomc.domain.Pedido;
import com.rodriguesg.cursomc.domain.Produto;
import com.rodriguesg.cursomc.domain.enums.EstadoPagamento;
import com.rodriguesg.cursomc.domain.enums.TipoCliente;
import com.rodriguesg.cursomc.repositories.CategoriaRepository;
import com.rodriguesg.cursomc.repositories.CidadeRepository;
import com.rodriguesg.cursomc.repositories.ClienteRepository;
import com.rodriguesg.cursomc.repositories.EnderecoRepository;
import com.rodriguesg.cursomc.repositories.EstadoRepository;
import com.rodriguesg.cursomc.repositories.ItemPedidoRepository;
import com.rodriguesg.cursomc.repositories.PagamentoRepository;
import com.rodriguesg.cursomc.repositories.PedidoRepository;
import com.rodriguesg.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;	
	@Autowired 
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//INSTANCIA DE CATEGORIAS
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		//INSTANCIA DE PRODUTOS
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//INSTANCIA DOS ESTADOS
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		//INSTANCIA DAS CIDADES
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		//INSTANCIA DOS CLIENTES
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		//INSTANCIA DOS ENDERECOS
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//INSTANCIA DOS PEDIDOS
		Pedido ped1 = new Pedido(null, sdf.parse("09/04/2019 12:06"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("09/04/2019 13:06"), cli1, e2);
		
		//INSTANCIA DOS PAGAMENTOS
		Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2010 00:00"), null);
		
		//INSTANCIA DE PEDIDOS
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);			
		
		//ASSOCIACAO CLIENTE/ENDERECO
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		//ASSOCIACAO CIDADE/ESTADO
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		//ASSOCIACAO CATEGORIA/PRODUTO
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//ASSOCIACAO PRODUTO/CATEGORIA
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//ASSOCIACAO PAGAMENTO/PEDIDO
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		//ASSOCIACAO CLIENTE/PEDIDO
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		//ASSOCIACAO PEDIDO/ITENS DE PEDIDO
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//ASSOCIACAO PRODUTO/ITENS DE PEDIDO
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		//SALVANDO NO BANCO DE DADOS
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
