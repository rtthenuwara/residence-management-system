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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Rasindu Thenuwara
 */
public class Employees extends javax.swing.JFrame {

    private String inviteStatus;

    HashMap<String, String> statusMap = new HashMap<>();

    Employee_Registration er;

    Employee_Dashboard ed;

    public Employees() {
        initComponents();

        this.ed = ed;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadEmployeestable(null);
        loadStatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }

    public Employees(Employee_Dashboard ed) {
        initComponents();

        this.ed = ed;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadEmployeestable(null);
        loadStatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
    }

    public void loadEmployeestable(String searchKey) {
        try {

            if (searchKey == null || searchKey.trim().isEmpty() || searchKey.equals("Select")) {
                searchKey = "";
            }

            String query = "SELECT employee.id, employee.full_name, employee.name_with_initials, "
                    + "employee.display_name, gender.name AS gender_name, employee.date_of_birth, "
                    + "employee.applicant_email, employee.employee_email, employee.mobile1, employee.mobile2, "
                    + "employee.old_nic, employee.new_nic, status.name AS status_name, "
                    + "(SELECT COUNT(*) FROM employee "
                    + "INNER JOIN gender ON employee.gender_id = gender.id "
                    + "INNER JOIN status ON employee.status_id = status.id "
                    + "WHERE (employee.id = '" + searchKey + "' "
                    + "OR employee.employee_email LIKE '%" + searchKey + "%' "
                    + "OR employee.old_nic LIKE '%" + searchKey + "%' "
                    + "OR employee.new_nic LIKE '%" + searchKey + "%' "
                    + "OR employee.mobile1 LIKE '%" + searchKey + "%' "
                    + "OR employee.mobile2 LIKE '%" + searchKey + "%' "
                    + "OR status.name = '" + searchKey + "')) AS total_count "
                    + "FROM employee "
                    + "INNER JOIN gender ON employee.gender_id = gender.id "
                    + "INNER JOIN status ON employee.status_id = status.id "
                    + "WHERE (employee.id = '" + searchKey + "' "
                    + "OR employee.employee_email LIKE '%" + searchKey + "%' "
                    + "OR employee.old_nic LIKE '%" + searchKey + "%' "
                    + "OR employee.new_nic LIKE '%" + searchKey + "%' "
                    + "OR employee.mobile1 LIKE '%" + searchKey + "%' "
                    + "OR employee.mobile2 LIKE '%" + searchKey + "%' "
                    + "OR status.name = '" + searchKey + "') "
                    + "ORDER BY status.name ASC, employee.id ASC";

            ResultSet resultSet = MySQL.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            int totalCount = 0;

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("full_name"));
                vector.add(resultSet.getString("name_with_initials"));
                vector.add(resultSet.getString("display_name"));
                vector.add(resultSet.getString("gender_name"));
                vector.add(resultSet.getString("date_of_birth"));
                vector.add(resultSet.getString("applicant_email"));
                vector.add(resultSet.getString("employee_email"));
                vector.add(resultSet.getString("mobile1"));

                String mobile2 = resultSet.getString("mobile2");

                if (mobile2 != null && mobile2.startsWith("empty")) {
                    mobile2 = "empty";
                }

                vector.add(mobile2);

                vector.add(resultSet.getString("old_nic"));
                vector.add(resultSet.getString("new_nic"));
                vector.add(resultSet.getString("status_name"));

                model.addRow(vector);

                totalCount = resultSet.getInt("total_count");
            }

            jTable1.repaint();

