package comkhalid.bankmanagement.models;

import java.time.LocalDate;


public class Operation {
    private String num;
    private Double montant;
    private LocalDate date;

    public Operation(String num, Double montant, LocalDate date) {
        this.num = num;
        this.montant = montant;
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
