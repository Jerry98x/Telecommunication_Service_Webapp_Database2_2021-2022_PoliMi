package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.telco_services.TelcoService;

public class ClientTelcoService {
    private int serviceId;
    private String description;

    public ClientTelcoService(TelcoService telcoService) {
        this.serviceId = telcoService.getServiceId();
        this.description = telcoService.getDescription();
    }

}
