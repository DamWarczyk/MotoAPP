package com.example.TestBackend;

import com.example.TestBackend.model.Rider;
import com.example.TestBackend.service.RiderService;
import com.github.parsad23.motogpapi.domain.*;
import com.github.parsad23.motogpapi.exceptions.DataNotAvailableException;
import com.github.parsad23.motogpapi.reader.MotoGPData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/riders")
public class RiderController {
    private final RiderService riderService;

    MotoGPData data = new MotoGPData();

    @Autowired
    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }
//baza danych riders
        @GetMapping
        @CrossOrigin("http://localhost:4200")
        public ResponseEntity<List<Rider>> getAllRiders() {
            List<Rider> riders = riderService.getAllRiders();
            return new ResponseEntity<>(riders, HttpStatus.OK);
        }
        //Lista wszystkich posortowana po numerze rosnąco
        @GetMapping("/sorted/asc")
        @CrossOrigin("http://localhost:4200")
        public ResponseEntity<List<Rider>> getAllRidersSortedAsc() {
            List<Rider> riders = riderService.getAllRidersSortedAsc();
            return new ResponseEntity<>(riders, HttpStatus.OK);
        }
        //malejąco
        @GetMapping("/sorted/desc")
        @CrossOrigin("http://localhost:4200")
        public ResponseEntity<List<Rider>> getAllRidersSortedDesc() {
            List<Rider> riders = riderService.getAllRidersSortedDesc();
            return new ResponseEntity<>(riders, HttpStatus.OK);
        }
        //do określnego teamu
        @GetMapping("/team/{team}")
        @CrossOrigin("http://localhost:4200")
        public ResponseEntity<List<Rider>> getRidersByTeam(@PathVariable("team") String team) {
            List<Rider> riders = riderService.getRidersByTeam(team);
            return new ResponseEntity<>(riders, HttpStatus.OK);
        }


    @GetMapping("/grid")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<?> getGridByRaceNumber () throws IOException, DataNotAvailableException {
        try {

            String url = "https://motorsportstats.com/api/results-summary?seasonSlug=motogp_2021&seriesSlug=motogp&stats=championshipWin,championshipRank,starts,wins,podiums,poles,fastestLaps,bestFinishPosition,bestGridPosition,points,retirements,avgFinishPosition,avgGridPosition&size=100";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject respond = (JSONObject) jsonParser.parse(result);
            System.out.println("Grid Endpoint");
            return new ResponseEntity<>(respond, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/grid/{year}")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<?> getGridByYear (@PathVariable String year) throws IOException, DataNotAvailableException {
        try {

            String url = "https://motorsportstats.com/api/results-summary?seasonSlug=motogp_" + year + "&seriesSlug=motogp&stats=championshipWin,championshipRank,starts,wins,podiums,poles,fastestLaps,bestFinishPosition,bestGridPosition,points,retirements,avgFinishPosition,avgGridPosition&size=100";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject respond = (JSONObject) jsonParser.parse(result);
            System.out.println("Grid Year Endpoint");
            return new ResponseEntity<>(respond, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    //zapisywanie do bazy
    @PostMapping
    public ResponseEntity<Rider> addRider(@RequestBody Rider rider) {
        Rider savedRider = riderService.saveRider(rider);
        return ResponseEntity.ok(savedRider);
    }


    //update bazy danych

    @PutMapping("/{id}")
    public ResponseEntity<Rider> updateRider(@PathVariable("id") long id, @RequestBody Rider rider) {
        Rider updatedRider = riderService.updateRider(id, rider);
        if (updatedRider == null) {
            // error
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedRider);
    }

    //eksport do csv
    @GetMapping("/export/csv")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<String> exportRidersToCSV() {
        List<Rider> riders = riderService.getAllRiders();
        String csvFilePath = "riders.csv";

        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // nagówki do pliku csv
            writer.append("Number,Name,Team\n");

            // Zapisanie danych do pliku
            for (Rider rider : riders) {
                writer.append(String.format("%d,%s,%s\n", rider.getNumber(), rider.getName(), rider.getTeam()));
            }

            writer.flush();
        } catch (IOException e) {
            return new ResponseEntity<>("Blad eksportu do csv.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Wykonano eksport do csv.", HttpStatus.OK);
    }

    //eksport do xml
    @GetMapping("/export/xml")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<String> exportRidersToXML() {
        List<Rider> riders = riderService.getAllRiders();
        String xmlFilePath = "riders.xml";

        try (FileWriter writer = new FileWriter(xmlFilePath)) {
            // nagówek i ele główny
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.append("<riders>\n");

            // zapis do ele xml
            for (Rider rider : riders) {
                writer.append(String.format("  <rider>\n    <number>%d</number>\n    <name>%s</name>\n    <team>%s</team>\n  </rider>\n",
                        rider.getNumber(), rider.getName(), rider.getTeam()));
            }

            // zamknięcie elementu
            writer.append("</riders>");

            writer.flush();
        } catch (IOException e) {
            return new ResponseEntity<>("Blad eksportu do xml", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Wykonano eksport do xml.", HttpStatus.OK);
    }

    @RequestMapping("/riderCSV")
    @CrossOrigin("http://localhost:4200")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"employees.csv\"");
        riderService.writeRidersToCsv(servletResponse.getWriter());
    }


}
