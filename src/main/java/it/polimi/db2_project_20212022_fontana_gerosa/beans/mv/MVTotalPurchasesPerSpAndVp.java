package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;


import jakarta.persistence.*;

@Entity
@Table(name = "mv_total_purchases_per_sp_and_vp", schema = "db2_project")
@NamedQuery(name = "MVTotalPurchasesPerSpAndVp.getAllTotalPurchasesPerSpAndVp", query = "SELECT mv FROM MVTotalPurchasesPerSpAndVp mv")
public class MVTotalPurchasesPerSpAndVp {

    @Id//TODO composite key
    private int servicePackageId;
    private int validityPeriodId;
    @Column(name = "totalPurchases")
    private int totalPurchases;

    public int getServicePackageId() {
        return servicePackageId;
    }

    public int getValidityPeriodId() {
        return validityPeriodId;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }
}
