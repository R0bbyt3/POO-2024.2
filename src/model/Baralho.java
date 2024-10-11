package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {

    // Atributo para armazenar todas as cartas geradas
    private List<Carta> cartas;
    private int cartasUsadas; // Número de cartas usadas desde o último embaralhamento
    private int limiteEmbaralhar;  // Valor arredondado equivalente ao total inicial - 10%
    
    // Construtor que inicializa o baralho com a quantidade desejada de baralhos
    public Baralho(int quantidadeDeBaralhos) {
        cartas = new ArrayList<>();
        for (int i = 0; i < quantidadeDeBaralhos; i++) {
            gerarBaralho();
        }
        embaralhar(); // Embaralha as cartas inicialmente
        limiteEmbaralhar = (int) Math.round(cartas.size() * 0.1); //10% do baralho 
        cartasUsadas = 0; // 0 cartas usadas      
    }
    
    // Método privado para gerar um baralho completo (52 cartas)
    private void gerarBaralho() {
        for (Carta.Naipe naipe : Carta.Naipe.values()) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas.add(new Carta(valor, naipe));
            }
        }
    }

    // Método público para embaralhar as cartas
    public void embaralhar() {
        Collections.shuffle(cartas);
        cartasUsadas = 0; // Reinicia o uso de cartas 
    }

 // Método para fornecer a primeira carta do baralho e movê-la para o final
    public Carta darCarta() {

        // Retira a primeira carta do baralho (no topo)
        Carta cartaDistribuida = cartas.remove(0); 

        // Adiciona a carta no final do baralho
        cartas.add(cartaDistribuida);

        // Incrementa o contador de cartas usadas
        cartasUsadas++;

        // Verifica se o número de cartas usadas atingiu 10% do total inicial
        if (cartasUsadas >= limiteEmbaralhar) {
        	embaralhar();
        }

        return cartaDistribuida; // Retorna a carta que foi distribuída
    }

}

