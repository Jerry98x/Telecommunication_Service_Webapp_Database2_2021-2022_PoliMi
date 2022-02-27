package it.polimi.db2_project_20212022_fontana_gerosa.utils;

import it.polimi.db2_project_20212022_fontana_gerosa.beans.OptionalProduct;

public class ClientOptionalProduct {
    private int optionalProductId;
    private String name;
    private float monthlyFee_euro;

    public ClientOptionalProduct(OptionalProduct optionalProduct){
        this.optionalProductId = optionalProduct.getOptionalProductId();
        this.name = optionalProduct.getName();
        this.monthlyFee_euro = optionalProduct.getMonthlyFee_euro();
    }

}
