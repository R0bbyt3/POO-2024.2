package model;

public class MaoJogador extends Mao {

    // Valor da aposta associado a esta mão
    private int aposta;

    // Construtor para inicializar a mão com aposta
    public MaoJogador(int apostaInicial) {
        super();  // Chama o construtor da classe base Mao
        this.aposta = apostaInicial;
    }

    // Dobra a aposta (usado para o double down)
    public void dobrarAposta() {
        this.aposta *= 2;  // Dobra o valor da aposta
    }

    // Retorna o valor da aposta associado a esta mão
    public int getAposta() {
        return aposta;
    }
    
 // Método para verificar se a mão pode ser dividida (split)
    public boolean podeSplit() {
        // A mão só pode ser dividida se tiver exatamente 2 cartas e elas tiverem o mesmo valor
        return cartas.size() == 2 && cartas.get(0).getValor() == cartas.get(1).getValor();
    }

    // Remove e retorna a segunda carta (usado no split)
    public Carta removerSegundaCarta() {
        if (cartas.size() == 2) {
            return cartas.remove(1);  // Remove e retorna a segunda carta
        }
        return null;  // Caso não haja duas cartas, retorna null
    }
}
