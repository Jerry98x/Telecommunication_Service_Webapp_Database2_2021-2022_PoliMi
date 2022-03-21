package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;

import jakarta.persistence.*;

@Entity
@Table(name = "mv_total_purchases_per_op", schema = "db2_project")
@NamedQuery(name = "MVTotalPurchasesPerOp.getAllTotalPurchasesPerOp", query = "SELECT mv FROM MVTotalPurchasesPerOp mv")
public class MVTotalPurchasesPerOp {
    @Id
    private int optionalProductId;
    @Column(name = "totalPurchases")
    private int totalPurchases;

    public int getOptionalProductId() {
        return optionalProductId;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }
}