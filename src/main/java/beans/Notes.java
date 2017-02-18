package beans;

import data_access.DBHandler;
import data_access.DataLayer;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "NotesBean")
@ViewScoped
public class Notes {

    private DataLayer storage;
    private Map<Integer, List<String>> notes;

    private int noteId;
    private String noteTitle;
    private String noteContent;

    public Notes() {
        //this.storage = new XmlHandler();
        this.storage = new DBHandler();
        notes = storage.getAllNotes();
        this.noteTitle = "";
        this.noteContent = "";
    }

    public void openWidgetForAddingNote() {
        RequestContext.getCurrentInstance().execute("PF('adder').show()");
    }

    public void addNoteAndCloseWidget() {
        storage.addNewNote(noteTitle, noteContent);
        RequestContext.getCurrentInstance().execute("PF('adder').hide()");
        this.reloadPage();
    }

    public void openWidgetForNoteChanging(SelectEvent e) {
        noteId = ((Map.Entry<Integer, List<String>>) e.getObject()).getKey();
        RequestContext.getCurrentInstance().execute("PF('changer').show()");
    }

    public void setValueAndCloseWidget() {
        storage.changeNote(noteId, noteTitle, noteContent);
        RequestContext.getCurrentInstance().execute("PF('changer').hide()");
        this.reloadPage();
    }

    public void removeNoteAndCloseWidget() {
        storage.removeNote(noteId);
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
    public Map<Integer, List<String>> getNotes() {
        return notes;
    }

    public void setNotes(Map<Integer, List<String>> notes) {
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
