package notes;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class NoteManager {
    Session session;
    RequestJson requestJson;
    public NoteManager(Session session, RequestJson requestJson) {
        this.session = session;
        this.requestJson = requestJson;
    }

    public List<Note> getNotesFromDateToDate(){

        long dateCreatedFrom = Long.parseLong(requestJson.getDateCreatedFrom());
        long dateCreatedTo = Long.parseLong(requestJson.getDateCreatedTo());
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
        Root<Note> note = criteriaQuery.from(Note.class);

        criteriaQuery.select(note).where(criteriaBuilder.and(criteriaBuilder.ge(note.get("dateCreated"), dateCreatedFrom), criteriaBuilder.le(note.get("dateCreated"), dateCreatedTo)));

        criteriaQuery.orderBy(criteriaBuilder.desc(note.get("dateCreated")));
        Query<Note> query = session.createQuery(criteriaQuery);
        List<Note> list = query.getResultList();
        return list;
    }

    public List<Note> getNotes(){

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
        Root<Note> note = criteriaQuery.from(Note.class);
        criteriaQuery.select(note);
        criteriaQuery.orderBy(criteriaBuilder.desc(note.get("dateCreated")));
        Query<Note> query = session.createQuery(criteriaQuery);
        List<Note> list = query.getResultList();
        return list;
    }

    public Note toNote(){
        Note note = new Note();
        note.setContent(requestJson.getContent());
        note.setTitle(requestJson.getTitle());
        if(requestJson.getDateCreated() != null)
            note.setDateCreated(Long.parseLong(requestJson.getDateCreated()));
        else
            note.setDateCreated(System.currentTimeMillis());
        return note;
    }

}
