/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.NaiveBayes;
import java.util.Random;

/**
 *
 * @author M.Hakim Amransya
 */
public class Partikel {
    
    private double batas_atas=5;
    private double batas_bawah=0;
    private NaiveBayes nb;
    private double fitness;
    private double[] Pbest;
    private double[] posisi;
    private double[] velocity;
    
    public Partikel(NaiveBayes nb, int dimensi_data){
        this.nb       = nb;
        this.posisi   = new double[dimensi_data];
        this.velocity = new double[dimensi_data];
        this.Pbest    = new double[dimensi_data];
        this.inisialisasiPosisi();
    }
    
    public String ambilBobotFiturTertinggi(){
         String message = null;
         double max = -99;
         int fitur_ke = -1;
         for(int i=0;i<this.posisi.length;i++){
             if(max < this.posisi[i]){
                 max = this.posisi[i];
                 fitur_ke = (i+1);
             }
         }
         message = "Fitur ke  "+fitur_ke+" bobot "+max;
         return message;
    }
    
    public void evaluasiNilaiFitness(){
        double akurasi = this.nb.getClassifierResult(this.posisi).getAkurasi();
        if(akurasi > this.fitness){
            this.fitness = akurasi;
            for(int i=0;i<this.posisi.length;i++){
                this.Pbest[i] = this.posisi[i];
            }
        }
    }
    
    public void updateVelocity(Partikel Gbest,double c1, double c2){
       for(int i=0;i<this.velocity.length;i++){
           double r1 = new Random().nextGaussian();
           double r2 = new Random().nextGaussian();
           this.velocity[i] = this.velocity[i]+(c1*r1*(this.Pbest[i]-this.posisi[i]))+(c2*r2*(Gbest.getPosisi()[i]-this.posisi[i]));
           if(this.velocity[i] > this.batas_atas){
               this.velocity[i] = this.batas_bawah+(this.batas_atas-this.batas_bawah)*new Random().nextDouble();
           }else if(this.velocity[i] < this.batas_bawah){
               this.velocity[i] = this.batas_bawah+(this.batas_atas-this.batas_bawah)*new Random().nextDouble();
           }
       }   
       this.updatePosisi();
    }
    
    private void updatePosisi(){
       for(int i=0;i<this.posisi.length;i++){
           this.posisi[i] = this.posisi[i] + this.velocity[i];
           if(this.posisi[i] > this.batas_atas || this.posisi[i] < this.batas_bawah){
               this.posisi[i] = this.batas_bawah+(this.batas_atas-this.batas_bawah)*new Random().nextDouble(); // Range posisi awal 0...1
           }
       }    
    }
    
    public double[] getVelocity() {
        return velocity;
    }
     
    public double[] getPosisi() {
        return posisi;
    }
    
    private void inisialisasiPosisi(){
        for(int i=0;i<posisi.length;i++){
            this.posisi[i] =this.batas_bawah+(this.batas_atas-this.batas_bawah)*new Random().nextDouble(); // Range posisi awal 0...1
            this.Pbest[i] = this.posisi[i];
        }
    }
    
    public double getFitness(){
        return this.fitness;
    }
    
    public void cetakPartikel(){
        for(int i=0;i<this.posisi.length;i++){
            System.out.println("Bobot fitur ke-"+(i+1)+" : "+this.posisi[i]);
        }
        System.out.println("Fitness : "+this.fitness);
    }
    
}
