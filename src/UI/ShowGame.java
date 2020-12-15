package UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.io.*;
import java.util.ArrayList;

public class ShowGame extends JPanel{
	
	public ShowGame() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setBounds(0, 117, 313, 382);
		JButton btn1=new JButton("刷新游戏");
		btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ShowGame.this.repaint(); 
               gamelist();
               ShowGame.this.add(btn1);
            }
        });
		btn1.setBounds(5, 5, 100, 30);
		this.add(btn1);
			
	}
	class Game extends JLabel {
        private boolean isSupported;
        private String gamename;

        public Game(String gamename,String gamelink) {
            // TODO Auto-generated constructor stub
            this.gamename=gamename;

            try {
                this.isSupported = Desktop.isDesktopSupported()
                                   && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
            } catch (Exception e) {
                this.isSupported = false;
            }
            setText(false);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setText(isSupported);
                    if (isSupported)
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(MouseEvent e) {
                    setText(false);
                }
                public void mouseClicked(MouseEvent e) {

                	try {
                        Desktop.getDesktop().browse(new java.net.URI(gamelink));
                    } 
                     catch (Exception ex) {
                    }
                }
            });
        }
        private void setText(boolean b) {
            if (!b)
                setText( gamename);
            else
                setText("<html><font color=blue><u>" + gamename);
        }
	}
	

	public void gamelist() {
		this.removeAll();
		ArrayList<String> list = new ArrayList<String>();
		try { 
            BufferedReader reader = new BufferedReader(new FileReader("src/file/games.csv"));
            String line = null; 
            int i =0;
            int j=(int)(java.lang.Math.random()*5000);
            while(i<j){ 
            	reader.readLine();
            	i++;
            } 
            for(int n=0;n<20;n++) {
            line=reader.readLine();
            String item[] = line.split(",");
            list.add(item[0]);
            list.add(item[1]);}
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 


		
		Game game2 = new Game(list.get(3),list.get(2));
        game2.setFont(new Font("黑体",Font.BOLD,18));
        game2.setForeground(Color.RED);
        game2.setBounds(157,0,155, 35);
		Game game3 = new Game(list.get(5),list.get(4));
        game3.setFont(new Font("黑体",Font.BOLD,18));
        game3.setForeground(Color.PINK);
        game3.setBounds(0,35,155, 35);
		Game game4 = new Game(list.get(7),list.get(6));
        game4.setFont(new Font("黑体",Font.BOLD,18));
        game4.setForeground(Color.RED);
        game4.setBounds(157,35,155, 35);
		Game game5 = new Game(list.get(9),list.get(8));
        game5.setFont(new Font("黑体",Font.BOLD,18));
        game5.setForeground(Color.GREEN);
        game5.setBounds(0,70,155, 35);
		Game game6 = new Game(list.get(11),list.get(10));
        game6.setFont(new Font("黑体",Font.BOLD,18));
        game6.setForeground(Color.ORANGE);
        game6.setBounds(157,70,155, 35);
		Game game7 = new Game(list.get(13),list.get(12));
        game7.setFont(new Font("黑体",Font.BOLD,18));
        game7.setForeground(Color.BLUE);
        game7.setBounds(0,105,155, 35);
		Game game8 = new Game(list.get(15),list.get(14));
        game8.setFont(new Font("黑体",Font.BOLD,18));
        game8.setForeground(Color.ORANGE);
        game8.setBounds(157,105,155, 35);
		Game game9 = new Game(list.get(17),list.get(16));
        game9.setFont(new Font("黑体",Font.BOLD,18));
        game9.setForeground(Color.GREEN);
        game9.setBounds(0,140,155, 35);
		Game game10 =new Game(list.get(19),list.get(18));
        game10.setFont(new Font("黑体",Font.BOLD,18));
        game10.setForeground(Color.PINK);
        game10.setBounds(157,140,155, 35);
		Game game11= new Game(list.get(21),list.get(20));
        game11.setFont(new Font("黑体",Font.BOLD,18));
        game11.setForeground(Color.BLUE);
        game11.setBounds(0,175,155, 35);
		Game game12 = new Game(list.get(23),list.get(22));
        game12.setFont(new Font("黑体",Font.BOLD,18));
        game12.setForeground(Color.RED);
        game12.setBounds(157,175,155, 35);
		Game game13 = new Game(list.get(25),list.get(24));
        game13.setFont(new Font("黑体",Font.BOLD,18));
        game13.setForeground(Color.CYAN);
        game13.setBounds(0,210,155, 35);
		Game game14 = new Game(list.get(27),list.get(26));
        game14.setFont(new Font("黑体",Font.BOLD,18));
        game14.setForeground(Color.MAGENTA);
        game14.setBounds(157,210,155, 35);
		Game game15 = new Game(list.get(29),list.get(28));
        game15.setFont(new Font("黑体",Font.BOLD,18));
        game15.setForeground(Color.BLACK);
        game15.setBounds(0,245,155, 35);
		Game game16 = new Game(list.get(31),list.get(30));
        game16.setFont(new Font("黑体",Font.BOLD,18));
        game16.setForeground(Color.GREEN);
        game16.setBounds(157,245,155, 35);
		Game game17 = new Game(list.get(33),list.get(32));
        game17.setFont(new Font("黑体",Font.BOLD,18));
        game17.setForeground(Color.RED);
        game17.setBounds(0,280,155, 35);
		Game game18 = new Game(list.get(35),list.get(34));
        game18.setFont(new Font("黑体",Font.BOLD,18));
        game18.setForeground(Color.PINK);
        game18.setBounds(157,280,155, 35);
		Game game19 = new Game(list.get(37),list.get(36));
        game19.setFont(new Font("黑体",Font.BOLD,18));
        game19.setForeground(Color.MAGENTA);
        game19.setBounds(0,315,155, 35);
		Game game20 = new Game(list.get(39),list.get(38));
        game20.setFont(new Font("黑体",Font.BOLD,18));
        game20.setForeground(Color.ORANGE);
        game20.setBounds(157,315,155, 35);
        

        this.add(game2);
        this.add(game3);
        this.add(game4);
        this.add(game5);
        this.add(game6);
        this.add(game7);
        this.add(game8);
        this.add(game9);
        this.add(game10);
        this.add(game11);
        this.add(game12);
        this.add(game13);
        this.add(game14);
        this.add(game15);
        this.add(game16);
        this.add(game17);
        this.add(game18);
        this.add(game19);
        this.add(game20);
	}
	  
		
	}
	

	

