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
@XmlType(name = "page", propOrder = {
    "linkPage",
    "xpath",
    "foodName",
    "image",
    "nextPage"
})
public class PageDTO implements Serializable {

    private String site;
    private String linkPage;
    private String xpath;
    private String foodName;
    private String image;
    private String nextPage;

    public PageDTO() {
    }

    public PageDTO(String site, String linkPage, String xpath, String foodName, String image, String nextPage) {
        this.site = site;
        this.linkPage = linkPage;
        this.xpath = xpath;
        this.foodName = foodName;
        this.image = image;
        this.nextPage = nextPage;
    }
    @XmlAttribute
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @XmlElement
    public String getLinkPage() {
        return linkPage;
    }

    public void setLinkPage(String linkPage) {
        this.linkPage = linkPage;
    }

    @XmlElement
    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    @XmlElement
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    @XmlElement
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @XmlElement
    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
    private PageDTOList pages;

    public PageDTOList getPages() {
        return pages;
    }
}
