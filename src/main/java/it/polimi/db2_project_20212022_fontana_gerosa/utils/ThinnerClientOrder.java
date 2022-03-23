//package it.polimi.db2_project_20212022_fontana_gerosa.utils;
//
//import it.polimi.db2_project_20212022_fontana_gerosa.beans.Order;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//public class ThinnerClientOrder {
//    private int orderId;
//    private Date creationDate;
//    private Time creationTime;
//    private float totalCost_euro;
//    private int valid;
//    private int userId;
//    private int servicePackageId;
//    private String servicePackageName;
//
//    public ThinnerClientOrder(Order order){
//        this.orderId = order.getOrderId();
//        this.creationDate = order.getCreationDate2();
//        this.creationTime = order.getCreationHour2();
//        this.totalCost_euro = order.getTotalCost_euro();
//        this.valid = order.getValid();
//        this.userId = order.getUser().getUserId();
//        this.servicePackageId = order.getServicePackage().getServicePackageId();
//        this.servicePackageName = order.getServicePackage().getName();
//    }
//}
