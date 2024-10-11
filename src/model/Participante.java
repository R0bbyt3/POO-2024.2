package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Participante {

    // Lista de mãos que o participante possui
    protected List<Mao> maos;

    // Mão ativa que o participante está jogando atualmente
    protected int maoAtiva;

    // Construtor inicializando a lista de mãos e definindo a mão ativa como a primeira
    public Participante() {
        maos = new ArrayList<>();
        maoAtiva = 0;  // Primeira mão é a ativa por padrão
    }

    // Método para adicionar uma nova mão ao participante
    public void adicionarMao(Mao mao) {
        maos.add(mao);
    }

    // Retorna a mão ativa atual do participante
    public Mao getMaoAtiva() {
        return maos.get(maoAtiva);
    }

    // Método para realizar o hit (pede uma carta ao jogo para a mão ativa)
    public void hit() {
        if (!getMaoAtiva().isFinalizado()) {  // Verifica se a mão já não foi finalizada
        	Jogo.getInstance().darCarta(getMaoAtiva());     // Passa a mão ativa para darCarta
            if (getMaoAtiva().isFinalizado()) {
                trocarTurno();
            }
        }
    }

    // Finaliza a mão ativa do participante (ação de stand)
    public void stand() {
        getMaoAtiva().finalizarMao();
        trocarTurno();  // Após dar stand, tenta trocar o turno
    }

    // Método abstrato para finalizar o turno, a ser implementado pelas subclasses
    protected abstract void acaoFinalizarTurno();

    // Método para alternar para a próxima mão ativa ou trocar o turno
    public void trocarTurno() {
        // Se houver mais de uma mão e a próxima mão não estiver finalizada, troca para a próxima mão
        if (maoAtiva < maos.size() - 1) {
            maoAtiva++;
            // Checa se a próxima mão também está finalizada
            if (getMaoAtiva().isFinalizado()) {
                trocarTurno();  // Caso a próxima mão já tenha sido finalizada, troca novamente
            }
        } else {
            // Todas as mãos do participante foram finalizadas
            acaoFinalizarTurno();  // Chama o método abstrato
        }
    }

    // Limpa todas as mãos do participante para uma nova rodada
    public void limparMaos() {
        maos.clear();
        maoAtiva = 0;  // Reseta para primeira mão como ativa
    }
}
