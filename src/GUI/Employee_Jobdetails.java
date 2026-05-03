package GUI;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.File;
import model.MySQL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Employee_Jobdetails extends javax.swing.JDialog {

    HashMap<String, String> departmentMap = new HashMap<>();
    HashMap<String, String> positionMap = new HashMap<>();
    HashMap<String, String> typeMap = new HashMap<>();

    private String empID;
    private String empName;
    Employee_Profile employeeProfile;
    Employees employee;
    private String endDate;

    public Employee_Jobdetails(java.awt.Frame parent, boolean modal, String employeeID, String name, Employee_Profile empProfile, Employees emp) {
        super(parent, modal);

        this.empID = employeeID;
        this.empName = name;
        employeeProfile = empProfile;
        employee = emp;
        initComponents();

        loadDepartment();
        loadType();
        if (empID != null) {
            loadjobDeatails();

        }

    }

    //emp id
    public JTextField getjTextField3() {

        return jTextField3;

    }

    //emp name
    public JTextField getjTextField1() {

        return jTextField1;

    }

    public void loadDepartment() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `department` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String id = resultSet.getString("id");

                departmentMap.put(name, id);

                vector.add(name);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadPosition(String departmentName) {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `jobrole` INNER JOIN `department` ON "
                    + " `jobrole`.`department_id` = `department`.`id` WHERE `department`.`name` = '" + departmentName + "' ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String role = resultSet.getString("role");
                String id = resultSet.getString("id");

                positionMap.put(role, id);

                vector.add(role);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadType() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employment_type` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String id = resultSet.getString("id");

                typeMap.put(type, id);

                vector.add(type);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox3.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadjobDeatails() {

        try {
            // SQL Query to fetch details for both Transition and Permanent Employees
            ResultSet resultSet = MySQL.executeSearch(
                    "SELECT COALESCE(tejd.start_date, pejd.start_date) AS start_date, "
                    + "COALESCE(tejd.end_date, pejd.end_date) AS end_date, "
                    + "COALESCE(tejd.time_period, NULL) AS time_period, "
                    + "d.name AS department_name, "
                    + "et.type AS employment_type, "
                    + "jr.role AS jobrole_name "
                    + "FROM employee e "
                    + "LEFT JOIN transition_employee_jobdetails tejd "
                    + "ON e.id = tejd.employee_id AND tejd.status_id IN ('4', '46') "
                    + "LEFT JOIN permanent_employee_jobdetails pejd "
                    + "ON e.id = pejd.employee_id AND pejd.status_id IN ('4', '46') "
                    + "INNER JOIN department d "
                    + "ON d.id = COALESCE(tejd.department_id, pejd.department_id) "
                    + "INNER JOIN employment_type et "
                    + "ON et.id = COALESCE(tejd.employment_type_id, pejd.employment_type_id) "
                    + "INNER JOIN jobrole jr "
                    + "ON jr.id = COALESCE(tejd.jobrole_id, pejd.jobrole_id) "
                    + "WHERE e.id = '" + empID + "'");

            if (resultSet.next()) {
                // Extract data
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");
                String timePeriod = resultSet.getString("time_period");
                String departmentName = resultSet.getString("department_name");
                String employmentType = resultSet.getString("employment_type");
                String jobRoleName = resultSet.getString("jobrole_name");

                // Update UI fields
                jTextField3.setText(empID); // Employee ID
                jTextField1.setText(empName); // Employee Name
                jComboBox2.setSelectedItem(departmentName); // Department
                jComboBox1.setSelectedItem(jobRoleName); // Job Role
                jComboBox3.setSelectedItem(employmentType); // Employment Type

                // Set Start Date
                if (startDate != null) {
                    jDateChooser1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
                }

                // Set End Date
                if (endDate != null) {
                    jDateChooser2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
                }

                // Handle Time Period
                if (timePeriod != null && !timePeriod.isEmpty()) {
                    String[] parts = timePeriod.split(" ");
                    if (parts.length == 2) {
                        jFormattedTextField1.setText(parts[0]); // Numeric value
                        for (int i = 0; i < jComboBox4.getItemCount(); i++) {
                            if (jComboBox4.getItemAt(i).toString().equalsIgnoreCase(parts[1])) {
                                jComboBox4.setSelectedIndex(i); // Time Unit
                                break;
                            }
                        }
                    }
                }
            } else {
                // No records found
                JOptionPane.showMessageDialog(this, "No job details found for the employee.",
                        "No Data", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading job details: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updatejobDetails() {

        try {

            if (empID != null) {

                ResultSet transitionresultSet = MySQL.executeSearch(" SELECT * FROM `transition_employee_jobdetails` INNER JOIN "
                        + " `employment_type` ON `transition_employee_jobdetails`.`employment_type_id` = `employment_type`.`id` WHERE `employee_id` = '" + empID + "' AND"
                        + " `status_id` IN ('4','46') ");

                if (transitionresultSet.next()) {

                    String empID = jTextField3.getText();
                    String department = String.valueOf(jComboBox2.getSelectedItem());
                    String position = String.valueOf(jComboBox1.getSelectedItem());
                    String type = String.valueOf(jComboBox3.getSelectedItem());

                    // Validate other fields
                    String time = jFormattedTextField1.getText();
                    String unit = String.valueOf(jComboBox4.getSelectedItem());
                    if (empID.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Employee ID is not found", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (department.equals("Select")) {
                        JOptionPane.showMessageDialog(this, "Please select a Department", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (position.equals("Select") || position.isEmpty() || position == null) {
                        JOptionPane.showMessageDialog(this, "Please select a Position", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (type.equals("Select")) {
                        JOptionPane.showMessageDialog(this, "Please select an Employment Type", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Validate Start Date
                    Date selectedstartDate = jDateChooser1.getDate();
                    if (selectedstartDate == null) {
                        JOptionPane.showMessageDialog(this, "Please enter a start date", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Format start date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String startDate = dateFormat.format(selectedstartDate);

                    // Remove time component for selected start date
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.setTime(selectedstartDate);
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    selectedCalendar.set(Calendar.MINUTE, 0);
                    selectedCalendar.set(Calendar.SECOND, 0);
                    selectedCalendar.set(Calendar.MILLISECOND, 0);

                    // Remove time component for current date
                    Date currentDate = new Date();
                    Calendar currentCalendar = Calendar.getInstance();
                    currentCalendar.setTime(currentDate);
                    currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    currentCalendar.set(Calendar.MINUTE, 0);
                    currentCalendar.set(Calendar.SECOND, 0);
                    currentCalendar.set(Calendar.MILLISECOND, 0);

                    if (selectedCalendar.before(currentCalendar)) {
                        JOptionPane.showMessageDialog(this, "The selected start date has already passed. Please select a future date!",
                                "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (!type.equals("Permanent")) {

                        if (time.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter time period", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        int numericValue = Integer.parseInt(time);

                        if (numericValue <= 0) {
                            JOptionPane.showMessageDialog(this, "Please enter a positive numeric value for time period", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        if (unit.equals("Select")) {
                            JOptionPane.showMessageDialog(this, "Please select a unit", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Validate End Date
                        Date selectedendDate = jDateChooser2.getDate();
                        if (selectedendDate == null) {
                            JOptionPane.showMessageDialog(this, "Please enter an end date", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        endDate = dateFormat.format(selectedendDate);

                    }

                    String timePeriod = time + " " + unit;
                    try {
                        if (type.equals("Training") || type.equals("Contract")) {

                            ResultSet dbTransitionData = MySQL.executeSearch(" SELECT * FROM `transition_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                                    + " `status_id` IN ('4','46') ");

                            

                            if (dbTransitionData.next()) {

                                int traID = dbTransitionData.getInt("id");
                                

                                MySQL.executeIUD(" INSERT INTO `employee_job_history` (`employee_id`,`transition_employee_jobdetails_id`)"
                                        + " VALUES ('" + empID + "' , '" + traID + "') ");

                            }

                            MySQL.executeIUD(" UPDATE `transition_employee_jobdetails` SET `status_id` = '45' WHERE `employee_id` = '" + empID + "' AND"
                                    + " `status_id` IN ('4','46') ");

                            

                            MySQL.executeIUD("INSERT INTO `transition_employee_jobdetails` (`employee_id`, `department_id`, `jobrole_id`, `employment_type_id`, "
                                    + "`start_date`, `end_date`, `time_period`, `status_id`) VALUES ('" + empID + "', '" + departmentMap.get(department) + "', "
                                    + "'" + positionMap.get(position) + "', '" + typeMap.get(type) + "', '" + startDate + "', '" + endDate + "', "
                                    + "'" + timePeriod + "', '46')");

                            

                            JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                            employeeProfile.loademploymentDetails();
                            employeeProfile.loadaccessDetails();
                            this.dispose();

                        } else {

                            ResultSet dbTransitionData = MySQL.executeSearch(" SELECT * FROM `transition_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                                    + " `status_id` IN ('4','46') ");

                            

                            if (dbTransitionData.next()) {

                                int traID = dbTransitionData.getInt("id");
                                

                                MySQL.executeIUD(" INSERT INTO `employee_job_history` (`employee_id`,`transition_employee_jobdetails_id`)"
                                        + " VALUES ('" + empID + "' , '" + traID + "') ");

                            }
                            MySQL.executeIUD(" UPDATE `transition_employee_jobdetails` SET `status_id` = '45' WHERE `employee_id` = '" + empID + "' AND"
                                    + " `status_id` IN ('4','46') ");

                            

                            MySQL.executeIUD("INSERT INTO `permanent_employee_jobdetails` (`employee_id`, `department_id`, `jobrole_id`, `employment_type_id`, "
                                    + "`start_date`,`status_id`) VALUES ('" + empID + "', '" + departmentMap.get(department) + "', "
                                    + "'" + positionMap.get(position) + "', '" + typeMap.get(type) + "', '" + startDate + "' , '46')");

                            

                            JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                            employeeProfile.loademploymentDetails();
                            employeeProfile.loadaccessDetails();
                            this.dispose();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    ResultSet permanentresultSet = MySQL.executeSearch(" SELECT * FROM `permanent_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                            + " `status_id` IN ('4','46') ");

                    if (permanentresultSet.next()) {

                        String empID = jTextField3.getText();
                        String department = String.valueOf(jComboBox2.getSelectedItem());
                        String position = String.valueOf(jComboBox1.getSelectedItem());
                        String type = String.valueOf(jComboBox3.getSelectedItem());

                        if (empID.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Employee ID is not found", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (department.equals("Select")) {
                            JOptionPane.showMessageDialog(this, "Please select a Department", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (position.equals("Select") || position.isEmpty() || position == null) {
                            JOptionPane.showMessageDialog(this, "Please select a Position", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (type.equals("Select")) {
                            JOptionPane.showMessageDialog(this, "Please select an Employment Type", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Validate Start Date
                        Date selectedstartDate = jDateChooser1.getDate();
                        if (selectedstartDate == null) {
                            JOptionPane.showMessageDialog(this, "Please enter a start date", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Format start date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String startDate = dateFormat.format(selectedstartDate);

                        // Remove time component for selected start date
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.setTime(selectedstartDate);
                        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
                        selectedCalendar.set(Calendar.MINUTE, 0);
                        selectedCalendar.set(Calendar.SECOND, 0);
                        selectedCalendar.set(Calendar.MILLISECOND, 0);

                        // Remove time component for current date
                        Date currentDate = new Date();
                        Calendar currentCalendar = Calendar.getInstance();
                        currentCalendar.setTime(currentDate);
                        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                        currentCalendar.set(Calendar.MINUTE, 0);
                        currentCalendar.set(Calendar.SECOND, 0);
                        currentCalendar.set(Calendar.MILLISECOND, 0);

                        if (selectedCalendar.before(currentCalendar)) {
                            JOptionPane.showMessageDialog(this, "The selected start date has already passed. Please select a future date!",
                                    "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        try {
                            if (type.equals("Permanent")) {

                                ResultSet dbPermanentData = MySQL.executeSearch(" SELECT * FROM `permanent_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                                        + " `status_id` IN ('4','46') ");

                                

                                String date = "";
                                if (dbPermanentData.next() ) {

                                    int traID = dbPermanentData.getInt("id");
                                    
                                    date = dbPermanentData.getString("start_date");

                                    MySQL.executeIUD(" INSERT INTO `employee_job_history` (`employee_id`,`permanent_employee_jobdetails_id`)"
                                            + " VALUES ('" + empID + "' , '" + traID + "') ");

                                }

                                MySQL.executeIUD(" UPDATE `permanent_employee_jobdetails` SET `end_date` = '"+startDate+"', "
                                        + " `time_period` = CONCAT( TIMESTAMPDIFF(YEAR, '" + date + "', '"+startDate+"'), ' years, ', "
                                        + " MOD(TIMESTAMPDIFF(MONTH, '" + date + "', '"+startDate+"'), 12), ' months, and ', "
                                        + " DATEDIFF('"+startDate+"', DATE_ADD('" + date + "', INTERVAL TIMESTAMPDIFF(MONTH, '" + date + "', '"+startDate+"') MONTH)), ' days' ), "
                                        + " `status_id` = '45' WHERE `employee_id` = '" + empID + "' AND `status_id` IN ('4','46') ");

                                

                                MySQL.executeIUD("INSERT INTO `permanent_employee_jobdetails` (`employee_id`, `department_id`, `jobrole_id`, `employment_type_id`, "
                                        + "`start_date`,`status_id`) VALUES ('" + empID + "', '" + departmentMap.get(department) + "', "
                                        + "'" + positionMap.get(position) + "', '" + typeMap.get(type) + "', '" + startDate + "', '46')");

                                

                                JOptionPane.showMessageDialog(this, "Successfully Updated", "Info", JOptionPane.INFORMATION_MESSAGE);
                                employeeProfile.loademploymentDetails();
                                employeeProfile.loadaccessDetails();
                                this.dispose();

                            } else {
                                JOptionPane.showMessageDialog(this, "Can't change employment type. This employee is already permanent", "Info", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField4 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jComboBox4 = new javax.swing.JComboBox<>();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();

        jMenuItem1.setText("Delete");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jTextField4.setText("jTextField4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Job Registration");

        jPanel1.setBackground(new java.awt.Color(238, 243, 253));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Employee Name");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField1.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Employee ID");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField3.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Department");

        jLabel8.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 24)); // NOI18N
        jLabel8.setText("Employee Job Registration");

        jLabel9.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 20)); // NOI18N
        jLabel9.setText("Job Details");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reset.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Position");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jButton2.setBackground(new java.awt.Color(56, 78, 120));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Register");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Type");

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel11.setText("Start Date");

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel13.setText("Time Period");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel14.setText("End Date");

        jDateChooser2.setEnabled(false);
        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        jComboBox4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "day", "month", "year" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField3)
                                .addComponent(jTextField1)
                                .addComponent(jComboBox2, 0, 409, Short.MAX_VALUE)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jFormattedTextField1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel8)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (empID != null) {

            updatejobDetails();

        } else {
            String empID = jTextField3.getText();
            String department = String.valueOf(jComboBox2.getSelectedItem());
            String position = String.valueOf(jComboBox1.getSelectedItem());
            String type = String.valueOf(jComboBox3.getSelectedItem());

            // Validate other fields
            String time = jFormattedTextField1.getText();
            String unit = String.valueOf(jComboBox4.getSelectedItem());
            if (empID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee ID is not found", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (department.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a Department", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (position.equals("Select") || position.isEmpty() || position == null) {
                JOptionPane.showMessageDialog(this, "Please select a Position", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (type.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select an Employment Type", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate Start Date
            Date selectedstartDate = jDateChooser1.getDate();
            if (selectedstartDate == null) {
                JOptionPane.showMessageDialog(this, "Please enter a start date", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Format start date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = dateFormat.format(selectedstartDate);

            // Remove time component for selected start date
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.setTime(selectedstartDate);
            selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
            selectedCalendar.set(Calendar.MINUTE, 0);
            selectedCalendar.set(Calendar.SECOND, 0);
            selectedCalendar.set(Calendar.MILLISECOND, 0);

            // Remove time component for current date
            Date currentDate = new Date();
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            currentCalendar.set(Calendar.SECOND, 0);
            currentCalendar.set(Calendar.MILLISECOND, 0);

            if (selectedCalendar.before(currentCalendar)) {
                JOptionPane.showMessageDialog(this, "The selected start date has already passed. Please select a future date!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (jFormattedTextField1.isEnabled()) {
                if (time.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter time period", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int numericValue = Integer.parseInt(time);

                if (numericValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive numeric value for time period", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

            }

            if (jComboBox4.isEnabled()) {
                if (unit.equals("Select")) {
                    JOptionPane.showMessageDialog(this, "Please select a unit", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            Date selectedendDate = jDateChooser2.getDate();
            // Validate End Date
            if (selectedendDate != null) {
                endDate = dateFormat.format(selectedendDate);

            }

            String timePeriod = time + " " + unit;
            try {
                if (type.equals("Training") || type.equals("Contract")) {

                    ResultSet transitionEmp = MySQL.executeSearch("SELECT * FROM `transition_employee_jobdetails` WHERE `employee_id` = '" + empID + "'");

                    if (transitionEmp.next()) {
                        JOptionPane.showMessageDialog(this, "This employee already Registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MySQL.executeIUD("INSERT INTO `transition_employee_jobdetails` (`employee_id`, `department_id`, `jobrole_id`, `employment_type_id`, "
                                + "`start_date`, `end_date`, `time_period`, `status_id`) VALUES ('" + empID + "', '" + departmentMap.get(department) + "', "
                                + "'" + positionMap.get(position) + "', '" + typeMap.get(type) + "', '" + startDate + "', '" + endDate + "', "
                                + "'" + timePeriod + "', '46')");

                        

                        JOptionPane.showMessageDialog(this, "Successfully Registered", "Info", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                } else {

                    ResultSet permanentEmp = MySQL.executeSearch("SELECT * FROM `permanent_employee_jobdetails` WHERE `employee_id` = '" + empID + "'");

                    if (permanentEmp.next()) {
                        JOptionPane.showMessageDialog(this, "This employee already Registered", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MySQL.executeIUD("INSERT INTO `permanent_employee_jobdetails` (`employee_id`, `department_id`, `jobrole_id`, `employment_type_id`, "
                                + "`start_date`,`status_id`) VALUES ('" + empID + "', '" + departmentMap.get(department) + "', "
                                + "'" + positionMap.get(position) + "', '" + typeMap.get(type) + "', '" + startDate + "', "
                                + " '46')");

                        

                        ResultSet insertPermanentData = MySQL.executeSearch(" SELECT * FROM `permanent_employee_jobdetails` WHERE `employee_id` = '" + empID + "' AND"
                                + " `status_id` = '46' ");

                        

                        JOptionPane.showMessageDialog(this, "Successfully Registered", "Info", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }

                MySQL.executeIUD(" UPDATE `employee` SET `status_id` = '4' WHERE `employee`.`id` = '" + empID + "' ");
                employee.loadEmployeestable("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        reset();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

        String departmentName = String.valueOf(jComboBox2.getSelectedItem());
        loadPosition(departmentName);
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged

        try {

            Date startDate = jDateChooser1.getDate();

            String timePeriodText = jFormattedTextField1.getText();

            int numericValue;

            numericValue = Integer.parseInt(timePeriodText);

            String unit = (String) jComboBox4.getSelectedItem();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            if (unit.equalsIgnoreCase("Day")) {
                calendar.add(Calendar.DAY_OF_YEAR, numericValue);
            } else if (unit.equalsIgnoreCase("Month")) {
                calendar.add(Calendar.MONTH, numericValue);
            } else if (unit.equalsIgnoreCase("Year")) {
                calendar.add(Calendar.YEAR, numericValue);
            }

            Date endDate = calendar.getTime();
            jDateChooser2.setDate(endDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged

        String type = String.valueOf(jComboBox3.getSelectedItem());

        if (type.equals("Permanent")) {

            jFormattedTextField1.setEnabled(false);
            jFormattedTextField1.setText("");

//            jComboBox4.setSelectedIndex(0);
            jComboBox4.setEnabled(false);
            jDateChooser1.setDate(null);
            jDateChooser2.setDate(null);

        } else {

            jFormattedTextField1.setEnabled(true);
            jComboBox4.setEnabled(true);

        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jComboBox1.setSelectedItem(null);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);

        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        jFormattedTextField1.setText("");

    }

}
