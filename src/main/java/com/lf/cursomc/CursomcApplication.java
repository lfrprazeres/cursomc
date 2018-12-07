package com.lf.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lf.cursomc.domain.Categoria;
import com.lf.cursomc.domain.Cidade;
import com.lf.cursomc.domain.Cliente;
import com.lf.cursomc.domain.Endereco;
import com.lf.cursomc.domain.Estado;
import com.lf.cursomc.domain.Pagamento;
import com.lf.cursomc.domain.PagamentoComBoleto;
import com.lf.cursomc.domain.PagamentoComCartao;
import com.lf.cursomc.domain.Pedido;
import com.lf.cursomc.domain.Produto;
import com.lf.cursomc.domain.enums.EstadoPagamento;
import com.lf.cursomc.domain.enums.TipoCliente;
import com.lf.cursomc.repositories.CategoriaRepository;
import com.lf.cursomc.repositories.CidadeRepository;
import com.lf.cursomc.repositories.ClienteRepository;
import com.lf.cursomc.repositories.EnderecoRepository;
import com.lf.cursomc.repositories.EstadoRepository;
import com.lf.cursomc.repositories.PagamentoRepository;
import com.lf.cursomc.repositories.PedidoRepository;
import com.lf.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepositorio;
	@Autowired
	private ProdutoRepository produtoRepositorio;
	@Autowired
	private CidadeRepository cidadeRepositorio;
	@Autowired
	private EstadoRepository estadoRepositorio;
	@Autowired
	private ClienteRepository clienteRepositorio;
	@Autowired
	private EnderecoRepository enderecoRepositorio;
	@Autowired
	private PedidoRepository pedidoRepositorio;
	@Autowired
	private PagamentoRepository pagamentoRepositorio;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/* Relação Categorias <-> Produtos é ManyToMany, logo terão arrays dos 2 lados para dizer quem se comunica com quem */
		/* instanciando as categorias que vão para o banco de dados */
		
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		/* instanciando os produtos que vão para o banco de dados*/
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		/* Colocando os produtos no array criado em categorias */
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		/* Colocando as categorias no array criado em produtos */
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		/* Salva no banco de dados as categorias e os produtos */
		
		categoriaRepositorio.saveAll(Arrays.asList(cat1,cat2));
		produtoRepositorio.saveAll(Arrays.asList(p1,p2,p3));
		
		
		/* Relação Estado <-> Cidade é OneToMany, logo terá um array no One(estado) para dizer com quem ele se comunica*/
		/* instanciando estados que vão para o banco de dados */
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		/* instanciando cidades que vão para o banco de dados */
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		/* coloca as cidades no array criado em estado*/
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		/* como o estado pode ter nenhuma ou muitas cidades e a cidade pode ser de apenas um estado,
		 * deve-se salvar primeiro o estado depois a cidade */
		
		estadoRepositorio.saveAll(Arrays.asList(est1,est2));
		cidadeRepositorio.saveAll(Arrays.asList(c1,c2,c3));
		
		/* Como o cliente é o independente ( não existe nenhum tipo de chave estrangeira )
		 * ele vai ser instanciado e salvo primeiro */
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","363789123770",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","993838393"));
	
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 303","Jardim","38220834",cli1,c1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","38777012",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepositorio.saveAll(Arrays.asList(cli1));
		
		enderecoRepositorio.saveAll(Arrays.asList(e1,e2));
		
		/* SimpleDateFormat é um tipo padrão do java que você pode determinar o tipo de Date que você quer usar*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		/* Usei set e não direto no constructor para poder instanciar pagamento antes de setar ele em pedido */
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2 , sdf.parse("20/10/2017 00:00"), null );
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		/* Pedido é salvo antes do pagamento pois o pedido é independente do pagamento */
		pedidoRepositorio.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepositorio.saveAll(Arrays.asList(pagto1,pagto2));
	}
		
}
