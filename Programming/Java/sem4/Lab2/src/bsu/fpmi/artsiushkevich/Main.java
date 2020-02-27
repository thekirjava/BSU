package bsu.fpmi.artsiushkevich;

import bsu.fpmi.artsiushkevich.devices.*;
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
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void help() {
        System.out.println("read: Read from xml-file in directory");
        System.out.println("add: Add new device");
        System.out.println("print: Print the list of devices");
        System.out.println("switch: Turn off/on one of devices");
        System.out.println("count: Count power usage of all turned on devices");
        System.out.println("sort: Sort devices by power usage");
        System.out.println("find: Find exact device by parameters");
        System.out.println("help: Print list of commands");
        System.out.println("exit: Finish work");
    }

    public static Device consoleInput() throws NumberFormatException {
        Scanner scanner = new Scanner(System.in);
        Device device = null;
        System.out.println("What do you want to input?");
        System.out.println("(kettle/fridge/lamp)");
        String input = scanner.next();
        while ((!input.equals("kettle")) && (!input.equals("fridge")) && (!input.equals("lamp"))) {
            System.out.println("Wrong input! Try again.");
            input = scanner.next();
        }
        String manufacturer;
        int powerUsage;
        boolean turnedOn;
        System.out.println("Input manufacturer of the " + input);
        manufacturer = scanner.next();
        System.out.println("Input power usage of the " + input);
        powerUsage = Integer.parseInt(scanner.next());
        System.out.println("Is it already turned on? (y/n)");
        String flag = scanner.next();
        while ((!flag.equals("y")) && (!flag.equals("n"))) {
            System.out.println("Wrong input! Try again.");
            flag = scanner.next();
        }
        turnedOn = flag.equals("y");
        switch (input) {
            case "kettle":
                int volume;
                System.out.println("Input kettle volume");
                volume = Integer.parseInt(scanner.next());
                device = new Kettle(manufacturer, powerUsage, turnedOn, volume);
                break;
            case "fridge":
                int temperature;
                System.out.println("Input temperature in the fridge");
                temperature = Integer.parseInt(scanner.next());
                device = new Fridge(manufacturer, powerUsage, turnedOn, temperature);
                break;
            case "lamp":
                int brightness;
                String color;
                System.out.println("Input brightness of lamp");
                brightness = Integer.parseInt(scanner.next());
                System.out.println("Input color of lamps light");
                color = scanner.next();
                device = new Lamp(manufacturer, powerUsage, turnedOn, brightness, color);

        }
        return device;
    }

    public static void main(String[] args) {
        ArrayList<Device> arrayList = new ArrayList<>();
        String command;
        Scanner scanner = new Scanner(System.in);
        do {
            command = scanner.next();
            switch (command) {
                case "read":
                    try {
                        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = builderFactory.newDocumentBuilder();
                        Document document = builder.parse(new File("input.xml"));
                        NodeList nodeList = document.getDocumentElement().getChildNodes();
                        arrayList.clear();
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
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "add":
                    try {
                        arrayList.add(consoleInput());
                        System.out.println("Successfully added");
                    } catch (NumberFormatException e) {
                        System.out.println("NumberFormatException: " + e.getMessage());
                    }
                    break;
                case "print":
                    for (Device d : arrayList)
                    {
                        System.out.println(d);
                    }
                    break;
                case "switch":
                    System.out.println("Input index of device");
                    int x = Integer.parseInt(scanner.next());
                    if (arrayList.size() > x) {
                        arrayList.get(x).switchDevice();
                        if (arrayList.get(x).isTurnedOn()) {
                            System.out.println("Turned on!");
                        } else {
                            System.out.println("Turned off!");
                        }
                    } else {
                        System.out.println("Wrong index!");
                    }
                    break;
                case "count":
                    long ans = 0;
                    for (Device d : arrayList) {
                        if (d.isTurnedOn()) {
                            ans += d.getPowerUsage();
                        }
                    }
                    System.out.println("Summary power usage is " + ans);
                    break;
                case "sort":
                    arrayList.sort(Comparator.comparingInt(Device::getPowerUsage));
                    System.out.println("Sorted!");
                    break;
                case "find":
                    try {
                        Device toFind = consoleInput();
                        boolean notInArray = true;
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (toFind.equals(arrayList.get(i))) {
                                System.out.println("On " + i + " position: " + arrayList.get(i));
                                notInArray = false;
                            }
                        }
                        if (notInArray) {
                            System.out.println("No such element!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("NumberFormatException: " + e.getMessage());
                    }
                    break;
                case "help":
                    help();
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("In order to see list of commands type \"help\"");
            }
        }
        while (!command.equals("exit"));
    }
}
