package GUI;

import static GUI.Selection_Data.content;
import static GUI.Selection_Data.from;
import static GUI.Selection_Data.host;
import static GUI.Selection_Data.path;
import static GUI.Selection_Data.sub;
import static GUI.Selection_Data.to;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.PasswordAuthentication;
import model.MySQL;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Interview_Data extends javax.swing.JDialog {

    public HashMap<String, String> statusMap;
    private Shortlist selectors;

    private String email;
    private String fullName;
    private String mobile;
    private String desiredPosition;
    private String status;

    public Interview_Data(java.awt.Frame parent, boolean modal, Shortlist selectors, String email, HashMap<String, String> statusMap) {
        super(parent, modal);
        this.selectors = selectors;
        this.email = email;
        this.statusMap = statusMap;
        initComponents();

        try {
            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `applicant` INNER JOIN "
                    + " `status` ON `applicant`.`status_id` = `status`.`id` INNER JOIN "
                    + " `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id`"
                    + "  WHERE `applicant_email` = '" + this.email + "' ");

            while (resultSet.next()) {

                fullName = resultSet.getString("applicant_name");
                mobile = resultSet.getString("mobile");
                desiredPosition = resultSet.getString("jobrole.role");
                status = resultSet.getString("status.name");

            }

            jTextField3.setText(fullName);
            jTextField1.setText(this.email);
            jTextField2.setText(mobile);
            jTextField4.setText(desiredPosition);

            if ("Pending".equals(status)) {
                jRadioButton1.setSelected(true);
            } else if ("Pending Invite".equals(status) || "Sent Invite".equals(status)) {
                jRadioButton2.setSelected(true);
            } else if ("Reject".equals(status)) {
                jRadioButton3.setSelected(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    public static boolean sendEmailWithAttachment(String email, String subject, String Content) {
        from = "nexusresidenceofficial@gmail.com";
        to = email;
        sub = subject;
        content = Content;
        host = "localhost";

        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        javax.mail.Session s = javax.mail.Session.getDefaultInstance(p, new javax.mail.Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("nexusresidenceofficial@gmail.com", "wosm flyf hkbe ozcc");
            }
        });

        try {

            javax.mail.internet.MimeMessage m = new javax.mail.internet.MimeMessage(s);

            m.setFrom(from);
            m.addRecipient(javax.mail.Message.RecipientType.TO, new javax.mail.internet.InternetAddress(to));
            m.setSubject(sub);
            m.setText(content);

            javax.mail.Transport.send(m);
            

            return true;

        } catch (Exception e) {
            return false;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jCheckBox1 = new javax.swing.JCheckBox();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jMenuItem1.setText("Delete");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Interview Data");

        jPanel1.setBackground(new java.awt.Color(238, 243, 253));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Email");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Mobile");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField1.setFocusable(false);

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField2.setFocusable(false);

        jLabel5.setBackground(new java.awt.Color(204, 204, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Applicant Name");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField3.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Desired Popsition");

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField4.setFocusable(false);

        jButton1.setBackground(new java.awt.Color(98, 134, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("View CV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("Pending");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("Shortlisted");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton3.setText("Reject");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(98, 134, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Change Status");
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
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(40, 40, 40)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setText("Interview Place");

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Interview Date");

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Interview Time");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("h:mm"))));
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));

        jButton3.setBackground(new java.awt.Color(98, 134, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Send Email");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton4.setText("AM");

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton5.setText("PM");

        jLabel9.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 24)); // NOI18N
        jLabel9.setText("Interview Details");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reset.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton5))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 24)); // NOI18N
        jLabel8.setText("Applicant Details");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField4)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField4)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `cv` WHERE `applicant_email`='" + email + "' ");

            while (resultSet.next()) {

                String cvPath = resultSet.getString("path");

                if (cvPath != null) {
                    File cvFile = new File(cvPath);
                    Desktop.getDesktop().open(cvFile); // Open the CV
                } else {
                    JOptionPane.showMessageDialog(null, "CV not available.");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // Check if radio button 1 (Pending) is selected
            if (jRadioButton1.isSelected()) {

                if (status.equals("Pending")) {
                    JOptionPane.showMessageDialog(this, "Applicant is already Pending ", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '" + statusMap.get("Pending") + "' WHERE "
                            + "`applicant_email` = '" + email + "'");

                    selectors.loadSelectorstable();

                    JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }

            } else if (jRadioButton2.isSelected()) {

                if (status.equals("Pending Invite")) {
                    JOptionPane.showMessageDialog(this, "Applicant is already Shortlisted ", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int response = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to update the status to 'Select'?",
                            "Confirm Update",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '" + statusMap.get("Pending Invite") + "' WHERE "
                                + "`applicant_email` = '" + email + "'");

                        selectors.loadSelectorstable();
                        JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } else {
                        if ("Pending".equals(status)) {
                            jRadioButton1.setSelected(true);
                        } else if ("Pending Invite".equals(status)) {
                            jRadioButton2.setSelected(true);
                        } else {
                            jRadioButton3.setSelected(true);
                        }
                    }
                }

            } else {
                if (status.equals("Rejected")) {
                    JOptionPane.showMessageDialog(this, "Applicant is already Rejected ", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '" + statusMap.get("Rejected") + "' WHERE "
                            + "`applicant_email` = '" + email + "'");

                    selectors.loadSelectorstable();
                    JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String applicantName = jTextField3.getText();
        String applicantEmail = jTextField1.getText();
        String jobTitle = jTextField4.getText();
        String address = jTextField5.getText();
        String date = "";
        Date selectedDate = jDateChooser1.getDate();

        // Date validation
        if (selectedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(selectedDate);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an Interview Date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String time = jFormattedTextField1.getText();
        String newTime = jRadioButton4.isSelected() ? time + " AM" : jRadioButton5.isSelected() ? time + " PM" : "";

        String specialNote = jTextArea1.getText().trim();

        // Validation
        if (applicantEmail == null || applicantEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Interview Place", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (newTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Interview Time", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Past date validation
        Date currentDate = new Date();
        if (selectedDate.before(currentDate)) {
            JOptionPane.showMessageDialog(this, "The selected date has already passed. Please select a future date!",
                    "Invalid Date", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {

            if (specialNote == null || specialNote.trim().isEmpty()) {
                specialNote = "No special note provided.";
            }

            // Check for previous invitations
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `interview_invitations` "
                    + "WHERE `applicant_email` = '" + applicantEmail + "' "
                    + "AND `place` = '" + address + "' "
                    + "AND `date` = '" + date + "' "
                    + "AND `time` = '" + newTime + "' AND `special_note` = '" + specialNote + "' AND `status_id` = '37' ");

            if (rs.next()) {
                // A previous invitation exists for the same details
                String existingSpecialNote = rs.getString("special_note");

                // Special Note is required if a previous invitation exists
                if ((existingSpecialNote == null || existingSpecialNote.equalsIgnoreCase("No special note provided."))
                        && (specialNote == null || specialNote.trim().isEmpty())) {
                    JOptionPane.showMessageDialog(this,
                            "Special Note is required because the applicant has already received an invitation with the same details!",
                            "Special Note Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Special Note must be different from the previous one
                if (existingSpecialNote != null && specialNote.equalsIgnoreCase(existingSpecialNote)) {
                    JOptionPane.showMessageDialog(this,
                            "The Special Note must be different from the previous one!",
                            "Duplicate Special Note", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {
                // No previous invitation; Special Note is optional
                specialNote = specialNote.isEmpty() ? "No special note provided." : specialNote;
            }

            // Confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to send the email invitation to " + applicantName + "?\nSpecial Note: " + specialNote,
                    "Confirm Email Sending", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Email sending canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Email content
            String subject = "Interview Invitation for " + jobTitle + " Position at Nexus Residence";
            String message = "Dear " + applicantName + ",\n\n"
                    + "We are pleased to inform you that you have been shortlisted for an interview.\n\n"
                    + "Interview Details:\n"
                    + "Place: " + address + "\n"
                    + "Date: " + date + "\n"
                    + "Time: " + newTime + "\n\n"
                    + "Special Note: " + specialNote + "\n\n"
                    + "We look forward to meeting you.\n\n"
                    + "Best Regards,\nHR Team";

            // Send the email
            boolean emailSent = sendEmailWithAttachment(applicantEmail, subject, message);

            if (emailSent) {
                MySQL.executeIUD("INSERT INTO `interview_invitations` "
                        + "(`applicant_email`, `place`, `date`, `time`, `special_note`, `status_id`) "
                        + "VALUES ('" + applicantEmail + "', '" + address + "', '" + date + "', '" + newTime + "', '"
                        + specialNote + "', '" + statusMap.get("Sent Invite") + "')");

                MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '" + statusMap.get("Sent Invite") + "' "
                        + "WHERE `applicant_email` = '" + applicantEmail + "'");

                JOptionPane.showMessageDialog(this, "Email sent successfully to: " + applicantName, "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                selectors.loadSelectorstable();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send email: " + applicantEmail, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while processing.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        
        reset();
    }//GEN-LAST:event_jLabel12MouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jTextField5.setText("");
        jDateChooser1.setDate(null);
        jFormattedTextField1.setText("");
        buttonGroup2.clearSelection();
        jTextArea1.setText("");
        jTextArea1.setFocusable(false);
        jTextField5.setFocusable(true);

    }

}
