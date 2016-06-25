package com.psib.util;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LatitudeAndLongitudeWithPincode {

    private static final Logger LOG = Logger.getLogger(LatitudeAndLongitudeWithPincode.class);

    public static String[] getLatLongPositions(String address) {

        LOG.info("[getLatLongPositions] Start: address = " + address);

        int responseCode = 0;
        String api;
        try {
            api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8")
                    + "&sensor=true";
            System.out.println("URL : " + api);
            URL url = new URL(api);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.connect();
            responseCode = httpConnection.getResponseCode();
            if (responseCode == 200) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

                Document document = builder.parse(httpConnection.getInputStream());
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("/GeocodeResponse/status");
                String status = (String) expr.evaluate(document, XPathConstants.STRING);
                if (status.equals("OK")) {
                    expr = xpath.compile("//geometry/location/lat");
                    String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
                    expr = xpath.compile("//geometry/location/lng");
                    String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
                    return new String[]{latitude, longitude};
                } else {
                    throw new Exception("Error from the API - response status: " + status);
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOG.error("[getLatLongPositions] UnsupportedEncodingException " + e.getMessage());
            return null;
        } catch (MalformedURLException e) {
            LOG.error("[getLatLongPositions] MalformedURLException " + e.getMessage());
            return null;
        } catch (IOException e) {
            LOG.error("[getLatLongPositions] IOException " + e.getMessage());
            return null;
        } catch (ParserConfigurationException e) {
            LOG.error("[getLatLongPositions] ParserConfigurationException " + e.getMessage());
            return null;
        } catch (SAXException e) {
            LOG.error("[getLatLongPositions] SAXException " + e.getMessage());
            return null;
        } catch (XPathExpressionException e) {
            LOG.error("[getLatLongPositions] XPathExpressionException " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOG.error("[getLatLongPositions] Exception " + e.getMessage());
            return null;
        }

        LOG.info("[getLatLongPositions] End");
        return null;
    }
}
