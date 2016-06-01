package tcitsteam.gestionsoussous;

/**
 * Created by tcit on 31/05/16.
 */
public class Expense {
    private String nom;
    private double montant;

    Expense(String nom, double montant) {
        this.nom = nom;
        this.montant = montant;
    }

    public String getNom() {
        return this.nom;
    }

    public double getMontant() {
        return this.montant;
    }
}
