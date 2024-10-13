package tests;

import model.Carta;
import model.Carta.Naipe;
import static org.junit.Assert.*;

import org.junit.Test;

public class CartaTeste {

    @Test
    public void testGetValor() {
        Carta carta = new Carta(10, Naipe.COPAS);
        assertEquals(10, carta.getValor());

        carta = new Carta(11, Naipe.ESPADAS);
        assertEquals(10, carta.getValor());

        carta = new Carta(1, Naipe.OUROS);
        assertEquals(1, carta.getValor());
    }

    @Test
    public void testGetNaipe() {
        Carta carta = new Carta(5, Naipe.PAUS);
        assertEquals(Naipe.PAUS, carta.getNaipe());
    }

    @Test
    public void testIsAs() {
        Carta carta = new Carta(1, Naipe.COPAS);
        assertTrue(carta.isAs());

        carta = new Carta(5, Naipe.ESPADAS);
        assertFalse(carta.isAs());
    }

    @Test
    public void testToString() {
        Carta carta = new Carta(1, Naipe.COPAS);
        assertEquals("Ás de COPAS", carta.toString());

        carta = new Carta(11, Naipe.ESPADAS);
        assertEquals("Valete de ESPADAS", carta.toString());

        carta = new Carta(13, Naipe.PAUS);
        assertEquals("Rei de PAUS", carta.toString());

        carta = new Carta(5, Naipe.OUROS);
        assertEquals("5 de OUROS", carta.toString());
    }

    @Test
    public void testEquals() {
        Carta carta1 = new Carta(1, Naipe.COPAS);
        Carta carta2 = new Carta(1, Naipe.COPAS);	
        Carta carta3 = new Carta(5, Naipe.ESPADAS);
        
        
        assertEquals(carta1, carta2); // Deve ser igual
        assertNotEquals(carta1, carta3); // Não deve ser igual
    }
}