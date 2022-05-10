package com.example.workflow.mvc.external.model;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeCurrModel {

    private Map<String, Rate> rates;

    public Map<String, Rate> getRates() {
        return rates;
    }

    public void setRates(Map<String, Rate> rates) {
        this.rates = rates;
    }

    public static class Rate {
        String name;
        String unit;
        BigDecimal value;
        String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
