package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixed_internet")
public class FixedInternet extends TelcoService {
    @Column(nullable = false)
    private int GBs;
    private float extraGBFee_euro;

    public int getGBs() {
        return GBs;
    }

    public void setGBs(int GBs) {
        this.GBs = GBs;
    }

    public float getExtraGBFee_euro() {
        return extraGBFee_euro;
    }

    public void setExtraGBFee_euro(float extraGBFee_euro) {
        this.extraGBFee_euro = extraGBFee_euro;
    }


    @Override
    public String getDescription() {
        String description = "You get " + GBs + " GBs on your home connection";
        return description;
    }
}
