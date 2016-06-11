package tcitsteam.gestionsoussous;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tcit on 31/05/16.
 */
public class Operation implements Serializable {
    private Integer id;
    private String nom, detail;
    private double montant;
    private boolean type;
    private Date d;

    Operation(Integer id, String nom, String detail, double montant, boolean type, Date d) {
        this.id = id;
        this.nom = nom;
        this.detail = detail;
        this.montant = montant;
        this.type = type;
        this.d = d;
    }

    Operation(String nom, String detail, double montant, boolean type, Date d) {
        this.nom = nom;
        this.detail = detail;
        this.montant = montant;
        this.type = type;
        this.d = d;
    }

    public Integer getId() {
        return this.id;
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

    public Date getDate() {
        return this.d;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setDate(Date d) { this.d = d; }

    public String toString() {
        return "ID : " + this.id + " Nom : " + this.nom + " Montant : " + this.montant + " Détail : " + this.detail + " est income : " + this.type + " à la date " + this.d.toString() + " " + this.d.getTime();
    }
}
