package beans;

import dao.NoteEntity;
import dao.NoteService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@ManagedBean(name = "NotesBean")
@ViewScoped
public class Notes {

    private NoteService noteService;
    private List<NoteEntity> notes;

    private int noteId;
    private String noteTitle;
    private String noteContent;

    public Notes() {
        noteService = new NoteService();
        notes = noteService.findAll();
        this.noteTitle = "";
        this.noteContent = "";
    }

    public void openWidgetForAddingNote() {
        RequestContext.getCurrentInstance().execute("PF('adder').show()");
    }

    public void addNoteAndCloseWidget() {
        noteService.create(new NoteEntity(noteTitle, noteContent));
        RequestContext.getCurrentInstance().execute("PF('adder').hide()");
        this.reloadPage();
    }

    public void openWidgetForNoteChanging(SelectEvent e) {
        noteId = ((NoteEntity) e.getObject()).getId();
        RequestContext.getCurrentInstance().execute("PF('changer').show()");
    }

    public void setValueAndCloseWidget() {
        noteService.update(new NoteEntity(noteId, noteTitle, noteContent));
        RequestContext.getCurrentInstance().execute("PF('changer').hide()");
        this.reloadPage();
    }

    public void removeNoteAndCloseWidget() {
        noteService.delete(noteId);
        RequestContext.getCurrentInstance().execute("PF('changer').hide()");
        this.reloadPage();
    }

    private void reloadPage() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters and setters
    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
