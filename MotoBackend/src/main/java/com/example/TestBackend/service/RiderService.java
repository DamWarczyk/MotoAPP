package com.example.TestBackend.service;

import com.example.TestBackend.model.Rider;
import com.example.TestBackend.repo.RiderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.List;


@Service
@Slf4j
@Transactional
public class RiderService {
    private final RiderRepository riderRepository;

    @Autowired
    public RiderService(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public List<Rider> getAllRidersSortedAsc() {
        return riderRepository.findAllByOrderByNumberAsc();
    }

    public List<Rider> getAllRidersSortedDesc() {
        return riderRepository.findAllByOrderByNumberDesc();
    }

    public List<Rider> getRidersByTeam(String team) {
        return riderRepository.findAllByTeam(team);
    }

    //save
    public Rider saveRider(Rider rider) {
        boolean riderExists = riderRepository.findByTeamAndAndNumber(rider.getTeam(), rider.getNumber()).isPresent();
        if (riderExists) {
            throw new IllegalStateException("Rider already exists");
        } else {
            return riderRepository.save(rider);
        }
    }

    //update
     public Rider updateRider(long id, Rider riderData) {
            Rider rider = riderRepository.findById(id);
            if (rider == null) {
                // error
                return null;
            }
            // akutualizowanie danych
            rider.setName(riderData.getName());
            rider.setTeam(riderData.getTeam());
            // zapisanie
            return riderRepository.save(rider);
        }


    public void writeRidersToCsv(Writer writer) {

        List<Rider> riders = riderRepository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("ID", "Number", "Name","team");
            for (Rider rider : riders) {
                csvPrinter.printRecord(rider.getId(), rider.getNumber(), rider.getName(), rider.getTeam());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }

    public byte[] writeRidersToXML(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // Tworzenie dokumentu XML
            Document document = documentBuilder.newDocument();

            Element ridersElement = document.createElement("riders");
            document.appendChild(ridersElement);

            List<Rider> riders = riderRepository.findAll();
            for (Rider rider : riders) {
                appendRiderElement(document, ridersElement, rider.getId().toString(), String.valueOf(rider.getNumber()), rider.getName(), rider.getTeam());
            }
            // Tworzenie obiektu Transformer do przekształcania dokumentu XML w strumień bajtów
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Konfiguracja parametrów Transformera
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Tworzenie strumienia bajtów do przechowywania wygenerowanego XML
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Przekształcenie dokumentu XML na strumień bajtów
            transformer.transform(new DOMSource(document), new StreamResult(outputStream));

            // Pobieranie wygenerowanego XML jako tablicy bajtów
            byte[] xmlBytes = outputStream.toByteArray();

            // Zwracanie tablicy bajtów
            return xmlBytes;
        }
        catch (Exception e) {
            e.printStackTrace();
            // Obsługa wyjątków
        }
        return null;
    }

    private void appendRiderElement(Document document, Element ridersElement, String id, String number, String name, String team) {
        // Tworzenie elementu <rider>
        Element riderElement = document.createElement("rider");
        ridersElement.appendChild(riderElement);

        // Tworzenie elementów <id>, <number>, <name>, <team> i dodawanie ich do <rider>
        appendElementWithValue(document, riderElement, "id", id);
        appendElementWithValue(document, riderElement, "number", number);
        appendElementWithValue(document, riderElement, "name", name);
        appendElementWithValue(document, riderElement, "team", team);
    }

    private void appendElementWithValue(Document document, Element parentElement, String elementName, String value) {
        Element element = document.createElement(elementName);
        element.setTextContent(value);
        parentElement.appendChild(element);
    }
}
