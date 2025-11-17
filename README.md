# Obligatorisk Oppgave 3 i DATS2300 - Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i DATS2300 - Algoritmer og datastrukturer. Den er innlevert av følgende student:
* s371439@oslomet.no

## Oppgavebeskrivelser

### Oppgave 3 - POSTORDEN

#### førstePostorden(Node<T> p)
For å finne første node i postorden må vi gå nedover i treet, for hver iterasjon til venstre hvis mulig,
ellers til høyre. Når vi kommer til en node uten barn vet vi at det er første node i postorden, fordi det
er bladnoden som er lengst til venstre i treet.

#### nestePostorden(Node<T> p)
Denne metoden finner neste node i postorden. Merk at p er en node som allerede har blitt besøkt i postorden 
traverseringen. Her er det flere ulike muligheter:
* Dersom p er roten, finnes det ingen neste node.
* Dersom p er et høyrebarn, er neste node p.forelder.
* Dersom p er venstrebarn uten søsken: neste node er p.forelder.
* Dersom p er venstrebarn med et høyresøsken: neste node er første node postorden i subrteet med høyresøskenet som rot.

