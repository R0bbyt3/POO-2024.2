package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import model.Baralho;
import model.Carta;

import org.junit.Before;

public class BaralhoTest {

	private Baralho baralho1;
	private Baralho baralho2;

	@Before
	public void setUp() {
		// Cria um baralho com 1 baralho (52 cartas) e 8 baralhos
		baralho1 = new Baralho(1);
		baralho2 = new Baralho(8);
	}

	@Test
	public void testConstrutorInicializaCorretamente() {
		// Verifica se o número de cartas do baralho com 1 conjunto e 8 conjuntos
		assertEquals(52, baralho1.getCartas().size());
		assertEquals(416, baralho2.getCartas().size());

		// Verifica que o limite para embaralhar é 10% do total
		assertTrue(baralho1.getLimiteEmbaralhar() == 5 || baralho1.getLimiteEmbaralhar() == 6);     // apenas 1
		assertTrue(baralho2.getLimiteEmbaralhar() == 41 || baralho2.getLimiteEmbaralhar() == 42);

		// Verifica se o contador de cartas usadas é 0
		assertEquals(0, baralho1.getCartasUsadas());
		assertEquals(0, baralho2.getCartasUsadas());
	}

	@Test
	public void testEmbaralhar() {
		// Salva o estado do baralho antes de embaralhar
		List<Carta> cartasAntesEmbaralhar1 = new ArrayList<>(baralho1.getCartas());
		List<Carta> cartasAntesEmbaralhar2 = new ArrayList<>(baralho2.getCartas());

		// Embaralha as cartas
		baralho1.embaralhar();
		baralho2.embaralhar();

		// Verifica que as cartas foram embaralhadas (ou seja, a ordem mudou)
		assertNotEquals(cartasAntesEmbaralhar1, baralho1.getCartas());
		assertNotEquals(cartasAntesEmbaralhar2, baralho2.getCartas());

		// Verifica se o contador de cartas usadas foi reiniciado
		assertEquals(0, baralho1.getCartasUsadas());
		assertEquals(0, baralho2.getCartasUsadas());
	}

	@Test
	public void testDarCarta() {
		// Tira a primeira carta do baralho
		Carta primeiraCarta1 = baralho1.darCarta();
		Carta primeiraCarta2 = baralho2.darCarta();

		// Verifica se a carta foi distribuída e não é nula
		assertNotNull(primeiraCarta1);
		assertNotNull(primeiraCarta2);

		// Verifica se o número de cartas usadas aumentou
		assertEquals(1, baralho1.getCartasUsadas());
		assertEquals(1, baralho2.getCartasUsadas());
	}

	@Test
	public void testEmbaralharAposLimiteUsado() {
		// Distribui o número de cartas até atingir o limite de embaralhamento (10% do
		// total)
		for (int i = 0; i < baralho1.getLimiteEmbaralhar() ; i++) {
			baralho1.darCarta();
		}
		for (int i = 0; i < baralho2.getLimiteEmbaralhar() ; i++) {
			baralho2.darCarta();
		}

		// O embaralhamento deve ter sido acionado e o contador deve ter sido reiniciado
		assertEquals(0, baralho1.getCartasUsadas());
		assertEquals(0, baralho2.getCartasUsadas());
	}

	@Test
	public void testTodasCartasSaoUnicas() {
		// Distribui todas as cartas do baralho e verifica se todas são únicas
		Set<Carta> cartasDistribuidas = new HashSet<>();
		List<Carta> cartas = baralho1.getCartas();
		for (int i = 0; i < 52; i++) {
			cartasDistribuidas.add(cartas.get(i));
		}

		// Verifica se o número de cartas distribuídas é igual a 52 e todas são únicas
		assertEquals(52, cartasDistribuidas.size());
	}

}
