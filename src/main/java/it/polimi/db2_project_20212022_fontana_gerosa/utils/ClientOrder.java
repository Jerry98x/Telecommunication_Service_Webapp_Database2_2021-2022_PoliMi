package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ClientOrder {
    private int orderId;
    private float totalCost_euro;
    private LocalDate startDate;
    private int valid;
    private int userId;
    private int servicePackageId;
    private String servicePackageName;
    private Collection<String> servicesDescriptions;
    private Collection<ClientOptionalProduct> chosenOptionalProducts;
    private ClientValidityPeriod chosenValidityPeriod;

    public ClientOrder(Order order){
        this.orderId = order.getOrderId();
        this.totalCost_euro = order.getTotalCost_euro();
        this.startDate = order.getStartDate();
        this.valid = order.getValid();
        this.userId = order.getUser().getUserId();
        this.servicePackageId = order.getServicePackage().getServicePackageId();
        this.servicePackageName = order.getServicePackage().getName();
        this.servicesDescriptions = new ArrayList<>();
        order.getServicePackage().getServices().forEach(telcoService -> this.servicesDescriptions.add(telcoService.getDescription()));
        this.chosenValidityPeriod = new ClientValidityPeriod(order.getChosenValidityPeriod());
        if(order.getChosenOptionalProducts() != null){
            this.chosenOptionalProducts = new ArrayList<>();
            order.getChosenOptionalProducts().
                    forEach(optionalProduct -> this.chosenOptionalProducts.add(new ClientOptionalProduct(optionalProduct)));
        }
    }
}
