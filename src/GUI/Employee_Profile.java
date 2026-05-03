package GUI;

import static GUI.email.empID;
import static GUI.email.path;
import static GUI.email.sendEmail;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import model.MySQL;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;


public class Employee_Profile extends javax.swing.JFrame {

    public String empID;
    public String imgPath;
    public String mainimgPath;
    public String displayName;
    private char defaultEchoChar;
    private Employees emp;
    private String empType;
    private static String path;
    HashMap<String, String> categoryMap = new HashMap<>();
    HashMap<String, String> brandMap = new HashMap<>();
    HashMap<String, String> companyMap = new HashMap<>();
    HashMap<String, String> resignMap = new HashMap<>();
    HashMap<String, Object> params = new HashMap<>();

    public Employee_Profile(String empID, String empType, Employees emp) {

        this.empID = empID;

        this.emp = emp;

        this.empType = empType;

        initComponents();

        if (this.empType.equals("Resign")) {
            jButton1.setVisible(false);
            jButton10.setVisible(false);

            jDateChooser1.setEnabled(false);

            jComboBox1.setEnabled(false);

            jTextArea1.setEditable(false);

        }

        jTextField1.setText(this.empID);

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        JTableHeader tableHeader = jTable1.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 16);
        tableHeader.setFont(headerFont);

        jTable1.setDefaultRenderer(Object.class, render);

        defaultEchoChar = jPasswordField1.getEchoChar();

        loadpersonalDetails();
        loadprofileImage();
        loadVehicles();
        loademploymentDetails();
        loadaddressDetails();
        loadaccessDetails();
        loadResignType();
        Boolean loadResignButton = loadResignationDetails();

