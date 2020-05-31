package bsu.fpmi.artsiushkevich.parsers;


import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;

public class HTMLCreater {
    public static void createHTML(File xml, URL xsl) throws IOException, TransformerException {
        StringTokenizer name = new StringTokenizer(xml.getName(), ".");
        FileWriter fileWriter = new FileWriter(name.nextToken() + ".html");
        StringWriter stringWriter = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsl.openStream()));
        transformer.transform(new StreamSource(xml), new StreamResult(stringWriter));
        fileWriter.write(stringWriter.toString());
        fileWriter.close();
    }
}
