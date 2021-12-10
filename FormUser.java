/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjualan;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.event.KeyEvent;
/**
 *
 * @author USER
 */
public class FormUser extends javax.swing.JFrame {
    String user = "root";
    String pwd = "";
    String url="jdbc:mysql://localhost/penjualan";
    Boolean isi=true;
    public FormUser() {
        initComponents();
        setLocationRelativeTo(null);
    }
    void aktif(){
        txtNama.setEnabled(true);
        txtKode.setEnabled(true);
        txtPas.setEnabled(true);
        cbakses.setEnabled(true);
    }
     void nonaktif(){
        txtKode.setEnabled(false);
        txtNama.setEnabled(false);
        txtPas.setEnabled(false);
        cbakses.setEnabled(false);
        /*txtHarga.setEnabled(false);
        txtStok.setEnabled(false);*/
        btnKeluar.setEnabled(true);
        btnTambah.setEnabled(true);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
        btnRubah.setEnabled(false);
        btnBatal.setEnabled(false);
    }
     void bersih(){
        txtKode.setText("");
        txtNama.setText("");
        txtPas.setText("");
        cbakses.setSelectedIndex(0);
        /*txtHarga.setText("");
        txtStok.setText("");*/
    }
     /*void otomatis () {
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "select right (kd_user,3)+1 from barang";
            ResultSet rs=st.executeQuery(sql);
            if (rs.next ()) {
                rs.last();
                String kode = rs.getString(1);
                kode = kode.substring(0, kode.length() - 2);
                while (kode.length()<3) {
                    kode = "0"+kode;
                    txtKode.setText("BR"+kode);            
                }
            } else{
                txtKode.setText("BR001");
            }
        }catch(Exception e){
        }
    }*/
    void cari(){
        try{
        Connection conn = DriverManager.getConnection(url,user,pwd);
        Statement st = (Statement) conn.createStatement();
        
        ResultSet rs = st.executeQuery("Select * from user where kd_user='"+txtcari.getText()+"'");
        
        if (rs.next()){
                txtKode.setText(rs.getString("kd_user"));
                txtNama.setText(rs.getString("nm_user"));
                txtPas.setText(rs.getString("password"));
                cbakses.setSelectedItem(rs.getString("hak_akses"));
        }
        else
            JOptionPane.showMessageDialog(this, "data tidak ditemukan","Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNama.grabFocus();
        }   catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }
    void simpan(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "Insert into user Values('"+txtKode.getText()+"','"+txtNama.getText()+"','"+txtPas.getText()+"','"+cbakses.getSelectedItem()+"')";
            
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }
    void hapus(){
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            String sql = "delete from user where kd_user ='"+txtKode.getText()+"'";
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this,"Data berhasil dihapus","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }
    void update(){
      try{
        Connection conn = DriverManager.getConnection(url, user, pwd);
        Statement st = (Statement) conn.createStatement();
            
        String sql = "update user set nm_user = '"+txtNama.getText()+"',password='"+txtPas.getText()+"',hak_akses='"+cbakses.getSelectedItem()+"'"+"where kd_user='"+txtKode.getText()+"'";
          st.executeUpdate(sql);
                    
        JOptionPane.showMessageDialog(this,"Data berhasil diupdate","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtPas = new javax.swing.JTextField();
        cbakses = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnRubah = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("Kode User");

        jLabel2.setText("Nama User");

        jLabel3.setText("Password");

        jLabel4.setText("Hak akses");

        txtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeActionPerformed(evt);
            }
        });
        txtKode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeKeyPressed(evt);
            }
        });

        cbakses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnRubah.setText("Rubah");
        btnRubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRubahActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
        });

        btnCari.setText("cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });

        jLabel5.setText("Cari Kode");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(5, 5, 5))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbakses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPas, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(btnCari)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBatal))
                            .addComponent(btnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbakses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan)
                    .addComponent(btnRubah)
                    .addComponent(btnBatal)
                    .addComponent(btnHapus))
                .addGap(31, 31, 31)
                .addComponent(btnKeluar)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void formWindowActivated(java.awt.event.WindowEvent evt) {                                     
nonaktif();
bersih();// TODO add your handling code here:
    }                                    

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
    if(isi==true)
        simpan();
    else
        update();
    }                                         

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {                                          
        txtKode.setEnabled(true);
        txtNama.setEnabled(true);
        txtPas.setEnabled(true);
        cbakses.setEnabled(true);
        btnHapus.setEnabled(true);
        btnRubah.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnBatal.setEnabled(true);
        btnTambah.setEnabled(false);
        txtNama.grabFocus(); 
    }                                         

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
    JOptionPane.showConfirmDialog(this, "Yakin akan dihapus?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if((JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
        hapus();
        formWindowActivated(null);
        }    
    }                                        

    private void btnRubahActionPerformed(java.awt.event.ActionEvent evt) {                                         
isi=false;
aktif();
btnSimpan.setText("Update");
btnSimpan.setEnabled(true);
    }                                        

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
dispose();        // TODO add your handling code here:
    }                                         

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
    formWindowActivated(null);        
    }                                        

    private void txtKodeActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void txtKodeKeyPressed(java.awt.event.KeyEvent evt) {                                   

    }                                  

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        cari();
        btnCari.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
        btnBatal.setEnabled(true);
        btnKeluar.setEnabled(true);
        btnRubah.setEnabled(true);
    }                                       

    private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
        cari();
        btnCari.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
        btnBatal.setEnabled(true);
        btnKeluar.setEnabled(true);
        btnRubah.setEnabled(true);
    }
    }                                  

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {                                   
    if(evt.getKeyChar()==KeyEvent.VK_ENTER){
        cari();
        }
    }                                  

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnRubah;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbakses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtPas;
    private javax.swing.JTextField txtcari;
    // End of variables declaration                   
}
