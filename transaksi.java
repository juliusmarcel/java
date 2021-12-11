/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjualan;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JTable;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author USER
 */
public class transaksi extends javax.swing.JFrame {
    String user="root";
    String pwd= "";
    String url= "jdbc:mysql://localhost/penjualan";
    double total= 0;
    int stokAwal;
    //intstokBaru= 0;
    /**
     * Creates new form transaksi
     */
    public transaksi() {
        initComponents();
        setLocationRelativeTo(null);
    }
    void aktif(){
        cbkode.setEnabled(true);
        txtqty.setEnabled(true);
        txtbayar.setEnabled(true);
    }
    void bersih(){
        txtnotrans.setText("");
        txttgl.setText("");
        txtnama.setText("");
        txtharga.setText("");
        txtqty.setText("0");
        txtsub.setText("0");
        txttotal.setText("");
        txtbayar.setText("0");
        txtkembali.setText("0");
        cbkode.setSelectedIndex(0);
    }
    void nonaktif(){
        txtnotrans.setEnabled(false);
        txttgl.setEnabled(false);
        txtnama.setEnabled(false);
        txtharga.setEnabled(false);
        txtsub.setEnabled(false);
        txttotal.setEnabled(false);
        txtkembali.setEnabled(false);
        cbkode.setEnabled(false);
        txtqty.setEnabled(false);
        txtbayar.setEnabled(false);
    }
    void isiKode(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            String sql = "Select * from barang";
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                cbkode.addItem(rs.getString("kd_brg"));
            }
        }catch (Exception e){
            System.out.println("Koneksi Gagal" +e.toString());
        }
    }
    void otomatis(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st =(Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "Select right(no_trans,3)+1 from transaksi";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                rs.last();
                String no = rs.getString(1);
                no = no.substring(0, no.length() - 2);
                while(no.length()<3){
                    no= "0"+no;
                    txtnotrans.setText("TR"+no);
                }
            }
                else{
                        txtnotrans.setText("TR001");
                    }
            }catch(Exception e){
                    }
        }
    void simpan(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st= (Statement) conn.createStatement();
            
            String sql = "insert into transaksi values ('"+txtnotrans.getText()+"','"+txttgl.getText()+"','"+txttotal.getText()+"')";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }
    private Object[][]getData(){
        Object[][]data1=null;
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from sementara");
            rs.last();
            int rowCount=rs.getRow();
            rs.beforeFirst();
            data1=new Object[rowCount][5];
            int no=-1;
            while(rs.next()){
                no=no+1;
                data1[no][0]=rs.getString("kd_brg");
                data1[no][1]=rs.getString("nm_brg");
                data1[no][2]=rs.getString("harga");
                data1[no][3]=rs.getString("qty");
                data1[no][4]=rs.getString("subtotal");
            }
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        return data1;
    }
    void masukGrid(){
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            ResultSet rs = st.executeQuery("SELECT * FROM sementara WHERE kd_brg='"+ cbkode.getSelectedItem() +"'");
            if(rs.next()) {
                String id = rs.getString("kd_brg");
                
                if(id.equals(cbkode.getSelectedItem())) {
                    String sql = "UPDATE sementara SET qty = qty + '"+ Integer.parseInt(txtqty.getText()) +"', subtotal = subtotal + '"+ Integer.parseInt(txtsub.getText()) +"' WHERE kd_brg='"+ cbkode.getSelectedItem() +"'";
                    st.executeUpdate(sql);
                } else {
                    String sql = "INSERT INTO sementara VALUES ('"+ cbkode.getSelectedItem() +"','"+ txtnama.getText() +"','"+ txtharga.getText() +"','"+ txtqty.getText() +"','"+ txtsub.getText() +"')";
                    st.execute(sql);
                }
            } 
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }
    void tampilGrid(){
        String[] columnNames = {"Kode barang", "Nama Barang", "Harga", "QTY", "Sub Total"};
        JTable table=new JTable (getData(), columnNames);
        table.setEnabled(false);
        jScrollPane1.setViewportView(table);
    }
    void hapusGrid(){
        try{
            Connection conn = DriverManager.getConnection(url, user, pwd);
            Statement st = (Statement) conn.createStatement();
            String sql = "delete from sementara";
            st.executeUpdate(sql);
        }
        catch (SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }
    void kurangStok(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sqll = "SELECT * FROM barang WHERE kd_brg='"+ cbkode.getSelectedItem() +"'";
            ResultSet rs = st.executeQuery(sqll);
            rs.next();
            String sql="update barang set stok = stok-'"+txtqty.getText()+"'where kd_brg= '"+cbkode.getSelectedItem()+"'";
            
            st.executeUpdate(sql);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
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
        txtnotrans = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txttgl = new javax.swing.JTextField();
        txtnama = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        cbkode = new javax.swing.JComboBox<>();
        txtqty = new javax.swing.JTextField();
        txtsub = new javax.swing.JTextField();
        btnbeli = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnadd = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        txtbayar = new javax.swing.JTextField();
        txtkembali = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("No Transaksi");

        txtnotrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnotransActionPerformed(evt);
            }
        });

        jLabel2.setText("Kode Barang");

        jLabel3.setText("Nama Barang");

        jLabel4.setText("Harga");

        jLabel5.setText("Tanggal");

        jLabel6.setText("QTY");

        jLabel7.setText("Sub Total");

        cbkode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbkode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkodeActionPerformed(evt);
            }
        });

        txtqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtqtyKeyReleased(evt);
            }
        });

        btnbeli.setText("Beli");
        btnbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeliActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnadd.setText("Tambah");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnbatal.setText("Batal");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        jLabel8.setText("Total");

        jLabel9.setText("Uang bayar");

        jLabel10.setText("Kembali");

        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbayarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtnotrans, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(0, 48, Short.MAX_VALUE))
                                    .addComponent(txtnama))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(2, 2, 2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(59, 59, 59))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(btnbeli)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtsub, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(25, 25, 25))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txttgl)
                                .addGap(67, 67, 67))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnadd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnsimpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnbatal))
                                    .addComponent(btnkeluar))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtbayar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txttotal)
                                    .addComponent(txtkembali))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtnotrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txttgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(4, 4, 4))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnbeli)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtsub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadd)
                    .addComponent(btnsimpan)
                    .addComponent(btnbatal)
                    .addComponent(jLabel8)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnkeluar)
                    .addComponent(jLabel9)
                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtkembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void formWindowActivated(java.awt.event.WindowEvent evt) {                                     
        cbkode.removeAllItems();
        tampilGrid();
        hapusGrid();
        nonaktif();
        isiKode();
        bersih();
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        txttgl.setText(sdf.format(cal.getTime()));
        btnadd.setEnabled(true);
        btnsimpan.setEnabled(false);
        btnbeli.setEnabled(false);
    }                                    

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {                                       
        aktif();
        otomatis();
        hapusGrid();
        cbkode.grabFocus();
        btnbeli.setEnabled(true);
        btnsimpan.setEnabled(true);
        btnadd.setEnabled(false);
        btnbatal.setEnabled(true);
    }                                      

    private void txtbayarKeyPressed(java.awt.event.KeyEvent evt) {                                    
    if(evt.getKeyCode()==KeyEvent.VK_ENTER);
    total=Double.parseDouble(txttotal.getText());
    double bayar = Double.parseDouble(txtbayar.getText());
    double kembali;
    kembali=bayar-total;
    txtkembali.setText(Double.toString(kembali));
    }                                   

    private void btnbeliActionPerformed(java.awt.event.ActionEvent evt) {                                        
     int qty=Integer.parseInt(txtqty.getText());
    int harga=Integer.parseInt(txtharga.getText());
    double subtotal;
    subtotal=harga*qty;
    txtsub.setText(Double.toString(subtotal));
    total=subtotal+total;
    txttotal.setText(Double.toString(total));
    masukGrid();
    tampilGrid();
    kurangStok();
    }                                       

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        simpan();
        hapusGrid();
        tampilGrid();
        btnadd.setEnabled(true);
        btnsimpan.setEnabled(false);
    }                                         

    private void cbkodeActionPerformed(java.awt.event.ActionEvent evt) {                                       
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            String sql = "Select * from barang where kd_brg='"+cbkode.getSelectedItem()+"'";
            ResultSet rs=st.executeQuery(sql);
            
            if(rs.next()){
                txtnama.setText(rs.getString("nm_brg"));
                txtharga.setText(String.format("%.0f",rs.getDouble("harga")));
                txtqty.setText("");
                txtqty.grabFocus();
            }
        }catch(Exception e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
    }                                      

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
        bersih();
        hapusGrid();
        nonaktif();
        btnkeluar.setEnabled(true);
        btnadd.setEnabled(true);
        btnbatal.setEnabled(false);
        btnsimpan.setEnabled(false);
    }                                        

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
   dispose();
    }                                         

    private void txtnotransActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void txtqtyKeyReleased(java.awt.event.KeyEvent evt) {                                   
        int qty = Integer.parseInt(txtqty.getText());
        int harga = Integer.parseInt(txtharga.getText());
        double subtotal;
        subtotal = harga * qty;
        txtsub.setText(String.format("%.0f", subtotal));
    }                                  

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {                                     
        total = Double.parseDouble(txttotal.getText());
        double bayar = Double.parseDouble(txtbayar.getText());
        double kembali;
        kembali = bayar - total;
        txtkembali.setText(String.format("%.0f", kembali));
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
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btnbeli;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JComboBox<String> cbkode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtkembali;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnotrans;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtsub;
    private javax.swing.JTextField txttgl;
    private javax.swing.JTextField txttotal;
    // End of variables declaration                   
}
