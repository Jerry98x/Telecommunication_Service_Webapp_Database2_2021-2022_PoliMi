package it.polimi.db2_project_20212022_fontana_gerosa.entities.mvs;

import jakarta.persistence.*;

/**
 * Entity which maps the materialized view collecting
 * the total amount of value (in euros) generated by the sale of each service package
 */
@Entity
@Table(name = "mv_total_value_per_sp", schema = "db2_project")
@NamedQuery(name = "MVTotalValuePerSp.getAllTotalValuePerSp", query = "SELECT mv FROM MVTotalValuePerSp mv")
public class MVTotalValuePerSp {

    @Id
    private int servicePackageId;
    @Column(name = "totalValue_euro")
    private float totalValue_euro;

    public int getServicePackageId() {
        return servicePackageId;
    }

    public float getTotalValue_euro() {
        return totalValue_euro;
    }

}

