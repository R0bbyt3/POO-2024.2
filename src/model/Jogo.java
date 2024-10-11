package model;

public class Jogo {
	
	public enum EstadoJogo {
	    APOSTANDO,  // 0: O jogador está apostando
	    JOGANDO,    // 1: O jogo está em andamento
	    RESULTADOS  // 2: O jogo terminou e os resultados estão sendo calculados
	}
	
    // Atributos do Jogo
    private Baralho baralho;                // O baralho usado no jogo
    private Jogador jogador;                // O jogador principal
    private Dealer dealer;                  // O dealer do jogo
    private EstadoJogo estadoDoJogo;       // Estado do jogo (usando enum)
    private int apostaMinima;               // Valor mínimo para apostas

    // Instância única do Jogo
    private static Jogo instance = null;

    // Construtor privado que inicializa o jogo com o jogador, dealer, baralho e estado inicial
    private Jogo(int numeroDeBaralhos, int apostaMinima) {
        this.baralho = new Baralho(numeroDeBaralhos);
        this.jogador = new Jogador(2400);
        this.dealer = new Dealer();
        this.estadoDoJogo = EstadoJogo.APOSTANDO;  // Começa em estado de apostas
        this.apostaMinima = apostaMinima;
    }

    // Método público estático para obter a instância única de Jogo
    public static synchronized Jogo getInstance(int numeroDeBaralhos, int apostaMinima) {
        if (instance == null) {
            instance = new Jogo(numeroDeBaralhos, apostaMinima);
        }
        return instance;
    }

    // Sobrecarga para obter a instância sem parâmetros após a primeira criação
    public static synchronized Jogo getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Jogo ainda não foi inicializado. Chame getInstance(int, int) primeiro.");
        }
        return instance;
    }

    // Método para iniciar a partida, verificando se o jogador fez uma aposta válida
    public void iniciarPartida(int apostaJogador) {
        if (jogador.getAposta() >= apostaMinima) {
            estadoDoJogo = EstadoJogo.JOGANDO; // Estado do jogo muda para jogando
            distribuirCartasIniciais();
        } else {
            System.out.println("Aposta inválida.");
        }
    }

    // Método para distribuir as cartas iniciais ao jogador e ao dealer
    private void distribuirCartasIniciais() {
        // Primeiro, o dealer recebe duas cartas
        dealer.adicionarMao(new Mao()); // Cria uma nova mão para o dealer
        dealer.getMaoAtiva().adicionarCarta(baralho.darCarta()); // Primeira carta do dealer
        dealer.getMaoAtiva().adicionarCarta(baralho.darCarta()); // Segunda carta do dealer

        System.out.println("Dealer recebeu suas duas cartas.");

        // Verifica se o dealer tem Blackjack
        if (dealer.getMaoAtiva().isBlackjack()) {
            System.out.println("Dealer tem Blackjack!");
            terminarRodada(); // Encerra a rodada imediatamente
            return; // Sai do método para evitar distribuição de cartas ao jogador
        }

        // Em seguida, o jogador recebe duas cartas
        jogador.adicionarMao(new MaoJogador(jogador.getAposta())); // Cria a mão com a aposta do jogador
        jogador.getMaoAtiva().adicionarCarta(baralho.darCarta()); // Primeira carta do jogador
        jogador.getMaoAtiva().adicionarCarta(baralho.darCarta()); // Segunda carta do jogador

        System.out.println("Jogador recebeu suas duas cartas.");

        // Verifica se o jogador tem Blackjack
        if (jogador.getMaoAtiva().isBlackjack()) {
            System.out.println("Jogador tem Blackjack!");
            terminarRodada(); // Encerra a rodada imediatamente
        } else {
            System.out.println("Cartas distribuídas. O jogo começa.");
        }
    }


    // Método para fornecer uma carta a uma mão específica
    public void darCarta(Mao mao) {
        Carta novaCarta = baralho.darCarta(); // Retira uma carta do baralho
        mao.adicionarCarta(novaCarta);         // Adiciona a carta à mão fornecida
    }

    // Método chamado quando o turno do jogador termina e passa para o dealer
    public void mudarParaDealer() {
        System.out.println("Turno do Dealer.");
        dealer.jogar();  // O Dealer joga seu turno automaticamente
    }

    // Método que calcula os resultados finais do jogo após o dealer finalizar sua jogada
    public void terminarRodada() {
        System.out.println("Calculando os resultados...");

        for (Mao jogadorMao : jogador.getMaos()) {
            boolean jogadorBlackjack = jogadorMao.isBlackjack(); // Usando o método isBlackjack
            boolean dealerBlackjack = dealer.getMaoAtiva().isBlackjack(); // Usando o método isBlackjack

            if (jogadorBlackjack && !dealerBlackjack) {
                System.out.println("Jogador tem Blackjack! Vitória automática.");
                jogador.ajustarSaldo((int) Math.round(((MaoJogador) jogadorMao).getAposta() * 2.5)); // Ganha 1,5x a aposta, arredonda para o inteiro mais próximo 
            } else if (dealerBlackjack && !jogadorBlackjack) {
                System.out.println("Dealer tem Blackjack. Jogador perde.");
            } else if (jogadorBlackjack && dealerBlackjack) {
                System.out.println("Empate com Blackjack.");
                jogador.ajustarSaldo(((MaoJogador) jogadorMao).getAposta()); // Devolve a aposta
            } else if (jogadorMao.getValorTotal() > 21) {
                System.out.println("Jogador estourou. Perdeu a mão.");
            } else if (dealer.getMaoAtiva().getValorTotal() > 21) {
                System.out.println("Dealer estourou. Jogador ganha.");
                jogador.ajustarSaldo(((MaoJogador) jogadorMao).getAposta() * 2); // Ganha o dobro
            } else if (jogadorMao.getValorTotal() > dealer.getMaoAtiva().getValorTotal()) {
                System.out.println("Jogador vence a mão.");
                jogador.ajustarSaldo(((MaoJogador) jogadorMao).getAposta() * 2); // Ganha o dobro
            } else if (jogadorMao.getValorTotal() == dealer.getMaoAtiva().getValorTotal()) {
                System.out.println("Empate.");
                jogador.ajustarSaldo(((MaoJogador) jogadorMao).getAposta()); // Devolve a aposta
            } else {
                System.out.println("Dealer vence a mão.");
            }
        }

        // Muda o estado do jogo para resultados
        estadoDoJogo = EstadoJogo.RESULTADOS;
    }

    // Método para reiniciar o jogo para uma nova rodada
    public void novaRodada() {
        if (estadoDoJogo == EstadoJogo.RESULTADOS) {
            jogador.limparMaos();
            dealer.limparMaos();
            estadoDoJogo = EstadoJogo.APOSTANDO;  // Volta para o estado de apostas
            baralho.embaralhar();  // Embaralha o baralho para a próxima rodada
        }
    }
}
