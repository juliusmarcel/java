/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjualan;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;


/**
 *
 * @author USER
 */
public class FormBarang1 extends javax.swing.JFrame {

    /**
     * Creates new form FormBarang
     */
    String user = "root";
    String pwd = "";
    String url="jdbc:mysql://localhost/penjualan";
    Boolean isi=true;
    public FormBarang1() {
        initComponents();
        setLocationRelativeTo(null);
    }
    void aktif(){
        txtNama.setEnabled(true);
        txtHarga.setEnabled(true);
        txtStok.setEnabled(true);
    }
    void nonaktif(){
        txtKode.setEnabled(false);
        txtNama.setEnabled(false);
        txtHarga.setEnabled(false);
        txtStok.setEnabled(false);
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
        txtHarga.setText("");
        txtStok.setText("");
    }
    void otomatis () {
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "select right (kd_brg,3)+1 from barang";
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
    }
    void cari(){
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from barang where kd_brg='"+txtCari.getText()+"'");
            if (rs.next()){
                txtKode.setText(rs.getString("kd_brg"));
                txtNama.setText(rs.getString("nm_brg"));
                txtHarga.setText(rs.getString("harga"));
                txtStok.setText(rs.getString("stock"));
            }
            else
                JOptionPane.showMessageDialog(this,"Data tidak ditemukan","info",JOptionPane.INFORMATION_MESSAGE);
                txtCari.setText("");
            } catch(SQLException e){
                System.out.println("Koneksi gagal"+e.toString());
            }
    }
    void simpan(){
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "insert into barang values('"+txtKode.getText()+"','"+txtNama.getText()+"','"+txtHarga.getText()+"','"+txtStok.getText()+"')";
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this,"Data berhasil disimpan","info",JOptionPane.INFORMATION_MESSAGE);
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
            String sql = "delete from barang where kd_brg ='"+txtKode.getText()+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this,"Data berhasil dihapus","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }
        void update(){
        try{
           Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            String sql = "update barang set nm_brg ='"+txtNama.getText()+"' ,harga='"+txtHarga.getText()+"' ,stock='"+txtStok.getText()+"'"+"where kd_brg='"+txtKode.getText()+"'";
            st.executeUpdate(sql);
             JOptionPane.showMessageDialog(this,"Data berhasil diupdate","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e){
            System.out.println("koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }
    private Object[][]getData(){
        Object[][]data1=null;
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from barang");
            rs. last();
            int rowCount=rs.getRow();
            rs.beforeFirst();
            data1=new Object[rowCount][4];
            int no=1;
            while(rs.next()){
                no=no+1;
                data1[no][0]=rs.getString("kd_brg");
                data1[no][1]=rs.getString("nm_brg");
                data1[no][2]=rs.getString("harga");
                data1[no][3]=rs.getString("stock");
            }
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        return data1;
    }
    void tampil(){
        String[] columnNames = {"Kode Barang","Nama Barang","Harga","stock"};
        JTable table=new JTable (getData(), columnNames);
        table.setEnabled(false);
        jScrollPane1.setViewportView(table);
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
        jLabel5 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnRubah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("Pencarian Kode Barang");

        jLabel2.setText("Kode Barang");

        jLabel3.setText("Nama Barang");

        jLabel4.setText("Harga");

        jLabel5.setText("Stok");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        txtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeActionPerformed(evt);
            }
        });

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaKeyPressed(evt);
            }
        });

        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHargaKeyPressed(evt);
            }
        });

        txtStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStokActionPerformed(evt);
            }
        });
        txtStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtStokKeyPressed(evt);
            }
        });

        btnCari.setText("Cari");
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

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
        btnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSimpanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnSimpanKeyReleased(evt);
            }
        });

        btnRubah.setText("Rubah");
        btnRubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRubahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        btnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBatalKeyPressed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(45, 45, 45)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtStok)
                                        .addComponent(txtHarga)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(btnCari))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRubah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnHapus)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal))
                            .addComponent(btnKeluar))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan)
                    .addComponent(btnRubah)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKeluar)
                .addGap(0, 112, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {                                        
// TODO add your handling code here:
    }                                       

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {                                          
    otomatis();
    isi= false;
       aktif();
        txtNama.setEnabled(true);
        txtHarga.setEnabled(true);
        txtStok.setEnabled(true);
        btnCari.setEnabled(true);
        btnHapus.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnBatal.setEnabled(true);
        btnTambah.setEnabled(false);
        otomatis();
        txtNama.grabFocus();
    }                                         

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
    if(isi==true)
        simpan();
    else
        update();
    }                                         

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
    formWindowActivated(null);
    }                                        

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        dispose();       
    }                                         

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int ok= JOptionPane.showConfirmDialog(this, "Yakin akan dihapus?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(ok==0){
            hapus();
        formWindowActivated(null);
        }
    }                                        

    private void btnRubahActionPerformed(java.awt.event.ActionEvent evt) {                                         
        isi=false;
        aktif();
        btnRubah.setEnabled(false);
        btnTambah.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnBatal.setEnabled(true);
        //update(); 
    }                                        

    private void formWindowActivated(java.awt.event.WindowEvent evt) {                                     
        nonaktif();
        bersih();
        tampil();
    }                                    

    private void txtKodeActionPerformed(java.awt.event.ActionEvent evt) {                                        
//if (evt.getKeyChar()== KeyEvent.VK_ENTER)
    //cari();

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

    private void txtNamaKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if (evt.getKeyChar() == KeyEvent.VK_ENTER){
            txtHarga.requestFocusInWindow();
        }        
    }                                  

    private void txtHargaKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if (evt.getKeyChar() == KeyEvent.VK_ENTER){
            txtStok.requestFocusInWindow();
        }      
    }                                   

    private void txtStokKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if (evt.getKeyChar() == KeyEvent.VK_ENTER){
            btnSimpan.requestFocusInWindow();
        }         
    }                                  

    private void btnSimpanKeyPressed(java.awt.event.KeyEvent evt) {                                     
       
    }                                    

    private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if(evt.getKeyChar()==KeyEvent.VK_ENTER){
            cari();
            btnSimpan.setEnabled(true);
            btnHapus.setEnabled(true);
            btnTambah.setEnabled(false);
            btnBatal.setEnabled(true);
            btnKeluar.setEnabled(true);
            btnRubah.setEnabled(true);
    }                                  
    }
    private void btnBatalKeyPressed(java.awt.event.KeyEvent evt) {                                    
        // TODO add your handling code here:
    }                                   

    private void txtStokActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
    
    }                                       

    private void btnSimpanKeyReleased(java.awt.event.KeyEvent evt) {                                      
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(FormBarang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBarang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBarang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBarang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBarang1().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    // End of variables declaration                   
}
