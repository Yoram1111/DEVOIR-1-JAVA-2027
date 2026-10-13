package sio.devoir1.model;

import java.util.ArrayList;

public class Equipe
{
    private String nom;
    private ArrayList<Integer> scores;

    public Equipe(String nom) {
        this.nom = nom;
        this.scores = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTotalPoints()
    {
        // A vous de jouer
        // On initialise a 0
        int total = 0;
        for (int unScore : scores)
        {
            total = total + unScore;
        }
        return total;
    }
}
