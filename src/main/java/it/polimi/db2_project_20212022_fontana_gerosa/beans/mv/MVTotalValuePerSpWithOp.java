

package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.services.OptionalProductService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.persistence.*;

@Entity
@Table(name = "mv_total_value_per_sp_with_op", schema = "db2_project")
@NamedQuery(name = "MVTotalValuePerSpWithOp.getAllTotalValuePerSpWithOp", query = "SELECT mv FROM MVTotalValuePerSpWithOp mv")
public class MVTotalValuePerSpWithOp {

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ServicePackageService")
    private ServicePackageService servicePackageService;

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

    public String getDescription(){
        servicePackageService = new ServicePackageService();
        ServicePackage servicePackage = servicePackageService.getServicePackageById(servicePackageId);
        return "Service package " + servicePackage.getName() + "(id:" + servicePackageId + ") has produced " + totalValue_euro +
                "€ of value in sales, including the optional products sold with it";
    }
}

