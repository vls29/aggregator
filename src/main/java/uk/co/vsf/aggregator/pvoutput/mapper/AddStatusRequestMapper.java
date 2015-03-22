package uk.co.vsf.aggregator.pvoutput.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AddStatusRequestMapper extends AbstractTransformer {

    private InputStream mappingFileData;
    private Document document;

    @Override
    protected Object doTransform(Object src, String enc) throws TransformerException {
        return mapValues((Map<String, Object>) src);
    }

    public Map<String, String> mapValues(Map<String, Object> data) {

        Map<String, String> postValues = new HashMap<String, String>();

        mapMappings(postValues, data);
        mapDefaultsMappings(postValues);

        return postValues;
    }

    private void mapMappings(Map<String, String> postData, Map<String, Object> data) {
        for (int i = 0; i < getMappings().getLength(); ++i) {
            Node e = (Node) getMappings().item(i);
            String databaseFieldName = e.getTextContent();

            if (data.containsKey(databaseFieldName)) {
                String pvoutputValue = getPvOutputValue(databaseFieldName);
                Object dataValue = data.get(databaseFieldName);

                if (dataValue != null) {
                    if (dataValue instanceof BigDecimal) {
                        postData.put(pvoutputValue, ((BigDecimal) dataValue).toPlainString());
                    } else {
                        postData.put(pvoutputValue, dataValue.toString());
                    }
                }
            }
        }
    }

    private void mapDefaultsMappings(Map<String, String> postData) {
        for (int i = 0; i < getDefaults().getLength(); ++i) {
            Node e = (Node) getDefaults().item(i);
            String field = e.getTextContent();
            Object dataValue = getDefaultValue(field);

            if (dataValue instanceof BigDecimal) {
                postData.put(field, ((BigDecimal) dataValue).toPlainString());
            } else {
                postData.put(field, dataValue.toString());
            }
        }
    }

    public void init() throws Exception {
        createMappingDocument();
    }

    private void createMappingDocument() throws SAXException, IOException, ParserConfigurationException {
        if (document == null) {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            document = factory.newDocumentBuilder().parse(mappingFileData);
        }
    }

    private NodeList getMappings() {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            return (NodeList) xPath.evaluate("//mapping/database/text()", document.getDocumentElement(), XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("problem with xpath");
        }
    }

    private NodeList getDefaults() {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            return (NodeList) xPath.evaluate("//default/field/text()", document.getDocumentElement(), XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("problem with xpath");
        }
    }

    private String getPvOutputValue(String databaseFieldName) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            Node node = (Node) xPath.evaluate("//mapping[database='" + databaseFieldName + "']/pvoutput/text()",
                    document.getDocumentElement(), XPathConstants.NODE);
            return node.getTextContent();
        } catch (XPathExpressionException e) {
            throw new RuntimeException("problem with xpath");
        }
    }

    private String getDefaultValue(String fieldName) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            Node node = (Node) xPath.evaluate("//default[field='" + fieldName + "']/value/text()", document.getDocumentElement(),
                    XPathConstants.NODE);
            return node.getTextContent();
        } catch (XPathExpressionException e) {
            throw new RuntimeException("problem with xpath");
        }
    }

    public void setMappingFileData(InputStream mappingFileData) {
        this.mappingFileData = mappingFileData;
    }
}