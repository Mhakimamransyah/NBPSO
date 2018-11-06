/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Fitur {
    
    private int nomor;
    private int subjek_id;
    private ArrayList<Double> nilai;
    private String label;
    
    public Fitur(){
        this.nilai = new ArrayList<Double>(26);
    }
    
    public void tambahNilai(double fitur){
        this.nilai.add(fitur);
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(int no_label) {
        if(no_label == 1){
            this.label = "Parkinson";
        }else if(no_label == 0){
            this.label = "Tidak";
        }
    }
    
    public ArrayList<Double> getNilaiFitur() {
        return nilai;
    }

    public void setFitur(ArrayList<Double> fitur) {
        this.nilai = fitur;
    }
       
    public int getSubjek_id() {
        return subjek_id;
    }

    public void setSubjek_id(int subjek_id) {
        this.subjek_id = subjek_id;
    }

}
