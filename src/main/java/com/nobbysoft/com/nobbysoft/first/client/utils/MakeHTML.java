package com.nobbysoft.com.nobbysoft.first.client.utils;

import java.io.File;
import java.io.StringWriter;

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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.nobbysoft.com.nobbysoft.first.common.entities.pc.PlayerCharacter;

public class MakeHTML {

	public MakeHTML() {

	}

	private String makeHtmlError(Throwable t) {
		StringBuilder sb = new StringBuilder("<html><body>");
		sb.append("<h1>").append(t.toString()).append("</h1>");
		sb.append("<table>");
		for (StackTraceElement ste : t.getStackTrace()) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("at");
			sb.append("</td>");
			sb.append("<td>");
			sb.append(ste.getClassName()).append(".").append(ste.getMethodName());
			sb.append("</td>");
			sb.append("<td>");
			sb.append("(").append(ste.getLineNumber()).append(")");
			sb.append("</td>");
			sb.append("</tr>");
		}

		sb.append("</table>");
		sb.append("</body></html>");
		return sb.toString();
	}

	public String makeDocument(PlayerCharacter pc) {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element body = doc.createElement("body");
			doc.appendChild(body);

			addElement(body, "h1", "Character Sheet");
			{
				Element table = addElement(body, "table");
				addAttribute(table, "border", "1");
				Element row = addElement(table, "tr");

				addElement(row, "th", "Character Name");
				addElement(row, "td", pc.getCharacterName());
				addElement(row, "th", "Gender");
				addElement(row, "td", pc.getGender().name());
				addElement(row, "th", "Race");
				addElement(row, "td", pc.getRaceId());
			}
			{
				Element table = addElement(body, "table");

				addAttribute(table, "border", "1");
				{
					Element row = addElement(table, "tr");
					addElement(row, "th", "str");
					addElement(row, "td", pc.getAttrStr());
				}
				{
					Element row = addElement(table, "tr");
					addElement(row, "th", "int");
					addElement(row, "td", pc.getAttrInt());
				}
				{
					Element row = addElement(table, "tr");
					addElement(row, "th", "wis");
					addElement(row, "td", pc.getAttrWis());
				}
				{
					Element row = addElement(table, "tr");
					addElement(row, "th", "dex");
					addElement(row, "td", pc.getAttrDex());
				}
				{
					Element row = addElement(table, "tr");
					addElement(row, "th", "con");
					addElement(row, "td", pc.getAttrCon());
				}
				{
					Element row = addElement(table, "tr");
					addElement(row, "th", "chr");
					addElement(row, "td",pc.getAttrChr());
				}
			}
			String xmlString = xmlToHtmlString(doc);

			return xmlString;

		} catch (Exception e) {
			return makeHtmlError(e);
		}
	}

	private void addAttribute(Element node, String name, String value) {

		((Element) node).setAttribute(name, value);
	}

	private String xmlToHtmlString(Document doc)
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

	private Element addElement(Node n, String name) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		return newEl;
	}

	private Element addElement(Node n, String name, int value) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		Node tn = od.createTextNode(""+value);
		newEl.appendChild(tn);
		return newEl;
	}
	
	private Element addElement(Node n, String name, String value) {
		Document od = n.getOwnerDocument();
		Element newEl = od.createElement(name);
		n.appendChild(newEl);
		Node tn = od.createTextNode(value);
		newEl.appendChild(tn);
		return newEl;
	}
}
