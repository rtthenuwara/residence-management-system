package GUI;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.File;
import model.MySQL;
import java.sql.ResultSet;
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

public class Employee_Vehicle extends javax.swing.JDialog {

    HashMap<String, String> vehicleTypeMap = new HashMap<>();
    HashMap<String, String> colorMap = new HashMap<>();

    public Employee_Vehicle(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();

        loadvehicleType();
        loadvehicleColor();

    }

    //emp id
    public JTextField getjTextField3() {

        return jTextField3;

    }

    //emp name
    public JTextField getjTextField1() {

        return jTextField1;

    }

    //table
    public JTable getvehicleTable() {

        return jTable1;
    }

    //update button
    public JButton getupdateButton() {

        return jButton3;
    }

    public void loadvehicleType() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `vehicle_type` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String Id = resultSet.getString("id");

                vehicleTypeMap.put(type, Id);

                vector.add(type);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadvehicleColor() {

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `vehicle_color` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                String color = resultSet.getString("color");
                String Id = resultSet.getString("id");

                colorMap.put(color, Id);

                vector.add(color);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadVehicle() {

        String employeeID = jTextField3.getText();

        try {

            ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_vehicles` INNER JOIN "
                    + " `vehicle_type` ON `employee_vehicles`.`vehicle_type_id` = `vehicle_type`.`id` INNER JOIN"
                    + " `vehicle_color` ON `employee_vehicles`.`vehicle_color_id` = `vehicle_color`.`id` WHERE `employee_id` = '" + employeeID + "' ");

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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        jMenuItem1.setText("Delete");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Vehicle");

        jPanel1.setBackground(new java.awt.Color(238, 243, 253));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Employee Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Vehicle Number");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField1.setFocusable(false);

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Employee ID");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField3.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Vehicle Type");

        jLabel8.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 24)); // NOI18N
        jLabel8.setText("Employee Vehicle");

        jLabel9.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 20)); // NOI18N
        jLabel9.setText("Vehicle Information");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/reset.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Vehicle Color");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton2.setBackground(new java.awt.Color(56, 78, 120));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jTable1.setBackground(new java.awt.Color(238, 243, 253));
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
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1)
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        jButton3.setBackground(new java.awt.Color(56, 78, 120));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 20)); // NOI18N
        jLabel10.setText("Registered Vehicles");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
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
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField3)
                                .addComponent(jTextField1)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        int row  = jTable1.getSelectedRow();
        
        String vehicleNumber = String.valueOf(jTable1.getValueAt(row, 0));
        
        try {
            
            MySQL.executeIUD(" DELETE FROM `employee_vehicles` WHERE `vehicle_number` = '"+vehicleNumber+"' ");
            
            JOptionPane.showMessageDialog(this, "Successfully Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadVehicle();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String empID = jTextField3.getText();
        String vehicleNumber = jTextField2.getText();

        String vehicleType = String.valueOf(jComboBox2.getSelectedItem());
        String vehicleColor = String.valueOf(jComboBox1.getSelectedItem());

        if (empID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID is not found", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehicleNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter vehicle number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehicleType.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle type", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehicleColor.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle color", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_vehicles` INNER JOIN"
                        + " `vehicle_type` ON `employee_vehicles`.`vehicle_type_id` = `vehicle_type`.`id` INNER JOIN"
                        + " `vehicle_color` ON `employee_vehicles`.`vehicle_color_id` = `vehicle_color`.`id` WHERE `employee_id` = '" + empID + "' ");

                if (resultSet.next()) {

                    String dbVehicleNumber = resultSet.getString("vehicle_number");
                    String dbVehicleType = resultSet.getString("vehicle_type.type");
                    String dbVehicleColor = resultSet.getString("vehicle_color.color");

                    if (dbVehicleNumber.equals(vehicleNumber) && dbVehicleType.equals(vehicleType) && dbVehicleColor.equals(vehicleColor)) {
                        JOptionPane.showMessageDialog(this, "This vehicle number already registered", "Info", JOptionPane.WARNING_MESSAGE);
                    } else {

                        ResultSet resultSet2 = MySQL.executeSearch(" SELECT * FROM `employee_vehicles` WHERE `vehicle_number` = '" + vehicleNumber + "'  ");

                        if (resultSet2.next()) {
                            JOptionPane.showMessageDialog(this, "This vehicle number already registered", "Info", JOptionPane.WARNING_MESSAGE);
                        } else {
                            MySQL.executeIUD(" INSERT INTO `employee_vehicles` (`employee_id`,`vehicle_number`,`vehicle_type_id`,`vehicle_color_id`) VALUES"
                                    + " ('" + empID + "' , '" + vehicleNumber + "' , '" + vehicleTypeMap.get(vehicleType) + "' , '" + colorMap.get(vehicleColor) + "') ");

                            JOptionPane.showMessageDialog(this, "Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                            loadVehicle();
                            reset();
                        }

                    }
                } else {

                    MySQL.executeIUD(" INSERT INTO `employee_vehicles` (`employee_id`,`vehicle_number`,`vehicle_type_id`,`vehicle_color_id`) VALUES"
                            + " ('" + empID + "' , '" + vehicleNumber + "' , '" + vehicleTypeMap.get(vehicleType) + "' , '" + colorMap.get(vehicleColor) + "') ");

                    JOptionPane.showMessageDialog(this, "Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadVehicle();
                    reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        reset();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String empID = jTextField3.getText();
        String vehicleNumber = jTextField2.getText();

        String vehicleType = String.valueOf(jComboBox2.getSelectedItem());
        String vehicleColor = String.valueOf(jComboBox1.getSelectedItem());

        if (empID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID is not found", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehicleNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter vehicle number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehicleType.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle type", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (vehicleColor.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle color", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                ResultSet resultSet = MySQL.executeSearch(" SELECT * FROM `employee_vehicles` INNER JOIN"
                        + " `vehicle_type` ON `employee_vehicles`.`vehicle_type_id` = `vehicle_type`.`id` INNER JOIN"
                        + " `vehicle_color` ON `employee_vehicles`.`vehicle_color_id` = `vehicle_color`.`id` WHERE "
                        + "  `vehicle_number` = '" + vehicleNumber + "' ");

                if (resultSet.next()) {

                    String dbVehicleNumber = resultSet.getString("vehicle_number");
                    String dbVehicleType = resultSet.getString("vehicle_type.type");
                    String dbVehicleColor = resultSet.getString("vehicle_color.color");

                    if (dbVehicleNumber.equals(vehicleNumber) && dbVehicleType.equals(vehicleType) && dbVehicleColor.equals(vehicleColor)) {
                        JOptionPane.showMessageDialog(this, "This vehicle details already exists", "Info", JOptionPane.WARNING_MESSAGE);
                    } else {

                        MySQL.executeIUD(" UPDATE `employee_vehicles` SET `vehicle_type_id` = '" + vehicleTypeMap.get(vehicleType) + "' , "
                                + " `vehicle_color_id` = '" + colorMap.get(vehicleColor) + "' WHERE `vehicle_number` = '" + vehicleNumber + "' ");

                        JOptionPane.showMessageDialog(this, "Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadVehicle();
                        reset();
                    }
                } else {

                    JOptionPane.showMessageDialog(this, "This vehicle is not Registered", "Warning", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();

        String vehicleNumber = String.valueOf(jTable1.getValueAt(row, 0));
        String vehicleType = String.valueOf(jTable1.getValueAt(row, 1));
        String vehicleColor = String.valueOf(jTable1.getValueAt(row, 2));

        if (evt.getClickCount() == 2) {
            if (row != -1) {

                jTextField2.setText(vehicleNumber);
                jTextField2.setEditable(false);
                jTextField2.setFocusable(false);

                jComboBox2.setSelectedItem(vehicleType);
                jComboBox1.setSelectedItem(vehicleColor);

            }
        }
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jTable1.clearSelection();
    }

}