            jTextField1.setText(String.valueOf(totalCount));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void loadStatus() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `status` WHERE `process_id` = '1' AND `process_type_id` = '2' AND"
                    + " `status`.`id` IN ('4','5') ");

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

        jMenuItem1.setText("Address Registration");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Vehicle Registration");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Job Regstration");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jMenuItem4.setText("View Profile");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        setTitle("Employees");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(56, 78, 120));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Employees");

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
        jLabel2.setText("Total Employees");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("00");
        jTextField1.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(944, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Search by Emp ID");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Search by NIC");

        jTable1.setBackground(new java.awt.Color(238, 243, 253));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Full Name", "Name with Initials", "Display Name", "Gender", "Date of Birth", "Applicant Email", "Employee Email", "Mobile 01", "Mobile 02", "Old NIC", "New NIC", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Search by Email");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Search by Mobile");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Sort by Status");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Deactive" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField6)
                            .addComponent(jComboBox1, 0, 259, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
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
        String employeeID = String.valueOf(jTable1.getValueAt(row, 0));
        String fullName = String.valueOf(jTable1.getValueAt(row, 1));
        String namewithInitials = String.valueOf(jTable1.getValueAt(row, 2));
        String displayName = String.valueOf(jTable1.getValueAt(row, 3));
        String gender = String.valueOf(jTable1.getValueAt(row, 4));

        Object selectedValue = jTable1.getValueAt(row, 5); // Column index for the date

        String applicantEmail = String.valueOf(jTable1.getValueAt(row, 6));
        String employeeEmail = String.valueOf(jTable1.getValueAt(row, 7));
        String mobile01 = String.valueOf(jTable1.getValueAt(row, 8));
        String mobile02 = String.valueOf(jTable1.getValueAt(row, 9));
        String oldNIC = String.valueOf(jTable1.getValueAt(row, 10));
        String newNIC = String.valueOf(jTable1.getValueAt(row, 11));

        String empType = String.valueOf(jTable1.getValueAt(row, 12));

        if (empType.equals("Resign")) {

        } else {

            if (evt.getClickCount() == 2) {

                if (row != -1) {

                    Employee_Registration empRegistration = new Employee_Registration(ed);
                    this.dispose();
                    empRegistration.setVisible(true);

                    empRegistration.getjButton1().setText("Update");

                    empRegistration.getempID().setText(employeeID);
                    empRegistration.getfullName().setText(fullName);
                    empRegistration.getnamewithInitials().setText(namewithInitials);
                    empRegistration.getdisplayName().setText(displayName);

                    if (gender.equals("Male")) {
                        empRegistration.getMale().setSelected(true);
                    } else {
                        empRegistration.getFemale().setSelected(true);
                    }

                    if (selectedValue != null) {
                        try {
                            // Check if the value is already a Date
                            if (selectedValue instanceof Date) {
                                empRegistration.getdateofBirth().setDate((Date) selectedValue); // Set the Date object directly
                            } else {
                                // Assume it's a String, parse it to a Date
                                String dateString = selectedValue.toString();
                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
                                Date parsedDate = inputFormat.parse(dateString);
                                empRegistration.getdateofBirth().setDate(parsedDate); // Set the parsed Date
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Invalid date format in the table!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    empRegistration.getjTextField4().setText(applicantEmail);
                    empRegistration.getjTextField4().setEditable(false);
                    empRegistration.getjTextField4().setFocusable(false);

                    empRegistration.getempEmail().setText(employeeEmail);

                    empRegistration.getjTextField7().setText(mobile01);
                    empRegistration.getjTextField7().setEditable(false);
                    empRegistration.getjTextField7().setFocusable(false);

                    empRegistration.getmobile02().setText(mobile02);

                    if (!newNIC.isEmpty()) {
                        empRegistration.getoldNIC().setEditable(false);
                        empRegistration.getoldNIC().setFocusable(false);
                        empRegistration.getnewNIC().setEditable(false);
                        empRegistration.getnewNIC().setFocusable(false);
                    } else {

                        empRegistration.getoldNIC().setEditable(false);
                        empRegistration.getoldNIC().setFocusable(false);
                    }
                    empRegistration.getoldNIC().setText(oldNIC);
                    empRegistration.getnewNIC().setText(newNIC);

                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

        String empID = jTextField4.getText();
        jTextField5.setText("");//nic
        jTextField6.setText("");//email
        jTextField7.setText("");//mobile
        jComboBox1.setSelectedIndex(0);

        loadEmployeestable(empID);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        reset();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        int row = jTable1.getSelectedRow();
        String employeeID = String.valueOf(jTable1.getValueAt(row, 0));
        String employeeName = String.valueOf(jTable1.getValueAt(row, 3));

        try {

            Employee_Address empAddress = new Employee_Address(this, rootPaneCheckingEnabled);
            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_address` INNER JOIN "
                    + " `city` ON `employee_address`.`city_id` = `city`.`id` WHERE `employee_id` = '" + employeeID + "' ");

            if (resultSet.next()) {

                String line01 = resultSet.getString("line1");
                String line02 = resultSet.getString("line2");
                String city = resultSet.getString("city.name");

                empAddress.getjTextField3().setText(employeeID);
                empAddress.getjTextField1().setText(employeeName);
                empAddress.getLine01().setText(line01);
                empAddress.getLine02().setText(line02);
                empAddress.getCity().setSelectedItem(city);

                empAddress.getUpdate().setText("Update");
                empAddress.setVisible(true);

            } else {

                empAddress.getjTextField3().setText(employeeID);
                empAddress.getjTextField1().setText(employeeName);
                empAddress.setVisible(true);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased

        int row = jTable1.getSelectedRow(); // Select the clicked row

        // Validate if a row is selected
        if (row == -1) {
            if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click detected
                JOptionPane.showMessageDialog(this, "Please select a row first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        String empID = String.valueOf(jTable1.getValueAt(row, 0));

        String empType = String.valueOf(jTable1.getValueAt(row, 12));

        if (empType.equals("Resign")) {
            jMenuItem1.setVisible(false);
            jMenuItem2.setVisible(false);
            jMenuItem3.setVisible(false);
        }

        try {

            ResultSet transitionEmp = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND "
                    + " `status_id` IN ('4','45','46') ");

            ResultSet permanentEmp = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND "
                    + " `status_id` IN ('4','45','46') ");

            if (transitionEmp.next() || permanentEmp.next()) {
                jMenuItem3.setVisible(false);
                jMenuItem4.setVisible(true);
            } else {
                jMenuItem3.setVisible(true);
                jMenuItem4.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee data!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        //view popupmenu
        if (evt.getButton() == MouseEvent.BUTTON3) {
            jTable1.setRowSelectionInterval(row, row);
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased

        String nic = jTextField5.getText();

        jTextField4.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jComboBox1.setSelectedIndex(0);
        loadEmployeestable(nic);
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased

        String email = jTextField6.getText();

        jTextField5.setText("");//nic
        jTextField4.setText("");//empid
        jTextField7.setText("");//mobile
        jComboBox1.setSelectedIndex(0);
        loadEmployeestable(email);
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased

        String mobile = jTextField7.getText();

        jTextField5.setText("");//nic
        jTextField6.setText("");//email
        jTextField4.setText("");//empid
        jComboBox1.setSelectedIndex(0);
        loadEmployeestable(mobile);
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        String status = String.valueOf(jComboBox1.getSelectedItem());

        jTextField5.setText("");//nic
        jTextField4.setText("");//empid
        jTextField6.setText("");//email
        jTextField7.setText("");//mobile

        loadEmployeestable(status);

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        int row = jTable1.getSelectedRow();
        String employeeID = String.valueOf(jTable1.getValueAt(row, 0));
        String employeeName = String.valueOf(jTable1.getValueAt(row, 3));

        try {

            Employee_Vehicle empVehicles = new Employee_Vehicle(this, rootPaneCheckingEnabled);

            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(SwingConstants.CENTER);

            JTableHeader tableHeader = empVehicles.getvehicleTable().getTableHeader();
            Font headerFont = new Font("Arial", Font.BOLD, 14);
            tableHeader.setFont(headerFont);

            empVehicles.getvehicleTable().setDefaultRenderer(Object.class, render);

            empVehicles.getjTextField3().setText(employeeID);
            empVehicles.getjTextField1().setText(employeeName);

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_vehicles` INNER JOIN "
                    + " `vehicle_type` ON `employee_vehicles`.`vehicle_type_id` = `vehicle_type`.`id` INNER JOIN"
                    + " `vehicle_color` ON `employee_vehicles`.`vehicle_color_id` = `vehicle_color`.`id` WHERE `employee_id` = '" + employeeID + "' ");

            DefaultTableModel model = (DefaultTableModel) empVehicles.getvehicleTable().getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("vehicle_number"));
                vector.add(resultSet.getString("vehicle_type.type"));
                vector.add(resultSet.getString("vehicle_color.color"));

                model.addRow(vector);

            }
            empVehicles.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        int row = jTable1.getSelectedRow();
        String employeeID = String.valueOf(jTable1.getValueAt(row, 0));
        String employeeName = String.valueOf(jTable1.getValueAt(row, 3));

        Employee_Jobdetails empJob = new Employee_Jobdetails(this, rootPaneCheckingEnabled, null, null, null, this);
        empJob.getjTextField3().setText(employeeID);
        empJob.getjTextField1().setText(employeeName);

        empJob.setVisible(true);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

        int row = jTable1.getSelectedRow();

        String employeeID = String.valueOf(jTable1.getValueAt(row, 0));
        String empType = String.valueOf(jTable1.getValueAt(row, 12));
        Employee_Profile empProfile = new Employee_Profile(employeeID, empType, this);
        empProfile.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:

        if (ed != null) {
            ed.loadEmployeeCount();
            ed.loadAttendanceCount();
            ed.createChart();
        }

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

        if (ed != null) {
            ed.loadEmployeeCount();
            ed.loadAttendanceCount();
            ed.createChart();
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jComboBox1.setSelectedIndex(0);
        jTable1.clearSelection();
        loadEmployeestable(inviteStatus);
    }
}
