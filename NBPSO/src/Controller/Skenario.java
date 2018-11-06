/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Percobaan;
import java.util.ArrayList;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Skenario {
    
    private int id_skenario;
    private String nama;
    private int awal_dl;
    private int akhir_dl;
    private int awal_du;
    private int akhir_du;
    private ArrayList<Percobaan> daftar_percobaan;

    public Skenario(){
       this.daftar_percobaan = new ArrayList<Percobaan>();
    }
    
    public double getRerataAkurasiSkenario(){
       double num = 0;
       for(Percobaan percobaan : this.daftar_percobaan){
           num = num + percobaan.getAkurasi();
       }
       return num/this.daftar_percobaan.size();
    }
    
    public Percobaan getPercobaan(int id_percobaan){
        Percobaan percobaan_terpilih = null;
        for(Percobaan percobaan : this.daftar_percobaan){
            if(id_percobaan == percobaan.getId_percobaan()){
                percobaan_terpilih = percobaan;
                break;
            }
        }
        return percobaan_terpilih;
    }
    
    public void hapusPercobaan(int id_percobaan){
        int index = 0,index_terpilih = 0;
        
        for(Percobaan percobaan : this.daftar_percobaan){
            if(percobaan.getId_percobaan() == id_percobaan){
                index_terpilih = index;
                break;
            }
            index++;
        }
        this.daftar_percobaan.remove(index_terpilih);
        
    }
    
    public int getId_skenario() {
        return id_skenario;
    }

    public void setId_skenario(int id_skenario) {
        this.id_skenario = id_skenario;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getAwal_dl() {
        return awal_dl;
    }

    public void setPartisi(String data_latih, String data_uji){
        String part_latih[] = data_latih.split("-");
        String part_uji[] = data_uji.split("-");
        this.awal_dl = Integer.parseInt(part_latih[0]);
        this.akhir_dl = Integer.parseInt(part_latih[1]);
        this.awal_du = Integer.parseInt(part_uji[0]);
        this.akhir_du = Integer.parseInt(part_uji[1]);
    }   

    public int getAkhir_dl() {
        return akhir_dl;
    }

    public int getAwal_du() {
        return awal_du;
    }

    public void setAwal_du(int awal_du) {
        this.awal_du = awal_du;
    }

    public int getAkhir_du() {
        return akhir_du;
    }

    public void setAkhir_du(int akhir_du) {
        this.akhir_du = akhir_du;
    }

    public ArrayList<Percobaan> getDaftar_percobaan() {
        return daftar_percobaan;
    }

    public void setDaftar_percobaan(ArrayList<Percobaan> daftar_percobaan) {
        this.daftar_percobaan = daftar_percobaan;
    }
    
    public void tambahPercobaan(Percobaan percobaan){
        this.daftar_percobaan.add(percobaan);
        this.urutkanPercobaan();
    }
    
    private void urutkanPercobaan(){
        Percobaan temp;
        for(int i=this.daftar_percobaan.size()-1;i>0;i--){
            for(int j=0;j<i;j++){
                if(this.daftar_percobaan.get(j).getId_percobaan()<this.daftar_percobaan.get(j+1).getId_percobaan()){
                    temp = this.daftar_percobaan.get(j);
                    this.daftar_percobaan.set(j, this.daftar_percobaan.get(j+1));
                    this.daftar_percobaan.set(j+1, temp);
                }
            }
        }
    }
}
