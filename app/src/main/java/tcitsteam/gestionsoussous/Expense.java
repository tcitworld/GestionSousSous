package tcitsteam.gestionsoussous;

import java.io.Serializable;

/**
 * Created by tcit on 31/05/16.
 */
public class Expense implements Serializable {
    private String nom, detail;
    private double montant;
    private boolean type;

    Expense(String nom, String detail, double montant, boolean type) {
        this.nom = nom;
        this.detail = detail;
        this.montant = montant;
        this.type = type;
    }

    public String getNom() {
        return this.nom;
    }

    public String getDetail() {
        return this.detail;
    }

    public double getMontant() {
        return this.montant;
    }

    public boolean getType() {
        return this.type;
    }
}
