package etn.app.danghoc.webservicetolisview_recycelerview;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class ItemStudent implements Serializable {
    private String name,address;
    private  String birth;
    int id;

    public ItemStudent(String name, String address, String birth,int id) {
        this.name = name;
        this.address = address;
        this.birth = birth;
        this.id=id;
    }

    public ItemStudent(String name, String address, String birth) {
        this.name = name;
        this.address = address;
        this.birth = birth;
    }

    public ItemStudent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
