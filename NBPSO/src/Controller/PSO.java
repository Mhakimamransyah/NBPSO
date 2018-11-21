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
    private double generasi;
    private int jumlah_populasi;
    private ArrayList<Partikel> populasi;
    
    public PSO(Data latih, Data uji, HashMap<String, String> konfigurasi){
        this.jumlah_populasi = Integer.parseInt(konfigurasi.get("Populasi"));
        this.latih = latih;
        this.uji = uji;
        this.generasi = Double.parseDouble(konfigurasi.get("Generasi"));
        this.c1 = Double.parseDouble(konfigurasi.get("c1"));
        this.c2 = Double.parseDouble(konfigurasi.get("c2"));
        this.populasi = new ArrayList<Partikel>(jumlah_populasi);
        this.inisialisasiPartikel();
    }
    
    public Percobaan getClassificationResult(Percobaan hasil){
        int generasi = 0;
        double akurasi = 0;
        Partikel Gbest = null;
        
        long waktu_eksekusi = System.currentTimeMillis();
        while(generasi < this.generasi){
            this.evaluasiFitnessSeluruhPartikel();
            Gbest = this.ambilPartikelTerbaik();
            this.updateKecepatanSeluruhPartikel(Gbest);
//            System.out.println("Generasi ke-"+(generasi+1)+" --> partikel terbaik : "+Gbest.getFitness());
            akurasi = Gbest.getFitness(); 
            generasi++;
        }
        hasil.tambahAkurasiNBPSO(akurasi);
        hasil.tambahBobotTiapK(Gbest.getPosisi());
        return hasil;
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
