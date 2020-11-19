package common;
//实现头像显示的公用类
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.Font;


public class FriendLabel extends JLabel implements ListCellRenderer {

    private Border lineBorder = BorderFactory.createLineBorder(Color.blue, 1);
    private Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    public FriendLabel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        String s = value.toString();
        int beginIndex = s.indexOf("*src/head/");// 返回指定子字符串在此字符串中第一次出现处的索引。
        //在线用户的头像
        /*public String substring(int beginIndex,
            int endIndex)返回一个新字符串，它是此字符串的一个子字符串。
             该子字符串从指定的 beginIndex 处开始，直到索引 endIndex - 1 处的字符。
              因此，该子字符串的长度为 endIndex-beginIndex。 */
        String picURL = s.substring(beginIndex + 1, s.indexOf("^"));
        //离线用户的头像
        String offLinePicURL = picURL.substring(0, picURL.indexOf("-") + 1) +
                               "2" +
                               picURL.substring(picURL.indexOf("-") + 2,
                                                picURL.length());
        //System.out.println(offLinePicURL);
        int status = Integer.parseInt(s.substring(0, 1));
        this.setText(s.substring(1, beginIndex));
        this.setIcon(new ImageIcon(picURL));
        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }
        if (cellHasFocus) {
            this.setBorder(lineBorder);
        } else {
            this.setBorder(emptyBorder);
            this.setForeground(list.getForeground());
        }
        //如果好友不在线，应将其背景设为灰色
        if (status == 0) {
            this.setIcon(new ImageIcon(offLinePicURL));
        } else if (status == 1) {
            this.setIcon(new ImageIcon(picURL));
        }
        this.setEnabled(list.isEnabled());
        //this.setFont();
        this.setOpaque(true);
        return this;
    }

    private void jbInit() throws Exception {
        this.setFont(new java.awt.Font("宋体", Font.PLAIN, 15));
    }
}