package GUI;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Attendance_history extends javax.swing.JFrame {

    HashMap<String, String> statusMap = new HashMap<>();
    HashMap<String, Object> params = new HashMap<>();

    Date date;
    private String query;
    JasperPrint jasperPrint;
    static Boolean ok = false;

    public Attendance_history() {
        initComponents();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadEmployeestable("");
        loadStatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);
        jDateChooser1.setDate(date);
    }

    public void loadEmployeestable(String searchKey) {
        try {

            query = "SELECT * FROM `employee_attendance` "
                    + "INNER JOIN `employee` ON `employee_attendance`.`employee_id` = `employee`.`id` "
                    + "INNER JOIN `status` ON `employee_attendance`.`status_id` = `status`.`id` ";

            if (searchKey.isEmpty() || searchKey == null) {
                query += "WHERE `employee_attendance`.`date` = CURRENT_DATE ORDER BY employee_attendance.in_time ASC";
            } else {
                query += searchKey;
            }

            ResultSet resultSet = MySQL.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            int totalCount = 0;

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("employee.id"));
                vector.add(resultSet.getString("employee.display_name"));

                vector.add(resultSet.getString("employee_attendance.date"));
                vector.add(resultSet.getString("employee_attendance.in_time"));
                vector.add(resultSet.getString("employee_attendance.out_time"));
                vector.add(resultSet.getString("status.name"));

                model.addRow(vector);

                date = resultSet.getDate("employee_attendance.date");
                
                ok = true;

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void loadStatus() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `status` WHERE `process_id` = '1' AND `process_type_id` = '11'");

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
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Attendance History");

        jPanel1.setBackground(new java.awt.Color(56, 78, 120));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Attendance History");

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

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel9.setText("Search by Date");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel10.setText("to");

        jDateChooser3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jButton3.setBackground(new java.awt.Color(98, 134, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
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
                "Employee ID", "Display Name", "Date", "In Time", "Out Time", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Print Attendance Sheet");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel11.setText("Search by Status");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
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

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        reset();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed


    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed


    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed


    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased

        jTextField4.setText("");
        jTextField6.setText("");
        jComboBox1.setSelectedIndex(0);

    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased

        jTextField5.setText("");
        jTextField6.setText("");
        jComboBox1.setSelectedIndex(0);

    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased


    }//GEN-LAST:event_jTable1MouseReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();
        String empID = String.valueOf(jTable1.getValueAt(row, 0));
        String empEmail = String.valueOf(jTable1.getValueAt(row, 1));
        String date = String.valueOf(jTable1.getValueAt(row, 4));

        if (evt.getClickCount() == 2) {

            if (row != -1) {

                Employee_Attendance empAttendance = new Employee_Attendance(this, rootPaneCheckingEnabled, empID, empEmail, date, this);
                empAttendance.setVisible(true);
            }
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased

        jTextField4.setText("");
        jTextField5.setText("");
        jComboBox1.setSelectedIndex(0);

    }//GEN-LAST:event_jTextField6KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged


    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String status = String.valueOf(jComboBox1.getSelectedItem());

        String empNIC = jTextField5.getText();
        String empID = jTextField4.getText();
        String empEmail = jTextField6.getText();

        String searchfromDate = "";
        String searchtoDate = "";

        Date selectedfromDate = jDateChooser1.getDate();
        Date selectedtoDate = jDateChooser3.getDate();

        if (selectedfromDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            searchfromDate = dateFormat.format(selectedfromDate);
        }

        if (selectedtoDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            searchtoDate = dateFormat.format(selectedtoDate);
        }

        if (searchfromDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter search date", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            if (!empID.isEmpty() && empEmail.isEmpty() && empNIC.isEmpty() && status.equals("Select")) {
                loadEmployeestable("WHERE `employee_attendance`.`date` = '" + searchfromDate + "' AND `employee`.`id` = '" + empID + "'");
                ok = true;
            }

            if (empID.isEmpty() && !empEmail.isEmpty() && empNIC.isEmpty() && status.equals("Select")) {
                loadEmployeestable("WHERE `employee_attendance`.`date` = '" + searchfromDate + "' AND `employee`.`employee_email` = '" + empEmail + "'");
                ok = true;
            }

            if (empID.isEmpty() && empEmail.isEmpty() && !empNIC.isEmpty() && status.equals("Select")) {
                loadEmployeestable(
                        "WHERE `employee_attendance`.`date` = '" + searchfromDate + "' "
                        + "AND (`employee`.`old_nic` = '" + empNIC + "' OR `employee`.`new_nic` = '" + empNIC + "')"
                );
                ok = true;
            }

            if (empID.isEmpty() && empEmail.isEmpty() && empNIC.isEmpty() && !status.equals("Select")) {
                loadEmployeestable("WHERE `employee_attendance`.`date` = '" + searchfromDate + "' AND `status`.`name` = '" + status + "'");
                ok = true;
            }

            if (empID.isEmpty() && empEmail.isEmpty() && empNIC.isEmpty() && status.equals("Select") && searchtoDate.isEmpty() && !searchfromDate.isEmpty()) {
                loadEmployeestable("WHERE `employee_attendance`.`date` = '" + searchfromDate + "' ");
                ok = true;
            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty()) {
                loadEmployeestable("WHERE `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;
            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty() && !empID.isEmpty()) {
                loadEmployeestable(" WHERE `employee`.`id` = '" + empID + "' AND `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;

            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty() && !empID.isEmpty() && !status.equals("Select")) {
                loadEmployeestable(" WHERE `employee`.`id` = '" + empID + "' AND  `status`.`name` = '" + status + "' AND `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;
            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty() && !empEmail.isEmpty()) {
                loadEmployeestable(" WHERE `employee`.`employee_email` = '" + empEmail + "' AND `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;
            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty() && !empEmail.isEmpty() && !status.equals("Select")) {
                loadEmployeestable(" WHERE `employee`.`employee_email` = '" + empEmail + "' AND  `status`.`name` = '" + status + "' AND `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;
            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty() && !empNIC.isEmpty()) {
                loadEmployeestable(" WHERE `employee`.`old_nic` = '" + empNIC + "' OR `employee`.`NEW_nic` = '" + empNIC + "' AND `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;

            }

            if (!searchfromDate.isEmpty() && !searchtoDate.isEmpty() && !empNIC.isEmpty() && !status.equals("Select")) {
                loadEmployeestable(" WHERE `employee`.`old_nic` = '" + empNIC + "' OR `employee`.`NEW_nic` = '" + empNIC + "' AND  `status`.`name` = '" + status + "'  AND `employee_attendance`.`date` BETWEEN '" + searchfromDate + "' AND '" + searchtoDate + "'");
                ok = true;
            }

            if (searchfromDate.equals(searchtoDate)) {
                loadEmployeestable("WHERE `employee_attendance`.`date` = '" + searchfromDate + "' ");
                ok = true;
            }

        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        String param1 = "All Employees";

        String empID = jTextField4.getText();
        String NIC = jTextField5.getText();
        String email = jTextField6.getText();

        if (empID.isEmpty() && NIC.isEmpty() && !email.isEmpty()) {
            param1 = email;
        } else if (empID.isEmpty() && email.isEmpty() && !NIC.isEmpty()) {
            param1 = NIC;
        } else if (NIC.isEmpty() && email.isEmpty() && !empID.isEmpty()) {
            param1 = empID;
        }

        String timePeriod = "";
        String f_Date = "";
        String t_Date = "";

        Date fDate = jDateChooser1.getDate();
        Date tDate = jDateChooser3.getDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        if (fDate != null) {
            f_Date = dateFormat.format(fDate);
        }

        if (tDate != null) {
            t_Date = dateFormat.format(tDate);
        }

        if (!f_Date.isEmpty() && !t_Date.isEmpty()) {
            timePeriod = f_Date + " -  " + t_Date;
        } else if (!f_Date.isEmpty()) {
            timePeriod = f_Date;
        } else if (!t_Date.isEmpty()) {
            timePeriod = t_Date;
        }

        if (ok) {
            try {
                InputStream s = this.getClass().getResourceAsStream("/Reports/Attendance_Report.jasper");

                params.put("Parameter1", param1);
                params.put("Parameter3", timePeriod);

                JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                jasperPrint = JasperFillManager.fillReport(s, params, dataSource);
                JasperViewer.viewReport(jasperPrint, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please firstly search Attendance Details", "Warning", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTable1.clearSelection();
        jDateChooser1.setDate(new Date());
        jDateChooser3.setDate(null);
        loadEmployeestable("");

    }
}
