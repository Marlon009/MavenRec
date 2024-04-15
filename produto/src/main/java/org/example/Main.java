package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        try {
            // Carrega os dados da API
            ObjectMapper mapper = new ObjectMapper();
            JsonNode products = mapper.readTree(new URL("https://api.escuelajs.co/api/v1/products/"));
            List<String> imperdiveis = products.findValuesAsText("price").stream()
                    .filter(price -> new BigDecimal(price).compareTo(new BigDecimal("30")) < 0)
                    .map(price -> products.findValue("title").asText())
                    .collect(Collectors.toList());

            System.out.println("Produtos imperdíveis:");
            imperdiveis.forEach(System.out::println);

            System.out.println("\nProdutos em promoção:");
            products.findValuesAsText("title").stream()
                    .map(String::toUpperCase)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
