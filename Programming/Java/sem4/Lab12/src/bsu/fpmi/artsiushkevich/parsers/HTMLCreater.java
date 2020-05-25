package bsu.fpmi.artsiushkevich.parsers;

import org.xml.sax.InputSource;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.StringTokenizer;

public class HTMLCreater {
    public static void createHTML(File xml, File xls) throws IOException, TransformerException {
        StringTokenizer name = new StringTokenizer(xml.getName(), ".");
        FileWriter fileWriter = new FileWriter(name.nextToken() + ".html");
        StringWriter stringWriter = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xls));
        transformer.transform(new StreamSource(xml), new StreamResult(stringWriter));
        fileWriter.write(stringWriter.toString());
        fileWriter.close();
    }
}
