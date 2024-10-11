package model;

public class Dealer extends Participante {

    // Construtor do Dealer
    public Dealer() {
        super(); // Inicializa a lista de mãos e define a mão ativa como a primeira
    }

    // Implementação do método abstrato para finalizar o turno
    @Override
    protected void acaoFinalizarTurno() {
    	Jogo.getInstance().terminarRodada();  // Finaliza a rodada e calcula os resultados
    }
    
    // Método para o dealer jogar
    public void jogar() {
        while (getMaoAtiva().getValorTotal() < 17) {
            // O dealer dá um hit, pedindo uma carta ao baralho
           hit();

            // Verifica se o dealer estourou ou fez blackjack
            if (getMaoAtiva().getValorTotal() > 21) {
                System.out.println("Dealer estourou!");
                getMaoAtiva().finalizarMao(); // Finaliza a mão do dealer
                Jogo.getInstance().terminarRodada(); // Termina a rodada, calculando resultados
                return; // Sai do método
            } else if (getMaoAtiva().getValorTotal() == 21) {
                System.out.println("Dealer fez Blackjack!");
                getMaoAtiva().finalizarMao();// Finaliza a mão do dealer
                Jogo.getInstance().terminarRodada(); // Termina a rodada, calculando resultados
                return; // Sai do método
            }
        }
        System.out.println("Dealer decide não pegar mais cartas (stand).");
        getMaoAtiva().finalizarMao(); // Finaliza a mão do dealer ao atingir 17 ou mais (stand)
        Jogo.getInstance().terminarRodada(); // Termina a rodada, calculando resultados
    }
}
