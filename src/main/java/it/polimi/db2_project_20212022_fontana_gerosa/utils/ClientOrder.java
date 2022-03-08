package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
//TODO fix Date management
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

public class ClientOrder {
    private int orderId;
    //TODO maybe single param parsing for date?
    /*
    private LocalDate confirmationDate;
    private LocalTime confirmationHour;

     */
    private float totalCost_euro;
    /*
    private LocalDate startDate;

     */
    private Boolean valid;
    private int userId;
    private int servicePackageId;
    private Collection<ClientOptionalProduct> chosenOptionalProducts;
    private ClientValidityPeriod chosenValidityPeriod;

    public ClientOrder(Order order){
        this.orderId = order.getOrderId();
        /*
        Date confirmationDate = order.getConfirmationDate();
        this.confirmationDate = LocalDate.of(confirmationDate.getYear(),confirmationDate.getMonth(),confirmationDate.getDay());
        Time confirmationHour = order.getConfirmationHour();
        this.confirmationHour = LocalTime.of(confirmationHour.getHours(),confirmationHour.getMinutes(),confirmationHour.getSeconds());

         */
        this.totalCost_euro = order.getTotalCost_euro();
        /*
        Date startDate = order.getStartDate();
        this.startDate = LocalDate.of(startDate.getYear(),startDate.getMonth(),startDate.getDay());

         */
        this.valid = order.getValid() == 1;
        this.userId = order.getUser().getUserId();
        this.servicePackageId = order.getServicePackage().getServicePackageId();
        this.chosenValidityPeriod = new ClientValidityPeriod(order.getValidityPeriod());
        if(order.getChosenOptionalProducts() != null){
            this.chosenOptionalProducts = new ArrayList<>();
            order.getChosenOptionalProducts().
                    forEach(optionalProduct -> this.chosenOptionalProducts.add(new ClientOptionalProduct(optionalProduct)));
        }

    }
}
