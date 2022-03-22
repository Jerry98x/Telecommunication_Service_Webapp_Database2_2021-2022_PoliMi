package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.persistence.*;

@Entity
@Table(name = "mv_avg_amount_op_per_sp", schema = "db2_project")
@NamedQuery(name = "MVAvgAmountOpPerSp.getAllAvgAmountOpPerSp", query = "SELECT mv FROM MVAvgAmountOpPerSp mv")
public class MVAvgAmountOpPerSp {

    @Id
    private int servicePackageId;
    @Column(name = "avgAmountOptionalProducts")
    private float avgAmountOptionalProducts;

    public int getServicePackageId() {
        return servicePackageId;
    }

    public float getAvgAmountOptionalProducts() {
        return avgAmountOptionalProducts;
    }
}
