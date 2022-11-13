package up.JeuDeLaVie.ig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("unchecked")
/***
 * @author : Jalal malkawi, Chérif Seddik, Mamadou Saliou Baldé, Martin Champion,Yahya Hamdi
 */
public class Scenario extends JFrame {
    private JPanel acceuil;
    private JPanel règlesPerso;
    private String[] nbCellulesVois = { "4", "8" };
    private String[] vois4 = { "1", "2", "3", "4" };

    public Scenario() {
        setTitle("Jeu de La vie");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 650));

        // Page d'acceuil
        acceuil = new JPanel();
        acceuil.setLayout(new BorderLayout());
        GridLayout grid = new GridLayout(3, 1);

        // Boutons
        JButton classique = new JButton("Version Classique");
        JButton perso = new JButton("Version personnalisée");

        JPanel boutons = new JPanel();
        boutons.setLayout(null);

        classique.setBounds(90, 20, 320, 100);
        perso.setBounds(430, 20, 320, 100);

        boutons.add(classique);
        boutons.add(perso);

        // Titre
        JLabel titre = new JLabel();
        titre.setText("Bienvenu(e) sur le jeu de la vie");
        titre.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        titre.setForeground(new Color(0, 0, 0));
        titre.setHorizontalAlignment(JLabel.CENTER);

        acceuil.setLayout(grid);
        acceuil.add(titre);
        acceuil.add(boutons);
        getContentPane().add(acceuil);

        classique.addActionListener((event) -> {
            Launcher.launch();
            repaint();
            revalidate();
        });

        règlesPerso = new JPanel();
        règlesPerso.setLayout(new BorderLayout(3, 1));
        perso.addActionListener((event) -> {
            titre.setText("Règles personnelles");
            règlesPerso.add(titre, BorderLayout.NORTH);
            setContentPane(règlesPerso);
            repaint();
            revalidate();
        });

        // Formulaire des règles
        JPanel formRègles = new JPanel();
        formRègles.setLayout(null);

        JLabel regle1 = new JLabel("Nombre de cellules voisines à considérer");
        JLabel regle2 = new JLabel(
                "Nombre de cellules voisines vivantes que doit avoir une cellule vivante pour survivre");
        JLabel regle3 = new JLabel(
                "Nombre de cellules voisines vivantes que doit avoir une cellule morte pour se régénerer");

        JComboBox<String> nbCellRegle1 = new JComboBox<>(nbCellulesVois);
        JComboBox<String> nbCellRegle2 = new JComboBox<>(vois4);
        JComboBox<String> nbCellRegle3 = new JComboBox<>(vois4);

        regle1.setBounds(20, 100, 800, 30);
        nbCellRegle1.setBounds(600, 100, 100, 30);

        regle2.setBounds(20, 130, 800, 30);
        nbCellRegle2.setBounds(600, 130, 100, 30);

        regle3.setBounds(20, 160, 800, 30);
        nbCellRegle3.setBounds(600, 160, 100, 30);

        JButton demarrer = new JButton("Démarrer le jeu");
        demarrer.setBounds(600, 300, 130, 80);

        JButton paramClassique = new JButton("Règles classiques");
        paramClassique.setBounds(20, 200, 150, 60);

        // Changer la valeur des jComboBox selon règle 1
        nbCellRegle1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (nbCellRegle1.getItemAt(nbCellRegle1.getSelectedIndex()).equals("4")) {
                    nbCellRegle2.removeAllItems();
                    nbCellRegle2.addItem("1");
                    nbCellRegle2.addItem("2");
                    nbCellRegle2.addItem("3");
                    nbCellRegle2.addItem("4");
                    nbCellRegle3.removeAllItems();
                    nbCellRegle3.addItem("1");
                    nbCellRegle3.addItem("2");
                    nbCellRegle3.addItem("3");
                    nbCellRegle3.addItem("4");
                    formRègles.repaint();
                } else {
                    nbCellRegle2.removeAllItems();
                    nbCellRegle2.addItem("1");
                    nbCellRegle2.addItem("2");
                    nbCellRegle2.addItem("3");
                    nbCellRegle2.addItem("4");
                    nbCellRegle2.addItem("5");
                    nbCellRegle2.addItem("6");
                    nbCellRegle2.addItem("7");
                    nbCellRegle2.addItem("8");
                    nbCellRegle3.removeAllItems();
                    nbCellRegle3.addItem("1");
                    nbCellRegle3.addItem("2");
                    nbCellRegle3.addItem("3");
                    nbCellRegle3.addItem("4");
                    nbCellRegle3.addItem("5");
                    nbCellRegle3.addItem("6");
                    nbCellRegle3.addItem("7");
                    nbCellRegle3.addItem("8");
                    formRègles.repaint();
                }
            }
        });

        demarrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] tab = new int[3];
                tab[0] = Integer.valueOf(nbCellRegle1.getItemAt(nbCellRegle1.getSelectedIndex()));
                tab[1] = Integer.valueOf(nbCellRegle2.getItemAt(nbCellRegle2.getSelectedIndex()));
                tab[2] = Integer.valueOf(nbCellRegle3.getItemAt(nbCellRegle3.getSelectedIndex()));
                Launcher.launch(tab);
                repaint();
                revalidate();
            }
        });

        paramClassique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nbCellRegle1.removeAllItems();
                nbCellRegle1.addItem("4");
                nbCellRegle1.addItem("8");
                nbCellRegle1.setSelectedIndex(1);
                nbCellRegle2.removeAllItems();
                nbCellRegle2.addItem("2");
                nbCellRegle3.removeAllItems();
                nbCellRegle3.addItem("3");
                formRègles.repaint();
            }
        });

        formRègles.add(regle1);
        formRègles.add(nbCellRegle1);

        formRègles.add(regle2);
        formRègles.add(nbCellRegle2);

        formRègles.add(regle3);
        formRègles.add(nbCellRegle3);

        formRègles.add(paramClassique);

        formRègles.add(demarrer);

        règlesPerso.add(formRègles);

    }

    public class FramePrincipal extends JFrame { //GamePanel

        private JPanel grille; // grille de cellues
        private JPanel nav = new JPanel(); // menu de navigation
    
        private Controleur controler;
        private PlateauComponent plateauComponent;
    
        /***
         * largeur de la fenetre
         */
        public	static final int	FRAME_W=	800;
    
        /***
         * hauteur de la fenetre
         */
        public	static final int	FRAME_H=	500;
    
    
    
        public FramePrincipal() {
            setTitle("Jeu de la vie");
            setSize(FRAME_W, FRAME_H);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
    
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
    
            JButton droite = new JButton("droite");
            nav.add(droite);
    
            JButton gauche = new JButton("gauche");
            nav.add(gauche);
    
            JButton haut = new JButton("haut");
            nav.add(haut);
    
            JButton bas = new JButton("bas");
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
                plateauComponent.getAfficheurPlateau().setView_x(plateauComponent.getAfficheurPlateau().getView_w()/2 + plateauComponent.getAfficheurPlateau().getView_x() );
                repaint();
                revalidate();
            });
    
            gauche.addActionListener(actionEvent -> {
                plateauComponent.getAfficheurPlateau().setView_x(plateauComponent.getAfficheurPlateau().getView_x() - plateauComponent.getAfficheurPlateau().getView_w()/2 );
                repaint();
                revalidate();
            });
    
            bas.addActionListener(actionEvent -> {
                plateauComponent.getAfficheurPlateau().setView_y(plateauComponent.getAfficheurPlateau().getView_y() + plateauComponent.getAfficheurPlateau().getView_h()/2 );
                repaint();
                revalidate();
            });
    
            haut.addActionListener(actionEvent -> {
                plateauComponent.getAfficheurPlateau().setView_y(plateauComponent.getAfficheurPlateau().getView_y() - plateauComponent.getAfficheurPlateau().getView_h()/2 );
                repaint();
                revalidate();
            });
    
    
    
            // grille de cellules ( panel )
            grille = new JPanel();
            grille.setSize(new Dimension(FRAME_W, FRAME_H));
            BorderLayout layout=new BorderLayout();
            grille.setLayout(layout);
    
            //composant qui affiche le plateau
            plateauComponent=new PlateauComponent();
            JPanel sp=new JPanel();
            sp.setLayout(new BorderLayout());
            sp.add(plateauComponent,BorderLayout.CENTER);
    
    
            grille.add(sp,BorderLayout.CENTER);
    
            //slider pour le zoom
            JSlider zoom=new JSlider();
            zoom.setOrientation(JSlider.VERTICAL);
            zoom.setMaximum(5000);
            zoom.setMinimum(500);
            zoom.setValue(1000);
            zoom.addChangeListener(zoom1 -> {
                plateauComponent.getAfficheurPlateau().setZoom(((JSlider) zoom1.getSource()).getValue()/1000.0);
                repaint();
                revalidate();
            });
    
            grille.add(zoom,BorderLayout.EAST);
            grille.add(nav,BorderLayout.SOUTH);
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
}