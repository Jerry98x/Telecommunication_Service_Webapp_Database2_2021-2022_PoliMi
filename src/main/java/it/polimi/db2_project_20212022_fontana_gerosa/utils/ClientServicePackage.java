package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;

import java.util.ArrayList;
import java.util.Collection;

public class ClientServicePackage {
    private int servicePackageId;
    private String name;
    private Collection<String> servicesDescriptions;
    private Collection<String> availableOptionalProductsNames;

    public ClientServicePackage(ServicePackage servicePackage){
        this.servicePackageId = servicePackage.getServicePackageId();
        this.name = servicePackage.getName();
        this.servicesDescriptions = new ArrayList<>();
        servicePackage.getServices()
                .forEach(telcoService -> this.servicesDescriptions.add(telcoService.getDescription()));
        this.availableOptionalProductsNames = new ArrayList<>();
        servicePackage.getAvailableOptionalProducts()
                .forEach(optionalProduct -> this.availableOptionalProductsNames.add(optionalProduct.getName()));
    }
}
