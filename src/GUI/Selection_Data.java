package GUI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.PasswordAuthentication;
import model.MySQL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.JFileChooser;
import java.io.*;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Selection_Data extends javax.swing.JDialog {

    public static String from, to, sub, content, host, path;

    private Applicants applicants;
    

    private String email;
    private String fullName;
    private String mobile;
    private String desiredPosition;
    private String status;
    private String inviteStatus;
    private String inviteofferStatus;

    public Selection_Data(java.awt.Frame parent, boolean modal, Applicants applicants, String email) {
        super(parent, modal);
        this.applicants = applicants;
        
        this.email = email;

        initComponents();

        try {
            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `applicant` INNER JOIN "
                    + " `status` ON `applicant`.`status_id` = `status`.`id` INNER JOIN "
                    + " `jobrole` ON `applicant`.`desired_position` = `jobrole`.`id`"
                    + "  WHERE `applicant_email` = '" + this.email + "' ");

            ResultSet invitations = MySQL.executeSearch(" SELECT * FROM `interview_invitations` INNER JOIN"
                    + " `status` ON `interview_invitations`.`status_id` = `status`.`id`"
                    + " WHERE `special_note` = 'Confirmation Letter' AND `status_id` = '42' AND `applicant_email` = '" + this.email + "' ");

            ResultSet invitationsOffer = MySQL.executeSearch(" SELECT * FROM `interview_invitations` INNER JOIN"
                    + " `status` ON `interview_invitations`.`status_id` = `status`.`id`"
                    + " WHERE `special_note` = 'Offer Letter' AND `status_id` = '44' AND `applicant_email` = '" + this.email + "' ");

            while (resultSet.next()) {
                fullName = resultSet.getString("applicant_name");
                mobile = resultSet.getString("mobile");
                desiredPosition = resultSet.getString("jobrole.role");
                status = resultSet.getString("status.name");
            }

            while (invitations.next()) {
                inviteStatus = invitations.getString("status.name");
            }

            while (invitationsOffer.next()) {
                inviteofferStatus = invitationsOffer.getString("status.name");
            }

            jTextField3.setText(fullName);
            jTextField1.setText(this.email);
            jTextField2.setText(mobile);
            jTextField4.setText(desiredPosition);

            if ("Pending Confirmed".equals(status)) {
                jRadioButton1.setSelected(true);
            } else if ("Selected".equals(status)) {
                jRadioButton2.setSelected(true);
            } else if ("Reject Confirmed".equals(status)) {
                jRadioButton3.setSelected(true);
            }

            if (!"Selected".equals(status)) { // Not selected
                jButton3.setVisible(false);
                jPanel2.setVisible(true); // Ensure this is the intended behavior
                jButton4.setVisible(false);
                this.setSize(498, 490);
            } else if ("Selected".equals(status) && !"Sent Joined".equals(inviteStatus)) { // Selected but inviteStatus != "Sent Joined"
                jPanel2.setVisible(true);
                jButton4.setVisible(false);
                this.setSize(498, 530);
            } else if ("Selected".equals(status) && "Sent Joined".equals(inviteStatus) && !"Sent Offer".equals(inviteofferStatus)) { // Selected but inviteStatus != "Sent Joined"
                jPanel2.setVisible(false);
                jButton3.setVisible(false);
                jButton4.setVisible(true);
                this.setSize(482, 420);
            } else if ("Selected".equals(status) && "Sent Offer".equals(inviteofferStatus) && "Sent Joined".equals(inviteStatus)) {
                jPanel2.setVisible(false);
                jButton3.setVisible(false);
                jButton4.setText("Offer Letter Sent");
                jButton4.setBackground(Color.GREEN);

                this.setSize(482, 420);

            }

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = this.getSize();
            int x = (screenSize.width - frameSize.width) / 2;
            int y = (screenSize.height - frameSize.height) / 2;
            this.setLocation(x, y);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
            javax.mail.Multipart ms = new javax.mail.internet.MimeMultipart();
            javax.mail.internet.MimeBodyPart pdf = new javax.mail.internet.MimeBodyPart();

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

    public JButton getjButton4() {

        return jButton4;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
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
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jMenuItem1.setText("Delete");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selection Data");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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

        jButton1.setBackground(new java.awt.Color(56, 78, 120));
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
        jRadioButton1.setText("Pending Confirmed");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("Selected");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton3.setText("Reject Confirmed");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(13, 13, 13))
        );

        jLabel8.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 24)); // NOI18N
        jLabel8.setText("Applicant Selection");

        jButton3.setBackground(new java.awt.Color(255, 0, 51));
        jButton3.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Send Confirmation Mail");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 0, 51));
        jButton4.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Send Offer Letter Mail");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
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
                .addGap(30, 30, 30)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

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

            ResultSet rs = MySQL.executeSearch("SELECT * FROM `interview_invitations` "
                    + "WHERE `applicant_email` = '" + email + "' AND `status_id` = '42' ");

            if (rs.next()) {

                JOptionPane.showMessageDialog(this, "Can't change status this applicant.Because already sent the Confirmation letter",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (jRadioButton1.isSelected()) {

                if (status.equals("Pending Confirmed")) {
                    JOptionPane.showMessageDialog(this, "Applicant is already Pending ", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '40' WHERE `applicant_email` = '" + email + "'");

                    

                    JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }

            } else if (jRadioButton2.isSelected()) {

                if (status.equals("Selected")) {
                    JOptionPane.showMessageDialog(this, "Applicant is already Selected", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int response = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to update the status to Selected?",
                            "Confirm Update",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '2' WHERE `applicant_email` = '" + email + "'");

                        

                        JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();

                    } else {
                        JOptionPane.showMessageDialog(this, "Update Cancelled", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } else {

                if (status.equals("Reject Confirmed")) {
                    JOptionPane.showMessageDialog(this, "Applicant is already Rejected", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    MySQL.executeIUD("UPDATE `applicant` SET `status_id` = '41' WHERE `applicant_email` = '" + email + "'");

                    

                    JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        // Validation
        if (!status.equals("Selected")) {
            JOptionPane.showMessageDialog(this, "This applicant has not been selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Confirm the action before sending the email
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to send the email invitation to " + fullName + "?",
                    "Confirm Email Sending",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Email sending canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Step 1: Check if same details already exist
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `interview_invitations` "
                    + "WHERE `applicant_email` = '" + email + "' AND `status_id` = '42' ");

            if (rs.next()) {

                JOptionPane.showMessageDialog(this, "An Employment Confirmation letter already been sent.",
                        "Duplicate Invitation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Email content
            String subject = "Congratulations on Joining Our Team!";
            String message = "Dear " + fullName + ",\n\n"
                    + "We are thrilled to officially welcome you to our team at Nexus Residence. "
                    + "Your skills and talents have truly impressed us, and we are excited to have you as part of our journey.\n\n"
                    + "Here are the next steps:\n"
                    + "1. Our HR team will reach out to you shortly to provide additional details and guidance.\n"
                    + "2. If you have any immediate questions, feel free to contact us at nexusresidenceofficial@gmail.com.\n\n"
                    + "We look forward to seeing you contribute to our success and grow with us.\n\n"
                    + "Once again, congratulations and welcome aboard!\n\n"
                    + "Best Regards,\nHR Team";

            // Send the email
            boolean emailSent = sendEmailWithAttachment(email, subject, message);

            if (emailSent) {

                MySQL.executeIUD("INSERT INTO `interview_invitations` "
                        + "(`applicant_email`,`special_note`,`status_id`) "
                        + "VALUES ('" + email + "','Confirmation Letter', '42')");

                JOptionPane.showMessageDialog(this, "Email sent successfully to: " + fullName, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Failed to send email: " + fullName, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while checking for duplicates.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        
        try {
            
           ResultSet resultSet =  MySQL.executeSearch(" SELECT * FROM `offer_letter` WHERE `applicant_email` = '"+email+"' ");
           
           
            if (resultSet.next()) {
                path = resultSet.getString("path");
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        // Check if the offer letter is already sent
        if ("Sent Offer".equals(inviteofferStatus)) {
            int response = JOptionPane.showConfirmDialog(this,
                    "Offer Letter has already been sent. Do you want to view the letter?",
                    "Offer Letter Sent",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // Open the letter if the path is assigned
                if (path != null && !path.isEmpty()) {
                    try {
                        Desktop.getDesktop().open(new File(path));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error opening the letter: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The letter path is not assigned or invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return;
        }

        // Check if the conditions for opening the email frame are met
        if ((inviteofferStatus == null || !"Sent Offer".equals(inviteofferStatus)) && "Sent Joined".equals(inviteStatus)) {
            // Close the current dialog
            this.setVisible(false);

            // Open the Testemail JFrame
            email Testemail = new email(null);
            Testemail.getjTextField1().setText(email);
            Testemail.getjTextField1().setEditable(false);
            Testemail.getjTextField1().setFocusable(false);

            Testemail.getjTextField2().setText("Offer Letter for the Position of " + desiredPosition + " at Nexus Residence");
            Testemail.getjTextField2().setEditable(false);
            Testemail.getjTextField2().setFocusable(false);

            Testemail.getjTextArea1().setEditable(false);
            Testemail.getjTextArea1().setFocusable(false);
            Testemail.setVisible(true);

            // Add a WindowListener to handle actions when Testemail JFrame is closed
            Testemail.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    Testemail.setVisible(false); // Hide the Testemail frame
                    setVisible(true);           // Show the current dialog
                }
            });
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        applicants.loadApplicantstable();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        applicants.loadApplicantstable();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

}
