/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Percobaan {
    
    private int kfold;
    private ArrayList<Double> akurasi_nb;
    private ArrayList<Double> akurasi_nbpso;
    private ArrayList<double[]> bobot;
    private ArrayList<Double> waktu_eksekusi_nb;
    private ArrayList<Double> waktu_eksekusi_nbpso;

    public Percobaan(int kfold){
        this.kfold = kfold;
        this.akurasi_nb = new ArrayList<Double>(kfold);
        this.akurasi_nbpso = new ArrayList<Double>(kfold);    
        this.bobot = new ArrayList<double[]>(kfold);
        this.waktu_eksekusi_nb = new ArrayList<>(kfold);
        this.waktu_eksekusi_nbpso = new ArrayList<>(kfold);
    }
    
    public void tambahWaktuEksekusi(String tipe, double waktu){
        switch(tipe){
            case "NB"   :  
                     this.waktu_eksekusi_nb.add(waktu);
                break;
            case "NBPSO":  
                     this.waktu_eksekusi_nbpso.add(waktu);
                break;
        }
    }
    
    public ArrayList<Double> getWaktu_eksekusi_nb() {
        return waktu_eksekusi_nb;
    }

    public ArrayList<Double> getWaktu_eksekusi_nbpso() {
        return waktu_eksekusi_nbpso;
    }
    
    public double getRerata(String tipe){
        double rerata;
        ArrayList<Double> alg = null;
        DecimalFormat df = new DecimalFormat("#.##");
        switch(tipe){
            case "akurasi NBPSO" : alg = this.akurasi_nbpso; break;
            case "akurasi NB" : alg = this.akurasi_nb; break;
            case "waktu NB" : alg = this.waktu_eksekusi_nb; break;
            case "waktu NBPSO" : alg = this.waktu_eksekusi_nbpso; break;
        }
        double sum = 0;
        for(Double akurasi : alg){
              sum = sum + akurasi;
        }
        rerata = sum/alg.size();
        return Double.parseDouble(df.format(rerata).replaceAll(",","."));
    }
   
    
    public void tambahAkurasiNB(double akurasi){
        this.akurasi_nb.add(akurasi);
    }
    
     public void tambahAkurasiNBPSO(double akurasi){
        this.akurasi_nbpso.add(akurasi);
    }
     
      public int getKfold() {
        return kfold;
    }

    public ArrayList<Double> getAkurasi_nb() {
        return akurasi_nb;
    }

    public ArrayList<Double> getAkurasi_nbpso() {
        return akurasi_nbpso;
    }
    
    public void tambahBobotTiapK(double[] bobot){
        this.bobot.add(bobot);
    }
    
    
    public ArrayList<double[]> getBobot() {
        return bobot;
    }
    
    public void cetakAkurasi(String tipe){
        for(int i=0;i<this.akurasi_nb.size();i++){
            if(tipe.equalsIgnoreCase("NB")){
                System.out.println("K"+(i+1)+" : "+this.akurasi_nb.get(i));
            }else if(tipe.equalsIgnoreCase("NBPSO")){
                System.out.println("K"+(i+1)+" : "+this.akurasi_nbpso.get(i));
            }
        }
    }
    
    public void cetakBobot(){
        int index = 1;
        for(double[] bobot : this.bobot){
            System.out.println("K"+index);
            for(int i=0;i<bobot.length;i++){
                System.out.print(bobot[i]+" ");
            }
            System.out.println("");
            index++;
        }
    }
    
}
