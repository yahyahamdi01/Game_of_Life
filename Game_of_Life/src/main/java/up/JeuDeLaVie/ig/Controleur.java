package up.JeuDeLaVie.ig;

import up.JeuDeLaVie.jeu.Plateau;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/***
 * @author Guillaume Bouchon, modifié par : Jalal malkawi, Chérif Seddik,
 *         Mamadou Saliou Baldé, Martin Champion
 */
public class Controleur extends Observable {

	static Plateau plateau;
	private final FramePrincipal vue;

	public static final double NANO_SECONDES = 1000000000; // combien de nanosecondes fait une seconde

	public static int FPS = 10; // nombre d'images par seconde désiré

	private boolean step_by_step = false; // mode pas a pas

	private long derniere_sec = 0;

	private boolean started = false; // booléen qui définit si oui ou non le programme est en marche, et non en pause

	private boolean paused = false; // idem

	public boolean isPaused() {
		return paused;
	}

	private long startTime = 0; // instant de départ en nanosecondes
	private long pauseTime = 0; // instant ou on est entré en pause

	public Plateau getPlateau() {
		return plateau;
	}

	public Controleur(Plateau p) {
		plateau = p;
		vue = new FramePrincipal();
		vue.setControleur(this);
		vue.setVisible(true);
	}

	/***
	 * démarre le plateau vivant
	 */
	public void start() {
		if (started)
			throw new RuntimeException("Le plateau est déjà démarré");
		started = true;
		long period = 1000 / FPS;// periode en ms pr attendre entre deux images (i.e entre l'affichage du passage
									// d'une génération à une autre)
		// le timer qui va rafraichir le plateau :
		Timer timer = new Timer();
		startTime = System.nanoTime();

		derniere_sec = System.currentTimeMillis();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() { // si une exception quelconque est lancée au cours de la tâche, l'éxecution en
								// cours est supprimée, stoppée.
				if (!paused) {
					nextFrame(System.nanoTime() - startTime);
					if (step_by_step)
						pause();
				}
				if (System.currentTimeMillis() - derniere_sec >= 1000) {
					derniere_sec = System.currentTimeMillis();
				}
				vue.repaint();
				vue.revalidate();
			}
		}, 0, period);
	}

	public void step() // active le mode pas à pas
	{
		startTime = System.nanoTime();
		if (!paused) {
			pause();
		}
		if (paused) {
			nextFrame(System.nanoTime() - startTime);
		}
	}

	/***
	 * met en pause le plateau
	 */
	public synchronized void pause() {
		if (!started)
			throw new RuntimeException("Le plateau n'est pas démarré");
		if (paused)
			throw new RuntimeException("Le plateau est deja en pause !");

		pauseTime = System.nanoTime();
		paused = true;
	}

	/**
	 * reprends le plateau en pause
	 */
	public synchronized void reprends() {
		if (!started)
			throw new RuntimeException("Le plateau n'est pas démarré");
		if (!paused)
			throw new RuntimeException("Le plateau n'est pas en pause !");

		long ecart = System.nanoTime() - pauseTime;
		startTime = startTime + ecart;
		paused = false;
	}

	/***
	 * rafraichi le plateau
	 * 
	 * @param time instant courant en nanosecondes
	 */
	private synchronized void nextFrame(long time) {
		plateau.generationSuivante(time / NANO_SECONDES);
		Plateau.nbGenerations++;
	}
}
