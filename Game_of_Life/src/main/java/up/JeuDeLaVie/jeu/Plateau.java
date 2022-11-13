package up.JeuDeLaVie.jeu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Plateau {
    public LinkedHashSet<Cellule> cellulesVivantes; // il s'agit d'un ensemble de coordonnées qui sont celles des
                                                    // cellules vivantes
    private ArrayList<Cellule> cellulesVivantesAvecVoisines; // On collectera ici, à chaque appel de
                                                             // générationSuivante() (qui fera appel à une autre
                                                             // fonction), chaque coordonnée de cellulesVivantes ainsi
                                                             // que ses 4 voisines (vivantes ou non)
    private int tab[] = { 8, 2, 3 };// tab contient :
    // tab[0] le nombre de cellules voisines considérées
    // tab[1] le nombre de cellules voisines que doit avoir une cellule vivante pour
    // suirvire
    // tab[2] le nombre de cellules voisines vivantes que doit avoir une cellule
    // morte pour vivre
    public static int nbGenerations = 0; // le numéro de la génération en cours

    public Plateau() {
        cellulesVivantes = new LinkedHashSet<>();
        cellulesVivantesAvecVoisines = new ArrayList<>();
    }

    public void setTab(int[] tab) {
        this.tab = tab;
    }

    public void setCellulesVivantes(LinkedHashSet<Cellule> cellulesVivantes) {
        this.cellulesVivantes = cellulesVivantes;
    }

    public HashSet<Cellule> getCellulesVivantes() {
        return cellulesVivantes;
    }

    public Cellule getCellule(int x, int y) {
        if (estVivante(x, y))
            return new Cellule(x, y);
        else {
            return null;
        }
    }

    void compteVoisinesVivantes(Cellule cellule) {
        int cpt = 0;
        for (Cellule c : cellulesVivantesAvecVoisines) {
            if (c.equals(cellule)) {
                cpt++;
            }
        }
        cellule.setNbVoisinesVivantes(cpt);
    }

    // elle ajoute la cellule courante et ces 8 voisine e
    void ajouteVoisines8() {
        int x, y;
        for (Cellule cellule : cellulesVivantes) {
            x = cellule.getX();
            y = cellule.getY();
            for (int k = -1; k < 2; k++) {
                for (int z = -1; z < 2; z++) {
                    cellulesVivantesAvecVoisines.add(new Cellule(x + k, y + z));
                }
            }
        }
    }

    // elle ajoute la cellule courante et ces 4 voisine e
    void ajouteVoisines4() {
        int x, y;
        for (Cellule cellule : cellulesVivantes) {
            x = cellule.getX();
            y = cellule.getY();
            cellulesVivantesAvecVoisines.add(new Cellule(x - 1, y));
            cellulesVivantesAvecVoisines.add(new Cellule(x, y));
            cellulesVivantesAvecVoisines.add(new Cellule(x + 1, y));
            cellulesVivantesAvecVoisines.add(new Cellule(x, y - 1));
            cellulesVivantesAvecVoisines.add(new Cellule(x, y + 1));
        }
    }

    public boolean estVivante(int x, int y) {
        return cellulesVivantes.contains(new Cellule(x, y));
    }

    public boolean estVivante(Cellule cellule) {
        return cellulesVivantes.contains(cellule);
    }

    /**
     * generationSuivante() :
     * on réunit toutes les fonctions auxiliaires codées précédemment pour
     * effectuer le passage à la génération suivante de cellules
     */

    public synchronized void generationSuivante(Double time) {
        ajouteVoisines();
        for (Cellule c : cellulesVivantesAvecVoisines) {
            compteVoisinesVivantes(c);
        }
        majCellules();
    }

    /***
     * Remplit la collection "cellulesVivantesAvecVoisines" des cellules censées
     * être vivantes ainsi que leurs voisines, avec répétition.
     *
     ***/
    void ajouteVoisines() {
        if (tab[0] == 8) {
            ajouteVoisines8();
        } else {
            ajouteVoisines4();
        }
    }

    /***
     * majCellules() :
     * Cette fonction itère sur l'ensemble des cellules contenues dans
     * "cellulesVivantesAvecVoisines", et selon l'état de la Cellule (on le rappelle
     * représentée par ses coordonnées)
     * ainsi que le nombre de ses voisines(*1), on agit ou non sur l'ensemble des
     * cellulesVivantes afin de passer à la génération suivante
     * ainsi on "suppose" ici que l'on a déjà compté le nombre de cellules vivantes
     * voisines de chaque cellule ( donc on a déjà fait appel à ajouteVoisines() et
     * compteVoisinesVivantes(Coordonnees coord) )
     * (*1) en réalité il s'agit du nombre de ses voisines +1 car on compte la
     * cellule elle-même. Voir le rapport section implémentation pour plus d'infos
     *
     */

    public synchronized void majCellules() {
        LinkedHashSet<Cellule> tmpCellulesVivantes = new LinkedHashSet<>();
        for (int i = 0; i < cellulesVivantesAvecVoisines.size(); i++) {
            Cellule cellule = cellulesVivantesAvecVoisines.get(i);
            if (estVivante(cellule) && (cellule.getNbVoisinesVivantes() == tab[1] + 1
                    || cellule.getNbVoisinesVivantes() == tab[1] + 2)) { // cel. vivante avec 3 ou 2 voisines vivantes :
                                                                         // donc vivante lors de la prochaine génération
                tmpCellulesVivantes.add(cellule);
            } else if (!(estVivante(cellule)) && cellule.getNbVoisinesVivantes() == tab[2]) {
                tmpCellulesVivantes.add(cellule);// cel. morte avec 3 cellules voisines vivantes : donc vivante lors de
                                                 // la prochaine génération
            }
            // le reste des cellules, celles qui n'obéissent pas aux 2 règles précédentes
            // meurent.
        }
        this.cellulesVivantes = tmpCellulesVivantes; // remplacement de la 1ère génération en la 2ème
        cellulesVivantesAvecVoisines.clear();
    }

    /***
     * Vide le plateau ( les deux collections : cellulesVivantes et
     * cellulesVivantesAvecVoisines )
     */
    public synchronized void clear() {
        cellulesVivantes.clear();
        cellulesVivantesAvecVoisines.clear();
    }

    public void ajouterCellule(int x, int y) {
        if (!estVivante(x, y))
            cellulesVivantes.add(new Cellule(x, y));
    }

}