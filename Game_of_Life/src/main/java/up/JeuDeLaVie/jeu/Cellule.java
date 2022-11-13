package up.JeuDeLaVie.jeu;

import java.util.Objects;

public class Cellule {
    private int PositX; // les coordonnées de la cellule
    private int PositY;
    private int nbVoisinesVivantes;

    public Cellule(int x, int y) {
        PositX = x;
        PositY = y;
    }

    // getters et setters
    public int getX() {
        return PositX;
    }

    public int getY() {
        return PositY;
    }

    public int getNbVoisinesVivantes() {
        return nbVoisinesVivantes;
    }

    public void setNbVoisinesVivantes(int nbVoisinesVivantes) {
        this.nbVoisinesVivantes = nbVoisinesVivantes;
    }

    @Override
    public String toString() {
        return "{x=" + PositX + ", y=" + PositY + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cellule that = (Cellule) o;
        return PositX == that.PositX && PositY == that.PositY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PositX, PositY);
    }
}
