package Controller.Manager.HourlyRate;

import Model.Database.DBUtils;
import Model.Database.SalarySqlStatement;

public class HourlyRateController {
    private DBUtils dbUtils;
    private SalarySqlStatement salarySqlStatement;

    public HourlyRateController(){
        dbUtils = new DBUtils();
        salarySqlStatement = new SalarySqlStatement();
    }

    public float getHourlyRate(){
        return salarySqlStatement.getHourlyRate();
    }

    public void updateHourlyRate(float newHourlyRate){
        salarySqlStatement.updateSalary(newHourlyRate);
    }
}
