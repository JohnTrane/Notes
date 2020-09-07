package notes;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    static org.hibernate.Session session;

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Note.class);
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        session = sessionFactory.openSession();
        SpringApplication.run(Main.class, args);
    }

    @PostMapping(value = "/api/addNote", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String postNote(@RequestBody RequestJson requestJson, @RequestHeader("Authorization") String authorization) {
        Gson gson = new Gson();
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive())
            transaction = session.beginTransaction();

        NoteManager noteManager = new NoteManager(session, requestJson);
        if (!authorization.equals("password123")) {
            transaction.rollback();
            return gson.toJson(new ResponseObj("wrong password", false));
        }

        Note note = noteManager.toNote();

        try {
            session.save(note);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
            return gson.toJson(new ResponseObj("unable to create note", false));
        }

        return gson.toJson(new ResponseObj("note added successfully", true));
    }

    @PostMapping(value = "/api/getNotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String getNotes(@RequestBody RequestJson requestJson) {
        Gson gson = new Gson();
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive())
            transaction = session.beginTransaction();

        NoteManager noteManager = new NoteManager(session, requestJson);
        List<Note> list;

        if (requestJson.getDateCreatedTo() != null && requestJson.getDateCreatedFrom() != null) {
            list = noteManager.getNotesFromDateToDate();
        } else {
            list = noteManager.getNotes();
        }

        return gson.toJson(list);
    }


    @PostMapping(value = "/api/deleteNote", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String deleteNote(@RequestBody RequestJson requestJson, @RequestHeader("Authorization") String authorization) {
        Transaction transaction = session.getTransaction();
        Gson gson = new Gson();


        if (!transaction.isActive())
            transaction = session.beginTransaction();

        if (!authorization.equals("password123")) {
            transaction.rollback();
            return gson.toJson(new ResponseObj("wrong password", false));
        }
        Note note = (Note) session.get(Note.class, requestJson.getNoteId());

        try {
            session.delete(note);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaction.rollback();
            return gson.toJson(new ResponseObj("unable to delete note, note does not exist", false));
        }

        return gson.toJson(new ResponseObj("note deleted successfully", true));
    }


}
