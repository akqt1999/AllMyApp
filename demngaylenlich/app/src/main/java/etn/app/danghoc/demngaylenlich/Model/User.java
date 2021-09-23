package etn.app.danghoc.demngaylenlich.Model;

import android.util.Log;

public class User {
   private String nameUser, content, dayStart, daySuccess;
    private String idNote;

    private boolean isSelect=false;


    public User(String content, String dayStart, String daySuccess) {
        this.content = content;
        this.dayStart = dayStart;
        this.daySuccess = daySuccess;

    }

    public User(String nameUser,String content, String dayStart, String daySuccess,String idNote) {
        this.nameUser=nameUser;
        this.content = content;
        this.dayStart = dayStart;
        this.daySuccess = daySuccess;
        this.idNote=idNote;

    }

    public User() {
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect_k_dc_chon(boolean select) {
        isSelect = select;
        Log.d("chon", "_set k dc chon");
    }
    public void setSelect_dc_chon(boolean select) {
        isSelect = select;
        Log.d("chon", "_set dc chon");
    }
    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDaySuccess() {
        return daySuccess;
    }

    public void setDaySuccess(String daySuccess) {
        this.daySuccess = daySuccess;
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
