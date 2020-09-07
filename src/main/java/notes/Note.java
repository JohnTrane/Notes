package notes;

import javax.persistence.*;

@Entity
@Table(name = "note")
public class Note {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "date")
    long dateCreated;
    @Column(name = "content")
    String content;
    @Column(name = "title")
    String title;

    public Note(int id,long dateCreated, String content) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    public Note(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
