package beans;

import data_access.DataLayer;
import data_access.XmlHandler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//http://www.primefaces.org/showcase/ui/data/repeat.xhtml
@ManagedBean(name = "notesBean")
@ViewScoped
public class Notes {
    private DataLayer storage;

    private Map<Integer, List<String>> notes;

    /*    public static void main(String[] a){
            new Notes();
        }*/
    public Notes() {
        this.storage = new XmlHandler();
        notes = storage.getAllNotes();
        System.out.println(notes);
    }

    public Map<Integer, List<String>> getNotes() {
        return notes;
    }

    public void setNotes(Map<Integer, List<String>> notes) {
        this.notes = notes;
    }

    public List<List<String>> getTempList() {
        System.out.println();
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(Arrays.asList(Integer.toString(i), Integer.toString(i), Integer.toString(i)));
        }
        System.out.println(list);
        return list;
    }
}
