package beans.services;

import jakarta.persistence.Entity;

@Entity
public class MobilePhone extends Service{
    private int minutes;
    private int SMSs;
    private int extraMinFee_euro;
    private int extraSMSFee_euro;

}
