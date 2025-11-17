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
        throw new NullPointerException();
    }

    if (antall == 0) rot = new Node<>(verdi, null, null, null);
    else {
        Node<T> denne = rot;
        Node<T> forelder = null;
        int cmp = 0;
        while (denne != null) {
            forelder = denne;
            cmp = comp.compare(verdi, denne.verdi);
            if (cmp < 0) {
                denne = denne.venstre;
            }
            else {
                denne = denne.høyre;
            }
        }
        Node <T> nynode = new Node<>(verdi, null, null, forelder);
        if (cmp < 0) forelder.venstre = nynode;
        else forelder.høyre = nynode;
        //Setter høyre/venstre pekeren fra forelder.
    }
    antall++;
    endringer++;
    return true;
    }


    // Oppgave 2
    // Teller antall forekomster av en verdi i treet
    public int antall(T verdi){
    if (verdi == null) return 0;
    int teller = 0;
    Node<T> denne = rot;

    while (denne != null){
        int cmp = comp.compare(verdi, denne.verdi);
        if (cmp == 0) {
            teller ++;
            denne = denne.høyre;
        }
        else if (cmp < 0){
            denne = denne.venstre;
        } else denne = denne.høyre;
    }
    return teller;
    }

    // Oppgave 3
    private Node<T> førstePostorden(Node<T> p) {
        Node<T> denne = p;
        while (denne.venstre != null || denne.høyre != null){ //hvis denne har barn
            if (denne.venstre != null) denne = denne.venstre; //går til venstre hvis mulig;
            else denne = denne.høyre;
            }
        return denne;
    }

    private Node<T> nestePostorden(Node<T> p) {
        Node<T> neste = null;
        if (p == rot) return neste;
        else if (p.forelder.høyre == p) neste = p.forelder;
        else if (p.forelder.venstre == p){
            if (p.forelder.høyre == null) neste = p.forelder;
            else {
                // neste node er første noden i subtreet der høyresøskenet er rot
                Node<T> nyrot = p.forelder.høyre;
                while (nyrot.venstre != null || nyrot.høyre != null){
                    if (nyrot.venstre != null) nyrot = nyrot.venstre;
                    else nyrot = nyrot.høyre;
                }
                neste = nyrot;
            }
        }
        return neste;
    }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {
        Node<T> node = førstePostorden(rot);
        while (node != null) {
            oppgave.utførOppgave(node.verdi);
            node = nestePostorden(node);
        }
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {

        if (p.venstre == null && p.høyre == null){
            oppgave.utførOppgave(p.verdi);
            return;
        }
        // base case

        if (p.venstre != null){
            postOrdenRekursiv(p.venstre, oppgave);
        }
        if (p.høyre != null) {
            postOrdenRekursiv(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }

    // Oppgave 5
    public boolean fjern(T verdi) { throw new UnsupportedOperationException(); }
    public int fjernAlle(T verdi) { throw new UnsupportedOperationException(); }
    public void nullstill() { throw new UnsupportedOperationException(); }
}