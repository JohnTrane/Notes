package notes;

public class RequestJson {
    String login;
    String password;
    String token;
    String content;
    int userId;
    int noteId;
    String dateCreated;
    String dateCreatedFrom;
    String dateCreatedTo;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreatedFrom() {
        return dateCreatedFrom;
    }

    public void setDateCreatedFrom(String dateCreatedFrom) {
        this.dateCreatedFrom = dateCreatedFrom;
    }

    public String getDateCreatedTo() {
        return dateCreatedTo;
    }

    public void setDateCreatedTo(String dateCreatedTo) {
        this.dateCreatedTo = dateCreatedTo;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
