package sio.devoir1;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sio.devoir1.model.Equipe;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Devoir1Controller implements Initializable
{

    private HashMap<String, TreeMap<String, Equipe>> tournois;

    @javafx.fxml.FXML
    private TextField txtScore;
    @javafx.fxml.FXML
    private Label lblResultat;
    @javafx.fxml.FXML
    private TextField txtEquipe;
    @javafx.fxml.FXML
    private TreeView tvTournois;
    @javafx.fxml.FXML
    private ComboBox cbTournois;
    @javafx.fxml.FXML
    private ComboBox cbEquipes;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        tournois = new HashMap<>();
        chargerDonnees();
        // DES QU'ON VEUX CHARGER LE TREE VIEW APRES UNE MODIF
        chargerTreeView();
        for (String nomTournoi : tournois.keySet())
        {
            cbTournois.getItems().add(nomTournoi);
        }
        cbTournois.getSelectionModel().selectFirst();
        // DES QU'ON VEUX CHARGER APRES MODIF SUR UNE EQUIPE
        chargerEquipes();
    }

    private void chargerDonnees()
    {
        tournois.put("Robotique", new TreeMap<>());
        tournois.put("Cyber", new TreeMap<>());

        Equipe e1 = new Equipe("Team Eclipse");
        e1.getScores().add(50);
        e1.getScores().add(40);
        e1.getScores().add(30);

        Equipe e2 = new Equipe("Team Alpha");
        e2.getScores().add(20);
        e2.getScores().add(25);
        e2.getScores().add(35);

        Equipe e3 = new Equipe("Team Nova");
        e3.getScores().add(60);
        e3.getScores().add(10);

        tournois.get("Robotique").put(e1.getNom(), e1);
        tournois.get("Robotique").put(e2.getNom(), e2);
        tournois.get("Robotique").put(e3.getNom(), e3);

        Equipe e4 = new Equipe("Phoenix");
        e4.getScores().add(35);
        e4.getScores().add(45);

        Equipe e5 = new Equipe("Titans");
        e5.getScores().add(50);
        e5.getScores().add(40);

        tournois.get("Cyber").put(e4.getNom(), e4);
        tournois.get("Cyber").put(e5.getNom(), e5);
    }

    private void chargerTreeView()
    {
        // A vous de jouer
        // La racine de l'arbre, ouverte pour que les tournois soient visibles
        TreeItem racine = new TreeItem("Tournois");
        racine.setExpanded(true);
        // 1e noeud
        for (String nomTournoi : tournois.keySet())
        {
            TreeItem noeudTournoi = new TreeItem(nomTournoi);
            noeudTournoi.setExpanded(true);
            // 2 eme noeud
            for (String nomEquipe : tournois.get(nomTournoi).keySet())
            {
                Equipe uneEquipe = tournois.get(nomTournoi).get(nomEquipe);
                TreeItem noeudEquipe = new TreeItem(uneEquipe.getNom() + " (" + uneEquipe.getTotalPoints() + " pts)");
                noeudTournoi.getChildren().add(noeudEquipe);
            }
            racine.getChildren().add(noeudTournoi);
        }
        tvTournois.setRoot(racine);
    }

    private void chargerEquipes()
    {
        // A vous de jouer
        cbEquipes.getItems().clear();
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();
        for (String nomEquipe : tournois.get(nomTournoi).keySet())
        {
            cbEquipes.getItems().add(nomEquipe);
        }
    }

    @javafx.fxml.FXML
    public void ajouterEquipe(ActionEvent actionEvent)
    {
        // A vous de jouer
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();
        // 1e cas
        if (txtEquipe.getText().isEmpty())
        {
            lblResultat.setText("Saisir un nom d'équipe");
        }
        // 2e cas
        else if (tournois.get(nomTournoi).containsKey(txtEquipe.getText()))
        {
            lblResultat.setText("Cette équipe existe déjà");
        }
        else
        {
            Equipe nouvelleEquipe = new Equipe(txtEquipe.getText());
            tournois.get(nomTournoi).put(nouvelleEquipe.getNom(), nouvelleEquipe);
            chargerTreeView();
            chargerEquipes();
        }
    }

    @javafx.fxml.FXML
    public void ajouterScore(ActionEvent actionEvent)
    {
        // A vous de jouer
        // 1e cas
        if (cbEquipes.getSelectionModel().getSelectedItem() == null)
        {
            lblResultat.setText("Sélectionner une équipe");
        }
        // 2e cas
        else if (txtScore.getText().isEmpty())
        {
            lblResultat.setText("Saisir un score");
        }
        else
        {
            String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();
            String nomEquipe = cbEquipes.getSelectionModel().getSelectedItem().toString();
            tournois.get(nomTournoi).get(nomEquipe).getScores().add(Integer.parseInt(txtScore.getText()));
            chargerTreeView();
        }
    }

    @javafx.fxml.FXML
    public void calculTotalTournoi(ActionEvent actionEvent)
    {
        // A vous de jouer
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();
        int total = 0;
        // On additionne le total
        for (String nomEquipe : tournois.get(nomTournoi).keySet())
        {
            total = total + tournois.get(nomTournoi).get(nomEquipe).getTotalPoints();
        }
        lblResultat.setText("Total : " + total + " pts");
    }


    @javafx.fxml.FXML
    public void meilleureEquipe(ActionEvent actionEvent) {
        // A vous de jouer
        Equipe equipeGagnante = null;
        // on parcourt tout les tournois et les equipe
        for (String nomTournoi : tournois.keySet())
        {
            for (String nomEquipe : tournois.get(nomTournoi).keySet())
            {
                Equipe uneEquipe = tournois.get(nomTournoi).get(nomEquipe);
                if (equipeGagnante == null || uneEquipe.getTotalPoints() > equipeGagnante.getTotalPoints())
                {
                    equipeGagnante = uneEquipe;
                }
            }
        }
        // Je n'est pas compris la faute j'ai passer bcp de temps
        lblResultat.setText("Meilleure équipe : " + equipeGagnante  + equipeGagnante.getTotalPoints() + " points)");
    }


    @javafx.fxml.FXML
    public void renommerEquipe(ActionEvent actionEvent)
    {
        // A vous de jouer
        //1 e cas
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();
        if (cbEquipes.getSelectionModel().getSelectedItem() == null)
        {
            lblResultat.setText("Sélectionner une équipe");
        }
        // 2e cas
        else if (txtEquipe.getText().isEmpty())
        {
            lblResultat.setText("Saisir le nom d'une équipe");
        }
        else if (tournois.get(nomTournoi).containsKey(txtEquipe.getText()))
        {
            lblResultat.setText("Cette équipe existe déjà");
        }
        // aucune idee pour renommer

    }

    @javafx.fxml.FXML
    public void chargerEquipes(ActionEvent actionEvent)
    {
        chargerEquipes();
    }
}
