package model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Mao {

    // Lista de cartas na mão
    protected List<Carta> cartas;

    // Valor total da mão
    protected int valorTotal;

    // Indica se a mão está finalizada
    protected boolean finalizado;

    // Construtor padrão para inicializar a mão vazia (sem aposta)
    public Mao() {
        this.cartas = new ArrayList<>();
        this.valorTotal = 0;
        this.finalizado = false;  // Inicialmente, a mão não está finalizada
    }

    // Adiciona uma carta à mão e recalcula o valor total
    public void adicionarCarta(Carta carta) {
        if (!finalizado) {  // Só adiciona cartas se a mão ainda não foi finalizada
            cartas.add(carta);
            calcularValor();

            // Verifica se a mão deve ser finalizada (por estourar ou blackjack)
            if (valorTotal > 21) {
                finalizado = true;  // Estourou, a mão é finalizada
            } else if (cartas.size() == 2 && valorTotal == 21) {
                finalizado = true;  // Blackjack, a mão é finalizada
            }
        }
    }

    // Método para calcular o valor da mão, considerando o Ás (1 ou 11)
    protected void calcularValor() {
        int total = 0;
        int quantidadeAses = 0;

        for (Carta carta : cartas) {
            if (carta.isAs()) {
                quantidadeAses++;
                total += 11;
            } else {
                total += carta.getValor();
            }
        }

        while (total > 21 && quantidadeAses > 0) {
            total -= 10;
            quantidadeAses--;
        }

        valorTotal = total;
    }


    // Finaliza a mão explicitamente (stand, double ou outra condição)
    public void finalizarMao() {
        finalizado = true;
    }

    // Retorna o valor total da mão
    public int getValorTotal() {
        return valorTotal;
    }

    // Verifica se a mão está finalizada
    public boolean isFinalizado() {
        return finalizado;
    }

    public boolean isBlackjack() {
        return cartas.size() == 2 && valorTotal == 21;
    }

    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

}
