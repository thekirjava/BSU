package bsu.fpmi.artsiushkevich.XML;

import bsu.fpmi.artsiushkevich.devices.Device;
import bsu.fpmi.artsiushkevich.devices.Fridge;
import bsu.fpmi.artsiushkevich.devices.Kettle;
import bsu.fpmi.artsiushkevich.devices.Lamp;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLReader {
    public static ArrayList<Device> readXML() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Device> arrayList = new ArrayList<>();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new File("input.xml"));
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node device = nodeList.item(i);
            if (device.getNodeName().equals("#text")) {
                continue;
            }
            NamedNodeMap attributes = device.getAttributes();
            String manufacturer = attributes.getNamedItem("manufacturer").getNodeValue();
            int powerUsage = Integer.parseInt(attributes.getNamedItem("powerUsage").getNodeValue());
            boolean turnedOn = Boolean.parseBoolean(attributes.getNamedItem("turnedOn").getNodeValue());
            switch (device.getNodeName()) {
                case "kettle":
                    int volume = Integer.parseInt(attributes.getNamedItem("volume").getNodeValue());
                    arrayList.add(new Kettle(manufacturer, powerUsage, turnedOn, volume));
                    break;
                case "fridge":
                    int temperature = Integer.parseInt(attributes.getNamedItem("temperature").getNodeValue());
                    arrayList.add(new Fridge(manufacturer, powerUsage, turnedOn, temperature));
                    break;
                case "lamp":
                    int brightness = Integer.parseInt(attributes.getNamedItem("brightness").getNodeValue());
                    String color = attributes.getNamedItem("color").getNodeValue();
                    arrayList.add(new Lamp(manufacturer, powerUsage, turnedOn, brightness, color));
                    break;
            }
        }
        System.out.println("File has been successfully read");
        return arrayList;
    }
 }
