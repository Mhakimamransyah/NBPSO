/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Fitur;
import java.text.DecimalFormat;

/**
 *
 * @author M.Hakim Amransyah
 */
public class NaiveBayes {
    
    private Data latih;
    private Data uji;
    private double prior_probability_kelas_parkinson;
    private double prior_probability_kelas_tidak_parkinson;
    
    public NaiveBayes(Data latih, Data uji){
        this.latih = latih;
        this.uji = uji;
        this.hitungPriorProbability();
    }
    
    public double getClassifierResult(double[] weight_vector){ 
        DecimalFormat df = new DecimalFormat("#.##");
        double[] cond_prob_1;
        double[] cond_prob_2;
        double true_classification = 0;
        int baris = 1;
        long waktu_eksekusi   = System.currentTimeMillis();
        for(Fitur fitur : this.uji.getFitur()){
           cond_prob_1 = this.hitungConditionalProbability(fitur, "Parkinson",weight_vector);
           cond_prob_2 = this.hitungConditionalProbability(fitur, "Tidak",weight_vector);
           true_classification = this.setAkurasi(true_classification,this.hitungPosteriorProbability(cond_prob_1, cond_prob_2), fitur.getLabel());
           baris++;
        }    
        return Double.parseDouble(df.format((true_classification/this.uji.getJumlahSeluruhData())*100).replaceAll(",","."));
    }
    
    private void hitungPriorProbability(){
        for(Fitur fitur : this.latih.getFitur()){
           if(fitur.getLabel().equalsIgnoreCase("Parkinson")){
               this.prior_probability_kelas_parkinson++;
           }else if(fitur.getLabel().equalsIgnoreCase("Tidak")){
               this.prior_probability_kelas_tidak_parkinson++;
           }          
        }      
        this.prior_probability_kelas_parkinson = (this.prior_probability_kelas_parkinson+1) / (this.latih.getJumlahSeluruhData()+2);
        this.prior_probability_kelas_tidak_parkinson = (this.prior_probability_kelas_tidak_parkinson+1) / (this.latih.getJumlahSeluruhData()+2);
    }
    
    private double[] hitungConditionalProbability(Fitur fitur_data_uji, String label, double[] weight_vector){
        double[] conditional_probability = new double[latih.getFitur().get(0).getNilaiFitur().size()];
        int kolom = 0;
        for(Double nilai_fitur_uji : fitur_data_uji.getNilaiFitur()){
            double same_label = 0;
            double iterator = 0;   
            for(Fitur fitur_latih : this.latih.getFitur()){
               if(nilai_fitur_uji.equals(fitur_latih.getNilaiFitur().get(kolom))){
                   iterator++;
                   if(fitur_latih.getLabel().equalsIgnoreCase(label)){
                       same_label++;
                   }
               }
           }
           if(weight_vector!=null){
              conditional_probability[kolom] = Math.pow(((same_label+1)/(iterator+(this.latih.countFiturValue(kolom)*1))),weight_vector[kolom]);     //NBPSO            
           }else{
              conditional_probability[kolom] = (same_label+1)/(iterator+(this.latih.countFiturValue(kolom)*1));     //NB Only          
           }           
           kolom++;
        }
        return conditional_probability;
    }
    
    private String hitungPosteriorProbability(double[] cond1, double[] cond2){
        String klasifikasi = null;
        double posterior_parkinson = 1;
        double posterior_tidak_parkinson = 1;
        
        for(int i=0;i<cond1.length;i++){
//            System.out.print(cond1[i]+" ");
            posterior_parkinson = posterior_parkinson * cond1[i];
        }
//        System.out.print("("+this.prior_probability_kelas_parkinson+")");
        posterior_parkinson = posterior_parkinson * this.prior_probability_kelas_parkinson;
//        System.out.print(" Posterior Parkinson: "+posterior_parkinson);
//        System.out.println("");
        for(int i=0;i<cond2.length;i++){
//            System.out.print(cond2[i]+" ");
            posterior_tidak_parkinson = posterior_tidak_parkinson * cond2[i];
        }
//        System.out.print("("+this.prior_probability_kelas_tidak_parkinson+")");
        posterior_tidak_parkinson = posterior_tidak_parkinson * this.prior_probability_kelas_tidak_parkinson;
//        System.out.print(" Posterior Tidak Parkinson : "+posterior_tidak_parkinson);
//        System.out.println("");
        if(posterior_parkinson > posterior_tidak_parkinson){
            klasifikasi = "Parkinson";
        }else if(posterior_parkinson < posterior_tidak_parkinson){
            klasifikasi = "Tidak";
        }
        
//        System.out.println("hasil : "+klasifikasi);
//        System.out.println("");
        
        return klasifikasi;
    }
    
    private double setAkurasi(double true_classification, String output, String aktual){
        if(output != null){
            if(output.equalsIgnoreCase(aktual)){
               true_classification++;
            }   
        }
        else{
           // tidak ada output naive bayes 
            System.out.println("GAGAL REK");
        }
        return true_classification;
    }
    
}
