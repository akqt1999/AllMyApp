package etn.app.danghoc.cloudfirestore.Model;

import com.google.firebase.firestore.Exclude;

public class Note {
    private String title, description,documentId;
    private  int priority ;
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Note(String title, String description,int priority) {
        this.title = title;
        this.description = description;
        this.priority=priority;
    }
    public Note() {
    }

    public Note(String title, String description, String documentId) {
        this.title = title;
        this.description = description;
        this.documentId = documentId;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
