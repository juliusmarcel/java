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
            
            String sql= "update barang set nm_brg ='"+txtNama.getText()+"',harga='"+txtHarga.getText()+"',stock'"+txtStok.getText()+"'"+"where kd_brg='"+txtKode.getText()+"'";
            
            st.executeUpdate(sql);
            
             JOptionPane.showMessageDialog(this,"Data berhasil diupload","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
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
simpan();
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
if (evt.getKeyChar()== KeyEvent.VK_ENTER){
    if (isi == true)
        simpan();

    else
        update();
}
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
