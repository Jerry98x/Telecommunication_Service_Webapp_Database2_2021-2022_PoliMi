package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import jakarta.ejb.EJB;
import jakarta.persistence.*;

@Entity
@Table(name = "mv_total_purchases_per_sp", schema = "db2_project")
@NamedQuery(name = "MVTotalPurchasesPerSp.getAllTotalPurchasesPerSp", query = "SELECT mv FROM MVTotalPurchasesPerSp mv")
public class MVTotalPurchasesPerSp {

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ServicePackageService")
    private ServicePackageService servicePackageService;

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

    public String getDescription(){
        servicePackageService = new ServicePackageService();
        ServicePackage servicePackage = servicePackageService.getServicePackageById(servicePackageId);
        return "Service package " + servicePackage.getName() + "(id:" + servicePackageId + ") has been sold " + totalPurchases + "times";
    }
}
