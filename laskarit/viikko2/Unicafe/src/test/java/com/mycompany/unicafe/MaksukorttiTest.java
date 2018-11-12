package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(10);
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeKunRahaaOnTarpeeksi() {
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuKunRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(15);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void palauttaaTrueKunRahaaTarpeeksi() {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void palauttaaFalseKunRahaaEiTarpeeksi() {
        assertFalse(kortti.otaRahaa(15));
    }
    
    @Test
    public void toStringToimiiOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void toStringToimiiOikeinLisayksenJalkeen() {
        kortti.lataaRahaa(5);
        assertEquals("saldo: 0.15", kortti.toString());
    }
    
}
