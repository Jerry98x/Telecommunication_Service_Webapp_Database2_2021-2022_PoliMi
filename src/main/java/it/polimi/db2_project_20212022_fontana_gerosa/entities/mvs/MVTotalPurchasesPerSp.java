package it.polimi.db2_project_20212022_fontana_gerosa.entities.mvs;

import jakarta.persistence.*;

/**
 * Entity which maps the materialized view collecting
 * the total number of purchases made for each service package
 */
@Entity
@Table(name = "mv_total_purchases_per_sp", schema = "db2_project")
@NamedQuery(name = "MVTotalPurchasesPerSp.getAllTotalPurchasesPerSp", query = "SELECT mv FROM MVTotalPurchasesPerSp mv")
public class MVTotalPurchasesPerSp {

    @Id
    private int servicePackageId;
    @Column(name = "totalPurchases")
    private int totalPurchases;

    public int getServicePackageId() {
        return servicePackageId;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }
}
