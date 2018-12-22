# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkötestein JUnitilla sekä ohjelman kokonaistoiminnallisuutta on testattu manuaalisesti.

## Yksikkötestaus

### Sovelluslogiikka

Sovelluslogiikan testeissä pyritään tarkistamaan, toimivatko luokan laskut onnistuneesti. 

### Tiedostonluku/-kirjoitus

Kaikki tiedostonluku ja -kirjoitustestit on suoritettu samaan kansioon sijoitetulla testitiedostolla.

Kaikkia tiedostonluku ja -kirjoitustoimia on pyritty testaamaan mahdollisimman kattavasti. Testeissä ei kuitenkaan ole sen erityisemmin otettu huomioon tilannetta, jossa tiedostoa ei ole olemassa.

Tällaista tilannetta ei tulisi tapahtua ilman käyttäjän manuaalista interaktiota tiedostojen kanssa.

### Testauskattavuus

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 83 % ja haarautumakattavuus 90 %.

Tämä kattavuus johtuu metodeista, jotka kutsuvat tiedostonluku/-kirjoitusmetodeja oletusarvoisella datatiedostonimellä.

![Testikattavuus](todo)

Testaamatta jäivät tilanteet, joissa käyttäjät tai tehtävät tallettavia tiedostoja ei ole, tai niihin ei ole luku- ja kirjoitusoikeutta.

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kuvaamalla tavalla.

Asennuksen onnistumisen testaaminen onnistui yksinkertaisesti testaamalla, käynnistyykö ohjelma.

Ensimmäisen kerran konfiguraatiossa pyrin testaamaan syötteitä, joita voin antaa ohjelmalle. Näillä yritin saada virheellisiä arvoja ensikerran konfiguraation luomaan tiedostoon, mutten onnistunut.

### Toiminnallisuudet

Kaikki [määrittelydokumentin](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/Vaatimusmaarittely.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi.

Virheellisiä arvoja (kuten negatiivisia, tyhjiä ja liian isoja) lukuja on testattu mahdollisimman kattavasti.

## Sovellukseen jääneet laatuongelmat

Tiedostonluku ja -kirjoitusongelmista ilmoitetaan vain konsoliin, mutta tämä ei käyttäjällä välttämättä ole auki, joten virhetila voi mennä käyttäjältä ohi.
