package etn.app.danghoc.button_with_listview.model;

public class info_fruit {
    private int number;
    private  String nameFruit;

    public void setNumber(int number){
        this.number=number;
    }

    public int getNumber() {
        return number;
    }

    public void setNameFruit(String  nameFruit){
        this.nameFruit=nameFruit;
    }
    public String getNameFruit(){
        return nameFruit;
    }

    public info_fruit(int number,String nameFruit){
        this.nameFruit=nameFruit;
        this.number=number;
    }

}
