package etn.app.danghoc.danh_ba.model;

import androidx.annotation.VisibleForTesting;

public class info_people {
    private String name;
    private  String numberPhone;
    private boolean sex;

    public info_people(String name, String numberPhone, boolean sex) {
        this.name = name;
        this.numberPhone = numberPhone;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public boolean isBoy() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
