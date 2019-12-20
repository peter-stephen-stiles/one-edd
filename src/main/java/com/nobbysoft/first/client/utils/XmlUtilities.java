package com.nobbysoft.first.client.utils;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtilities {

	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private XmlUtilities() {

	}

	
	public static final String trimNbsp(String in) {
		if(in==null){
			return null;
		}
		return in.replaceAll("\u00a0"," ").trim();
		
		
		
	}

	public static Document convertReaderToXML(Reader xmlReader) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(xmlReader));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Document convertStringToXML(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			LOGGER.error("Error making xml document",e);
		}
		return null;
	}

	public static void addAttribute(Element node, String name, String value) {

		((Element) node).setAttribute(name, value);
	}

	public static String xmlToPlainString(Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.transform(source, result);
		String xmlString = writer.getBuffer().toString();
		return xmlString;
	}
	
	public static String xmlToHtmlString(Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.setOutputProperty(OutputKeys.METHOD, "html");
		transformer.transform(source, result);
		String xmlString = writer.getBuffer().toString();
		return xmlString;
	}

	public static Element addElement(Node n, String name) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		return newEl;
	}

	public static Element addElementWithAttribute(Node n, String name, String attribute, String attributeValue) {
		Element newEl = addElement(n,name);
		addAttribute(newEl,attribute,attributeValue);		 
		return newEl;
	}
	
	public static Element addElement(Node n, String name, int value) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		Node tn = od.createTextNode("" + value);
		newEl.appendChild(tn);
		return newEl;
	}

	public static Element addElementWithAttribute(Node n, String name, int value, String attribute, String attributeValue) {
		Element newEl = addElement(n,name,value);
		addAttribute(newEl,attribute,attributeValue);		 
		return newEl;
	}

	public static Element addCDataElement(Node n, String name, String value) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		Node tn = od.createCDATASection(value);
		newEl.appendChild(tn);
		return newEl;
	}

	
	public static Element addElement(Node n, String name, String value) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		Node tn = od.createTextNode(value);
		newEl.appendChild(tn);
		return newEl;
	}

	public static Element addElementWithAttribute(Node n, String name, String value, String attribute, String attributeValue) {
		Element newEl = addElement(n,name,value);
		addAttribute(newEl,attribute,attributeValue);		 
		return newEl;
	}
	
	public static Element getElement(Node n, String name) {
		if (n instanceof Element) {
			Element e = (Element) n;
			NodeList nl = e.getElementsByTagName(name);
			if (nl != null && nl.getLength() > 0) {
				return (Element) nl.item(0);
			}

		}
		return null;

	}

	
	
	
	public static Node getNodeAfterwards(Element n,String name) {
		// need to walk along until we find a node called "name"
		Node ret=null;
		{
			NodeList kid = n.getElementsByTagName(name);
			if(kid.getLength()>0) {
				return kid.item(0);
			}
		}
		// else siblings
		Node ns = n.getNextSibling();
		while(ns!=null){
			// is ns anelement
			if(ns instanceof Element) {
				Element ee =(Element)ns;
				if(ee.getNodeName().equals(name)) {
					return ee;
				}
				NodeList kide = ee.getElementsByTagName(name);
				if(kide.getLength()>0) {
					return kide.item(0);
				}
			}
			ns  = ns.getNextSibling();
		}
		
		
		return ret;
	}
	
	public static List<Node> getElementsByTagName(Element n,String name){
		return list(n.getElementsByTagName(name));
	}
	
	public static List<Node> list(NodeList nl) {
		//LOGGER.info("node list len:" + nl.getLength());
		List<Node> list = new ArrayList<>();
		for (int i = 0, n = nl.getLength(); i < n; i++) {
			list.add(nl.item(i));
		}
		//LOGGER.info("list len:" + list.size());
		return list;
	}

	public static Node findParent(Node n, String name) {
		Node parent = null;
		Node cp = n.getParentNode();
		while (cp != null && !cp.getNodeName().equals(name)) {
			cp = cp.getParentNode();
		}
		if (cp != null && cp.getNodeName().equals(name)) {
			parent = cp;
		}

		return parent;
	}

	public static String getAttributeValue(Node n, String attributeName) {

		NamedNodeMap al = n.getAttributes();
		if (al != null) {
			Node a = al.getNamedItem(attributeName);

			if (a != null) {
				return a.getNodeValue();
			}
		}
		return "";
	}

	public static Document cleanHtml(String shoddyHtml) throws ParserConfigurationException {

		HtmlCleaner cleaner = new HtmlCleaner();

		// take default cleaner properties
		CleanerProperties props = cleaner.getProperties();

		// customize cleaner's behaviour with property setters
		// props.setXXX(...);

		// Clean HTML taken from simple string, file, URL, input stream,
		// input source or reader. Result is root node of created
		// tree-like structure. Single cleaner instance may be safely used
		// multiple times.
		TagNode node = cleaner.clean(shoddyHtml);

//		// optionally find parts of the DOM or modify some nodes
//		TagNode[] myNodes = node.getElementsByXXX(...);
//		// and/or
//		Object[] myNodes = node.evaluateXPath(xPathExpression);
//		// and/or
//		aNode.removeFromTree();
//		// and/or
//		aNode.addAttribute(attName, attValue);
//		// and/or
//		aNode.removeAttribute(attName, attValue);
//		// and/or
//		cleaner.setInnerHtml(aNode, htmlContent);

		Document doc = new DomSerializer(props, true).createDOM(node);

		return doc;

	}
	
	public static String getText(Node n) {		
		String s =n.getTextContent();		
		s= s.replaceAll("\\&amp\\;", "&");
		s= s.replaceAll("\\&lt\\;", "<");
		s= s.replaceAll("\\&gt\\;", ">");
		s= s.replaceAll("\\&apos\\;", "'");
		s= s.replaceAll("\\&quot\\;", "\"");	
		s=s.replace("½", "1/2");
		return(s);	
	}
	
	
	public static List<List<Node>> getTableData(Element tableNode){
		List<List<Node>> data = new ArrayList<>(); 
		//
		// get TR elements
		List<Node> trows =getElementsByTagName(tableNode, "tr");
		for(Node trow :trows) {
			Element erow = (Element)trow;
			List<Node> row = new ArrayList<>();
			List<Node> rdata = getElementsByTagName(erow,"td");
			for(Node rdatum:rdata) {
				row.add(rdatum);
			}
			data.add(row);
		}
		//
		return data;
	}
	

	public static Node getNodeBefore(Element n,String name) {
		// need to walk along until we find a node called "name"
		Node ret=null;

		// else siblings
		Node ns = n.getPreviousSibling();
		if(ns==null) {
			ns = n.getParentNode();
		}
		while(ns!=null){
			// is ns an element
			if(ns instanceof Element) {
				Element ee =(Element)ns;
				if(ee.getNodeName().equals(name)) {
					return ee;
				}
//				NodeList kide = ee.getElementsByTagName(name);
//				if(kide.getLength()>0) {
//					return kide.item(0);
//				}
			}
			Node ps = ns.getPreviousSibling();
			if(ps==null) {
				ps=ns.getParentNode();
			} 
			ns=ps;
		}
		
		
		return ret;
	}
	
}
