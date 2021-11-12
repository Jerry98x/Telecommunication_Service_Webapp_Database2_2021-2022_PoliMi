package beans.services;

import jakarta.persistence.Entity;

@Entity
public class FixedInternet extends Service{
    private int GBs;
    private int extraGBFee_euro;
}
