package Main;

import javax.swing.*;

import GlobalVariables.GlobalVar;
import ThreadsHandler.threadHandle1;
import ThreadsHandler.threadHandle2;
import ThreadsHandler.threadHandle3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class MainFrame {
    public MainFrame() {
        JFrame f = new JFrame("CLOSE MY EYES ON THE HIGHWAYS");
        f.setSize(1980, 1080);
        f.setLayout(new BorderLayout());
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.decode("#d8d0c1"));

        // _______________________TOP________________________________

        JPanel pHeader = new JPanel(new GridLayout(1, 3));
        JLabel l0 = new JLabel("Fleet Highway Simulator");
        l0.setBackground(Color.decode("#051072"));
        l0.setForeground(Color.decode("#d8d0c4"));
        l0.setFont(new Font("Arial", Font.BOLD, 50));
        l0.setOpaque(true);
        l0.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel l1 = new JLabel("Total Distance Travlled: ");
        l1.setBackground(Color.decode("#051072"));
        l1.setForeground(Color.decode("#d8d0c4"));
        l1.setFont(new Font("Arial", Font.BOLD, 30));
        l1.setOpaque(true);
        l1.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JLabel l2 = new JLabel("0");
        l2.setBackground(Color.decode("#051072"));
        l2.setForeground(Color.decode("#d8d0c4"));
        l2.setFont(new Font("Arial", Font.BOLD, 30));
        l2.setOpaque(true);
        l2.setHorizontalAlignment(SwingConstants.LEFT);
        
        pHeader.add(l0);
        pHeader.add(l1);
        pHeader.add(l2);
        f.add(pHeader, BorderLayout.NORTH);

        // _______________________MIDDLE_______________________________

        // Jpanel with Box Layout
        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));
        JScrollPane sp = new JScrollPane(p0);
        f.add(sp, BorderLayout.CENTER);

        JPanel p1 = new JPanel(new GridLayout(1, 6));

        //_________________ID1_______________________

        new threadHandle1();
        JLabel l11 = new JLabel(threadHandle1.a1.getType());
        JLabel l12 = new JLabel(String.valueOf(threadHandle1.a1.getID()));
        JLabel l13 = new JLabel(String.valueOf(threadHandle1.a1.getDisTvl()));
        JLabel l14 = new JLabel(String.valueOf(threadHandle1.a1.getGasLvl()));
        JLabel l15 = new JLabel(threadHandle1.a1.getStatus());
        JButton bRefuel1 = new JButton("Refuel");
        
        bRefuel1.setBackground(Color.decode("#87c3fa"));
        bRefuel1.setForeground(Color.WHITE);
        bRefuel1.setFont(new Font("Arial", Font.PLAIN, 25));
        l11.setFont(new Font("Arial", Font.PLAIN, 25));
        l12.setFont(new Font("Arial", Font.PLAIN, 25));
        l13.setFont(new Font("Arial", Font.PLAIN, 25));
        l14.setFont(new Font("Arial", Font.PLAIN, 25));
        l15.setFont(new Font("Arial", Font.PLAIN, 25));
        
        p1.add(l11);
        p1.add(l12);
        p1.add(l13);
        p1.add(l14);
        p1.add(l15);
        p1.add(bRefuel1);

        p0.add(p1);
        
        Runnable runGuiForAir = () -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    JOptionPane.showMessageDialog(null, "Error: " + ie.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ie.printStackTrace();
                }
                if (GlobalVar.Play) {
                    SwingUtilities.invokeLater(() -> {
                        l2.setText(String.valueOf(GlobalVar.Total_distance_travelled_by_all));
                        l11.setText(threadHandle1.a1.getType());
                        l12.setText(String.valueOf(threadHandle1.a1.getID()));
                        l13.setText(String.valueOf(threadHandle1.a1.getDisTvl()));
                        l14.setText(String.valueOf(threadHandle1.a1.getGasLvl()));
                        l15.setText(threadHandle1.a1.getStatus());    
                    });
                } else {
                    l14.setText(String.valueOf(threadHandle1.a1.getGasLvl()));
                    l15.setText(threadHandle1.a1.getStatus());    
                }
            }     
        };
        bRefuel1.addActionListener(e -> {
            threadHandle1.a1.setGasLvl(100);
            threadHandle1.a1.setStatus("Running");

        });    
        new Thread(runGuiForAir).start();

        
        // _____________________ID2____________________________
        

        JPanel p2 = new JPanel(new GridLayout(1, 6));

        new threadHandle2();
        JLabel l21 = new JLabel(threadHandle2.s1.getType());
        JLabel l22 = new JLabel(String.valueOf(threadHandle2.s1.getID()));
        JLabel l23 = new JLabel(String.valueOf(threadHandle2.s1.getDisTvl()));
        JLabel l24 = new JLabel(String.valueOf(threadHandle2.s1.getGasLvl()));
        JLabel l25 = new JLabel(threadHandle2.s1.getStatus());
        JButton bRefuel2 = new JButton("Refuel");
        
        bRefuel2.setBackground(Color.decode("#87c3fa"));
        bRefuel2.setForeground(Color.WHITE);
        bRefuel2.setFont(new Font("Arial", Font.PLAIN, 25));
        l21.setFont(new Font("Arial", Font.PLAIN, 25));
        l22.setFont(new Font("Arial", Font.PLAIN, 25));
        l23.setFont(new Font("Arial", Font.PLAIN, 25));
        l24.setFont(new Font("Arial", Font.PLAIN, 25));
        l25.setFont(new Font("Arial", Font.PLAIN, 25));
        
        p2.add(l21);
        p2.add(l22);
        p2.add(l23);
        p2.add(l24);
        p2.add(l25);
        p2.add(bRefuel2);

        p0.add(p2);
        
        Runnable runGuiForShip = () -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    JOptionPane.showMessageDialog(null, "Error: " + ie.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ie.printStackTrace();
                }
                if (GlobalVar.Play) {
                    SwingUtilities.invokeLater(() -> {
                        l2.setText(String.valueOf(GlobalVar.Total_distance_travelled_by_all));
                        l21.setText(threadHandle2.s1.getType());
                        l22.setText(String.valueOf(threadHandle2.s1.getID()));
                        l23.setText(String.valueOf(threadHandle2.s1.getDisTvl()));
                        l24.setText(String.valueOf(threadHandle2.s1.getGasLvl()));
                        l25.setText(threadHandle2.s1.getStatus());    
                    });
                } else {
                    l24.setText(String.valueOf(threadHandle2.s1.getGasLvl()));
                    l25.setText(threadHandle2.s1.getStatus());    
                }
            }     
        };
        bRefuel2.addActionListener(e -> {
            threadHandle2.s1.setGasLvl(100);
            threadHandle2.s1.setStatus("Running");

        });    
        new Thread(runGuiForShip).start();


        // _____________________ID3____________________________
        
        JPanel p3 = new JPanel(new GridLayout(1, 6));

        new threadHandle3();
        JLabel l31 = new JLabel(threadHandle3.t1.getType());
        JLabel l32 = new JLabel(String.valueOf(threadHandle3.t1.getID()));
        JLabel l33 = new JLabel(String.valueOf(threadHandle3.t1.getDisTvl()));
        JLabel l34 = new JLabel(String.valueOf(threadHandle3.t1.getGasLvl()));
        JLabel l35 = new JLabel(threadHandle3.t1.getStatus());
        JButton bRefuel3 = new JButton("Refuel");
        
        bRefuel3.setBackground(Color.decode("#87c3fa"));
        bRefuel3.setForeground(Color.WHITE);
        bRefuel3.setFont(new Font("Arial", Font.PLAIN, 25));
        l31.setFont(new Font("Arial", Font.PLAIN, 25));
        l32.setFont(new Font("Arial", Font.PLAIN, 25));
        l33.setFont(new Font("Arial", Font.PLAIN, 25));
        l34.setFont(new Font("Arial", Font.PLAIN, 25));
        l35.setFont(new Font("Arial", Font.PLAIN, 25));
        
        p3.add(l31);
        p3.add(l32);
        p3.add(l33);
        p3.add(l34);
        p3.add(l35);
        p3.add(bRefuel3);

        p0.add(p3);
        
        Runnable runGuiForTruck = () -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    JOptionPane.showMessageDialog(null, "Error: " + ie.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ie.printStackTrace();
                }
                if (GlobalVar.Play) {
                    SwingUtilities.invokeLater(() -> {
                        l2.setText(String.valueOf(GlobalVar.Total_distance_travelled_by_all));
                        l31.setText(threadHandle3.t1.getType());
                        l32.setText(String.valueOf(threadHandle3.t1.getID()));
                        l33.setText(String.valueOf(threadHandle3.t1.getDisTvl()));
                        l34.setText(String.valueOf(threadHandle3.t1.getGasLvl()));
                        l35.setText(threadHandle3.t1.getStatus());    
                    });
                } else {
                    l34.setText(String.valueOf(threadHandle3.t1.getGasLvl()));
                    l35.setText(threadHandle3.t1.getStatus());    
                }
            }     
        };
        bRefuel3.addActionListener(e -> {
            threadHandle3.t1.setGasLvl(100);
            threadHandle3.t1.setStatus("Running");

        });    
        new Thread(runGuiForTruck).start();



        // ________________________Below__________________________

        JPanel pDown = new JPanel(new GridLayout(1, 4));
        f.add(pDown, BorderLayout.SOUTH);

        JButton bPlay = new JButton("Play");
        bPlay.setBackground(Color.decode("#2f77b1"));
        bPlay.setForeground(Color.WHITE);
        bPlay.setFont(new Font("Arial", Font.BOLD, 32));
        pDown.add(bPlay);

        JButton bPause = new JButton("Pause");
        bPause.setBackground(Color.decode("#2f77b1"));
        bPause.setForeground(Color.WHITE);
        bPause.setFont(new Font("Arial", Font.BOLD, 32));
        pDown.add(bPause);

        JButton bFix = new JButton("Fix");
        bFix.setBackground(Color.decode("#2f77b1"));
        bFix.setForeground(Color.WHITE);
        bFix.setFont(new Font("Arial", Font.BOLD, 32));
        pDown.add(bFix);

        JButton bExit = new JButton("Exit");
        bExit.setBackground(Color.decode("#2f77b1"));
        bExit.setForeground(Color.WHITE);
        bExit.setFont(new Font("Arial", Font.BOLD, 32));
        pDown.add(bExit);
    
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        // ---- Action Listeners ----
        bPlay.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                GlobalVar.Play = true;
            }
        });

        bPause.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                GlobalVar.Play = false;
            }
        });

        bFix.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                GlobalVar.fixed = true;
            }
        });

        bExit.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "See U Soon..", "Exit", JOptionPane.INFORMATION_MESSAGE);
                f.dispose();
                System.exit(0);
            }
        });
    }

    public static void main(String[] arg) {
        new MainFrame();
    }
}
