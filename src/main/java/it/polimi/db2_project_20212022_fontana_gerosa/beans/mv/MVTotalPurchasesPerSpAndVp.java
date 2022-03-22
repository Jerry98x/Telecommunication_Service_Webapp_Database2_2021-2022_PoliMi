package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;


import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ValidityPeriod;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ServicePackageService;
import it.polimi.db2_project_20212022_fontana_gerosa.services.ValidityPeriodService;
import jakarta.ejb.EJB;
import jakarta.persistence.*;

@Entity
@Table(name = "mv_total_purchases_per_sp_and_vp", schema = "db2_project")
@IdClass(SpVpKey.class)
@NamedQuery(name = "MVTotalPurchasesPerSpAndVp.getAllTotalPurchasesPerSpAndVp", query = "SELECT mv FROM MVTotalPurchasesPerSpAndVp mv")
public class MVTotalPurchasesPerSpAndVp {

    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ServicePackageService")
    private ServicePackageService servicePackageService;
    @EJB(name = "it.polimi.db2_project_20212022_fontana_gerosa.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;

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

    public String getDescription(){
        servicePackageService = new ServicePackageService();
        ServicePackage servicePackage = servicePackageService.getServicePackageById(servicePackageId);
        validityPeriodService = new ValidityPeriodService();
        ValidityPeriod validityPeriod = validityPeriodService.getValidityPeriodById(validityPeriodId);
        return "Service package " + servicePackage.getName() + "(id:" + servicePackageId + ") has been sold with " +
                "validity period of " + validityPeriod.getMonthlyFee_euro() + "/" + validityPeriod.getMonthsOfValidity() + "(id:" +
                validityPeriod.getValidityPeriodId() + ") "
                + totalPurchases + "times";
    }
}
