package Controller.Manager;

import View.Manager.ManagerFrame;
import View.Manager.ManagerHomePagePanel;

import javax.swing.*;

public class SwitchManagerHomePagePanelController {
    private ManagerFrame frame;
    public SwitchManagerHomePagePanelController(ManagerFrame frame){
        if (frame == null){
            throw new NullPointerException("Frame is null");
        }
        this.frame = frame;
    }

    public JPanel getManagerHomePagePanel(){
        return new ManagerHomePagePanel(frame, frame.getGetEmployeesInfo());
    }
}
