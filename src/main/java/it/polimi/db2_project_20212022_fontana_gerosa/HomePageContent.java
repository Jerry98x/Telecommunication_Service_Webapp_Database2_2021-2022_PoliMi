package it.polimi.db2_project_20212022_fontana_gerosa;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.ServicePackage;
import it.polimi.db2_project_20212022_fontana_gerosa.utils.HomePageServicePackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomePageContent implements Serializable {
    private String username;
    private boolean insolvent;
    private List<HomePageServicePackage> homePageServicePackages;

    public HomePageContent(String username, boolean insolvent, List<ServicePackage> servicePackages){
        this.username = username;
        this.insolvent = insolvent;
        this.homePageServicePackages = new ArrayList<>();
        servicePackages.forEach(servicePackage -> this.homePageServicePackages.add(new HomePageServicePackage(servicePackage)));
    }
}
