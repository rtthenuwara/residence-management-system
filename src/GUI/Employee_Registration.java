package GUI;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.toedter.calendar.JDateChooser;
import java.awt.Toolkit;
import javax.swing.JFrame;
import model.MySQL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Employee_Registration extends javax.swing.JFrame {

    private Employee_Dashboard ed;
    

    public Employee_Registration() {

        initComponents();

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `employee`");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            count = count + 1;
            jTextField5.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Employee_Registration(Employee_Dashboard ed) {

        initComponents();

        this.ed = ed;

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `employee`");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            count = count + 1;
            jTextField5.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //applicant email
    public JTextField getjTextField4() {
        return jTextField4;
    }

    //applicant mobile
    public JTextField getjTextField7() {
        return jTextField7;
    }

    //emp id
    public JTextField getempID() {
        return jTextField5;
    }

    //emp fullName
    public JTextArea getfullName() {
        return jTextArea1;
    }

    //emp name with Initials
    public JTextField getnamewithInitials() {
        return jTextField2;
    }

    //emp displayName
    public JTextField getdisplayName() {
        return jTextField3;
    }

    //male
    public JRadioButton getMale() {
        return jRadioButton1;
    }

    //female
    public JRadioButton getFemale() {
        return jRadioButton2;
    }

    //date of birth
    public JDateChooser getdateofBirth() {
        return jDateChooser1;
    }

    //empEmail
    public JTextField getempEmail() {
        return jTextField6;
    }

    //mobile02
    public JTextField getmobile02() {
        return jTextField9;
    }

    //oldNic
    public JTextField getoldNIC() {
        return jTextField1;
    }

    //newNic
    public JTextField getnewNIC() {
        return jTextField8;
    }

    //button
    public JButton getjButton1() {
        return jButton1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setTitle("Employee Registration");
        setResizable(false);
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
        jLabel1.setText("Employee Registration");

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
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(238, 243, 253));

        jLabel8.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 22)); // NOI18N
        jLabel8.setText("Personal Information");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reset.png"))); // NOI18N
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel2.setText("Employee ID");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel20.setText("Full Name");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel7.setText("Applicant Email");

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel6.setText("Employee Email");

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField5.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel3.setText("Name with Initial");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel4.setText("Display Name");

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel5.setText("Mobile No 01");

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel15.setText("Mobile No 02");

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel16.setText("Gender");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jRadioButton1.setText("Male");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jRadioButton2.setText("Female");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel17.setText("Date of Birth");

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel18.setText("Old NIC");

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel19.setText("New NIC");

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField5))
                                .addGap(98, 98, 98))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(78, 78, 78)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButton2))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(98, 98, 98)))))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField4)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(48, 48, 48))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel20)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2))
                        .addGap(41, 41, 41)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(44, 44, 44))))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(56, 78, 120));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String fullName = jTextArea1.getText();
        String namewithInitials = jTextField2.getText();
        String displayName = jTextField3.getText();

        jRadioButton1.setActionCommand("1");  // Assign 1 for Male
        jRadioButton2.setActionCommand("2");  // Assign 2 for Female
        ButtonModel gender = buttonGroup1.getSelection();

        String date = "";
        Date selectedDate = jDateChooser1.getDate();

        if (selectedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(selectedDate);
        }

        String applicantEmail = jTextField4.getText();
        String employeeEmail = jTextField6.getText();

        String mobile01 = jTextField7.getText();
        String mobile02 = jTextField9.getText();

        String oldNIC = jTextField1.getText();
        String newNIC = jTextField8.getText();

        //validation
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter full name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (namewithInitials.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Name with Initials", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (displayName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter display name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (gender == null) {
            JOptionPane.showMessageDialog(this, "Please select a gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select an Interview Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (applicantEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter applicant email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!applicantEmail.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Invalid Applicant email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (employeeEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter employee email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!employeeEmail.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Invalid Employee email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (mobile01.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter mobile No 01", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!mobile01.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Invalid mobile No 01", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!mobile02.isEmpty() && !mobile02.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Invalid mobile No 02", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (oldNIC.isEmpty() && newNIC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter employee NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String genderId = gender.getActionCommand();

            try {

                String empID = jTextField5.getText();
                ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee` WHERE `id` = '" + empID + "' ");

                if (resultSet.next()) {

                    String dbfullName = resultSet.getString("full_name");
                    String dbnamewithInitials = resultSet.getString("name_with_initials");
                    String dbdisplayName = resultSet.getString("display_name");
                    String dbgenderID = resultSet.getString("gender_id");
                    String dbdateofBirth = resultSet.getString("date_of_birth");
                    String dbapplicantEmail = resultSet.getString("applicant_email");
                    String dbemployeeEmail = resultSet.getString("employee_email");
                    String dbMobile1 = resultSet.getString("mobile1");
                    String dbMobile2 = resultSet.getString("mobile2");
                    String dboldNic = resultSet.getString("old_nic");
                    String dbnewNic = resultSet.getString("new_nic");

                    if (fullName.equals(dbfullName) && namewithInitials.equals(dbnamewithInitials) && displayName.equals(dbdisplayName)
                            && genderId.equals(dbgenderID) && date.equals(dbdateofBirth) && applicantEmail.equals(dbapplicantEmail)
                            && employeeEmail.equals(dbemployeeEmail) && mobile01.equals(dbMobile1) && mobile02.equals(dbMobile2)
                            && oldNIC.equals(dboldNic) && newNIC.equals(dbnewNic)) {

                        JOptionPane.showMessageDialog(this, "This details already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        MySQL.executeIUD(" UPDATE `employee` SET `full_name` = '" + fullName + "' , `name_with_initials` = '" + namewithInitials + "' , `display_name` = '" + displayName + "' ,"
                                + " `gender_id` = '" + genderId + "' , `date_of_birth` = '" + date + "' , `employee_email` = '" + employeeEmail + "' , `mobile2` = '" + mobile02 + "' ,"
                                + " `old_nic` = '" + oldNIC + "' , `new_nic` = '" + newNIC + "' WHERE `id` = '" + empID + "'  ");

                        JOptionPane.showMessageDialog(this, "Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);

                        this.dispose();
                        

                    }

                } else {

                    ResultSet resultSetEmp = MySQL.executeSearch(" SELECT * FROM `employee` WHERE `applicant_email` = '" + applicantEmail + "' OR"
                            + " `employee_email` = '" + employeeEmail + "' OR `mobile1` = '" + mobile01 + "' OR `mobile2` = '" + mobile02 + "' ");

                    if (resultSetEmp.next()) {

                        JOptionPane.showMessageDialog(this, "This Employee already registered", "Warning", JOptionPane.WARNING_MESSAGE);

                    } else {
                        ResultSet resultSet2 = MySQL.executeSearch(" SELECT * FROM `interview_invitations` WHERE `applicant_email` = '" + applicantEmail + "' AND"
                                + " `special_note` = 'Offer Letter' AND `status_id` = '44' ");

                        if (resultSet2.next()) {

                            if (mobile02.isEmpty()) {
                                mobile02 = "empty" + empID;
                            }

                            MySQL.executeIUD(" INSERT INTO `employee` (`full_name`,`name_with_initials`,`display_name`,`gender_id`,`date_of_birth`,"
                                    + " `applicant_email`,`employee_email`,`mobile1`,`mobile2`,`old_nic`,`new_nic`,`status_id`) VALUES"
                                    + " ('" + fullName + "' , '" + namewithInitials + "' , '" + displayName + "' , '" + genderId + "' , '" + date + "' ,"
                                    + " '" + applicantEmail + "' , '" + employeeEmail + "' , '" + mobile01 + "' , '" + mobile02 + "' , '" + oldNIC + "' , '" + newNIC + "' , '5') ");

                            JOptionPane.showMessageDialog(this, "Successfully Registered", "Info", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                            

                        } else {

                            JOptionPane.showMessageDialog(this, "This Applicant is not eligible for Registration", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        reset();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

        if (ed != null) {
            ed.setVisible(true);
            ed.loadEmployeeCount();
        }


    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:

        if (ed != null) {
            ed.setVisible(true);
            ed.loadEmployeeCount();
        }


    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jTextArea1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        buttonGroup1.clearSelection();
        jDateChooser1.setDate(null);
        jTextField4.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField9.setText("");
        jTextField1.setText("");
        jTextField8.setText("");

    }
}
