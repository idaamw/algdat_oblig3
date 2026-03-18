package no.oslomet.cs.algdat;

import java.util.*;

public class SøkeBinærTre<T>  implements Beholder<T> {

    // En del kode er ferdig implementert, hopp til linje 91 for Oppgave 1

    private static final class Node<T> { // En indre nodeklasse
        private T verdi; // Nodens verdi
        private Node<T> venstre, høyre, forelder; // barn og forelder

        // Konstruktører
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> f) {
            this.verdi = verdi;
            venstre = v; høyre = h; forelder = f;
        }
        private Node(T verdi, Node<T> f) {
            this(verdi, null, null, f);
        }

        @Override
        public String toString() {return verdi.toString();}
    } // class Node

    private final class SBTIterator implements Iterator<T> {
        Node<T> neste;
        public SBTIterator() {
            neste = førstePostorden(rot);
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            Node<T> denne = neste;
            neste = nestePostorden(denne);
            return denne.verdi;
        }
    }

    public Iterator<T> iterator() {
        return new SBTIterator();
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SøkeBinærTre(Comparator<? super T> c) {
        rot = null; antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    public int antall() { return antall; }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() { return antall == 0; }

    // Oppgave 1
    public boolean leggInn(T verdi) {
        if (verdi == null){
            throw new NullPointerException("Kan ikke legge inn nullverdi");}

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null)
        {
            q = p;
            cmp = comp.compare(verdi,p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }

        p = new Node<>(verdi, q);                   // oppretter nodeobjekt med forelder q

        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        return true;
    }


    // Oppgave 2
    public int antall(T verdi){

        boolean tom = tom();
        if (tom || verdi==null) return 0;

        int teller = 0;
        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp == 0) teller++;
            if (cmp < 0) p = p.venstre;
            else p = p.høyre;
        }

        return teller;
    }

    // Oppgave 3
    private Node<T> førstePostorden(Node<T> p) {

        while (true) {  //while(true) kjører helt til den møter på return eller break
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private Node<T> nestePostorden(Node<T> p) {

        Node<T> neste = null;

        if (p.forelder == null) neste = null; //hvis roten er første noden i postorden, finnes det ingen flere noder.
        else if (p == p.forelder.høyre || p.forelder.høyre == null) neste = p.forelder;
            //hvis p er høyrebarn ELLER forelderen ikke har høyrebarn, er forelderen neste.
        else if (p.forelder.høyre != p && p.forelder.høyre != null) { //Forelder har høyrebarn
            neste = førstePostorden(p.forelder.høyre); //Neste node i postorden er første node i subtreet med høyrebarnet som rot.
        }

        return neste;
    }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {
        //utførOppgave() skal kjøres på hver node i treet etter postorden.

        if (rot == null) return;

        Node<T> p = førstePostorden(rot);
        while (p != null){ //siden nestePostorden returnerer null når p er roten, vil while løkken stoppe etter rotnoden.
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }

    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }
    //Fordi rot er private

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {

        if (p == null) return; //base case

        postOrdenRekursiv(p.venstre, oppgave);
        postOrdenRekursiv(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    // Oppgave 5
    public boolean fjern(T verdi) {
        if (verdi == null) return false;
        Node<T> p = rot, q = null;
        while (p != null){
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp == 0) {
                break;
            }
            else if (cmp > 0){ //verdi er større enn p.verdi. gå til høyre
                q = p;
                p = p.høyre;
            }
            else { //verdi er mindre enn p.verdi. gå til venstre
                q = p;
                p = p.venstre;
            }
            //q er forelder til p.
        }
        if (p == null) return false; //verdi ikke funnet.
        //koden videre vil gjelde for funnet verdi. pekeren p står ved noden, q ved dens forelder.
        if (p.venstre == null && p.høyre == null) { //p har ingen barn
            if (p == rot) rot = null;
            else if (q.venstre == p) q.venstre = null;
            else q.høyre = null;
        }
        else if (p.venstre != null && p.høyre == null){ //p har venstrebarn
            if (p == rot) {
                rot = p.venstre;
                rot.forelder = null;
            } else if (q.venstre == p) {
                q.venstre = p.venstre;
                p.venstre.forelder = q;
            } else {
                q.høyre = p.venstre;
                p.venstre.forelder = q;
            }
        } else if (p.venstre == null && p.høyre != null){ //p har høyrebarn
            if (p == rot) {
                rot = p.høyre;
                rot.forelder = null;
            } else if (q.venstre == p) {
                q.venstre = p.høyre;
                p.høyre.forelder = q;
            } else {
                q.høyre = p.høyre;
                p.høyre.forelder = q;
            }
        } else { // p har 2 barn
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null){
                s = r; //s er forelder til r
                r = r.venstre;
            } //finner minste verdi i høyre undertre
            p.verdi = r.verdi;
            if (s == p) { // r er direkte høyrebarn
                s.høyre = r.høyre;
                if (r.høyre != null) r.høyre.forelder = s;
            } else {
                s.venstre = r.høyre;
                if (r.høyre != null) r.høyre.forelder = s;
            }
        }
        antall--;
        return true;
    }


    public int fjernAlle(T verdi) {
        int teller = 0;

        while (fjern(verdi)) {
            teller ++;
        }

        return teller;
    }

    public void nullstill() {
        if (rot == null) return;

        Node<T> p = førstePostorden(rot);

        while (p != null) {
            Node<T> neste = nestePostorden(p);

            p.venstre = null;
            p.høyre = null;
            p.forelder = null;
            p.verdi = null;

            p = neste;
        }

        rot = null;
        antall = 0; }
}