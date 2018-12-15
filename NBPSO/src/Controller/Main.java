/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Percobaan;
import View.ClassificationFrame;
import View.DataFrame;
import View.Home;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

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
        Home home = new Home(this);
        home.setVisible(true);
    }
    
    public void tampilklanFrameKlasifikasi(){
        ClassificationFrame frame = new ClassificationFrame(this);
        frame.setVisible(true);
    }
    
    public void tampilkanFrameData(){
        try {
            Data data = this.dao.getDataParkinson();
            DataFrame frame = new DataFrame(data);
            frame.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Percobaan doClassificationData(int kfold, HashMap<String, String> konfigurasi){
        int fold;
        CrossValidation validation;
        Percobaan hasil = null;
        try {
            Data data = this.dao.getDataParkinson();
            fold = data.getJumlahSeluruhData()/kfold;
            validation = new CrossValidation(fold, data);
            hasil = validation.doCrossValidation(konfigurasi);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    
    public void simpanPercobaan(Percobaan hasil, HashMap<String, String> konfigurasi){
         JFileChooser chooser = new JFileChooser();
         int retrival = chooser.showSaveDialog(null);
         if (retrival == JFileChooser.APPROVE_OPTION) {
         try {
            PrintWriter writer = new PrintWriter(chooser.getSelectedFile()+".txt", "UTF-8");
            writer.println("----------------------------- KONFIGURASI PSO ---------------------------------------");
            writer.println("Generasi : "+konfigurasi.get("Generasi"));
            writer.println("Populasi : "+konfigurasi.get("Populasi"));
            writer.println("C1              : "+konfigurasi.get("c1"));
            writer.println("C2              : "+konfigurasi.get("c2"));
            writer.println(" ");
            writer.println("----------------------------- AKURASI PERCOBAAN -----------------------------------");
            for(int i=0;i<hasil.getAkurasi_nb().size();i++){
                writer.println("K"+(i+1)+" =>  NB : "+hasil.getAkurasi_nb().get(i)+" %, NBPSO : "+hasil.getAkurasi_nbpso().get(i)+" %");
            }
            writer.println("Rata-rata akurasi NB          : "+hasil.getRerata("akurasi NB")+" %");
            writer.println("Rata-rata akurasi NBPSO : "+hasil.getRerata("akurasi NBPSO")+" %");
            writer.println(" ");
             writer.println("----------------------------- WAKTU EKSEKUSI -----------------------------------------");
            for(int i=0;i<hasil.getWaktu_eksekusi_nb().size();i++){
                writer.println("K"+(i+1)+" =>  NB : "+hasil.getWaktu_eksekusi_nb().get(i)+" (detik), NBPSO : "+hasil.getWaktu_eksekusi_nbpso().get(i)+" (detik)");
            }
            writer.println("Rata-rata waktu eksekusi NB          : "+hasil.getRerata("waktu NB")+" (detik)");
            writer.println("Rata-rata waktu eksekusi NBPSO : "+hasil.getRerata("waktu NBPSO")+" (detik)");
            writer.println(" ");
            writer.println("----------------------------- BOBOT HASIL PERCOBAAN -----------------------------");
            int k_index = 1;
            for(double[] fold:hasil.getBobot()){
                writer.println("K"+k_index);
                for(int i=0;i<fold.length;i++){
                    writer.println("Bobot Fitur ke-"+(i+1)+" : "+fold[i]);
                }
                writer.println(" ");
                k_index++;
            }
            writer.close();
         } catch (Exception ex) {
            ex.printStackTrace();
         }     
      }
    }
    
}
