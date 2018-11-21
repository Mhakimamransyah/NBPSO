/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Fitur;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
   
   
    public Data getDataParkinson() throws SQLException{
        ResultSet rs;     
        PreparedStatement ps;
        ps = conn.prepareStatement("SELECT * FROM `data_parkinson`");
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
