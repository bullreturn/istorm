package UI;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

//显示时间的公用类
public class ShowTimeTask extends java.util.TimerTask {
    private JLabel showTime = null;

    public ShowTimeTask(JLabel showTime) {
        this.showTime= showTime;
    }
    public void run() {
        Date time = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String timeInfo = format.format(time);
        showTime.setText("现在时间：" + timeInfo + "    ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}