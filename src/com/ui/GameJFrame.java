package com.ui;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    String path = "image\\animal\\animal3\\";
    int[][] data = new int[4][4];
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    int x = 0;
    int y = 0;
    int step = 0;

    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenu saveJMenu = new JMenu("存档");
    JMenu loadJMenu = new JMenu("读档");

    JMenuItem saveItem0 = new JMenuItem("存档0(空)");
    JMenuItem saveItem1 = new JMenuItem("存档1(空)");
    JMenuItem saveItem2 = new JMenuItem("存档2(空)");
    JMenuItem saveItem3 = new JMenuItem("存档3(空)");
    JMenuItem saveItem4 = new JMenuItem("存档4(空)");

    JMenuItem loadItem0 = new JMenuItem("读档0(空)");
    JMenuItem loadItem1 = new JMenuItem("读档1(空)");
    JMenuItem loadItem2 = new JMenuItem("读档2(空)");
    JMenuItem loadItem3 = new JMenuItem("读档3(空)");
    JMenuItem loadItem4 = new JMenuItem("读档4(空)");

    JMenuItem accountItem = new JMenuItem("公众号");

    JMenuItem girl = new JMenuItem("女子");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");

    public GameJFrame() {

        //初始化界面
        initJFrame();

        //初始化菜单
        initMenu();

        //初始化数据
        initData();

        //初始化图片
        initImage();

        this.setVisible(true);
    }

    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        int number = 0;
        for (int i = 0; i < 4; i++) {
            for (int i1 = 0; i1 < 4; i1++) {
                if (tempArr[number] == 0) {
                    x = i;
                    y = i1;
                }
                data[i][i1] = tempArr[number];
                number++;
            }
        }
    }

    private void initImage() {
        this.getContentPane().removeAll();
        if (victory()) {
            ImageIcon victoryIcon = new ImageIcon("C:\\Users\\cjl\\Desktop\\program\\puzzlegame\\image\\win.png");
            JLabel label = new JLabel(victoryIcon);
            label.setBounds(203, 283, 197, 73);
            this.getContentPane().add(label);
        }
        JLabel stepcount = new JLabel("步数：" + step);
        stepcount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepcount);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageIcon imageIcon = new ImageIcon(path + data[i][j] + ".jpg");
                JLabel label = new JLabel(imageIcon);
                label.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                label.setBorder(new BevelBorder(1));
                this.getContentPane().add(label);
            }
        }
        ImageIcon bg = new ImageIcon("image\\background.png");
        JLabel background = new JLabel(bg);
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);
        this.getContentPane().repaint();
    }

    private void initJFrame() {
        this.setSize(603, 680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    private void initMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu changeImage = new JMenu("更换图片");

        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        saveItem0.addActionListener(this);
        saveItem1.addActionListener(this);
        saveItem2.addActionListener(this);
        saveItem3.addActionListener(this);
        saveItem4.addActionListener(this);
        loadItem0.addActionListener(this);
        loadItem1.addActionListener(this);
        loadItem2.addActionListener(this);
        loadItem3.addActionListener(this);
        loadItem4.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        //把5个存档，添加到saveJMenu中
        saveJMenu.add(saveItem0);
        saveJMenu.add(saveItem1);
        saveJMenu.add(saveItem2);
        saveJMenu.add(saveItem3);
        saveJMenu.add(saveItem4);

        //把5个读档，添加到loadJMenu中
        loadJMenu.add(loadItem0);
        loadJMenu.add(loadItem1);
        loadJMenu.add(loadItem2);
        loadJMenu.add(loadItem3);
        loadJMenu.add(loadItem4);

        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        functionJMenu.add(changeImage);
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);
        functionJMenu.add(saveJMenu);
        functionJMenu.add(loadJMenu);

        aboutJMenu.add(accountItem);

        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //读取存档信息，修改菜单上表示的内容
        getGameInfo();

        this.setJMenuBar(jMenuBar);
    }

    private void getGameInfo() {
        File file = new File("C:\\Users\\cjl\\Desktop\\java\\program\\puzzlegame\\save");
        File[] files = file.listFiles();
        for (File f : files) {
            GameInfo gi = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                gi = (GameInfo) ois.readObject();
                ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            int infoStep = gi.getStep();
            int index = f.getName().charAt(4) - '0';
            saveJMenu.getItem(index).setText("存档" + index + "(" + infoStep + "步)");
            loadJMenu.getItem(index).setText("读档" + index + "(" + infoStep + "步)");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) return;
        if (e.getKeyCode() == 65) {
            this.getContentPane().removeAll();
            ImageIcon all = new ImageIcon(path + "all.jpg");
            JLabel label = new JLabel(all);
            label.setBounds(83, 134, 420, 420);
            this.getContentPane().add(label);
            ImageIcon bg = new ImageIcon("image\\background.png");
            JLabel background = new JLabel(bg);
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (victory()) return;
        int code = e.getKeyCode();
        if (code == 37) {
            if (y == 3) return;
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
        } else if (code == 38) {
            if (x == 3) return;
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
        } else if (code == 39) {
            if (y == 0) return;
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
        } else if (code == 40) {
            if (x == 0) return;
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        } else return;
        initImage();
    }

    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Random r=new Random();
        if (obj == replayItem) {
            step = 0;
            initData();
            initImage();
        } else if (obj == reloginItem) {
            this.setVisible(false);
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.exit(0);
        } else if (obj == accountItem) {
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("image/about.png"));
            jLabel.setBounds(0, 0, 258, 258);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344, 344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        } else if (obj==girl) {
            path="image/girl/girl"+(r.nextInt(13)+1)+"\\";
            step = 0;
            initData();
            initImage();
        } else if (obj==animal) {
            path="image/animal/animal"+(r.nextInt(8)+1)+"\\";
            step = 0;
            initData();
            initImage();
        } else if (obj==sport) {
            path="image/sport/sport"+(r.nextInt(10)+1)+"\\";
            step = 0;
            initData();
            initImage();
        } else if (obj==saveItem0||obj==saveItem1||obj==saveItem2||obj==saveItem3||obj==saveItem4) {
            System.out.println("123");
            JMenuItem item = (JMenuItem) obj;
            int index = item.getText().charAt(2) - '0';
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream("save\\save"+index+".data"));
                IoUtil.writeObj(oos,true,new GameInfo(data,path,x,y,step));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            item.setText("存档" + index + "(" + step + "步)");
            loadJMenu.getItem(index).setText("读档" + index + "(" + step + "步)");
        } else if (obj==loadItem0||obj==loadItem1||obj==loadItem2||obj==loadItem3||obj==loadItem4) {
            JMenuItem item = (JMenuItem) obj;
            int index = item.getText().charAt(2) - '0';
            GameInfo gi;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save\\save"+index+".data"));
                gi = (GameInfo) ois.readObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            data = gi.getData();
            path = gi.getPath();
            step=gi.getStep();
            x=gi.getX();
            y=gi.getY();
            initImage();
        }
    }
}
