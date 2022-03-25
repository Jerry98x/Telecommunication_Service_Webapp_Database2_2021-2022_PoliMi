package it.polimi.db2_project_20212022_fontana_gerosa.entities.mvs;


import jakarta.persistence.*;

/**
 * Entity which maps the materialized view collecting
 * the total number of purchases made for each pair of service package and validity period
 */
@Entity
@Table(name = "mv_total_purchases_per_sp_and_vp", schema = "db2_project")
@IdClass(SpVpKey.class)
@NamedQuery(name = "MVTotalPurchasesPerSpAndVp.getAllTotalPurchasesPerSpAndVp", query = "SELECT mv FROM MVTotalPurchasesPerSpAndVp mv")
public class MVTotalPurchasesPerSpAndVp {
    @Id
    private int servicePackageId;
    @Id
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
