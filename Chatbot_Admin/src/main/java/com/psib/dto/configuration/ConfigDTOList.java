package com.psib.dto.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "configuration"
})
@XmlRootElement(name = "configurations")
public class ConfigDTOList {

	@XmlElement(required = true)
    private List<ConfigDTO> configuration;

    public ConfigDTOList() {
    }

    public List<ConfigDTO> getConfig() {
        if (configuration == null) {
            configuration = new ArrayList<ConfigDTO>();
        }
        return configuration;
    }
    
    public boolean checkIfExisted(String site){
        for(ConfigDTO config : configuration){
            if (config.getSite().equals(site)){
                return true;
            }
        }
        
        return false;
    }
}
