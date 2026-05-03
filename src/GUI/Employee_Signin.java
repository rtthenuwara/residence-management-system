package gui;

//import com.formdev.flatlaf.extras.FlatSVGIcon;
import GUI.AdminDashboard;
import GUI.Employee_Dashboard;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import model.MySQL;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Employee_Signin extends javax.swing.JFrame {

    public static String ed;

    public Employee_Signin() {

//        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/Dashboard.png")));
        initComponents();
        signinImg();

    }

    private void signinImg() {

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/logo1.png"));
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/Dashboard.png"));

        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        jLabel1.setIcon(imageIcon);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Signin");
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 50)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sign In");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Password");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButton1.setBackground(new java.awt.Color(56, 78, 120));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sign In");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jCheckBox1.setText("Admin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox1)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jCheckBox1)
                .addGap(25, 25, 25)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Signin Button

        String username = jTextField1.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        try {

            if (jCheckBox1.isSelected()) {

                if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {

                    ResultSet rs3 = MySQL.executeSearch(" SELECT * FROM `admin` WHERE `username` = '" + username + "' AND `password` = '" + password + "' ");

                    if (rs3.next()) {

                        AdminDashboard admin = new AdminDashboard();
                        this.dispose();
                        admin.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "You are not a Admin", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid username & password", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } else {

                if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                    ResultSet rs1 = MySQL.executeSearch(" SELECT * FROM transition_employee_jobdetails "
                            + "INNER JOIN department ON transition_employee_jobdetails.department_id = department.id "
                            + "INNER JOIN employee ON transition_employee_jobdetails.employee_id = employee.id "
                            + "WHERE username = '" + username + "' AND password = '" + password + "' AND transition_employee_jobdetails.`status_id` = '4' "
                    );

                    ResultSet rs2 = MySQL.executeSearch(" SELECT * FROM permanent_employee_jobdetails "
                            + "INNER JOIN department ON permanent_employee_jobdetails.department_id = department.id "
                            + "INNER JOIN employee ON permanent_employee_jobdetails.employee_id = employee.id "
                            + "WHERE username = '" + username + "' AND password = '" + password + "' AND permanent_employee_jobdetails.`status_id` = '4' ");

                    if (rs1.next()) {

                        String department = rs1.getString("department.name");
                        String employee = rs1.getString("employee.display_name");

                        if (department.equals("HR Department")) {
                            Employee_Dashboard ed = new Employee_Dashboard(employee);
                            ed.setVisible(true);
                            this.dispose();

                        }
                    } else if (rs2.next()) {
                        String department = rs2.getString("department.name");
                        String employee = rs2.getString("employee.display_name");

                        if (department.equals("HR Department")) {
                            Employee_Dashboard ed = new Employee_Dashboard(employee);
                            ed.setVisible(true);
                            this.dispose();

                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "Can't find a employee", "Warning", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid username & password", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee_Signin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
