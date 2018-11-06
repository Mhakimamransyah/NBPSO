/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Partikel;
import Model.Percobaan;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author M.Hakim Amransyah
 */
public class PSO {
    
    private Data latih;
    private Data uji;
    private double c1;
    private double c2;
    private double stoping_point;
    private int jumlah_populasi;
    private ArrayList<Partikel> populasi;
    private Percobaan percobaan;
    
    public PSO(Data latih, Data uji, HashMap<String, String> konfigurasi){
        this.jumlah_populasi = Integer.parseInt(konfigurasi.get("Populasi"));
        this.latih = latih;
        this.uji = uji;
        this.stoping_point = Double.parseDouble(konfigurasi.get("Stoping_point"));
        this.c1 = Double.parseDouble(konfigurasi.get("c1"));
        this.c2 = Double.parseDouble(konfigurasi.get("c2"));
        this.percobaan = new Percobaan();
        this.populasi = new ArrayList<Partikel>(jumlah_populasi);
        this.inisialisasiPartikel();
    }
    
    public Percobaan getClassificationResult(String stoping){
        int generasi = 1;
        double akurasi;
        Partikel Gbest;
        
        long waktu_eksekusi   = System.currentTimeMillis();
        while(true){
            this.evaluasiFitnessSeluruhPartikel();
            Gbest = this.ambilPartikelTerbaik();
            this.updateKecepatanSeluruhPartikel(Gbest);
            System.out.println("Generasi ke-"+(generasi)+" --> partikel terbaik : "+Gbest.getFitness());
            akurasi = Gbest.getFitness(); 
            if(stoping.equalsIgnoreCase("  Generasi")){
                if(generasi == this.stoping_point){
                    break;
                }
            }else if(stoping.equalsIgnoreCase("  Akurasi")){
                if(akurasi > this.stoping_point){
                  break;
                }   
            }
            generasi++;
        }
        percobaan.setWaktu((double)(System.currentTimeMillis()-waktu_eksekusi)/1000);
        percobaan.setAkurasi(akurasi);
        percobaan.setGenerasi(generasi);
        percobaan.setC1(this.c1);
        percobaan.setC2(this.c2);
        percobaan.setPopulasi(this.jumlah_populasi);
        percobaan.setTipe("NBPSO");
        percobaan.setBobot(Gbest.ambilBobotFiturTertinggi());
        
        return this.percobaan;
    }
    
    
    
    private void updateKecepatanSeluruhPartikel(Partikel Gbest){
        for(Partikel partikel : this.populasi){
            partikel.updateVelocity(Gbest, c1, c2);
        }
    }
    
    private void evaluasiFitnessSeluruhPartikel(){
        for(Partikel partikel : this.populasi){
            partikel.evaluasiNilaiFitness();
        }
    }
    
    private Partikel ambilPartikelTerbaik(){
        int index=0;
        for(int i=0;i<this.populasi.size();i++){
            if(this.populasi.get(index).getFitness() < this.populasi.get(i).getFitness()){
                index = i;
            }
        }
        return this.populasi.get(index);
    }
    
    private void inisialisasiPartikel(){
        Partikel partikel;
        NaiveBayes nb;
        for(int i=0;i<this.jumlah_populasi;i++){
            nb = new NaiveBayes(this.latih, this.uji);
            partikel = new Partikel(nb, this.latih.getJumlahAtributData()-3);
            this.populasi.add(partikel);
        }
    }
    
    private void cetakPopulasiPartikel(){
        int iter =1;
        for(Partikel partikel : this.populasi){
            System.out.println("Partikel ke : "+iter);
            partikel.cetakPartikel();
            System.out.println("");
            iter++;
        }
    }
    
    
}
