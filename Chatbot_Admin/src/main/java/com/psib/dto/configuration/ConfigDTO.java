/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psib.dto.configuration;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Dell
 */
@XmlType(name = "configuration", propOrder = {
    "name",
    "address",
    "userRate",
    "map"
})
public class ConfigDTO implements Serializable {

    private String site;
    private String name;
    private String address;
    private String userRate;
    private String map;

    public ConfigDTO() {
    }

    public ConfigDTO(String site, String name, String address, String userRate,String map) {
        this.site = site;
        this.name = name;
        this.address = address;
        this.userRate = userRate;
        this.map = map;
    }

    @XmlAttribute
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlElement
    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    @XmlElement
    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    private ConfigDTOList configs;

    public ConfigDTOList getConfigs() {
        return configs;
    }
}
