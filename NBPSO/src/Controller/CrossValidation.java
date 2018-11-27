/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Percobaan;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author M.Hakim Amransyah
 */
public class CrossValidation {
    
    private int kfold;
    private Data data;
    Percobaan hasil;
    
    public CrossValidation(int fold, Data data){
        this.data = data;
        this.kfold = fold;
        hasil = new Percobaan(this.kfold);
//        Collections.shuffle(this.data.getFitur());
    }
    
    public Percobaan doCrossValidation(HashMap<String, String> konfigurasi){
        int iter_fold = 0;
        int posisi_awal = 0;
        int posisi_akhir = kfold;
        
        NaiveBayes nb;
        PSO nbpso;
        while(posisi_akhir <= this.data.getJumlahSeluruhData()){
            Data uji = this.getPartDataUji(posisi_awal, posisi_akhir);
            Data latih = this.getPartDataLatih(posisi_awal, posisi_akhir);
            long waktu_eksekusi_nb       = System.currentTimeMillis();
            nb = new NaiveBayes(latih, uji);
            hasil.tambahAkurasiNB(nb.getClassifierResult(null));
            hasil.tambahWaktuEksekusi("NB", (double)(System.currentTimeMillis()-waktu_eksekusi_nb)/1000);
            long waktu_eksekusi_nbpso    = System.currentTimeMillis();
            nbpso = new PSO(latih, uji, konfigurasi);
            hasil = nbpso.getClassificationResult(hasil);
            hasil.tambahWaktuEksekusi("NBPSO", (double)(System.currentTimeMillis()-waktu_eksekusi_nbpso)/1000);
            posisi_awal = (posisi_awal + kfold);
            posisi_akhir = posisi_awal + kfold;
            iter_fold++;
        }
        
        return this.hasil;
    }
    
    
    private Data getPartDataUji(int posisi_awal, int posisi_akhir){
        Data part_data_uji = new Data();
        for(int i=posisi_awal;i<posisi_akhir;i++){
           part_data_uji.tambahFitur(this.data.getFitur().get(i));
        }
        
        return part_data_uji;
    }
    
    private Data getPartDataLatih(int posisi_awal_data_uji, int posisi_akhir_data_uji){
        Data part_data_latih = new Data();
        
        for(int i=0;i<this.data.getJumlahSeluruhData();i++){
           if(i == posisi_awal_data_uji && i < posisi_akhir_data_uji){
               // do nothing      
               posisi_awal_data_uji++;
           }else{
               part_data_latih.tambahFitur(this.data.getFitur().get(i));
           }
        }
        
        return part_data_latih;
    }
    
    
    
    
    
}
