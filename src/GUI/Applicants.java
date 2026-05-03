/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
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
public class Applicants extends javax.swing.JFrame {

    private String inviteStatus;
    private Employee_Dashboard ed;

    HashMap<String, String> statusMap = new HashMap<>();

    public Applicants() {
        initComponents();
        loadApplicantstable();
        loadPositions();
        loadstatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }
    
    public Applicants(Employee_Dashboard ed) {
        
        this.ed = ed;
        initComponents();
        loadApplicantstable();
        loadPositions();
        loadstatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }

    public void loadApplicantstable() {
        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT applicant.applicant_name, applicant.applicant_email, "
                    + "applicant.mobile, jobrole.role, status.name, "
                    + "(SELECT COUNT(*) FROM `applicant` WHERE `status_id` = 1) AS pending_count, "
                    + "(SELECT COUNT(*) FROM `applicant` WHERE `status_id`IN ('1','3','36','37','40','41')) AS total_count "
                    + "FROM `applicant` "
                    + "INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id` "
                    + "INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` "
                    + "WHERE `applicant`.`status_id` IN ('1','2','3','36','37','40','41') ORDER BY applicant.apply_date ASC");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            int pendingCount = 0;
            int totalCount = 0;

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("applicant_name"));
                vector.add(resultSet.getString("applicant_email"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("jobrole.role"));
                vector.add(resultSet.getString("status.name"));

                pendingCount = resultSet.getInt("pending_count");
                totalCount = resultSet.getInt("total_count");

                model.addRow(vector);
            }

            jTable1.setModel(model);

            jTextField1.setText(String.valueOf(totalCount)); // Total applicants
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

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `status` WHERE `process_id` = '1' AND `process_type_id` = '1' AND `id` NOT IN (42, 44); ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String statusName = resultSet.getString("name");
                String statusId = resultSet.getString("id");

                statusMap.put(statusName, statusId);

                vector.add(statusName);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadApplicants(String mobile) {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `applicant` INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id`"
                    + " INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` WHERE `mobile` LIKE '" + mobile + "%' AND "
                    + " `status`.`id` IN ('1','2','3','36','37','40','41') ORDER BY applicant.apply_date ASC ");

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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();

        jMenuItem1.setText("Registration");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setTitle("Applicants");

        jPanel1.setBackground(new java.awt.Color(56, 78, 120));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Applicants");

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
        jLabel2.setText("Total Applicants");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("00");
        jTextField1.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Pending Applicants");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 0, 0));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("00");
        jTextField2.setFocusable(false);

        jButton1.setBackground(new java.awt.Color(56, 78, 120));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("View Shortlist");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jButton2.setBackground(new java.awt.Color(98, 134, 204));
        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("View Invitations");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
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

        int row = jTable1.getSelectedRow(); // Get the selected row index
        String email = String.valueOf(jTable1.getValueAt(row, 1));
        try {

            ResultSet invitations = MySQL.executeSearch(" SELECT * FROM `interview_invitations` INNER JOIN"
                    + " `status` ON `interview_invitations`.`status_id` = `status`.`id`"
                    + " WHERE `status_id` = '37' AND `applicant_email` = '" + email + "' ");

            while (invitations.next()) {
                inviteStatus = invitations.getString("status.name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (evt.getClickCount() == 2) {

            if (row != -1) { // Ensure a valid row is selected
                String status = String.valueOf(jTable1.getValueAt(row, 4));

                if (status.equals("Pending") || status.equals("Pending Invite") || status.equals("Rejected")) {

                    // Open Applicant_Data GUI
                    Applicant_Data applicantData = new Applicant_Data(this, rootPaneCheckingEnabled, this, email, statusMap);
                    applicantData.setVisible(true); // Show the GUI

                } else if (status.equals("Selected") || status.equals("Pending Confirmed") || status.equals("Reject Confirmed") || status.equals("Sent Invite")) {

                    // Open Selection_Data GUI
                    Selection_Data selection = new Selection_Data(this, rootPaneCheckingEnabled, this, email);
                    selection.setVisible(true);

                }

            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        String mobile = jTextField4.getText();

        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);

        loadApplicants(mobile);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

        String position = String.valueOf(jComboBox2.getSelectedItem());

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (position != null && !position.equals("Select")) {
                try {

                    jComboBox1.setSelectedIndex(0);
                    jTextField4.setText("");

                    ResultSet resultSet = MySQL.executeSearch("SELECT applicant.applicant_name, applicant.applicant_email, applicant.mobile, "
                            + "jobrole.role, status.name "
                            + "FROM `applicant` "
                            + "INNER JOIN `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id` "
                            + "INNER JOIN `status` ON `applicant`.`status_id` = `status`.`id` "
                            + "WHERE `jobrole`.`role` = '" + position + "' "
                            + "AND `status`.`id` IN ('1','2','3','36','37','40','41') ORDER BY `applicant`.`apply_date` ASC");

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
                loadApplicantstable();
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
                loadApplicantstable();
            }
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Shortlist shortlist = new Shortlist(this);
        shortlist.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        reset();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        int row = jTable1.getSelectedRow();
        String applicantEmail = String.valueOf(jTable1.getValueAt(row, 1));
        String applicantMobile = String.valueOf(jTable1.getValueAt(row, 2));

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `interview_invitations` WHERE `special_note` = 'Offer Letter' "
                    + " AND `applicant_email` = '" + applicantEmail + "' ");

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(this, "This Applicant's offer letter not sent", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                ResultSet resultSet2 = MySQL.executeSearch(" SELECT * FROM `employee` WHERE `applicant_email` = '" + applicantEmail + "' ");

                if (resultSet2.next()) {

                    JOptionPane.showMessageDialog(this, "This Applicant already registered", "Warning", JOptionPane.WARNING_MESSAGE);

                } else {

                    this.dispose();

                    Employee_Registration employeeRegistration = new Employee_Registration(ed);
                    employeeRegistration.setVisible(true);

                    employeeRegistration.getjTextField4().setText(applicantEmail);
                    employeeRegistration.getjTextField4().setEditable(false);
                    employeeRegistration.getjTextField4().setFocusable(false);

                    employeeRegistration.getjTextField7().setText(applicantMobile);
                    employeeRegistration.getjTextField7().setEditable(false);
                    employeeRegistration.getjTextField7().setFocusable(false);

                    employeeRegistration.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {

                            setVisible(true);
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased

        int row = jTable1.getSelectedRow();

        if (row == -1) {
            if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click detected
                JOptionPane.showMessageDialog(this, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            String status = String.valueOf(jTable1.getValueAt(row, 4));
            if (status.equals("Selected")) {

                if (status.equals("Selected")) {
                    if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click detected
                        jTable1.setRowSelectionInterval(row, row); // Select the row under the cursor
                        jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY()); // Show the popup menu
                    }
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        Interview_invitations invitations = new Interview_invitations(this);
        invitations.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
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
        loadApplicantstable();
    }
}
