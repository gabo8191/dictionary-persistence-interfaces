package co.edu.uptc.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PersistenceXML implements IPersistence {

    private File fileName;
    private Document doc;

    public PersistenceXML(String fileName) {
        this.fileName = new File(fileName);
        readDocument();
    }

    public void readDocument() {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileName);
            doc.getDocumentElement().normalize();
            this.doc = doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public String getRootElementName() {
        if (doc != null) {
            return doc.getDocumentElement().getNodeName();
        } else {
            return null;
        }
    }

    public NodeList getWordNodes() {
        if (doc != null) {
            return doc.getElementsByTagName("word");
        } else {
            return null;
        }
    }

    public Map<String, ArrayList<String>> getWords() {
        Map<String, ArrayList<String>> words = new HashMap<>();
        NodeList wordNodes = getWordNodes();
        for (int i = 0; i < wordNodes.getLength(); i++) {
            Element wordElement = (Element) wordNodes.item(i);
            String wordId = getWordId(wordElement);
            String wordSpanish = getWordSpanish(wordElement);
            String wordEnglish = getWordEnglish(wordElement);
            String wordFrench = getWordFrench(wordElement);
            ArrayList<String> translates = new ArrayList<>();
            translates.add(wordEnglish);
            translates.add(wordFrench);
            words.put(wordSpanish, translates);
        }
        return words;
    }

    public String getWordId(Element element) {
        return element.getAttribute("id");
    }

    public String getWordSpanish(Element element) {
        return getTagValue("spanish", element);
    }

    public String getWordEnglish(Element element) {
        Node englishNode = element.getElementsByTagName("english").item(0);
        if (englishNode != null) {
            return englishNode.getTextContent();
        } else {
            return null;
        }
    }

    public String getWordFrench(Element element) {
        Node frenchNode = element.getElementsByTagName("french").item(0);
        if (frenchNode != null) {
            return frenchNode.getTextContent();
        } else {
            return null;
        }
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        return node.getTextContent();

    }

    private void saveDocument() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult file2 = new StreamResult(fileName);
            transformer.transform(source, file2);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private Node newWord(Document doc, String id, String spanishWord, String translate, boolean flagLanguage) {
        Element word = doc.createElement("word");
        word.setAttribute("id", id);
        word.appendChild(newWordElements(doc, word, "spanish", spanishWord));
        if (flagLanguage) {
            word.appendChild(newWordElements(doc, word, "english", translate));
        } else {
            word.appendChild(newWordElements(doc, word, "french", translate));
        }

        return word;
    }

    private Node newWordElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public String getLastId() {
        NodeList wordNodes = getWordNodes();
        Element wordElement = (Element) wordNodes.item(wordNodes.getLength() - 1);
        return getWordId(wordElement);
    }

    public boolean validateSpanishWord(String spanishWord) {
        NodeList wordNodes = getWordNodes();
        for (int i = 0; i < wordNodes.getLength(); i++) {
            Element wordElement = (Element) wordNodes.item(i);
            String wordSpanish = getWordSpanish(wordElement);
            if (wordSpanish.equalsIgnoreCase(spanishWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeDocument(String word, String translate, boolean flagLanguage) {
        int newId = 1;
        NodeList wordNodes = getWordNodes();

        for (int i = 0; i < wordNodes.getLength(); i++) {
            Element wordElement = (Element) wordNodes.item(i);
            String wordId = getWordId(wordElement);
            int currentId = Integer.parseInt(wordId);
            if (currentId >= newId) {
                newId = currentId + 1;
            }
        }

        Element rootElement = doc.getDocumentElement();
        rootElement.appendChild(newWord(doc, String.valueOf(newId), word, translate, flagLanguage));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);

            StreamResult file2 = new StreamResult(new File(fileName.toString()));

            transformer.transform(source, file2);
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void updateDocument(String word, String englishTranslation, String frenchTranslation) {
        NodeList wordNodes = getWordNodes();
        for (int i = 0; i < wordNodes.getLength(); i++) {
            Element wordElement = (Element) wordNodes.item(i);
            String wordSpanish = getWordSpanish(wordElement);
            if (wordSpanish.equalsIgnoreCase(word)) {
                if (englishTranslation != null) {
                    Node englishNode = wordElement.getElementsByTagName("english").item(0);
                    if (englishNode != null) {
                        englishNode.setTextContent(englishTranslation);
                    } else {
                        Element newEnglishNode = doc.createElement("english");
                        newEnglishNode.appendChild(doc.createTextNode(englishTranslation));
                        wordElement.appendChild(newEnglishNode);
                    }
                }
                if (frenchTranslation != null) {
                    Node frenchNode = wordElement.getElementsByTagName("french").item(0);
                    if (frenchNode != null) {
                        frenchNode.setTextContent(frenchTranslation);
                    } else {
                        Element newFrenchNode = doc.createElement("french");
                        newFrenchNode.appendChild(doc.createTextNode(frenchTranslation));
                        wordElement.appendChild(newFrenchNode);
                    }
                }
            }
        }
        saveDocument();
    }

}
