/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Fitur;
import Model.Percobaan;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author M.Hakim Amransyah
 */
public class Dao {
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/nbpso";
    private final String USER = "root";
    private final String PASS = "";
    private Connection conn = null;
    
    public Dao(){
      this.bukaKoneksi();
    }
    
    private void bukaKoneksi() {
        boolean flag = false;
        try {
                Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
                System.out.println(e.getMessage());
                flag = true;
            }
        
        if (!flag) {
        try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    
    public void hapusPercobaan(int id_percobaan) throws SQLException{
        PreparedStatement ps;
        ps = conn.prepareCall("DELETE percobaan FROM percobaan WHERE Id_percobaan = ?");
        ps.setInt(1, id_percobaan);
        ps.executeUpdate();     
    }
    
    public void hapusSkenario(int id_skenario) throws SQLException{
        PreparedStatement ps;
        ps = conn.prepareCall("DELETE skenario,percobaan FROM skenario LEFT JOIN percobaan ON skenario.Id_skenario = percobaan.Id_skenario WHERE skenario.Id_skenario = ?");
        ps.setInt(1, id_skenario);
        ps.executeUpdate();
    }
    
    public ArrayList<Skenario> getAllSkenarioData() throws SQLException{
        ResultSet rs;     
        PreparedStatement ps;
        ArrayList<Skenario> daftar_skenario = new ArrayList<Skenario>();
        ps = conn.prepareStatement("SELECT * FROM skenario ORDER BY Id_skenario DESC");
        rs = ps.executeQuery();
        
        Skenario skenario;
        while(rs.next()){
            skenario = new Skenario();
            skenario.setId_skenario(rs.getInt("Id_skenario"));
            skenario.setNama(rs.getString("Nama_skenario"));
            skenario.setPartisi(rs.getString("Partisi_data_latih"),rs.getString("Partisi_data_uji"));
            daftar_skenario.add(skenario);
        }
        for(Skenario list : daftar_skenario){
             ps = conn.prepareStatement("SELECT * FROM percobaan WHERE Id_skenario = ? ORDER BY Id_percobaan DESC");
             ps.setInt(1, list.getId_skenario());
             rs = ps.executeQuery();
             
             Percobaan percobaan;
             while(rs.next()){
                 percobaan = new Percobaan();
                 percobaan.setId_percobaan(rs.getInt("Id_percobaan"));
                 percobaan.setTipe(rs.getString("Tipe"));
                 percobaan.setAkurasi(rs.getDouble("Akurasi"));
                 percobaan.setWaktu(rs.getDouble("Waktu"));
                 percobaan.setBobot(rs.getString("Bobot"));
                 percobaan.setGenerasi(rs.getInt("Generasi"));
                 percobaan.setPopulasi(rs.getInt("Populasi"));
                 percobaan.setC1(rs.getDouble("c1"));
                 percobaan.setC2(rs.getDouble("c2"));
                 list.tambahPercobaan(percobaan);
             }
        }
        
        return daftar_skenario;
    }
    
    public void tambahSkenario(String nama, String partisi_data_latih, String partisi_data_uji){
        try {
            ResultSet rs;
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO skenario(Nama_skenario, Partisi_data_latih, Partisi_data_uji) VALUES(?,?,?)");
            ps.setString(1, nama);
            ps.setString(2, partisi_data_latih);
            ps.setString(3, partisi_data_uji);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Pastikan nama skenario berbeda","Aduuuuh Gagal tambah skenario!!",JOptionPane.ERROR_MESSAGE);   
        }
    }
    
    public int tambahPercobaan(int id_skenario, Percobaan percobaan) throws SQLException{
        int last_inserted_id=0;
        PreparedStatement ps;
        ps = conn.prepareStatement("CALL tambahPercobaan(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, percobaan.getTipe());
        ps.setDouble(2, percobaan.getAkurasi());
        ps.setDouble(3, percobaan.getWaktu());
        ps.setInt(4, percobaan.getGenerasi());
        ps.setInt(5, percobaan.getPopulasi());
        ps.setDouble(6, percobaan.getC1());
        ps.setDouble(7,percobaan.getC2());
        ps.setString(8, percobaan.getBobot());
        ps.setInt(9, id_skenario);
        ps.executeUpdate();
        
        ResultSet rs;
        ps = conn.prepareStatement("SELECT MAX(Id_percobaan) FROM `percobaan`");
        rs = ps.executeQuery();
        while(rs.next()){
            last_inserted_id = rs.getInt("MAX(Id_percobaan)");
        }
        
        return last_inserted_id;
    }
    
    public Data getPartisiData(int awal, int akhir) throws SQLException{
        ResultSet rs;     
        PreparedStatement ps;
        ps = conn.prepareStatement("SELECT * FROM `data_parkinson` WHERE Nomor_data BETWEEN ? AND ?");
        ps.setInt(1, awal);
        ps.setInt(2, akhir);
        rs = ps.executeQuery(); 
        
        Data data = new Data();
        Fitur fitur;
        while(rs.next()){
            fitur = new Fitur();
            fitur.setNomor(rs.getInt("Nomor_data"));
            fitur.setSubjek_id(rs.getInt("subjek_id"));
            for(int i=1;i<27;i++){
                fitur.tambahNilai(rs.getDouble("fitur"+i));
            }
            fitur.setLabel(rs.getInt("class"));
            data.tambahFitur(fitur);
        }
        
        return data;
    }
   
   
}
