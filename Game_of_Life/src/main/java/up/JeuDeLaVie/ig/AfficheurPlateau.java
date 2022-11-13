package up.JeuDeLaVie.ig;

import up.JeuDeLaVie.jeu.Cellule;
import up.JeuDeLaVie.jeu.Plateau;

import java.awt.*;

/***
 * @author Guillaume Bouchon, modifié par : Jalal malkawi, Chérif Seddik, Mamadou Saliou Baldé, Martin Champion,Yahya Hamdi
 */

/***
 * affiche le plateau
 */
public class AfficheurPlateau {
    /***
     * cellule vivante
     */
    private static Color CELLULE_COLOR = new Color(20, 70, 60);

    /***
     * couleur du fonds
     */
    private static final Color FONDS_COLOR = new Color(255, 255, 255);

    /***
     * le plateau � afficher
     */
    private Plateau plateau;
    /***
     * zoom courant
     */
    private double zoom = 1.0;

    /***
     * largeur en pixels d'une cellule
     */
    public static final int CELLULE_W = 16;

    /***
     * heuteur en pixels d'une cellule
     */
    public static final int CELLULE_H = 16;

    /***
     * largeur de la cellule en fonction du zoom
     */
    private int cellule_w = CELLULE_W;

    /***
     * hauteur de la cellule en fonction du zoom
     */
    private int cellule_h = CELLULE_H;

    public void setZoom(double zoom) {
        this.zoom = zoom;
        cellule_w = (int) (CELLULE_W * zoom);
        cellule_h = (int) (CELLULE_H * zoom);

    }

    public Color randomColor() {
        int red = (int) Math.floor(Math.random() * (256));
        int green = (int) Math.floor(Math.random() * (256));
        int blue = (int) Math.floor(Math.random() * (256));
        return new Color(red, green, blue);
    }

    /***
     * position de la vue
     */
    private int view_x = 0;

    public int getView_x() {
        return view_x;
    }

    public void setView_x(int view_x) {
        this.view_x = view_x;
    }

    public int getView_y() {
        return view_y;
    }

    public void setView_y(int view_y) {
        this.view_y = view_y;
    }

    public int getView_w() {
        return view_w;
    }

    public void setView_w(int view_w) {
        this.view_w = view_w;
        setView_x(getView_w() / 2);
    }

    public int getView_h() {
        return view_h;
    }

    public void setView_h(int view_h) {
        this.view_h = view_h;
        setView_y(getView_h() / 2);
    }

    /***
     * position de la vue
     */
    private int view_y = 0;

    /***
     * largeur de la vue
     */
    private int view_w = 0;

    /***
     * heuteur de la vue
     */
    private int view_h = 0;

    /***
     * constructeur
     */
    public AfficheurPlateau(Plateau plateau, int view_w, int view_h) {
        this.plateau = plateau;
        this.setView_w(view_w);
        this.setView_h(view_h);
        setZoom(1);
    }

    public void ajouterCellule(int x, int y) {
        plateau.ajouterCellule(pixToPos(x, y)[0], pixToPos(x, y)[1]);
    }

    /***
     *
     * @param x
     * @param y
     * @return Un tableau de taille 2 contenant les coordonnées x, et y, d'une
     *         cellule du plateau après le changement de base "fenêtre -> plateau"
     *         ( " Position of pixel in bitmap to position ( in CellGrid)" )
     */
    public int[] pixToPos(int x, int y) {
        int dx = (int) ((view_x - view_w / 2.0) / cellule_w);
        int dy = (int) ((view_y - view_h / 2.0) / cellule_h);

        int cdx = dx * cellule_w - (view_x - view_w / 2);
        int cdy = dy * cellule_h - (view_y - view_h / 2);

        int cx = (x - cdx) / cellule_w;
        int cy = (y - cdy) / cellule_h;

        int[] pixtopos = new int[2];
        pixtopos[0] = cx + dx;
        pixtopos[1] = cy + dy;
        return pixtopos;
    }

    /***
     * affichage du plateau
     * 
     * @param where
     */
    public void render(Graphics2D where) {
        where.setClip(0, 0, view_w, view_h);
        where.setColor(FONDS_COLOR);
        where.fillRect(0, 0, view_w, view_h);

        int[] postopix;
        if (Plateau.nbGenerations % 30 == 0)
            CELLULE_COLOR = randomColor(); // toutes les 30 générations on change de couleur
        for (Cellule c : plateau.cellulesVivantes) {
            postopix = posToPix(c.getX(), c.getY());

            where.setColor(CELLULE_COLOR);
            where.fillRect(postopix[0], postopix[1], cellule_w, cellule_h);
        }
    }

    /***
     * @param x
     * @param y
     * @return Un tableau de taille 2 contenant les coordonnées x et y d'une cellule
     *         du plateau après changement de base " plateau -> fenêtre "
     *         ( Ceci en tenant compte du zoom, car cellule_w et cellule_h sont
     *         modifiés en fonction du zoom )
     *         " Position in Cellgrid to position in pixel bitmap "
     */
    public int[] posToPix(int x, int y) {
        int[] postopix = new int[2];

        // coordonnées de la cellule à l'extrémité en haut à gauche de la fenêtre dans
        // la base de la fenêtre
        int xd = (int) ((view_x - view_w / 2.0) / cellule_w);
        int yd = (int) ((view_y - view_h / 2.0) / cellule_h);
        if (xd < 0)
            xd = 0;
        if (yd < 0)
            yd = 0;

        // largeur et hauteur de la cellule en fonction du zoom
        int cx = xd * cellule_w - (view_x - view_w / 2);
        int cdy = yd * cellule_h - (view_y - view_h / 2);

        postopix[0] = cx + (x - xd) * cellule_w;
        postopix[1] = cdy + (y - yd) * cellule_h;
        return postopix;
    }

}
