package com.psib.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LatitudeAndLongitudeWithPincode {

	private static final Logger logger = LoggerFactory.getLogger(LatitudeAndLongitudeWithPincode.class);

	public static String[] getLatLongPositions(String address) {
		int responseCode = 0;
		String api;
		try {
			api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8")
					+ "&sensor=true";
			System.out.println("URL : "+api);
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
					return new String[] { latitude, longitude };
				} else {
					throw new Exception("Error from the API - response status: " + status);
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("[getLatLongPositions] UnsupportedEncodingException " + e.getMessage());
			return null;
		} catch (MalformedURLException e) {
			logger.error("[getLatLongPositions] MalformedURLException " + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("[getLatLongPositions] IOException " + e.getMessage());
			return null;
		} catch (ParserConfigurationException e) {
			logger.error("[getLatLongPositions] ParserConfigurationException " + e.getMessage());
			return null;
		} catch (SAXException e) {
			logger.error("[getLatLongPositions] SAXException " + e.getMessage());
			return null;
		} catch (XPathExpressionException e) {
			logger.error("[getLatLongPositions] XPathExpressionException " + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("[getLatLongPositions] Exception " + e.getMessage());
			return null;
		}
		return null;
	}
}
