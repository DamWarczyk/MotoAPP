package com.example.TestBackend;

import com.example.TestBackend.model.Rider;
import com.example.TestBackend.model.RidersWrapper;
import com.example.TestBackend.model.Role;
import com.example.TestBackend.model.Student;
import com.example.TestBackend.repo.RiderRepository;
import com.example.TestBackend.repo.RoleRepo;
import com.example.TestBackend.repo.StudentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MotoInitializer implements CommandLineRunner {
    private final StudentRepo studentRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final RiderRepository riderRepository;
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Autowired
    public MotoInitializer(StudentRepo studentRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, RiderRepository riderRepository) {
        this.studentRepo = studentRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.riderRepository = riderRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role(1L, "ROLE_ADMIN");

        if (roleRepo.findAll().size() != 0) {
            return;
        }

        if (roleRepo.findAll().size() == 0) {
            roleRepo.save(roleAdmin);

            Role role = new Role((long) 2, "ROLE_USER");
            roleRepo.save(role);
        }

        if ((long) studentRepo.findAll().size() == 0) {
            List<Role> roleList = new ArrayList<>();
            roleList.add(roleAdmin);

            Student student = new Student(1L, "Admin", "Admin", "AA@mail.com", "Qwer123!", roleList);
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            studentRepo.save(student);
        }

        System.out.println("try is working");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File("/app/drivers.xml");
            InputStream inputStream = new FileInputStream(file);

            Document document = builder.parse(new InputSource(inputStream));

            List<Rider> riderList = new ArrayList<>();

            NodeList nodeList = document.getElementsByTagName("driver");

            for (int i = 0; i <= nodeList.getLength() - 1; i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Pobieranie danych z elementu XML
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String team = element.getElementsByTagName("team").item(0).getTextContent();
                    int number = Integer.parseInt(element.getElementsByTagName("number").item(0).getTextContent());

                    // Tworzenie obiektu i dodawanie go do listy do zapisu
                    Rider entity = new Rider();
                    entity.setName(name);
                    entity.setTeam(team);
                    entity.setNumber(number);
                    riderList.add(entity);
                }
            }
            riderRepository.saveAll(riderList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("/app/drivers.json");

            RidersWrapper ridersWrapper = objectMapper.readValue(file, RidersWrapper.class);
            List<Rider> riders = ridersWrapper.getRiders();

            riderRepository.saveAll(riders);
        }catch (IOException e){
            e.printStackTrace();
        }


    }

}