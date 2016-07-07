package com.psib.util;

import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.psib.dto.configuration.ConfigDTOList;
import com.psib.dto.configuration.PageDTOList;

public class XMLUtils implements Serializable{

	public static String marshallConfigToString(ConfigDTOList fishs) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(ConfigDTOList.class);
            Marshaller mars = jaxb.createMarshaller();
//            mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8")

            StringWriter sw = new StringWriter();
            mars.marshal(fishs, sw);

            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String marshallPageToString(PageDTOList fishs) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(PageDTOList.class);
            Marshaller mars = jaxb.createMarshaller();
//            mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8")

            StringWriter sw = new StringWriter();
            mars.marshal(fishs, sw);

            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static boolean marshallToFile(ConfigDTOList configs, String path) {
        try {

            JAXBContext jaxb = JAXBContext.newInstance(ConfigDTOList.class);
            Marshaller mars = jaxb.createMarshaller();
            mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //mars.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://xml.netbeans.org/schema/fishs");

            mars.marshal(configs, new File(path));
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static ConfigDTOList unmarshall(String path) {
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(ConfigDTOList.class);
            Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            ConfigDTOList list = (ConfigDTOList) unmarshaller.unmarshal(new java.io.File(path)); //NOI18N
            return list;
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
//            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            System.out.println("cannot unmarshall");
        }
        return null;
    }
    public static boolean marshallToFilePage(PageDTOList pages, String path) {
        try {

            JAXBContext jaxb = JAXBContext.newInstance(PageDTOList.class);
            Marshaller mars = jaxb.createMarshaller();
            mars.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mars.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //mars.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://xml.netbeans.org/schema/fishs");

            mars.marshal(pages, new File(path));
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static PageDTOList unmarshallPage(String path) {
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(PageDTOList.class);
            Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            PageDTOList list = (PageDTOList) unmarshaller.unmarshal(new java.io.File(path)); //NOI18N
            return list;
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
//            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        	System.out.println("cannot unmarshall");
        }
        return null;
    }
}
