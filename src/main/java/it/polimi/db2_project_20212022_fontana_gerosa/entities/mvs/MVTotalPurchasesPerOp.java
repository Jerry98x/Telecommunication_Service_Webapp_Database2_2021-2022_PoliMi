package it.polimi.db2_project_20212022_fontana_gerosa.entities.mvs;

import jakarta.persistence.*;

/**
 * Entity which maps the materialized view collecting
 * the total number of purchases made for each optional product
 */
@Entity
@Table(name = "mv_total_purchases_per_op", schema = "db2_project")
@NamedQuery(name = "MVTotalPurchasesPerOp.getBestSellerOp", query = "SELECT mv FROM MVTotalPurchasesPerOp mv " +
        "WHERE mv.totalPurchases = (SELECT max(mv1.totalPurchases) FROM MVTotalPurchasesPerOp mv1)")
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