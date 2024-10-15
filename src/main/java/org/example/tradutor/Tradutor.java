package org.example.tradutor;

import java.util.HashMap;
import java.util.Map;

public class Tradutor {
    private static final Map<String, String> traducoes = new HashMap<>();

    static {
        traducoes.put("Shopping", "Compras");
        traducoes.put("Mortgage & Rent", "Hipoteca e Aluguel");
        traducoes.put("Restaurants", "Restaurantes");
        traducoes.put("Credit Card Payment", "Cartão de Crédito");
        traducoes.put("Movies & DVDs", "Filmes e DVDs");
        traducoes.put("Home Improvement", "Melhorias em Casa");
        traducoes.put("Utilities", "Serviços Públicos");
        traducoes.put("Gas & Fuel", "Combustível");
        traducoes.put("Groceries", "Mercearia");
        traducoes.put("Fast Food", "Fast Food");
        traducoes.put("Paycheck", "Salário");
        traducoes.put("Mobile Phone", "Conta Telefonica");
        traducoes.put("Music", "Música");
        traducoes.put("Coffee Shops", "Cafés");
        traducoes.put("Alcohol & Bars", "Bares e Bebidas");
        traducoes.put("Haircut", "Corte de Cabelo");
        traducoes.put("Japanese Restaurant", "Restaurante Japonês");
        traducoes.put("Gas Station", "Posto de Gasolina");
        traducoes.put("Internet Service Provider", "Provedor de Serviços de Internet");
        traducoes.put("BBQ Restaurant", "Restaurante de Churrasco");
        traducoes.put("Brunch Restaurant", "Restaurante de Brunch");
        traducoes.put("Power Company", "Companhia de Energia");
        traducoes.put("Biweekly Paycheck", "Salário Quinzenal");
        traducoes.put("Brewing Company", "Cervejaria");
        traducoes.put("Pizza Place", "Pizzaria");
        traducoes.put("Mediterranean Restaurant", "Restaurante Mediterrâneo");
        traducoes.put("City Water Charges", "Conta de água");
        traducoes.put("Grocery Store", "Supermercado");
        traducoes.put("Belgian Restaurant", "Restaurante Belga");
        traducoes.put("Chili's", "Chili's");
        traducoes.put("Greek Restaurant", "Restaurante Grego");
        traducoes.put("Hardware Store", "Loja de Ferragens");
        traducoes.put("Mortgage Payment", "Pagamento da IPTU");
        traducoes.put("debit", "Débito");
        traducoes.put("credit", "Crédito");
        traducoes.put("Platinum Card", "Cartão Platina");
        traducoes.put("Silver Card", "Cartão Prateado");
        traducoes.put("Checking", "Verificando pagamento");

    }

    public static String traduzir(String palavra) {
        return traducoes.getOrDefault(palavra, palavra);
    }
}
