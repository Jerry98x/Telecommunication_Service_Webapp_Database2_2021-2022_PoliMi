package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;

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
        if(servicePackage.getServices() != null){
            this.servicesDescriptions = new ArrayList<>();
            servicePackage.getServices().
                    forEach(telcoService -> this.servicesDescriptions.add(telcoService.getDescription()));
        }
        if(servicePackage.getAvailableOptionalProducts() != null){
            this.availableOptionalProductsNames = new ArrayList<>();
            servicePackage.getAvailableOptionalProducts().
                    forEach(optionalProduct -> this.availableOptionalProductsNames.add(optionalProduct.getName()));
        }
    }

    public ClientServicePackage(ServicePackage servicePackage, Collection<TelcoService> availableServices, Collection<OptionalProduct> availableOptionalProduct){
        ClientServicePackage clientServicePackage = new ClientServicePackage(servicePackage);
        clientServicePackage.addAvailableServices(availableServices);
        clientServicePackage.addAvailableOptionalProduct(availableOptionalProduct);
    }

    private void addAvailableServices(Collection<TelcoService> availableServices){
        this.servicesDescriptions = new ArrayList<>();
        availableServices.forEach(telcoService -> this.servicesDescriptions.add(telcoService.getDescription()));
    }

    private void addAvailableOptionalProduct(Collection<OptionalProduct> availableOptionalProducts){
        this.availableOptionalProductsNames = new ArrayList<>();
        availableOptionalProducts.forEach(optionalProduct -> this.availableOptionalProductsNames.add(optionalProduct.getName()));
    }
}
