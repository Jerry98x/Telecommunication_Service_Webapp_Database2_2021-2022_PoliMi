package it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity @Table(name = "fixed_internet")
public class FixedInternet extends Service{
    @Column(nullable = false)
    private int GBs;
    private int extraGBFee_euro;
}
