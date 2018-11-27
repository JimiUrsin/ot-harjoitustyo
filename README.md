# Budget Spinner
Sovelluksen avulla voi pitää yllä tietoa siitä, kuinka paljon rahaa käyttäjällä on saatavilla kunakin päivänä. Sovellukselle määritellään aluksi kuukausittaiset tulot ja menot, joiden pohjalta sovellus laskee käytettävissä olevaan rahamäärään päivittäin lisättävän summan. Käyttäjän satunnaistulot ja -menot kasvattavat tai laskevat käytettävissä olevan rahan määrää.

## Dokumentaatio

[Ajankäyttö](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/Ajankaytto.md)

[Vaatimusmäärittely](https://github.com/JimiUrsin/ot-harjoitustyo/blob/master/dokumentaatio/Vaatimusmaarittely.md)

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

Ohjelma voidaan suorittaa suoraan komennolla 
```
mvn compile exec:java -Dexec.mainClass=budgetspinner.Main
```

Kannattanee poistaa sovelluksen juuresta löytyvä data.txt aina ennen testausta. Nykyisessä tilassa jos data.txt on olemassa, ohjelma ei oikeastaan tee mitään.

### Checkstyle

Checkstyle suoritetaan komennolla
```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
