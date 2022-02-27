package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;

import java.util.ArrayList;
import java.util.Collection;

public class ClientServicePackage {
    private int servicePackageId;
    private String name;
    private Collection<String> servicesDescriptions;
    private Collection<ClientOptionalProduct> availableOptionalProducts;
    private Collection<ClientValidityPeriod> availableValidityPeriods;

    public ClientServicePackage(ServicePackage servicePackage){
        this.servicePackageId = servicePackage.getServicePackageId();
        this.name = servicePackage.getName();
        if(servicePackage.getServices() != null){
            this.servicesDescriptions = new ArrayList<>();
            servicePackage.getServices().
                    forEach(telcoService -> this.servicesDescriptions.add(telcoService.getDescription()));
        }
        if(servicePackage.getAvailableOptionalProducts() != null){
            this.availableOptionalProducts = new ArrayList<>();
            servicePackage.getAvailableOptionalProducts().
                    forEach(optionalProduct -> this.availableOptionalProducts.add(new ClientOptionalProduct(optionalProduct)));
        }
        if(servicePackage.getAvailableValidityPeriods() != null){
            this.availableValidityPeriods = new ArrayList<>();
            servicePackage.getAvailableValidityPeriods().
                    forEach(validityPeriod -> this.availableValidityPeriods.add(new ClientValidityPeriod(validityPeriod)));
        }
    }
}
