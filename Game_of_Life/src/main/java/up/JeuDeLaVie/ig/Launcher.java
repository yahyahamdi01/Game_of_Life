package up.JeuDeLaVie.ig;

import up.JeuDeLaVie.jeu.Cellule;
import up.JeuDeLaVie.jeu.Plateau;

import java.util.LinkedHashSet;

/***
 * @author Guillaume Bouchon, modifié par : Jalal malkawi, Chérif Seddik,
 *         Mamadou Saliou Baldé, Martin Champion,Yahya Hamdi
 */
public class Launcher {

    public static void launch(int[] tab) {
        Plateau p = new Plateau();
        p.setTab(tab);
        LinkedHashSet<Cellule> h = new LinkedHashSet<Cellule>();
        p.setCellulesVivantes(h);
        Controleur c = new Controleur(p);
        c.start();
        c.pause();
    }

    public static void launch() {
        int[] tab = { 8, 2, 3 };
        launch(tab);
    }

    public static void main(String[] args) {
        Scenario s = new Scenario();
        s.setVisible(true);
    }
}
