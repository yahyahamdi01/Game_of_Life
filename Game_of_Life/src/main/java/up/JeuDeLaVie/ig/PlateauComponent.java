package up.JeuDeLaVie.ig;

import up.JeuDeLaVie.jeu.Plateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.Observable;
import java.util.Observer;

/***
 * @author Guillaume Bouchon, modifié par : Jalal malkawi, Chérif Seddik,
 *         Mamadou Saliou Baldé, Martin Champion,Yahya Hamdi
 */
public class PlateauComponent extends JComponent
        implements Observer, MouseListener, MouseMotionListener, ComponentListener, MouseWheelListener {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 5827226467122712142L;

    /***
     * permet d'afficher le monde vivant
     */
    private AfficheurPlateau afficheurPlateau;

    private Plateau plateau;

    /***
     * bouton de la souris pressé
     */
    private int bouton = 0;

    public AfficheurPlateau getAfficheurPlateau() {
        return afficheurPlateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public PlateauComponent() {
        addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        afficheurPlateau.ajouterCellule((int) e.getPoint().getLocation().getX(),
                                (int) e.getPoint().getLocation().getY());
                    }
                });
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (afficheurPlateau == null) {
            afficheurPlateau = new AfficheurPlateau(plateau, this.getVisibleRect().width, this.getVisibleRect().height);
            afficheurPlateau.setView_x(afficheurPlateau.getView_w() / 2);
            afficheurPlateau.setView_y(afficheurPlateau.getView_h() / 2);
            setSize(afficheurPlateau.getView_w(), afficheurPlateau.getView_h());
        }
        afficheurPlateau.render(g2);
        // fin de l'affichage
        // g.dispose();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        afficheurPlateau.ajouterCellule(e.getX(), e.getY());
        repaint();
        revalidate();
    }

    public void mousePressed(MouseEvent e) {

        bouton = e.getButton();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if (bouton == MouseEvent.BUTTON1 || bouton == MouseEvent.BUTTON3) {
            afficheurPlateau.ajouterCellule(e.getX(), e.getY());
            repaint();
            revalidate();
        }

    }

    public void mouseMoved(MouseEvent arg0) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public void componentHidden(ComponentEvent arg0) {
    }

    public void componentMoved(ComponentEvent arg0) {
    }

    public void componentResized(ComponentEvent arg0) {
        if (afficheurPlateau != null) {
            afficheurPlateau.setView_w(this.getWidth());
            afficheurPlateau.setView_h(this.getHeight());
        }

    }

    public void componentShown(ComponentEvent arg0) {

    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

}
