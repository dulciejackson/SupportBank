package training.supportbank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class XMLReader implements Reader {
    private Bank currentBank;

    public XMLReader(Bank currentBank) {
        this.currentBank = currentBank;
    }

    public void readFile(String fileName) {
        try {
            File xmlFile = new File(fileName).getAbsoluteFile();

            JAXBContext jaxbContext = JAXBContext.newInstance(TransactionList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object trans = unmarshaller.unmarshal(xmlFile);
            System.out.println(String.valueOf(trans));
        } catch (JAXBException e) {
            e.printStackTrace();
            LOGGER.error("Unable to import file " + fileName);
        }


    }

    @XmlRootElement(name = "TransactionList")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class TransactionList {
        @XmlElement(name = "SupportTransaction")
        Transaction t;

        TransactionList() {}

        @Override
        public String toString() {
            return t.toString();
        }
    }
}
