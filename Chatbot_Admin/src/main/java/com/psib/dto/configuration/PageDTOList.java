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
    "page"
})
@XmlRootElement(name = "pages")
public class PageDTOList {

	    @XmlElement(required = true)
	    private List<PageDTO> page;

	    public PageDTOList() {
	    }

	    public List<PageDTO> getConfig() {
	        if (page == null) {
	            page = new ArrayList<PageDTO>();
	        }
	        return page;
	    }
}
