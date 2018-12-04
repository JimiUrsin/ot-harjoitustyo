﻿# Budget Spinner
Sovelluksen avulla voi pitää yllä tietoa siitä, kuinka paljon rahaa käyttäjällä on saatavilla kunakin päivänä. Sovellukselle määritellään aluksi kuukausittaiset tulot ja menot, joiden pohjalta sovellus laskee käytettävissä olevaan rahamäärään päivittäin lisättävän summan. Käyttäjän satunnaistulot ja -menot kasvattavat tai laskevat käytettävissä olevan rahan määrää.

## Attribution

Gear icon made by [Anton Saputro](http://www.antonps.com/) on [Flaticon](https://www.flaticon.com/free-icon/options-gear_70483)

## Dokumentaatio

[Ajankäyttö](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/Ajankaytto.md)

[Vaatimusmäärittely](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/Vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Releaset
TBD

## Komentorivitoiminnot

### Testaus
Testaus suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```
### Suoritus
Ohjelma voidaan suorittaa suoraan komennolla 
```
mvn compile exec:java -Dexec.mainClass=budgetspinner.Main
```


### Checkstyle

Checkstyle suoritetaan komennolla
```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
