package com.DataObject;

import java.io.Serializable;

public class Weight implements Serializable {
    private String imperial;
    private String metric;

    public String getImperial() { return imperial; }
    public void setImperial(String value) { this.imperial = value; }

    public String getMetric() { return metric; }
    public void setMetric(String value) { this.metric = value; }
}
