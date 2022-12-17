import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLReader {

    private String fileName = "shop.xml";

    private String isNeededToLoadDataFromBasket;
    private String fileNameForLoad;
    private String formatForLoad;

    private String isNeededToSaveDataFromBasket;
    private String fileNameForSaving;
    private String formatForSaving;

    private String isNeededToLog;
    private String fileNameForLog;

    public XMLReader() throws ParserConfigurationException, IOException, SAXException {
    }

    // получение объекта Document для XML файла
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new File(fileName));

    // получение списка дочерних узлов корневого элемента
    Node root = doc.getDocumentElement();

    public void readXMLDocument() {
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            // блок load говорит нужно ли загружать данные корзины при старте программы из файла (enabled),
            // указывает имя этого файла (fileName)
            // и формат (json или text). Ваша программа должна вести себя соответствующим образом.
            if (Node.ELEMENT_NODE == node.getNodeType() && node.getNodeName() == "load") {
                Element load = (Element) node;
                isNeededToLoadDataFromBasket = load.getElementsByTagName("enabled").item(0).getTextContent();
                fileNameForLoad = load.getElementsByTagName("fileName").item(0).getTextContent();
                formatForLoad = load.getElementsByTagName("format").item(0).getTextContent();
            }
            // блок save говорит нужно ли сохранять данные корзины после каждого ввода, куда и в каком формате (text или json).
            if (Node.ELEMENT_NODE == node.getNodeType() && node.getNodeName() == "save") {
                Element save = (Element) node;
                isNeededToSaveDataFromBasket = save.getElementsByTagName("enabled").item(0).getTextContent();
                fileNameForSaving = save.getElementsByTagName("fileName").item(0).getTextContent();
                formatForSaving = save.getElementsByTagName("format").item(0).getTextContent();
            }
            // блок log говорит нужно ли сохранять лог при завершении программы и в какой файл; формат лога всегда csv.
            if (Node.ELEMENT_NODE == node.getNodeType() && node.getNodeName() == "log") {
                Element log = (Element) node;
                isNeededToLog = log.getElementsByTagName("enabled").item(0).getTextContent();
                fileNameForLog = log.getElementsByTagName("fileName").item(0).getTextContent();
            }
        }
    }

    public String getIsNeededToLoadDataFromBasket() {
        return isNeededToLoadDataFromBasket;
    }

    public String getFileNameForLoad() {
        return fileNameForLoad;
    }

    public String getFormatForLoad() {
        return formatForLoad;
    }

    public String getIsNeededToSaveDataFromBasket() {
        return isNeededToSaveDataFromBasket;
    }

    public String getFileNameForSaving() {
        return fileNameForSaving;
    }

    public String getFormatForSaving() {
        return formatForSaving;
    }

    public String getIsNeededToLog() {
        return isNeededToLog;
    }

    public String getFileNameForLog() {
        return fileNameForLog;
    }
}






