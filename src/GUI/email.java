package GUI;

import java.awt.Desktop;
import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.JFileChooser;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.MySQL;
import java.sql.ResultSet;

public class email extends javax.swing.JFrame {

    public static String path;
    public static String empEmail;
    public static String empID;

    public email(String email) {
        initComponents();

        empEmail = email;

        if (empEmail != null) {
            jTextField1.setText(empEmail);
            jTextField1.setEditable(false);
            jTextField1.setFocusable(false);

            try {

                ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee` WHERE `employee_email` = '" + empEmail + "' ");

                if (resultSet.next()) {

                    empID = resultSet.getString("id");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JTextField getjTextField1() {
        return jTextField1;

    }

    public JTextField getjTextField2() {
        return jTextField2;

    }

    public JTextArea getjTextArea1() {
        return jTextArea1;

    }

    public static boolean sendEmail(String from, String to, String subject, String content, String host) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nexusresidenceofficial@gmail.com", "wosm flyf hkbe ozcc");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            if (path == null || path.isEmpty()) {
                message.setText(content);
            } else {
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(content);

                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(new File(path));

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

                message.setContent(multipart);
            }

            Transport.send(message);

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Send Email");
        setBackground(new java.awt.Color(153, 153, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setText("To");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("subject");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("content");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(98, 134, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Send Email");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton2.setText("Attach File");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(56, 78, 120));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Email");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/view.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField3)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        JFileChooser ch = new JFileChooser();

        if (empEmail == null) {

            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
            ch.setFileFilter(filter);
        }

        // Show the open dialog
        int returnValue = ch.showOpenDialog(null);

        // Check if a file was selected
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File f = ch.getSelectedFile();

            // Get the absolute path of the selected file
            path = f.getAbsolutePath();
// Ensure that path is correctly formatted for your platform
            path = path.replace(File.separator, "/");

            if (empEmail == null) {
                if (path.endsWith(".pdf")) {
                    JOptionPane.showMessageDialog(null, "Selected file: " + path);
                    jTextField3.setText(path);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a valid PDF file.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selected file: " + path);
                jTextField3.setText(path);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (empEmail != null) {

            employeeEmail();

        } else {
            String from = "nexusresidenceofficial@gmail.com";
            String to = jTextField1.getText();
            String sub = jTextField2.getText();
            String content = jTextArea1.getText();
            String host = "smtp.gmail.com";
            String pathText = jTextField3.getText();

            // Validation
            if (pathText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please selct offer letter", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Confirm the action before sending the email
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to send the email?",
                        "Confirm Email Sending",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirm != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "Email sending canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Step 1: Check if same details already exist
                ResultSet rs = MySQL.executeSearch("SELECT * FROM `interview_invitations` "
                        + "WHERE `applicant_email` = '" + to + "' AND `special_note` = 'Offer Letter' AND `status_id` = '44' ");

                if (rs.next()) {

                    JOptionPane.showMessageDialog(this, "An Offer letter already been sent.",
                            "Duplicate Invitation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Send the email
                boolean emailSent = sendEmail(from, to, sub, content, host);

                if (emailSent) {

                    MySQL.executeIUD("INSERT INTO `interview_invitations` "
                            + "(`applicant_email`,`special_note`,`status_id`) "
                            + "VALUES ('" + to + "','Offer Letter', '44')");

                    MySQL.executeIUD("INSERT INTO `offer_letter` "
                            + "(`path`,`applicant_email`) "
                            + "VALUES ('" + path + "','" + to + "')");

                    JOptionPane.showMessageDialog(this, "Email sent successfully", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to send email", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error occurred while checking for duplicates.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (path == null || path.isEmpty()) {
            // If the path is not set, show an error message
            JOptionPane.showMessageDialog(this, "No file selected to open!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = new File(path);

        if (!file.exists()) {
            // Check if the file exists
            JOptionPane.showMessageDialog(this, "The file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Use Desktop to open the file
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            // Handle exceptions when opening the file
            JOptionPane.showMessageDialog(this, "Unable to open the file!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    public void employeeEmail() {

        String from = "nexusresidenceofficial@gmail.com";
        String to = jTextField1.getText();
        String sub = jTextField2.getText();
        String content = jTextArea1.getText();
        String host = "smtp.gmail.com";
        String pathText = jTextField3.getText();

        try {
            // Confirm the action before sending the email
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to send the email?",
                    "Confirm Email Sending",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Email sending canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Send the email
            boolean emailSent = sendEmail(from, to, sub, content, host);

            if (emailSent) {

                MySQL.executeIUD("INSERT INTO `email_history` "
                        + "(`employee_id`,`subject`,`send_date`) "
                        + "VALUES ('" + empID + "','" + sub + "' , CURRENT_TIMESTAMP)");

                JOptionPane.showMessageDialog(this, "Email sent successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send email", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while checking for duplicates.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

}
