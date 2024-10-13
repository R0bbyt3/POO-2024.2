package model;

import java.util.List;

public class Jogador extends Participante {

    private int saldo;   // Dinheiro que o jogador possui
    private int aposta;  // Valor da aposta atual

    // Construtor do jogador com saldo inicial
    public Jogador(int saldoInicial) {
        this.saldo = saldoInicial;
        this.aposta = 0;  // Inicia a aposta com zero
    }

    // Método para ajustar o saldo do jogador
    public void ajustarSaldo(int valor) {
        saldo += valor;
    }

    // Retorna o saldo atual do jogador
    public int getSaldo() {
        return saldo;
    }

    // Retorna a aposta atual do jogador
    public int getAposta() {
        return aposta;
    }

    // Método para mudar a aposta
    public void mudarAposta(int valor) {
        // Se a aposta for negativa
        if (valor < 0) {
            // Verifica se a aposta menos o saldo é menor que zero
            if ((aposta + valor) < 0) { // "aposta + valor" se torna a nova aposta
                System.out.println("Aposta não pode ser negativa.");
                return; // Retorna se a condição não for atendida
            } else {
                // Se o valor é negativo e a aposta menos o valor é válido, devolve ao saldo
                saldo += -valor; // Adiciona o valor de volta ao saldo
                aposta += valor; // Atualiza a aposta
                System.out.println("Aposta reduzida para: " + aposta);
            }
        } else {
            // Se a aposta é positiva
            if (saldo >= valor) { // Verifica se o saldo é suficiente
                aposta += valor; // Atualiza a aposta
                saldo -= valor; // Desconta o valor do saldo
                System.out.println("Aposta aumentada para: " + aposta);
            } else {
                System.out.println("Saldo insuficiente para a aposta.");
            }
        }
    }
    
	 // Implementação do método abstrato para finalizar o turno
	 @Override
	 protected void acaoFinalizarTurno() {
		 Jogo.getInstance().mudarParaDealer();  // Passa o turno para o dealer
	 }

    // Método para obter todas as mãos do jogador
    public List<Mao> getMaos() {
        return maos; // Retorna a lista de mãos do jogador
    }
    
    // Método para realizar o "double", dobrando a aposta
    public void doubleAposta() {
        if (getSaldo() >= ((MaoJogador) getMaoAtiva()).getAposta() * 2) {
            ajustarSaldo(-((MaoJogador) getMaoAtiva()).getAposta());  // Diminui o saldo ao dobrar a aposta
            ((MaoJogador) getMaoAtiva()).dobrarAposta();
            hit();  // Dá uma carta a mais
            stand();  // Automaticamente finaliza a mão após o "double"
        } else {
            System.out.println("Saldo insuficiente para dar double.");
        }
    }

 // Método para realizar o "split", dividindo uma mão em duas
    public void split() {
        // Verifica se o jogador já tem mais de uma mão
        if (maos.size() > 1) {
            System.out.println("Não é possível dividir as cartas, já existe mais de uma mão.");
            return; // Sai do método se já houver mais de uma mão
        }

        Mao maoAtiva = getMaoAtiva();
        // Verifica se pode realizar o split e se o saldo é suficiente
        if (((MaoJogador) maoAtiva).podeSplit() && getSaldo() >= ((MaoJogador) maoAtiva).getAposta()) {
            ajustarSaldo(-((MaoJogador) maoAtiva).getAposta());  // Aposta da segunda mão
            MaoJogador novaMao = new MaoJogador(((MaoJogador) maoAtiva).getAposta());

            // Move a segunda carta da mão ativa para a nova mão
            novaMao.adicionarCarta(((MaoJogador) maoAtiva).removerSegundaCarta());

            // A nova mão também recebe uma carta do baralho
            Jogo.getInstance().darCarta(maoAtiva);
            Jogo.getInstance().darCarta(novaMao);

            adicionarMao(novaMao);  // Adiciona a nova mão ao jogador
        } else {
            System.out.println("Não é possível dividir as cartas.");
        }
    }


 // Método para realizar o "surrender", desistindo da mão e recuperando metade da aposta
    public void surrender() {
        // Verifica se o jogador tem apenas uma mão
        if (maos.size() == 1) {
            MaoJogador maoAtiva = (MaoJogador) getMaoAtiva(); // Faz downcast para MaoJogador
            // Verifica se a mão ativa tem exatamente duas cartas
            if (maoAtiva.getCartas().size() == 2) {
                int aposta = maoAtiva.getAposta(); // Obtém a aposta da mão ativa
                ajustarSaldo(aposta / 2);  // Retorna metade da aposta
                maoAtiva.finalizarMao();  // Finaliza a mão
                trocarTurno();  // Troca de turno
            } else {
                System.out.println("Não pode dar surrender neste momento.");
            }
        } else {
            System.out.println("Não pode dar surrender neste momento.");
        }
    }

}
