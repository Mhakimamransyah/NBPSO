/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Fitur;
import java.util.ArrayList;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Data {
    
    private String jenis_data;
    private ArrayList<Fitur> fitur;

    public Data(){
      this.fitur = new ArrayList<Fitur>();    
    }
    
    public ArrayList<Fitur> getFitur() {
        return fitur;
    }
    
    public double countFiturValue(int kolom){
        double max = 0;
        for(Fitur fitur : this.getFitur()){
            if(max < fitur.getNilaiFitur().get(kolom)){
                max = fitur.getNilaiFitur().get(kolom);
            }
        }
        return max;
    }
    
    public void setFitur(ArrayList<Fitur> fitur) {
        this.fitur = fitur;
    }
    
    public void tambahFitur(Fitur fitur){
        this.fitur.add(fitur);
    }
    
    public int getJumlahAtributData(){
        return this.fitur.get(0).getNilaiFitur().size()+3;
    }
    
    public String getJenis_data() {
        return jenis_data;
    }

    public void setJenis_data(String jenis_data) {
        this.jenis_data = jenis_data;
    }

    public int getJumlahSebaranData(String sebaran) {
        int num = 0;
        for(Fitur fitur : this.getFitur()){
             if(sebaran.equalsIgnoreCase("Parkinson")){
                 if(fitur.getLabel().equalsIgnoreCase("Parkinson")){
                     num++;
                 }
             }
             
             if(sebaran.equalsIgnoreCase("Tidak")){
                 if(fitur.getLabel().equalsIgnoreCase("Tidak")){
                     num++;
                 }
             }
        }  
        return num;
    }
    
    public int getJumlahSeluruhData(){
        return this.fitur.size();
    }

  
    public void cetakData(){
        for(Fitur fitur : fitur){
            System.out.print(fitur.getNomor()+" "+fitur.getSubjek_id());
            for(Double nilai_fitur : fitur.getNilaiFitur()){
                System.out.print(" "+nilai_fitur);
            }
            System.out.print(" "+fitur.getLabel());
            System.out.println("");
        }
    }
    
    
}
