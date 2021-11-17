package it.polimi.db2_project_20212022_fontana_gerosa.beans.services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity @Table(name = "mobile_phone")
public class MobilePhone extends Service{
    @Column(nullable = false)
    private int minutes;
    @Column(nullable = false)
    private int SMSs;
    private int extraMinFee_euro;
    private int extraSMSFee_euro;

}
