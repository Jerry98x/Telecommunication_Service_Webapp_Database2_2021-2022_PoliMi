package it.polimi.db2_project_20212022_fontana_gerosa.beans.mv;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpVpKey spVpKey = (SpVpKey) o;
        return servicePackageId == spVpKey.servicePackageId && validityPeriodId == spVpKey.validityPeriodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(servicePackageId, validityPeriodId);
    }
}