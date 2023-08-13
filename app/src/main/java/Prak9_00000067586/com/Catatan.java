package Prak9_00000067586.com;

public class Catatan {
    private long id;
    private String judul;
    private String notes;

    public Catatan(){}

    public void setID(long id){
        this.id = id;
    }

    public long getID(){
        return this.id;
    }

    public void setJudul(String judul){
        this.judul = judul;
    }

    public String getJudul(){
        return this.judul;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public String getNotes(){
        return this.notes;
    }
}
