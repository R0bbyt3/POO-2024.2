 package model;

public class Carta {

    // Enum para representar os naipes da carta
    public enum Naipe {
        COPAS, ESPADAS, OUROS, PAUS
    }

    // Atributos privados para encapsulamento
    private int valor;   // Valor numérico da carta
    private Naipe naipe; // Naipe da carta

    // Construtor para inicializar a carta
    public Carta(int valor, Naipe naipe) {
        if (valor < 1 || valor > 13) {
            throw new IllegalArgumentException("Valor da carta inválido. Deve estar entre 1 (Ás) e 13 (Rei).");
        }
        this.valor = valor;
        this.naipe = naipe;
    }

    // Método getter para obter o valor da carta
    public int getValor() {
        // Se a carta for Valete, Dama ou Rei, retorna 10
        if (valor > 10) {
            return 10;
        }
        return valor;
    }

    // Método getter para obter o naipe da carta
    public Naipe getNaipe() {
        return naipe;
    }

    // Método para verificar se a carta é um Ás
    public boolean isAs() {
        return valor == 1;
    }

    // Sobrescrita do método toString() para retornar uma representação da carta
    @Override
    public String toString() {
        String nomeCarta;

        switch (valor) {
            case 1:
                nomeCarta = "Ás";
                break;
            case 11:
                nomeCarta = "Valete";
                break;
            case 12:
                nomeCarta = "Dama";
                break;
            case 13:
                nomeCarta = "Rei";
                break;
            default:
                nomeCarta = String.valueOf(valor);
                break;
        }

        return nomeCarta + " de " + naipe.toString();
    }

    // Sobrescrita do método equals() para comparar duas cartas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Carta carta = (Carta) obj;
        return valor == carta.valor && naipe == carta.naipe;
    }
}
	