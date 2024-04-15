package br.edu.fatec.produtos;

import br.edu.fatec.produtos.services.ConsomeApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
public class ProdutosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProdutosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsomeApi lerApi = new ConsomeApi();
		String dados = lerApi.obterdados("https://api.escuelajs.co/api/v1/products");

			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonNode = objMapper.readTree(dados);

			List<JsonNode> produtolist = new ArrayList<>();
			jsonNode.forEach(produtolist::add);

			List<String> imperdiveis = produtolist.stream()
					.filter(node -> node.get("price").asDouble() <= 30)
					.map(node -> node.get("title").asText())
					.toList();

			List<String> promocao = produtolist.stream()
					.filter(node -> node.get("price").asDouble() > 0)
					.map(node -> node.get("title").asText().toUpperCase())
					.toList();

			System.out.println("Imperdíveis:");
			imperdiveis.forEach(System.out::println);

			System.out.println("Promoção:");
			promocao.forEach(System.out::println);
		
		}
	}

