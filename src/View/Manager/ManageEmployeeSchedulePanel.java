/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Manager;

import Controller.Manager.ManageWorkSchedule.AddEmployeeToScheduleController;
import Controller.Manager.ManageWorkSchedule.ConfirmEditEmployeeScheduleController;
import Controller.Manager.ManageWorkSchedule.RemoveEmployeeScheduleController;
import Model.Database.EmployeesInfo;
import View.ViewUtils.*;
import Controller.Manager.SwitchManagerHomePagePanelController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author HieuHoang
 */
public class ManageEmployeeSchedulePanel extends javax.swing.JPanel {
    private Object[][] employeeSchedule;
    private ManagerFrame managerFrame;
    private ConfirmEditEmployeeScheduleController confirmEditEmployeeScheduleController;
    private EmployeesInfo employeesInfo;

    /**
     * Creates new form ManageEmployeeSchedulePanel
     */
    public ManageEmployeeSchedulePanel(Object[][] employeeSchedule, ManagerFrame managerFrame, EmployeesInfo employeesInfo) {
        this.employeeSchedule = employeeSchedule;
        this.managerFrame = managerFrame;
        this.confirmEditEmployeeScheduleController = new ConfirmEditEmployeeScheduleController(managerFrame,employeesInfo);
        this.employeesInfo = employeesInfo;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        AddScheduleButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ConfirmButton = new javax.swing.JButton();
        BackButton = new javax.swing.JButton();

        setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        setPreferredSize(new java.awt.Dimension(1280, 960));

        AddScheduleButton.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        AddScheduleButton.setText("Thêm nhân viên vào lịch làm việc");
        AddScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddScheduleButtonActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                employeeSchedule,
                new String [] {
                        "Tên", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chử nhật", "Xóa"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];

            }
        });
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(5);
        jTable1.setRowHeight(30);
        jTable1.getColumn("Xóa").setCellRenderer(new ButtonRenderer());
        jTable1.getColumn("Xóa").setCellEditor(new EmployeeScheduleButtonEditor(new JCheckBox(),this,
                "Bạn có chắc chắn muốn xóa nhân viên này khỏi lịch làm việc không?"));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 10; i++){
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        ConfirmButton.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        ConfirmButton.setText("Đồng ý thay đổi");
        ConfirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmButtonActionPerformed(evt);
            }
        });

        BackButton.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        BackButton.setText("Quay về");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(ConfirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(AddScheduleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(AddScheduleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                        .addComponent(BackButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ConfirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(436, 436, 436))
        );
    }// </editor-fold>

    private void AddScheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        AddNewEmployeePanel addNewEmployeePanel = new AddNewEmployeePanel();
        int option = JOptionPane.showConfirmDialog(this, addNewEmployeePanel, "Bạn chắc chắn muốn thêm nhân viên vào lịch làm việc?", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.NO_OPTION){
            return;
        }else{
            String[] addEmployee = addNewEmployeePanel.addNewEmployee();
            AddEmployeeToScheduleController addEmployeeToScheduleController = new AddEmployeeToScheduleController(managerFrame, employeesInfo, addEmployee);

            try{
                addEmployeeToScheduleController.updateEmployeeSchedule();
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            }catch (Exception e){
                JOptionPane.showMessageDialog(this, "Không thể thêm nhân viên (kiểm tra lại việc nhập dữ liệu)");
            }
        }
    }

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        SwitchManagerHomePagePanelController switchManagerHomePagePanelController = new SwitchManagerHomePagePanelController(managerFrame);
        if (switchManagerHomePagePanelController.getManagerHomePagePanel() == null){
            throw new NullPointerException("HomePage Panel is null");
        }
        managerFrame.setJPanel(switchManagerHomePagePanelController.getManagerHomePagePanel());
    }

    private void ConfirmButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Bạn chắc chắn không?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if (confirmEditEmployeeScheduleController.updateAndReturnHomePage(employeeSchedule) == null){
                throw new NullPointerException("HomePage Panel is null");
            }
            Object[][] changedEmployeeSchedule = new Object[jTable1.getRowCount()][10];

            for(int i = 0; i < changedEmployeeSchedule.length; i++){
                for (int j = 0; j < 9; j++){
                    changedEmployeeSchedule[i][j] = jTable1.getValueAt(i,j);
                }
            }
            JOptionPane.showMessageDialog(this, "Thay đổi thành công!");
            managerFrame.setContentPane(confirmEditEmployeeScheduleController.updateAndReturnHomePage(changedEmployeeSchedule));
        }
    }

    public void deleteEmployeeSchedule(){
        int selectedRow = jTable1.getSelectedRow();
        String employeeName = (String)employeeSchedule[selectedRow][0];
        RemoveEmployeeScheduleController removeEmployeeScheduleController = new RemoveEmployeeScheduleController(managerFrame, employeesInfo, employeeName);
        managerFrame.setJPanel(removeEmployeeScheduleController.updateEmployeeSchedule());
    }


    // Variables declaration - do not modify
    private javax.swing.JButton AddScheduleButton;
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ConfirmButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}