        if (loadResignButton) {

            jButton3.setVisible(false);

        }
    }

    public void loadpersonalDetails() {

        try {

            ResultSet personalDetails = MySQL.executeSearch(" SELECT * FROM `employee` INNER JOIN"
                    + " `gender` ON `employee`.`gender_id` = `gender`.`id` WHERE `employee`.`id` = '" + empID + "' ");

            if (personalDetails.next()) {

                displayName = personalDetails.getString("display_name");
                String fullName = personalDetails.getString("full_name");
                String initialName = personalDetails.getString("name_with_initials");
                String gender = personalDetails.getString("gender.name");
                String date = personalDetails.getString("date_of_birth");
                String oldNic = personalDetails.getString("old_nic");
                String newNic = personalDetails.getString("new_nic");
                String empEmail = personalDetails.getString("employee_email");
                String mobile01 = personalDetails.getString("mobile1");
                String mobile02 = personalDetails.getString("mobile2");

                jTextField2.setText(displayName);
                jTextField4.setText(fullName);
                jTextField5.setText(initialName);
                jTextField6.setText(gender);
                jTextField7.setText(date);
                jTextField23.setText(oldNic);
                jTextField24.setText(newNic);
                jTextField15.setText(empEmail);
                jTextField16.setText(mobile01);
                jTextField17.setText(mobile02);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadprofileImage() {

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `profile_image` WHERE `employee_id` = '" + empID + "'");

            if (resultSet.next()) {
                mainimgPath = resultSet.getString("path");
                jButton1.setText("Update Image");
                if (mainimgPath != null && !mainimgPath.isEmpty()) {
                    // Load the image from the file path
                    File imageFile = new File(mainimgPath);
                    if (imageFile.exists()) {
                        ImageIcon imageIcon = new ImageIcon(mainimgPath);
                        Image image = imageIcon.getImage();

                        // Scale the image to the size of jLabel3
                        Image scaledImage = image.getScaledInstance(
                                jLabel39.getWidth(),
                                jLabel39.getHeight(),
                                Image.SCALE_SMOOTH
                        );

                        // Scale the image to the size of jLabel1
                        Image scaledImage2 = image.getScaledInstance(
                                jLabel11.getWidth(),
                                jLabel11.getHeight(),
                                Image.SCALE_SMOOTH
                        );

                        // Create a new ImageIcon with the scaled images
                        ImageIcon finalIcon = new ImageIcon(scaledImage);
                        ImageIcon finalIcon2 = new ImageIcon(scaledImage2);

                        // Clear any existing text
                        jLabel39.setText(null);
                        jLabel11.setText(null);

                        // Set the scaled images as the label icons
                        jLabel39.setIcon(finalIcon);
                        jLabel11.setIcon(finalIcon2);
                    }
                }
            } else {

                jButton1.setText("Add Image");
                jLabel11.setText("Select Image");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load profile image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadVehicles() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_vehicles` INNER JOIN "
                    + " `vehicle_type` ON `employee_vehicles`.`vehicle_type_id` = `vehicle_type`.`id` INNER JOIN"
                    + " `vehicle_color` ON `employee_vehicles`.`vehicle_color_id` = `vehicle_color`.`id` WHERE `employee_id` = '" + empID + "' ");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("vehicle_number"));
                vector.add(resultSet.getString("vehicle_type.type"));
                vector.add(resultSet.getString("vehicle_color.color"));

                model.addRow(vector);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loademploymentDetails() {

        try {
            // Combined SQL Query for Transition and Permanent Employees
            ResultSet resultSet = MySQL.executeSearch(
                    "SELECT `transition_employee_jobdetails`.`start_date` AS t_start_date, "
                    + "`transition_employee_jobdetails`.`end_date` AS t_end_date, "
                    + "`permanent_employee_jobdetails`.`start_date` AS p_start_date, "
                    + "`permanent_employee_jobdetails`.`end_date` AS p_end_date, "
                    + "`department`.`name` AS department_name, "
                    + "`employment_type`.`type` AS employment_type, "
                    + "`jobrole`.`role` AS jobrole_name "
                    + "FROM `employee` "
                    + "LEFT JOIN `transition_employee_jobdetails` "
                    + "ON `employee`.`id` = `transition_employee_jobdetails`.`employee_id` "
                    + "AND `transition_employee_jobdetails`.`status_id` IN ('4', '46') "
                    + "LEFT JOIN `permanent_employee_jobdetails` "
                    + "ON `employee`.`id` = `permanent_employee_jobdetails`.`employee_id` "
                    + "AND `permanent_employee_jobdetails`.`status_id` IN ('4', '46') "
                    + "INNER JOIN `department` ON `department`.`id` = COALESCE(`transition_employee_jobdetails`.`department_id`, `permanent_employee_jobdetails`.`department_id`) "
                    + "INNER JOIN `employment_type` ON `employment_type`.`id` = COALESCE(`transition_employee_jobdetails`.`employment_type_id`, `permanent_employee_jobdetails`.`employment_type_id`) "
                    + "INNER JOIN `jobrole` ON `jobrole`.`id` = COALESCE(`transition_employee_jobdetails`.`jobrole_id`, `permanent_employee_jobdetails`.`jobrole_id`) "
                    + "WHERE `employee`.`id` = '" + empID + "'");

            if (resultSet.next()) {
                // Fetch Transition Employee Details
                String transitionStartDate = resultSet.getString("t_start_date");
                String transitionEndDate = resultSet.getString("t_end_date");

                // Fetch Permanent Employee Details
                String permanentStartDate = resultSet.getString("p_start_date");
                String permanentEndDate = resultSet.getString("p_end_date");

                // Populate Fields
                jTextField9.setText(resultSet.getString("employment_type"));
                jTextField8.setText(resultSet.getString("department_name"));
                jTextField10.setText(resultSet.getString("jobrole_name"));
                jTextField3.setText(resultSet.getString("jobrole_name"));

                // Transition Dates
                if (transitionStartDate != null) {
                    jTextField14.setText(transitionStartDate);
                } else {
                    jTextField14.setText("N/A");
                }

                if (transitionEndDate != null) {
                    jTextField13.setText(transitionEndDate);
                } else {
                    jTextField13.setText("N/A");
                }

                // Permanent Dates
                if (permanentStartDate != null) {
                    jTextField11.setText(permanentStartDate);
                } else {
                    jTextField11.setText("N/A");
                }

                if (permanentEndDate != null) {
                    jTextField12.setText(permanentEndDate);
                } else {
                    jTextField12.setText("N/A");
                }

            } 

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employment details: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadaddressDetails() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_address` INNER JOIN `city` ON `employee_address`.`city_id` = `city`.`id` WHERE"
                    + " `employee_id` = '" + empID + "' ");

            if (resultSet.next()) {

                String line01 = resultSet.getString("line1");
                String line02 = resultSet.getString("line2");
                String city = resultSet.getString("city.name");

                jTextField18.setText(line01);
                jTextField19.setText(line02);
                jTextField20.setText(city);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadaccessDetails() {

        try {
            ResultSet transitionResultSet = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` "
                    + "WHERE `employee_id` = '" + empID + "' AND `status_id` = '4'");
            ResultSet permanentResultSet = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` "
                    + "WHERE `employee_id` = '" + empID + "' AND `status_id` = '4'");

            // Check transition result
            if (transitionResultSet.next()) {
                String username = transitionResultSet.getString("username");
                String password = transitionResultSet.getString("password");

                jTextField21.setText(username);
                jPasswordField1.setText(password);
            } // Check permanent result if transition result is empty
            else if (permanentResultSet.next()) {
                String username = permanentResultSet.getString("username");
                String password = permanentResultSet.getString("password");

                jTextField21.setText(username);
                jPasswordField1.setText(password);
            } else {
                // Reset fields if no result found
                jTextField21.setText("");
                jPasswordField1.setText("");
            }

            // Fetch QR details
            ResultSet QR = MySQL.executeSearch("SELECT * FROM `qr` WHERE `employee_id` = '" + empID + "'");

            if (QR.next()) {
                // Get the image path and QR ID from the database
                path = QR.getString("path");
                String qrID = QR.getString("id");

                // Check if the file exists
                File imageFile2 = new File(path);
                if (imageFile2.exists()) {
                    ImageIcon imageIcon = new ImageIcon(path);
                    Image image = imageIcon.getImage();

                    // Scale the image to the size of jLabel22
                    Image scaledImage2 = image.getScaledInstance(
                            jLabel22.getWidth(),
                            jLabel22.getHeight(),
                            Image.SCALE_SMOOTH
                    );

                    // Create a new ImageIcon with the scaled image
                    ImageIcon finalIcon = new ImageIcon(scaledImage2);

                    // Set the scaled image as the icon of the label
                    jLabel22.setIcon(finalIcon);
                }

                // Set QR ID to the corresponding text field
                jTextField22.setText(qrID);

                // Change the button text to indicate the QR can be updated
                jButton7.setText("Update QR");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadResignType() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `resignation_type` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String Id = resultSet.getString("id");

                resignMap.put(type, Id);

                vector.add(type);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean loadResignationDetails() {

        try {

            ResultSet rs = MySQL.executeSearch(" SELECT * FROM `employee_resignation` INNER JOIN `resignation_type` ON `employee_resignation`.`resignation_types_id` = `resignation_type`.`id`"
                    + " WHERE `employee_id` = '" + empID + "' ");

            if (rs.next()) {

                String type = rs.getString("resignation_type.type");
                String reason = rs.getString("reason");
                Date date = rs.getDate("resign_date");

                jDateChooser1.setDate(date);
                jComboBox1.setSelectedItem(type);
                jTextArea1.setText(reason);

                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSpinField1 = new com.toedter.components.JSpinField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jButton10 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel36 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButton11 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();

        jMenuItem1.setLabel("Delete Product");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jMenuItem2.setText("Delete supplier");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Profile");
        setResizable(false);

        jTabbedPane2.setBackground(new java.awt.Color(0, 153, 204));
        jTabbedPane2.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        jTabbedPane2.setName("Personal Details"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Full Name");

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField4.setFocusable(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Name with Initials");

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField5.setFocusable(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Gender");

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField6.setFocusable(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Date of Birth");

        jTextField7.setEditable(false);
        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField7.setFocusable(false);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 204, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Update Image");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel37.setText("Old NIC");

        jTextField23.setEditable(false);
        jTextField23.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField23.setFocusable(false);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel38.setText("New NIC");

        jTextField24.setEditable(false);
        jTextField24.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField24.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5)
                    .addComponent(jTextField4)
                    .addComponent(jTextField6)
                    .addComponent(jTextField7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Personal Details", jPanel1);

        jPanel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Vehicle Number", "Vehicle Type", "Vehicle Color"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1001, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Vehicle Details", jPanel2);

        jPanel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField8.setFocusable(false);
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel12.setText("Department");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel13.setText("Employment Type");

        jTextField9.setEditable(false);
        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField9.setFocusable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel14.setText("Designation");

        jTextField10.setEditable(false);
        jTextField10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField10.setFocusable(false);

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton10.setBackground(new java.awt.Color(0, 204, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton10.setText("Update");
        jButton10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 204, 153));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel17.setText("Transitions Time Period");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel15.setText("Start Date");

        jTextField11.setEditable(false);
        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField11.setFocusable(false);
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel16.setText("End Date");

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField12.setFocusable(false);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel18.setText("Permanent Time Period");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel19.setText("Start Date");

        jTextField13.setEditable(false);
        jTextField13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField13.setFocusable(false);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel20.setText("End Date");

        jTextField14.setEditable(false);
        jTextField14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField14.setFocusable(false);
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel18)))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(52, 52, 52)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19)
                                .addComponent(jLabel16))
                            .addGap(47, 47, 47)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField14, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                .addComponent(jTextField13))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(8, 8, 8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(jTextField12))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField14)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField13)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8))
        );

        jButton12.setBackground(new java.awt.Color(255, 102, 51));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton12.setText("View History");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(jTextField8)
                    .addComponent(jTextField9)
                    .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                .addGap(79, 79, 79)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 69, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Employment Details", jPanel3);

        jPanel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel24.setText("Employee Email");

        jTextField15.setEditable(false);
        jTextField15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField15.setFocusable(false);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel25.setText("Mobile No 01");

        jTextField16.setEditable(false);
        jTextField16.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField16.setFocusable(false);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel26.setText("Mobile No 02");

        jTextField17.setEditable(false);
        jTextField17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField17.setFocusable(false);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel27.setText("Line 01");

        jTextField18.setEditable(false);
        jTextField18.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField18.setFocusable(false);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel28.setText("Address Details");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel29.setText("Line 02");

        jTextField19.setEditable(false);
        jTextField19.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField19.setFocusable(false);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel30.setText("City");

        jTextField20.setEditable(false);
        jTextField20.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField20.setFocusable(false);

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton2.setBackground(new java.awt.Color(0, 204, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Send Email");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel31.setText("Contact Details");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField15)
                            .addComponent(jTextField16)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField19)
                            .addComponent(jTextField20)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(375, 375, 375))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27)
                                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(23, 23, 23)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(23, 23, 23)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30)))))
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Contact Details", jPanel5);

        jPanel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton5.setBackground(new java.awt.Color(153, 255, 153));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton5.setText("Create Username & Password");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(153, 255, 153));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton6.setText("Send Username & Password");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 255, 153));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton7.setText("Create QR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel23.setText("Username");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel32.setText("Password");

        jTextField21.setEditable(false);
        jTextField21.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField21.setFocusable(false);

        jPasswordField1.setEditable(false);
        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jPasswordField1.setFocusable(false);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel36.setText("QR ID");

        jTextField22.setEditable(false);
        jTextField22.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField22.setFocusable(false);

        jButton8.setBackground(new java.awt.Color(255, 51, 51));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton8.setText("Print QR");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/view.png"))); // NOI18N
        jButton9.setFocusable(false);
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton9MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton9MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton9MouseReleased(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 51, 51));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton11.setText("Update Password");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel32)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel36)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField22, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(jTextField21)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel32)
                            .addComponent(jPasswordField1)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane2.addTab("Access Details", jPanel6);

        jPanel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel33.setText("Resigned Date");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel34.setText("Resignation Type");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel35.setText("Reason for Leaving");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton3.setBackground(new java.awt.Color(204, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setText("Submit");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setFocusable(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jDateChooser1.setFocusable(false);
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(356, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Resignation Details", jPanel7);

        jPanel4.setBackground(new java.awt.Color(56, 78, 120));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Employee Profile");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(238, 243, 253));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel4.setText("Employee ID");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField1.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel5.setText("Display Name");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField2.setFocusable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel6.setText("Designation");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTextField3.setFocusable(false);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jTextField3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("Personal Details");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed


    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked


    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased

        int row = jTable1.getSelectedRow();

        if (row == -1) {
            if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click detected
                JOptionPane.showMessageDialog(this, "Please select a vehicle first!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            if (evt.getButton() == MouseEvent.BUTTON3) { // Right-click detected
                jTable1.setRowSelectionInterval(row, row); // Select the row under the cursor
                jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY()); // Show the popup menu
            }

        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed


    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

        if (this.empType.equals("Resign")) {
            JOptionPane.showMessageDialog(null, "The employee has Resigned", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // File filter for image files
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$");
                }

                @Override
                public String getDescription() {
                    return "Image Files (jpg, jpeg, png, gif)";
                }
            });

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                imgPath = selectedFile.getAbsolutePath();

                imgPath = imgPath.replace(File.separator, "/");

                if (!imgPath.isEmpty() || !imgPath.equals(null)) {

                    jLabel11.setText("");
                    jLabel11.setText(null);

                }

                try {
                    // Load the selected image
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image image = imageIcon.getImage();

                    // Scale the image to the exact size of jLabel11
                    Image scaledImage = image.getScaledInstance(
                            jLabel11.getWidth(),
                            jLabel11.getHeight(),
                            Image.SCALE_SMOOTH
                    );

                    // Create a new ImageIcon with the scaled image
                    ImageIcon finalIcon = new ImageIcon(scaledImage);

                    // Set the scaled image as the label icon
                    jLabel11.setIcon(finalIcon);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Failed to load the image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (imgPath == null || imgPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a image", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `profile_image` WHERE `employee_id` = '" + empID + "'");

            if (resultSet.next()) {
                String dbimgPath = resultSet.getString("path");

                if (imgPath.equals(dbimgPath)) {
                    JOptionPane.showMessageDialog(this, "This image already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL.executeIUD("UPDATE `profile_image` SET `path` = '" + imgPath + "' WHERE `employee_id` = '" + empID + "'");
                    JOptionPane.showMessageDialog(this, "Successfully Updated Image", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadprofileImage();
                }
            } else {
                MySQL.executeIUD("INSERT INTO `profile_image` (`employee_id`, `path`) VALUES ('" + empID + "', '" + imgPath + "')");
                JOptionPane.showMessageDialog(this, "Successfully Added Image", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadprofileImage();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        Employee_Jobdetails empjob = new Employee_Jobdetails(this, rootPaneCheckingEnabled, empID, displayName, this, null);
        empjob.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String empEmail = jTextField15.getText();
        email email = new email(empEmail);
        email.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        try {

            if (this.empType.equals("Resign")) {
                JOptionPane.showMessageDialog(null, "The employee has Resigned", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String department = jTextField8.getText();

                ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `transition_employee_jobdetails` WHERE "
                        + " `employee_id` = '" + empID + "' AND `status_id` = '4' ");

                ResultSet resultSet2 = MySQL.executeSearch(" SELECT * FROM `permanent_employee_jobdetails` WHERE "
                        + " `employee_id` = '" + empID + "' AND `status_id` = '4' ");

                if (resultSet.next() || resultSet2.next()) {
                    JOptionPane.showMessageDialog(null, "Username & Password already created", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    if (empID.isEmpty() || department.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter both Employee ID and Department", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Generate Username
                    String username = department + "_" + empID;

                    // Check if username already exists
                    boolean usernameExists = checkIfUsernameExists(username);
                    if (usernameExists) {
                        JOptionPane.showMessageDialog(null, "Username already exists! Try again.", "Username Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Generate Password
                    String password = generateUniquePassword();
                    if (password == null) {
                        JOptionPane.showMessageDialog(null, "Unable to generate unique password!", "Password Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update Database
                    int rowsAffected = MySQL.executeIUD("UPDATE `transition_employee_jobdetails` "
                            + "SET `username` = '" + username + "', `password` = '" + password + "', "
                            + " `status_id` = '4' WHERE `employee_id` = '" + empID + "' AND `status_id` = '46'");

                    int rowsAffected2 = MySQL.executeIUD("UPDATE `permanent_employee_jobdetails` "
                            + "SET `username` = '" + username + "', `password` = '" + password + "', "
                            + " `status_id` = '4' WHERE `employee_id` = '" + empID + "' AND `status_id` = '46'");

                    if (rowsAffected > 0 || rowsAffected2 > 0) {
                        JOptionPane.showMessageDialog(null, "Username and Password generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        jTextField21.setText(username);
                        jPasswordField1.setText(password);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating the database. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseReleased

// Hide Password when button is released
        jPasswordField1.setEchoChar(defaultEchoChar); // Reset to default masking
    }//GEN-LAST:event_jButton9MouseReleased

    private void jButton9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MousePressed

        // Show Password when button is pressed
        jPasswordField1.setEchoChar((char) 0); // No masking
    }//GEN-LAST:event_jButton9MousePressed

    private void jButton9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseEntered


    }//GEN-LAST:event_jButton9MouseEntered

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        if (this.empType.equals("Resign")) {
            JOptionPane.showMessageDialog(null, "The employee has Resigned", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String from = "nexusresidenceofficial@gmail.com";
            String to = jTextField15.getText();
            String username = jTextField21.getText();
            String password = String.valueOf(jPasswordField1.getPassword());
            String empName = jTextField2.getText();
            String host = "smtp.gmail.com";

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

                // Email Subject
                String sub = "Your Login Credentials for Nexus Residence System";

                // Email Body
                String message = "Dear " + empName + ",\n\n"
                        + "We are pleased to share your login credentials for accessing the Nexus Residence system:\n\n"
                        + "Username: " + username + "\n"
                        + "Password: " + password + "\n\n"
                        + "Important:\n"
                        + "- Please keep your login credentials confidential.\n"
                        + "- Do not share your password with anyone.\n"
                        + "- If you suspect any unauthorized access, contact the HR department immediately.\n\n"
                        + "If you have any issues logging in, please feel free to reach out to the HR team.\n\n"
                        + "Best Regards,\n"
                        + "HR Team\n"
                        + "Nexus Residence";

                // Send the email
                boolean emailSent = sendEmail(from, to, sub, message, host);

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

    }//GEN-LAST:event_jButton6ActionPerformed


    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (this.empType.equals("Resign")) {
            JOptionPane.showMessageDialog(null, "The employee has Resigned", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String username = jTextField21.getText();

            try {

                ResultSet transitionResultSet = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` "
                        + "WHERE `username` = '" + username + "' AND `status_id` = '4' AND `employee_id` = '" + empID + "' ");
                ResultSet permanentResultSet = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` "
                        + "WHERE `username` = '" + username + "' AND `status_id` = '4' AND `employee_id` = '" + empID + "' ");

                if (transitionResultSet.next()) {

                    MySQL.executeIUD(" UPDATE `transition_employee_jobdetails` SET `status_id` = '46',`password` = NULL WHERE `username` = '" + username + "' AND"
                            + " `status_id` = '4' AND `employee_id` = '" + empID + "' ");

                    // Generate Password
                    String password = generateUniquePassword();
                    if (password == null) {
                        JOptionPane.showMessageDialog(null, "Unable to generate unique password!", "Password Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update Database
                    int rowsAffected = MySQL.executeIUD("UPDATE `transition_employee_jobdetails` "
                            + "SET `password` = '" + password + "', "
                            + " `status_id` = '4' WHERE `employee_id` = '" + empID + "' AND `status_id` = '46' AND `username` = '" + username + "' ");

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        jPasswordField1.setText(password);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating the database. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (permanentResultSet.next()) {

                    MySQL.executeIUD(" UPDATE `permanent_employee_jobdetails` SET `status_id` = '46',`password` = NULL WHERE `username` = '" + username + "' AND"
                            + " `status_id` = '4' AND `employee_id` = '" + empID + "' ");

                    // Generate Password
                    String password = generateUniquePassword();
                    if (password == null) {
                        JOptionPane.showMessageDialog(null, "Unable to generate unique password!", "Password Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update Database
                    int rowsAffected = MySQL.executeIUD("UPDATE `permanent_employee_jobdetails` "
                            + "SET `password` = '" + password + "', "
                            + " `status_id` = '4' WHERE `employee_id` = '" + empID + "' AND `status_id` = '46' AND `username` = '" + username + "' ");

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        jPasswordField1.setText(password);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating the database. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (this.empType.equals("Resign")) {
            JOptionPane.showMessageDialog(null, "The employee has Resigned", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Employee details
            String empID = jTextField1.getText(); // Assuming you have a field for employee ID
            String empName = jTextField2.getText(); // Employee name
            String empDepartment = jTextField8.getText(); // Employee department
            String empPosition = jTextField10.getText(); // Employee position
            String empNIC = "";

            String oldNIC = jTextField23.getText();
            String newNIC = jTextField24.getText();

            if (oldNIC.isEmpty()) {
                empNIC = newNIC;
            } else {
                empNIC = oldNIC;
            }

            String empMobile = jTextField16.getText();

            if (empID.isEmpty() || empName.isEmpty() || empDepartment.isEmpty() || empPosition.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all employee details before generating QR code.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Combine employee details into a single string
            String qrData = "Employee ID: " + empID + "\n"
                    + "Name: " + empName + "\n"
                    + "Department: " + empDepartment + "\n"
                    + "Position: " + empPosition + "\n"
                    + "NIC: " + empNIC + "\n"
                    + "Mobile: " + empMobile;

            // Define QR code file path
            String filePath = "C:/Users/Rasindu Thenuwara/Documents/NetBeansProjects/Nexus_Residence/src/resources/QR/employee_" + empID + "_QRCode.png";

            try {
                // Create the directory if it doesn't exist
                File dir = new File("C:/Users/Rasindu Thenuwara/Desktop/QR");
                if (!dir.exists()) {
                    dir.mkdirs(); // Ensure all parent directories are created
                }

                // Check if QR code already exists for the employee
                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM qr WHERE employee_id = '" + empID + "'");

                if (resultSet.next()) {
                    // If QR code exists, delete the old one
                    MySQL.executeIUD("DELETE FROM qr WHERE employee_id = '" + empID + "'");

                    // Optionally, delete the old QR code image from the file system
                    String oldFilePath = resultSet.getString("path");
                    File oldFile = new File(oldFilePath);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // Generate QR code
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 400, 400);
                Path path = new File(filePath).toPath();
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

                // Save the new QR path to the database
                MySQL.executeIUD("INSERT INTO qr (employee_id, path) VALUES ('" + empID + "', '" + filePath + "')");

                loadaccessDetails();

                JOptionPane.showMessageDialog(null, "QR Code generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (WriterException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to generate QR code: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database operation failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        String empName = jTextField2.getText();

        Employment_History empHistory = new Employment_History(empID, empName);
        empHistory.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        String empID = jTextField1.getText();

        String eDate = "";
        Date selectedDate = jDateChooser1.getDate();

        if (selectedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            eDate = dateFormat.format(selectedDate);
        }

        String reType = String.valueOf(jComboBox1.getSelectedItem());
        String reason = jTextArea1.getText();

        if (empID.isEmpty() || empID == null) {
            JOptionPane.showMessageDialog(null, "Not found a Employee", "Eroor", JOptionPane.ERROR_MESSAGE);
        } else if (eDate.isEmpty() || eDate == null) {
            JOptionPane.showMessageDialog(null, "Please select a Resignation Date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (reType.equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please select a Resignation Type", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Reson", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet transitionResultSet = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` "
                        + "WHERE `employee_id` = '" + empID + "' AND `status_id` = '4' ");
                ResultSet permanentResultSet = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` "
                        + "WHERE `employee_id` = '" + empID + "' AND `status_id` = '4' ");

                if (transitionResultSet.next()) {

                    MySQL.executeIUD(" INSERT INTO `employee_resignation` (`employee_id`,`resignation_types_id`,`reason`,`resign_date`) VALUES"
                            + " ('" + empID + "' , '" + resignMap.get(reType) + "' , '" + reason + "' , '" + eDate + "') ");

                    ResultSet dbTransitionData = MySQL.executeSearch(" SELECT * FROM `transition_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                            + " `status_id` IN ('4','46') ");

                    if (dbTransitionData.next()) {

                        int traID = dbTransitionData.getInt("id");
                        MySQL.executeIUD(" INSERT INTO `employee_job_history` (`employee_id`,`transition_employee_jobdetails_id`)"
                                + " VALUES ('" + empID + "' , '" + traID + "') ");
                    }

                    MySQL.executeIUD(" UPDATE `transition_employee_jobdetails` SET `status_id` = '45', `end_date` = '" + eDate + "' "
                            + " WHERE `employee_id` = '" + empID + "' ");

                    MySQL.executeIUD("DELETE FROM `qr` WHERE `employee_id` = '" + empID + "' ");

                    MySQL.executeIUD(" UPDATE `employee` SET `status_id` = '49' WHERE `id` = '" + empID + "' ");

                    loademploymentDetails();
                    emp.loadEmployeestable("");

                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Success", "Success", JOptionPane.INFORMATION_MESSAGE);

                }

                if (permanentResultSet.next()) {

                    Date sDate = permanentResultSet.getDate("start_date"); // java.sql.Date
                    Date endDate = selectedDate; // already a java.util.Date

                    // Convert both to milliseconds
                    long startMillis = sDate.getTime();
                    long endMillis = endDate.getTime();

                    // Find difference in milliseconds
                    long diffMillis = endMillis - startMillis;

                    // Convert to days
                    long diffDays = diffMillis / (1000 * 60 * 60 * 24);

                    // Convert to years, months, days roughly
                    long years = diffDays / 365;
                    long remainingDays = diffDays % 365;
                    long months = remainingDays / 30;
                    long days = remainingDays % 30;

                    // Build readable time period string
                    StringBuilder timePeriod = new StringBuilder();
                    if (years > 0) {
                        timePeriod.append(years).append(" year").append(years > 1 ? "s " : " ");
                    }
                    if (months > 0) {
                        timePeriod.append(months).append(" month").append(months > 1 ? "s " : " ");
                    }
                    if (days > 0) {
                        timePeriod.append(days).append(" day").append(days > 1 ? "s" : "");
                    }

                    if (timePeriod.length() == 0) {
                        timePeriod.append("0 days");
                    }

                    MySQL.executeIUD("INSERT INTO `employee_resignation` (`employee_id`,`resignation_types_id`,`reason`,`resign_date`) VALUES"
                            + " ('" + empID + "' , '" + resignMap.get(reType) + "' , '" + reason + "' , '" + eDate + "') ");

                    ResultSet dbPermanentData = MySQL.executeSearch(" SELECT * FROM `permanent_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                            + " `status_id` IN ('4','46') ");

                    if (dbPermanentData.next()) {

                        int perID = dbPermanentData.getInt("id");
                        MySQL.executeIUD(" INSERT INTO `employee_job_history` (`employee_id`,`permanent_employee_jobdetails_id`)"
                                + " VALUES ('" + empID + "' , '" + perID + "') ");
                    }
                    MySQL.executeIUD("UPDATE `permanent_employee_jobdetails` SET `status_id` = '45', `end_date` = '" + eDate + "', `time_period` = '" + timePeriod.toString().trim() + "' "
                            + "WHERE `employee_id` = '" + empID + "' ");

                    MySQL.executeIUD("DELETE FROM `qr` WHERE `employee_id` = '" + empID + "' ");

                    MySQL.executeIUD("UPDATE `employee` SET `status_id` = '49' WHERE `id` = '" + empID + "' ");

                    loademploymentDetails();
                    emp.loadEmployeestable("");

                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        if (this.empType.equals("Resign")) {
            JOptionPane.showMessageDialog(null, "The employee has Resigned", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                
                String department  = jTextField8.getText();
                String position  = jTextField10.getText();
                
                InputStream s = this.getClass().getResourceAsStream("/Reports/EmployeeID_Card.jasper");

                // Define parameters
                
                params.put("Parameter2", empID);                      
                params.put("Parameter3", department);           
                params.put("Parameter4", position);              

                
                params.put("qr_path", path);

                // No data source
                JasperPrint jasperPrint = JasperFillManager.fillReport(s, params, new JREmptyDataSource());

                // Show/print
                JasperViewer.viewReport(jasperPrint, false);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private boolean checkIfUsernameExists(String username) {
        try {
            ResultSet transitionResultSet = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` "
                    + "WHERE `username` = '" + username + "' AND `status_id` = '46'");
            ResultSet permanentResultSet = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` "
                    + "WHERE `username` = '" + username + "' AND `status_id` = '46'");

            return transitionResultSet.next() || permanentResultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Assume exists if there's an error
        }
    }

    private String generateUniquePassword() {
        try {
            while (true) {
                String password = generateRandomPassword();
                if (!checkIfPasswordExists(password)) {
                    return password;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkIfPasswordExists(String password) {
        try {
            ResultSet transitionResultSet = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` "
                    + "WHERE `password` = '" + password + "' AND `status_id` IN ('46','4') ");
            ResultSet permanentResultSet = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` "
                    + "WHERE `password` = '" + password + "' AND `status_id` IN ('46','4') ");

            return transitionResultSet.next() || permanentResultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Assume exists if there's an error
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private com.toedter.components.JSpinField jSpinField1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    private void clear() {
//        jComboBox1.setSelectedIndex(0);
//        jComboBox2.setSelectedIndex(0);
//        jTextField2.setText("");
//        jTable1.clearSelection();
//        loadProducts(0);
//
//        jComboBox4.setSelectedIndex(0);
//        jTextField4.setText("");
//        jTable3.clearSelection();
//        loadSupplier(0);
    }
}
