package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import view.JanelaInicial;
import view.JanelaBanca;
import view.JanelaMao;
import view.JanelaMaoJogador;

public class Controller {

    private JanelaInicial janelaInicial;
    private JanelaBanca janelaBanca;
    private JanelaMao maoDealer;
    private JanelaMaoJogador maoJogador;
    private JanelaMaoJogador maoJogadorSplit;

    private Rectangle[] botoesBounds;
    private String[] botoesLabels = {"EXIT", "DOUBLE", "SPLIT", "CLEAR", "DEAL", "HIT", "STAND", "SURRENDER"};

    public Controller() {
        janelaInicial = new JanelaInicial();
        janelaInicial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (janelaInicial.getBotaoNovoJogoBounds().contains(p)) {
                    System.out.println("Botão 'Novo Jogo' clicado");
                    iniciarJogo();
                } else if (janelaInicial.getBotaoContinuarBounds().contains(p)) {
                    System.out.println("Botão 'Continuar Jogo' clicado");
                }
            }
        });
    }

    private void iniciarJogo() {
        janelaInicial.dispose();
        janelaBanca = new JanelaBanca();
        configurarBotoes();

        janelaBanca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                for (int i = 0; i < botoesBounds.length; i++) {
                    if (botoesBounds[i].contains(p)) {
                        System.out.println("Botão '" + botoesLabels[i] + "' clicado!");
                    }
                }
            }
        });

        configurarJanelas();
    }

    private void configurarBotoes() {
        botoesBounds = new Rectangle[]{
            new Rectangle(25, 600, 140, 50),    // EXIT
            new Rectangle(220, 700, 140, 50),   // DOUBLE
            new Rectangle(360, 700, 140, 50),   // SPLIT
            new Rectangle(510, 700, 140, 50),   // CLEAR
            new Rectangle(660, 700, 140, 50),   // DEAL
            new Rectangle(840, 600, 140, 50),   // HIT
            new Rectangle(840, 650, 140, 50),   // STAND
            new Rectangle(840, 700, 140, 50)    // SURRENDER
        };
    }

    private void configurarJanelas() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (tela.width - (1006 + 800)) / 2;
        int startY = (tela.height - 761) / 2;

        janelaBanca.setLocation(startX, startY);

        maoDealer = new JanelaMao(JanelaMao.TipoMao.DEALER);
        maoDealer.setLocation(startX + 1006, startY);

        maoJogador = new JanelaMaoJogador();
        maoJogador.setLocation(maoDealer.getX(), maoDealer.getY() + 250 + 10);

        maoJogadorSplit = new JanelaMaoJogador();
        maoJogadorSplit.setLocation(maoJogador.getX(), maoJogador.getY() + 250 + 10);

        janelaBanca.setVisible(true);
        maoDealer.setVisible(true);
        maoJogador.setVisible(true);
        maoJogadorSplit.setVisible(true);

     // Configurações para o Dealer
        maoDealer.atualizarPontos(18);
        maoDealer.receberCarta("2c");
        maoDealer.receberCarta("kh");

        // Configurações para o Jogador Principal
        maoJogador.atualizarPontos(15);
        maoJogador.atualizarAposta(100);
        maoJogador.atualizarDinheiro(900);
        maoJogador.receberCarta("5h");
        maoJogador.receberCarta("7d");

        // Configurações para a Segunda Mão do Jogador (Split)
        maoJogadorSplit.atualizarPontos(13);
        maoJogadorSplit.atualizarAposta(50);
        maoJogadorSplit.atualizarDinheiro(850);
        maoJogadorSplit.receberCarta("9s");
        maoJogadorSplit.receberCarta("3d");
    }

    public static void main(String[] args) {
        new Controller();
    }
}
