package data_access;

import org.primefaces.push.annotation.Singleton;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class XmlHandler implements DataLayer {

    private static final String xmlName = "C:\\Users\\hl\\IdeaProjects\\notes\\test.xml";
    private int id;
    private Document doc;
    public XmlHandler() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            if (new File(xmlName).exists()) {
                doc = docBuilder.parse(xmlName);
                id = Integer.parseInt(((Element) doc.getFirstChild()).getAttribute("lastId"));
            } else {
                doc = docBuilder.newDocument();
                id = 0;
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) {
        XmlHandler xh = new XmlHandler();
        /*xh.createNewXml();
        xh.addNewNote("some node name", "content");
        xh.addNewNote("Second note!", "YEAH");
        xh.addNewNote("THIRD note", "oh my");
        xh.addNewNote("4 note", "4oh my");
        xh.removeNote(2);
        xh.addNewNote("5 note", "5 my");
        xh.changeNote(1, "meow", "MEW MEW");*/
        //xh.addNewNote("some node name", "content");
        //System.out.println(xh.getAllNotes());
    }

    public int addNewNote(String noteTitle, String noteContent) {
        id++;
        Node notes = doc.getFirstChild();
        Element note = doc.createElement("note");
        notes.appendChild(note);
        note.setAttribute("id", Integer.toString(id));

        Element title = doc.createElement("title");
        title.appendChild(doc.createTextNode(noteTitle));
        note.appendChild(title);

        Element content = doc.createElement("content");
        content.appendChild(doc.createTextNode(noteContent));
        note.appendChild(content);

        ((Element) doc.getFirstChild()).setAttribute("lastId", Integer.toString(id));
        saveXmlChanges();
        return id;
    }

    public void removeNote(int noteId) {
        Node rootNode = doc.getFirstChild();
        rootNode.removeChild(doc.getElementsByTagName("note").item(noteId - 1));

        saveXmlChanges();
    }

    public void changeNote(int noteId, String title, String content) {
        //Node note = doc.getElementsByTagName("note").item(noteId - 1); nope
        NodeList notesList = doc.getElementsByTagName("note");
        Node note = null;
        for (int i = 0; i < notesList.getLength(); i++)
            if (notesList.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(Integer.toString(noteId)))
                note = notesList.item(i);

        NodeList noteFields = note.getChildNodes();
        if (!title.equals("")) {
            noteFields.item(0).setTextContent(title);
        }

        if (!content.equals("")) {
            noteFields.item(1).setTextContent(content);
        }

        saveXmlChanges();
    }

    public Map<Integer, List<String>> getAllNotes() {
        Map<Integer, List<String>> result = new HashMap<>();
        NodeList nodeList = doc.getElementsByTagName("note");
        for (int i = 0; i < nodeList.getLength(); i++) {
            result.put(
                    Integer.parseInt(nodeList.item(i).getAttributes().getNamedItem("id").getNodeValue()),
                    Arrays.asList(nodeList.item(i).getChildNodes().item(0).getTextContent(),
                            nodeList.item(i).getChildNodes().item(1).getTextContent()));
        }
        return result;
    }

    public void createNewXml() {
        Element rootElement = doc.createElement("notes");
        doc.appendChild(rootElement);
        rootElement.setAttribute("lastId", "0");

        saveXmlChanges();
    }

    private void saveXmlChanges() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlName));
            transformer.transform(source, result);

        } catch (TransformerException pce) {
            pce.printStackTrace();
        }
    }
}