/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Percobaan {
    
    private int id_percobaan;
    private double akurasi;
    private double waktu;
    private String tipe;
    private String bobot;
    private int generasi;
    private int Populasi = 0;
    private double c1 = 0;
    private double c2 = 0;

    public int getPopulasi() {
        return Populasi;
    }

    public void setPopulasi(int Populasi) {
        this.Populasi = Populasi;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }
    
    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }
    
    public int getGenerasi() {
        return generasi;
    }

    public void setGenerasi(int generasi) {
        this.generasi = generasi;
    }

    public int getId_percobaan() {
        return id_percobaan;
    }

    public void setId_percobaan(int id_percobaan) {
        this.id_percobaan = id_percobaan;
    }

    public double getAkurasi() {
        return akurasi;
    }

    public void setAkurasi(double akurasi) {
        this.akurasi = akurasi;
    }

    public double getWaktu() {
        return waktu;
    }

    public void setWaktu(double waktu) {
        this.waktu = waktu;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }
    
}
