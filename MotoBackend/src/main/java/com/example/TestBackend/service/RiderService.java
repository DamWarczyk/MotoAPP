package com.example.TestBackend.service;

import com.example.TestBackend.model.Rider;
import com.example.TestBackend.repo.RiderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
