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
public class Shortlist extends javax.swing.JFrame {

    HashMap<String, String> statusMap = new HashMap<>();

    Applicants ap;
    public Shortlist(Applicants ap) {
        initComponents();
        loadSelectorstable();
        loadPositions();
        loadstatus();
        
        this.ap =  ap;

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }

    public void loadSelectorstable() {
        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT applicant.applicant_name, applicant.applicant_email, "
                    + "applicant.mobile, jobrole.role, status.name, "
                    + "(SELECT COUNT(*) FROM `applicant` WHERE `status_id` IN ('36','37')) AS total_count, "
                    + "(SELECT COUNT(*) FROM `applicant` WHERE `status_id` = 36) AS pending_count "
                    + "FROM `applicant` "
                    + "INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id` "
                    + "INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` "
                    + "WHERE `applicant`.`status_id` IN ('36','37') ORDER BY applicant.apply_date ASC");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            int TotalCount = 0;
            int pendingCount = 0;

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("applicant_name"));
                vector.add(resultSet.getString("applicant_email"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("jobrole.role"));
                vector.add(resultSet.getString("status.name"));

                TotalCount = resultSet.getInt("total_count");
                pendingCount = resultSet.getInt("pending_count");

                model.addRow(vector);
            }

            jTable1.setModel(model);

            jTextField1.setText(String.valueOf(TotalCount)); // Total applicants
            jTextField2.setText(String.valueOf(pendingCount)); // Active applicants

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPositions() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `jobrole` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {

                vector.add(resultSet.getString("role"));

                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox2.setModel(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadstatus() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `status` WHERE `process_id` = '1' AND `process_type_id` = '1'; ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String statusName = resultSet.getString("name");
                String statusId = resultSet.getString("id");

                statusMap.put(statusName, statusId);

                if (!"1".equals(statusId) && !"2".equals(statusId) && !"3".equals(statusId)&& !"40".equals(statusId)&& !"41".equals(statusId)) {
                    vector.add(statusName);
                }

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadSelectors(String mobile) {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `applicant` INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id`"
                    + " INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` WHERE `mobile` LIKE '" + mobile + "%' AND "
                    + " `status`.`id` IN ('36', '37')ORDER BY applicant.apply_date ASC ");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("applicant_name"));
                vector.add(resultSet.getString("applicant_email"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("jobrole.role"));
                vector.add(resultSet.getString("status.name"));

                model.addRow(vector);

            }
            jTable1.setModel(model);

            jComboBox1.setSelectedIndex(0);
            jComboBox2.setSelectedIndex(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Shortlist");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(56, 78, 120));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Shortlist");

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Total Selectors");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("00");
        jTextField1.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Pending Invites");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 0, 0));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("00");
        jTextField2.setFocusable(false);

        jLabel7.setBackground(new java.awt.Color(255, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reset.png"))); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Search by Mobile");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Filter by Status");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTable1.setBackground(new java.awt.Color(238, 243, 253));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Applicant Name", "Email", "Mobile", "Desired Position", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Filter by Position ");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
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
        if (evt.getClickCount() == 2) {

            String email = String.valueOf(jTable1.getValueAt(row, 1));

            Interview_Data Interview_Data = new Interview_Data(this, rootPaneCheckingEnabled, this, email, statusMap);
            Interview_Data.setVisible(true);

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        String mobile = jTextField4.getText();

        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);

        loadSelectors(mobile);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

        String position = String.valueOf(jComboBox2.getSelectedItem());

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (position != null && !position.equals("Select")) {
                try {

                    jComboBox1.setSelectedIndex(0);

                    ResultSet resultSet = MySQL.executeSearch("SELECT applicant.applicant_name, applicant.applicant_email, applicant.mobile, "
                            + "jobrole.role, status.name "
                            + "FROM `applicant` "
                            + "INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id` "
                            + "INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` "
                            + "WHERE `jobrole`.`role` = '" + position + "' "
                            + " AND `status`.`id` IN ('36', '37') ORDER BY `applicant`.`apply_date` ASC");

                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);

                    while (resultSet.next()) {
                        Vector<String> vector = new Vector<>();
                        vector.add(resultSet.getString("applicant_name"));
                        vector.add(resultSet.getString("applicant_email"));
                        vector.add(resultSet.getString("mobile"));
                        vector.add(resultSet.getString("role"));
                        vector.add(resultSet.getString("name"));
                        model.addRow(vector);
                    }

                    jTable1.setModel(model);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                loadSelectorstable();
            }
        }


    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        String status = String.valueOf(jComboBox1.getSelectedItem());

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (status != null && !status.equals("Select")) {
                try {

                    jComboBox2.setSelectedIndex(0);

                    ResultSet resultSet = MySQL.executeSearch("SELECT applicant.applicant_name, applicant.applicant_email, applicant.mobile, "
                            + "jobrole.role, status.name "
                            + "FROM `applicant` "
                            + "INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id` "
                            + "INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` "
                            + "WHERE `status`.`name` = '" + status + "' "
                            + "ORDER BY `applicant`.`apply_date` ASC");

                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);

                    while (resultSet.next()) {
                        Vector<String> vector = new Vector<>();
                        vector.add(resultSet.getString("applicant_name"));
                        vector.add(resultSet.getString("applicant_email"));
                        vector.add(resultSet.getString("mobile"));
                        vector.add(resultSet.getString("role"));
                        vector.add(resultSet.getString("name"));
                        model.addRow(vector);
                    }

                    jTable1.setModel(model);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                loadSelectorstable();
            }
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        reset();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
        ap.loadApplicantstable();
        ap.setVisible(true);
        
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jTextField4.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        loadSelectorstable();
    }
}
