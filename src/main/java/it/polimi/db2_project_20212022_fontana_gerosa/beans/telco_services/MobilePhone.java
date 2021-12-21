package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mobile_phone")
public class MobilePhone extends TelcoService {
    @Column(nullable = false)
    private int minutes;
    @Column(nullable = false)
    private int SMSs;
    private float extraMinFee_euro;
    private float extraSMSFee_euro;

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSMSs() {
        return SMSs;
    }

    public void setSMSs(int SMSs) {
        this.SMSs = SMSs;
    }

    public float getExtraMinFee_euro() {
        return extraMinFee_euro;
    }

    public void setExtraMinFee_euro(float extraMinFee_euro) {
        this.extraMinFee_euro = extraMinFee_euro;
    }

    public float getExtraSMSFee_euro() {
        return extraSMSFee_euro;
    }

    public void setExtraSMSFee_euro(float extraSMSFee_euro) {
        this.extraSMSFee_euro = extraSMSFee_euro;
    }


    @Override
    public String getDescription() {
        String description = "You get " + minutes + " minutes and " + SMSs + " SMSs on your mobile device";
        return description;
    }
}
