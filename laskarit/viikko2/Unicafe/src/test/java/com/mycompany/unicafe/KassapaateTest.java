
package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    Kassapaate paate;    
    Maksukortti kortti;

    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(500);
    }
    
    @Test
    public void rahaaOikeaMaaraAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void lounaidenMaaraOikeinAlussa() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    // Käteisosto edullisesti -testit
    // -------------------------------------------------------------------------
    
    @Test
    public void kateisostoEdullisestiLisaaRahaaKassaan() {
        paate.syoEdullisesti(500);
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisostoEdullisestiLisaaMyytyjenMaaraa() {
        paate.syoEdullisesti(500);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEdullisestiVaihtorahaOnOikein() {
        assertEquals(260, paate.syoEdullisesti(500));
    }    
    
    @Test
    public void kateisostoEdullisestiEiTapahduKunRahaaEiTarpeeksi() {
        paate.syoEdullisesti(239);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEdullisestiPalauttaaOikeanMaaranRahaa() {
        assertEquals(0, paate.syoEdullisesti(240));
        assertEquals(60, paate.syoEdullisesti(300));
    }
    
    @Test
    public void kateisostoEdullisestiPalauttaaKaikenKunRahaaEiTarpeeksi() {
        assertEquals(239, paate.syoEdullisesti(239));
    }
    
    // Käteisosto maukkaasti -testit
    // -------------------------------------------------------------------------
    
    @Test
    public void kateisostoMaukkaastiLisaaRahaaKassaan() {
        paate.syoMaukkaasti(500);
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisostoMaukkaastiLisaaMyytyjenMaaraa() {
        paate.syoMaukkaasti(500);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoMaukkaastiVaihtorahaOnOikein() {
        assertEquals(100, paate.syoMaukkaasti(500));
    }
    
    @Test
    public void kateisostoMaukkaastiEiTapahduKunRahaaEiTarpeeksi() {
        paate.syoEdullisesti(399);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoMaukkaastiPalauttaaOikeanMaaranRahaa() {
        assertEquals(0, paate.syoMaukkaasti(400));
        assertEquals(100, paate.syoMaukkaasti(500));
    }
    
    @Test
    public void kateisostoMaukkaastiPalauttaaKaikenKunRahaaEiTarpeeksi() {
        assertEquals(399, paate.syoMaukkaasti(399));
    }
    
    // Korttiosto edullisesti -testit
    // -------------------------------------------------------------------------
    
    @Test
    public void korttiostoEdullisestiEiLisaaRahaaKassaan() {
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void korttiostoEdullisestiLisaaMyytyjenMaaraa() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }    
    
    @Test
    public void korttiostoEdullisestiEiTapahduKunRahaaEiTarpeeksi() {
        kortti.otaRahaa(500);
        assertFalse(paate.syoEdullisesti(kortti));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoEdullisestiEiPoistaRahaaKunKortillaEiTarpeeksi() {
        kortti.otaRahaa(400);
        paate.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    // Korttiosto maukkaasti -testit
    // -------------------------------------------------------------------------    
    
    @Test
    public void korttiostoMaukkaastiEiLisaaRahaaKassaan() {
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void korttiostoMaukkaastiLisaaMyytyjenMaaraa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }    
    
    @Test
    public void korttiostoMaukkaastiEiTapahduKunRahaaEiTarpeeksi() {
        kortti.otaRahaa(500);
        assertFalse(paate.syoMaukkaasti(kortti));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoMaukkaastiEiPoistaRahaaKunKortillaEiTarpeeksi() {
        kortti.otaRahaa(400);
        paate.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
}
