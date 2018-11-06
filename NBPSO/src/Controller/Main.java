/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Percobaan;
import View.MainFrame;
import View.PartisiData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Main {

    private Dao dao;
    
    public Main(){
        this.dao = new Dao();
    }
    
    public static void main(String[] args) {
       Main main = new Main();
       main.tampilkanFrameUtama();
    }
    
    private void tampilkanFrameUtama(){
        try {
            // METHOD UNTUK MENAMPILKAN TAMPILAN UTAMA APLIKASI
            MainFrame frame = new MainFrame(this,dao.getAllSkenarioData()); 
            frame.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tampilkanFramePartisiData(int awal_dl, int akhir_dl, int awal_du, int akhir_du){
      // METHOD UNTUK MENAMPILKAN TAMPILAN PARTISI DATA(UJI/ LATIH)
        try {
            Data data_latih = dao.getPartisiData(awal_dl,akhir_dl);
            data_latih.setJenis_data("Latih");
            Data data_uji = dao.getPartisiData(awal_du,akhir_du);
            data_uji.setJenis_data("Uji");
            PartisiData frame_partisi_data = new PartisiData(data_latih,data_uji);
            frame_partisi_data.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void hapusSkenario(int id_skenario){
        try {
            this.dao.hapusSkenario(id_skenario);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void hapusPercobaan(int id_percobaan){
        try {
            this.dao.hapusPercobaan(id_percobaan);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Skenario> buatSkenario(String nama, int awal_dl, int akhir_dl, int awal_du, int akhir_du){
        // METHOD UNTUK MEMBUAT SKENARIO BARU
        ArrayList<Skenario> daftar = null;
        try {
            dao.tambahSkenario(nama,awal_dl+"-"+akhir_dl,awal_du+"-"+akhir_du);
            daftar = this.dao.getAllSkenarioData();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftar;
    }
    
    public Percobaan doNaiveBayesClassifier(Skenario skenario){
        Percobaan percobaan = null;
        try {
            Data latih = this.dao.getPartisiData(skenario.getAwal_dl(), skenario.getAkhir_dl());
            Data uji = this.dao.getPartisiData(skenario.getAwal_du(), skenario.getAkhir_du());
            
            NaiveBayes classifier = new NaiveBayes(latih, uji);
            percobaan = classifier.getClassifierResult(null);
            percobaan.setId_percobaan( this.dao.tambahPercobaan(skenario.getId_skenario(), percobaan));
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return percobaan;
    }
    
    public Percobaan doPSONaiveBayesClassifier(Skenario skenario, HashMap<String, String> konfigurasi){
       Percobaan percobaan = null;
        try {
            DecimalFormat df = new DecimalFormat("#.##");
            Data latih = this.dao.getPartisiData(skenario.getAwal_dl(), skenario.getAkhir_dl());
            Data uji = this.dao.getPartisiData(skenario.getAwal_du(), skenario.getAkhir_du());
            
            PSO classifier = new PSO(latih, uji, konfigurasi);
            percobaan = classifier.getClassificationResult(konfigurasi.get("Stoping_criteria"));
            percobaan.setId_percobaan( this.dao.tambahPercobaan(skenario.getId_skenario(), percobaan));
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return percobaan;
            
    }
    
}
