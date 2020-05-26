package bsu.fpmi.artsiushkevich.parsers;

import bsu.fpmi.artsiushkevich.utility.LibraryCard;
import bsu.fpmi.artsiushkevich.utility.Pair;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXParse {
    public Pair<LibraryCard, LibraryCard> parseSax(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        LibraryXMLHandler handler = new LibraryXMLHandler();
        parser.parse(file, handler);
        return new Pair<>(taker, returner);
    }

    class LibraryXMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("libraryCard")) {
                card = new LibraryCard(attributes.getValue("name"),
                        attributes.getValue("surname"),
                        attributes.getValue("id"),
                        0,
                        0);
            }
            if (qName.equals("takenBooks")) {
                takenFlag = true;
            }
            if (qName.equals("returnedBooks")) {
                returnFlag = true;
            }
            if (qName.equals("book")) {
                if (takenFlag) {
                    card.taken++;
                }
                if (returnFlag) {
                    card.returned++;
                }
            }
        }


        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("libraryCard")) {
                if (card.returned > returner.returned) {
                    returner.setName(card.getName());
                    returner.setSurname(card.getSurname());
                    returner.setId(card.getId());
                    returner.setReturned(card.getReturned());
                    returner.setTaken(card.getTaken());
                }
                if (card.getTaken() > taker.getTaken()) {
                    taker.setName(card.getName());
                    taker.setSurname(card.getSurname());
                    taker.setId(card.getId());
                    taker.setReturned(card.getReturned());
                    taker.setTaken(card.getTaken());
                }
            }
            if (qName.equals("takenBooks")) {
                takenFlag = false;
            }
            if (qName.equals("returnedBooks")) {
                returnFlag = false;
            }
        }

        LibraryCard card;
        boolean takenFlag = false;
        boolean returnFlag = false;
    }

    LibraryCard taker = new LibraryCard();

    LibraryCard returner = new LibraryCard();
}

