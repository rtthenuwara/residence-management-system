/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.awt.event.ItemEvent;
import model.MySQL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Rasindu Thenuwara
 */
public class Interview_invitations extends javax.swing.JFrame {

    HashMap<String, String> statusMap = new HashMap<>();

    String applicantEmail;

    Applicants ap;

    public Interview_invitations() {
        initComponents();
        loadInvitationstable();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }

    public Interview_invitations(Applicants ap) {

        this.ap = ap;
        initComponents();
        loadInvitationstable();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }

    public void loadInvitationstable() {

        try {
            // Fetch all interview invitations
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `interview_invitations` WHERE `status_id` = '37'");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String applicantEmail = resultSet.getString("applicant_email");

                // Fetch applicant details for the current email
                ResultSet applicantDetails = MySQL.executeSearch(
                        "SELECT * FROM `applicant` WHERE `applicant_email` = '" + applicantEmail + "'");

                if (applicantDetails.next()) { // Ensure there's a matching applicant record
                    Vector<String> vector = new Vector<>();
                    vector.add(applicantDetails.getString("applicant_name"));
                    vector.add(resultSet.getString("applicant_email"));
                    vector.add(applicantDetails.getString("mobile"));
                    vector.add(resultSet.getString("place"));
                    vector.add(resultSet.getString("date"));
                    vector.add(resultSet.getString("time"));
                    // Process special note (remove "Special Note:" prefix if it exists)
                    String specialNote = resultSet.getString("special_note");
                    if (specialNote != null && specialNote.startsWith("Special Note:")) {
                        specialNote = specialNote.replace("Special Note:", "").trim();
                    }
                    vector.add(specialNote); // Add modified special note

                    model.addRow(vector); // Add row to table model
                } else {
                    System.out.println("No applicant details found for email: " + applicantEmail);
                }
            }

            jTable1.setModel(model); // Refresh table model
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading selectors table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadInvitations(String mobile, String email) {
        try {
            // Build the SQL query based on provided search criteria
            StringBuilder query = new StringBuilder("SELECT * FROM `interview_invitations`");

            // Add conditions to query based on mobile or email
            if (mobile != null && !mobile.isEmpty()) {
                query.append(" WHERE `applicant_email` IN (SELECT `applicant_email` FROM `applicant` WHERE `mobile` LIKE '" + mobile + "%')");
            } else if (email != null && !email.isEmpty()) {
                query.append(" WHERE `applicant_email` = '" + email + "'");
            }

            // Execute the search query
            ResultSet resultSet = MySQL.executeSearch(query.toString());

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                String applicantEmail = resultSet.getString("applicant_email");

                // Fetch applicant details for the current email
                ResultSet applicantDetails = MySQL.executeSearch(
                        "SELECT * FROM `applicant` WHERE `applicant_email` = '" + applicantEmail + "'");

                if (applicantDetails.next()) { // Ensure there's a matching applicant record
                    Vector<String> vector = new Vector<>();
                    vector.add(applicantDetails.getString("applicant_name"));
                    vector.add(resultSet.getString("applicant_email"));
                    vector.add(applicantDetails.getString("mobile"));
                    vector.add(resultSet.getString("place"));
                    vector.add(resultSet.getString("date"));
                    vector.add(resultSet.getString("time"));
                    vector.add(resultSet.getString("special_note"));

                    model.addRow(vector); // Add row to table model
                }
            }

            jTable1.setModel(model); // Refresh table model
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading selectors table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Interview Invitations");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(56, 78, 120));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Interview Invitations");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(238, 243, 253));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Search by Mobile");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Search by Email ");

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(269, 269, 269))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setBackground(new java.awt.Color(238, 243, 253));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Applicant Name", "Email", "Mobile", "place", "Date", "Time", "Special Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a applicant", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String email = String.valueOf(jTable1.getValueAt(row, 1));

            //Applicant_Data Applicant_Data = new Applicant_Data(this, rootPaneCheckingEnabled, this, email, statusMap);
            //Applicant_Data.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        String mobile = jTextField4.getText();
        String email = null;

        if (!mobile.isEmpty()) {
            jTextField3.setText("");
        }

        loadInvitations(mobile, email);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased

        String email = jTextField3.getText();
        String mobile = null;

        if (!email.isEmpty()) {
            jTextField4.setText("");
        }

        loadInvitations(mobile, email);

        //loadInvitations()
    }//GEN-LAST:event_jTextField3KeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
        ap.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
