package it.polimi.db2_project_20212022_fontana_gerosa;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;

import java.io.Serializable;
import java.util.List;

public class HomePageContent implements Serializable {
    private String username;
    private boolean insolvent;
    private List<ServicePackage> servicePackages;

    public HomePageContent(String username, boolean insolvent, List<ServicePackage> servicePackages){
        this.username = username;
        this.insolvent = insolvent;
        this.servicePackages = servicePackages;
    }
}
