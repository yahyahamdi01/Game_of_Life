package up.JeuDeLaVie.ig;

import javax.swing.*;
import java.awt.*;

/***
 * @author Guillaume Bouchon, modifié par : Jalal malkawi, Chérif Seddik,
 *         Mamadou Saliou Baldé, Martin Champion,Yahya Hamdi
 */
@SuppressWarnings("unchecked")
public class FramePrincipal extends JFrame { // GamePanel

    private JPanel grille; // grille de cellues
    private JPanel nav = new JPanel(); // menu de navigation

    private Controleur controler;
    private PlateauComponent plateauComponent;

    /***
     * largeur de la fenetre
     */
    public static final int FRAME_W = 800;

    /***
     * hauteur de la fenetre
     */
    public static final int FRAME_H = 500;

    public FramePrincipal() {
        setTitle("Jeu de la vie");
        setSize(FRAME_W, FRAME_H);

        // menu de navigation
        nav.setSize(new Dimension(800, 100));
        nav.setLayout(new BorderLayout());
        add(nav, BorderLayout.SOUTH);
        nav.setLayout(new GridLayout(0, 3));

        JButton start = new JButton("Start");
        nav.add(start);

        JButton pause = new JButton("Pause");
        nav.add(pause);

        JButton clear = new JButton("Clear");
        nav.add(clear);

        JButton step = new JButton("Step");
        nav.add(step);

        JButton droite = new JButton("Droite");
        nav.add(droite);

        JButton gauche = new JButton("Gauche");
        nav.add(gauche);

        JButton haut = new JButton("Haut");
        nav.add(haut);

        JButton bas = new JButton("Bas");
        nav.add(bas);

        start.addActionListener((event) -> {
            if (controler.isPaused())
                controler.reprends();
            repaint();
            revalidate();
        });

        step.addActionListener((event) -> {
            controler.step();
            repaint();
            revalidate();
        });

        pause.addActionListener((event) -> {
            if (!controler.isPaused())
                controler.pause();
            repaint();
            revalidate();
        });

        clear.addActionListener(actionEvent -> {
            controler.getPlateau().clear();
            repaint();
            revalidate();
        });

        droite.addActionListener(actionEvent -> {
            plateauComponent.getAfficheurPlateau().setView_x(plateauComponent.getAfficheurPlateau().getView_w() / 2
                    + plateauComponent.getAfficheurPlateau().getView_x());
            repaint();
            revalidate();
        });

        gauche.addActionListener(actionEvent -> {
            plateauComponent.getAfficheurPlateau().setView_x(plateauComponent.getAfficheurPlateau().getView_x()
                    - plateauComponent.getAfficheurPlateau().getView_w() / 2);
            repaint();
            revalidate();
        });

        bas.addActionListener(actionEvent -> {
            plateauComponent.getAfficheurPlateau().setView_y(plateauComponent.getAfficheurPlateau().getView_y()
                    + plateauComponent.getAfficheurPlateau().getView_h() / 2);
            repaint();
            revalidate();
        });

        haut.addActionListener(actionEvent -> {
            plateauComponent.getAfficheurPlateau().setView_y(plateauComponent.getAfficheurPlateau().getView_y()
                    - plateauComponent.getAfficheurPlateau().getView_h() / 2);
            repaint();
            revalidate();
        });

        // grille de cellules ( panel )
        grille = new JPanel();
        grille.setSize(new Dimension(FRAME_W, FRAME_H));
        BorderLayout layout = new BorderLayout();
        grille.setLayout(layout);

        // composant qui affiche le plateau
        plateauComponent = new PlateauComponent();
        JPanel sp = new JPanel();
        sp.setLayout(new BorderLayout());
        sp.add(plateauComponent, BorderLayout.CENTER);

        grille.add(sp, BorderLayout.CENTER);

        // slider pour le zoom
        JSlider zoom = new JSlider();
        zoom.setOrientation(JSlider.VERTICAL);
        zoom.setMaximum(5000);
        zoom.setMinimum(500);
        zoom.setValue(1000);
        zoom.addChangeListener(zoom1 -> {
            plateauComponent.getAfficheurPlateau().setZoom(((JSlider) zoom1.getSource()).getValue() / 1000.0);
            repaint();
            revalidate();
        });

        grille.add(zoom, BorderLayout.EAST);
        grille.add(nav, BorderLayout.SOUTH);
        this.setContentPane(grille);

        repaint();
        invalidate();
    }

    public void setControleur(Controleur c) {
        this.controler = c;
        plateauComponent.setPlateau(c.getPlateau());
        controler.addObserver(plateauComponent);
    }

}