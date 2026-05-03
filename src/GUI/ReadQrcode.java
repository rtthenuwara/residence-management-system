package GUI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.MySQL;
import java.awt.Toolkit;
import java.io.IOException;
import javax.sound.sampled.*;

public class ReadQrcode extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    public static String empID;
    public static String NIC;
    public static String Department;
    public static String Position;
    public String mainimgPath;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private boolean running = true;

    Employee_Dashboard empDashboard;

    public ReadQrcode(Employee_Dashboard empDash) {
        initComponents();
        initWebcam();

        this.empDashboard = empDash;

        // Add a WindowListener to close the webcam on application exit
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                stop();
                dispose();
            }
        });
    }

    private void initWebcam() {
        // Fetch the default webcam
        webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("No webcam detected.");
            return;
        }

        // Choose a supported resolution (e.g., 640x480)
        Dimension size = WebcamResolution.VGA.getSize(); // Use a standard resolution
        webcam.setViewSize(size);

        // Create the webcam panel
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        // Set the layout and add the panel to jPanel4
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(panel, java.awt.BorderLayout.CENTER);
        jPanel4.revalidate();
        jPanel4.repaint();

        // Start QR code scanning in a separate thread
        executor.execute(this);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1500);
                if (webcam.isOpen()) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        processImage(image);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processImage(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            if (result != null) {
                String qrText = result.getText();
                empID = extractEmployeeID(qrText);
                NIC = extractNIC(qrText);
                Department = extractDepartment(qrText);
                Position = extractPosition(qrText); // 

                if (empID != null) {
                    loadPersonalDetails();
                }
            }
        } catch (Exception ignored) {
        }
    }

    private String extractEmployeeID(String qrText) {
        String[] lines = qrText.split("\n"); 

        for (String line : lines) {
            if (line.startsWith("Employee ID:")) { 
                return line.split(":")[1].trim(); 
            }
        }
        return null; 
    }

    private String extractNIC(String qrText) {
        String[] lines = qrText.split("\n"); 

        for (String line : lines) {
            if (line.startsWith("NIC:")) { 
                return line.split(":")[1].trim(); 
            }
        }
        return null;
    }

    private String extractDepartment(String qrText) {
        String[] lines = qrText.split("\n"); 

        for (String line : lines) {
            if (line.startsWith("Department:")) { 
                return line.split(":")[1].trim(); 
            }
        }
        return null; 
    }

    private String extractPosition(String qrText) {
        String[] lines = qrText.split("\n");

        for (String line : lines) {
            if (line.startsWith("Position:")) { 
                return line.split(":")[1].trim();
            }
        }
        return null;
    }

    private void stop() {
        running = false;
        if (webcam != null) {
            webcam.close();
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "QR-Code-Scanner-Thread");
        t.setDaemon(true);
        return t;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mark Attendance");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(238, 243, 253));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel5.setText("Employee Name");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField2.setFocusable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel6.setText("Department");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField3.setFocusable(false);

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField4.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel7.setText("Designation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(jTextField4)
                            .addComponent(jTextField2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(98, 134, 204));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void loadPersonalDetails() {
        try {
            // Retrieve personal details
            ResultSet personalDetails = MySQL.executeSearch("SELECT * FROM `employee` INNER JOIN `gender` "
                    + "ON `employee`.`gender_id` = `gender`.`id` WHERE `employee`.`id` = '" + empID + "' AND "
                    + "( `old_nic` = '" + NIC + "' OR `new_nic` = '" + NIC + "' )");

            // Retrieve profile image details
            ResultSet profileImage = MySQL.executeSearch("SELECT * FROM `profile_image` WHERE `employee_id` = '" + empID + "'");

            // Retrieve job details (transition job details)
            ResultSet transitionResultSet = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` INNER JOIN "
                    + " `department` ON `transition_employee_jobdetails`.`department_id` = `department`.`id` INNER JOIN "
                    + " `jobrole` ON `transition_employee_jobdetails`.`jobrole_id` = `jobrole`.`id` "
                    + " WHERE `employee_id` = '" + empID + "' AND `department`.`name` = '" + Department + "' AND "
                    + " `jobrole`.`role` = '" + Position + "' AND `status_id` IN ('4','46') ");

            // Retrieve job details (permanent job details)
            ResultSet permanentResultSet = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` INNER JOIN "
                    + " `department` ON `permanent_employee_jobdetails`.`department_id` = `department`.`id` INNER JOIN "
                    + " `jobrole` ON `permanent_employee_jobdetails`.`jobrole_id` = `jobrole`.`id` "
                    + " WHERE `employee_id` = '" + empID + "' AND `department`.`name` = '" + Department + "' AND "
                    + " `jobrole`.`role` = '" + Position + "' AND `status_id` IN ('4','46') ");

            // Check if personal details were found
            if (personalDetails.next()) {
                String displayName = personalDetails.getString("display_name");
                jTextField2.setText(displayName);

                // Check if profile image exists
                if (profileImage.next()) {
                    mainimgPath = profileImage.getString("path");

                    if (mainimgPath != null && !mainimgPath.isEmpty()) {
                        // Load and scale the image
                        File imageFile = new File(mainimgPath);
                        if (imageFile.exists()) {
                            ImageIcon imageIcon = new ImageIcon(mainimgPath);
                            Image image = imageIcon.getImage();
                            Image scaledImage = image.getScaledInstance(
                                    jLabel11.getWidth(),
                                    jLabel11.getHeight(),
                                    Image.SCALE_SMOOTH
                            );
                            ImageIcon finalIcon = new ImageIcon(scaledImage);
                            jLabel11.setIcon(finalIcon);
                        }
                    }
                } else {

                    jLabel11.setIcon(null);
                }

                if (transitionResultSet.next()) {
                    jTextField3.setText(transitionResultSet.getString("department.name"));
                    jTextField4.setText(transitionResultSet.getString("jobrole.role"));
                    markAttendance();
                } else if (permanentResultSet.next()) {
                    jTextField3.setText(permanentResultSet.getString("department.name"));
                    jTextField4.setText(permanentResultSet.getString("jobrole.role"));
                    markAttendance();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAttendance() {
        try {
            // Check if attendance exists for today
            ResultSet resultSet = MySQL.executeSearch(
                    "SELECT * FROM employee_attendance "
                    + "WHERE employee_id = '" + empID + "' AND `date` = CURRENT_DATE"
            );

            if (resultSet.next()) {
                // Attendance exists, check if out_time is already marked
                if (resultSet.getTimestamp("out_time") == null) {
                    // Update out_time
                    MySQL.executeIUD(
                            "UPDATE employee_attendance SET out_time = CURRENT_TIME "
                            + "WHERE employee_id = '" + empID + "' AND `date` = CURRENT_DATE"
                    );
                    jTextField1.setBackground(Color.ORANGE);
                    jTextField1.setText("Out time marked successfully");
                    empDashboard.loadAttendanceCount();
                    playSound("C:/Users/Rasindu Thenuwara/Desktop/store-scanner-beep.wav"); // Play tick sound
                    clearFieldsAfterDelay();
                    Thread.sleep(3000);
                } else {
                    // Both in_time and out_time are filled
                    jTextField1.setBackground(Color.RED);
                    jTextField1.setForeground(Color.WHITE);
                    jTextField1.setText("You have already marked attendance today");
                    clearFieldsAfterDelay();
                    Thread.sleep(2000);
                }
            } else {
                // Insert new attendance record
                MySQL.executeIUD(
                        "INSERT INTO employee_attendance (employee_id, date ,in_time, status_id) "
                        + "VALUES ('" + empID + "' , CURRENT_DATE, CURRENT_TIME , '47')"
                );
                jTextField1.setBackground(Color.GREEN);
                jTextField1.setText("Successfully marked attendance");
                empDashboard.loadAttendanceCount();
                playSound("C:/Users/Rasindu Thenuwara/Desktop/store-scanner-beep.wav");//Play tick sound
                clearFieldsAfterDelay();
                Thread.sleep(3000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFieldsAfterDelay() {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    jTextField1.setText("");
                    jTextField1.setBackground(Color.LIGHT_GRAY);
                    jTextField1.setForeground(Color.BLACK);

                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");

                    jLabel11.setIcon(null);
                });
            }
        }, 3000);
    }

    private void playSound(String soundFileName) throws IOException {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

    }//GEN-LAST:event_jLabel11MouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
