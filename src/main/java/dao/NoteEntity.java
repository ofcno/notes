package dao;

import javax.persistence.*;

@Entity
@Table(name = "notes", schema = "testdb")
public class NoteEntity {

    private int id;
    private String title;
    private String content;

    public NoteEntity() {

    }

    public NoteEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteEntity(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Id
    @Column(name = "note_id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "note_title", nullable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "note_content", nullable = true, length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteEntity that = (NoteEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
