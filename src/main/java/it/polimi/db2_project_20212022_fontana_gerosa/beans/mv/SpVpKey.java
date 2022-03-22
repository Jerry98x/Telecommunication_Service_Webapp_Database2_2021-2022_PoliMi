package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;

import java.io.Serializable;

public class SpVpKey implements Serializable {
    private int servicePackageId;
    private int validityPeriodId;

    public SpVpKey(){
    }

    public int getServicePackageId() {
        return servicePackageId;
    }

    public int getValidityPeriodId() {
        return validityPeriodId;
    }
}
