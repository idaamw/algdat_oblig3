# Obligatorisk Oppgave 3 i DATS2300 - Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i DATS2300 - Algoritmer og datastrukturer. Den er innlevert av følgende student:
* s371439@oslomet.no

## Oppgavebeskrivelser

### Oppgave 1
Dette er så å si helt samme kode som i eksempelet fra kapittel 5.2. Forskjellen er at ved oppretting av et nytt nodeobjekt,
legges forelderen q inn som parameter i konstruktøren.

I while løkken flytter p og q seg nedover treet ved hjelp av komparatoren som bestemmer om de skal flytte seg mot høyre eller
venstre for hver node (komparatoren sammenlikner verdien som er oppgitt i metoden med nodens verdi). p blir alltid liggende et
steg foran q i hver iterasjon. Derfor vet vi at når p havner utenfor treet, er noden ved q forelder, og p har nådd sitt nivå.
Noden opprettes ved hjelp av konstruktøren i klassen som tar inn verdi og forelder. If setnignen avgjør om noden blir et venstre-
eller høyrebarn (eller blir rotnode hvis treet er tomt).

### Oppgave 2
I metoden antall(T verdi) sjekkes det først om treet er tomt eller om verdien vi ser etter er null, og returnerer i såfall 0. Vi kan
returnere 0 hvis vi ser etter nullverdi, fordi leggInn metoden ikke tillater nullverdier, og vi vet dermed at det ikke finnes i treet.

Videre brukes while løkke og komparatoren til å jobbe seg nedover treet. Hvis T verdi tilsvarer nodeverdien vil komparatoren gi 0, og
da økes telleren med 1. Pekeren p beveger seg så videre til høyre eller venstre utifra om komparatoren gir negativt tall (T verdi er mindre
enn nodeverdi så vi går mot venstre), eller positivt tall (T verdi er større eller lik nodeverdien så vi går mot høyre). Letingen stopper når
p==null og vi er ute av treet.

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


### Oppgave 4
I postOrden(), den ikke rekursive metoden, brukte jeg først førstePostorden(rot) for å finne første node i postorden. I while løkken kalles utførOppgave på den nåværende noden, og deretter
brukes nestePostorden() til å finne neste node. Siden nestePostorden() returnerer null når noden ikke har foreldre, vil while løkken slutte å iterere etter at oppgaven er utført på rotnoden.

For den rekursive metoden, er tankegangen at for å gå gjennom et binærtre (postorden), må man først gå gjennom hele det venstre subtreet, så det høyre subtreet, så rotnoden selv. Det samme vil
gjelde for subtrærne, og derfor er det perfekt å bruke rekursjon.

Vi har en base case som returnerer når rot == null. Så har vi et rekursivt kall som kaller metoden med venstrebarnet som rotnode. Rekursjonen vil fortsette hele veien nedover på venstre side,
helt til det ikke lenger finnes noen venstrebarn, og metoden kalles med rot: null. Dette vil føre til at base casen blir sann, og metoden returnerer. Når en metode er ferdig kjørt, som ved at den returneres,
fjernes metoden fra call stacken. Dermed er det forrige metodekallet(metodekall x, for forenkling) øverst i stacken. Denne metoden har allerede kjørt postOrdenRekursiv(p.venstre, oppgave), og går dermed videre til neste linje i koden.
Nå kalles metoden med høyrebarnet som rot. Hvis høyrebarnet eksisterer, vil rekursive kall fortsette til dets subtre har blitt gjennomgått fra venstre til høyre. Hvis det ikke eksisterer, har vi igjen null som rotnode,
og base casen utløses. Da er vi igjen tilbake i metodekall x. Siden det ikke er flere barn igjen å gå gjennom her, kan oppgaven nå utføres på rotnoden for dette kallet. Kallet fjernes så fra stacken, og vi går tilbake til forrige kall
der rotnoden er forelderen til noden vi nettopp gjorde oss ferdig med.

### Oppgave 5
Først brukes komparatoren til å finne fram til noden med gitt verdi. Videre er det 4 ulike tilfeller: p har ingen barn, p har et venstrebarn, p har et høyrebarn eller p har 2 barn.

#### Dersom p ikke har barn:
Hvis p er roten, settes roten lik null (fordi den hverken har barn eller foreldre er det den eneste noden).
Hvis p er et venstrebarn, nullstiller vi q sin venstrepeker. Hvis p er et høyrebarn, nullstiller vil q sin høyrepeker.

#### Dersom p har et venstrebarn:
Hvis p er rot, setter vi barnet lik rot og sletter nodens foreldrepeker.
Hvis p selv er et venstrebarn, setter vi q.venstre lik p.venstre. p.venstre får q som forelder. Dermed peker q og p.venstre på hverandre, og p forsvinner fra treet.
Hvis p er et høyrebarn, setter vi i stedet q sin høyrepeker lik p.venstre.

#### Dersom p har et høyrebarn:
Igjen, hvis p er rot, blir høyrebarnet den nye roten.
Hvis p er et venstrebarn, setter vi q.venstre lik p.høyre. p.høyre får q som forelder.
Hvis p er et høyrebarn, setter vi q sin høyrepeker lik p.høyre.

#### Dersom p har 2 barn:
Her lager vi nye pekere så vi kan finne frem til den nye noden og dens forelder, samtidig som vi bevarer p.
Vi ønsker å finne den minste verdien i det høyre undertreet, det vil si at vi først tar et skritt til høyre, før vi deretter
kun beveger oss mot venstre. Derfor blir s = p og r = p.høyre.
Når vi har beveget oss helt ned til venstre i undertreet, vet vi at vi har kommet fram til riktig node og vi kan gi verdien fra r
til noden p. Vi vet at r enten har 0 barn, eller et høyrebarn. Dette må vi isåfall flytte opp så det blir barnet til s. Hvis s fortsatt er lik p,
så vil det være høyrepekeren til s som peker på r.høyre. Hvis vi derimot har beveget oss nedover, vil det være venstrepekeren til s som peker på r.høyre.

#### *

fjernAlle(T verdi) trenger bare å bruke fjern(T verdi) iterativt. Siden fjern() er en boolean metode vil den returnere true så lenge
den finner en verdi å fjerne, og da vil while løkken fortsette å kjøre.

nullstill() bruker postorden metodene fra tidligere til å gå gjennom treet, og nullstiller alle pekere og verdier.

