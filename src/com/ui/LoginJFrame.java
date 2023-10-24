package com.ui;

import cn.hutool.core.io.FileUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

//package com.ui;
//
//import javax.swing.*;
//
//public class LoginJFrame extends JFrame {
//
//    public LoginJFrame(){
//        this.setSize(488,430);
//        this.setTitle("登录");
//        this.setAlwaysOnTop(true);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(3);
//        this.setVisible(true);
//    }
//}
public class LoginJFrame extends JFrame implements MouseListener {//为什么把User放到不同的包中会报错？...那倒也不会，不过一定要放到包里面
    //创建一个集合存储正确的用户名和密码
    ArrayList<User> list = new ArrayList<>();

    JButton login = new JButton();
    JButton register = new JButton();

    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    JTextField code = new JTextField();
    JLabel rightCode = new JLabel();
    String codeStr = CodeUtil.getCode();

    public LoginJFrame() {

        //读取本地的用户信息
        readUserInfo();

        //初始化界面
        initJFrame();

        //在这个界面中添加内容
        initView();

        //让当前界面显示出来
        this.setVisible(true);
    }

    private void readUserInfo() {
        List<String> list1= FileUtil.readUtf8Lines("C:\\Users\\cjl\\Desktop\\java\\program\\puzzlegame\\userinfo.txt");
        for (String s : list1) {
            String[] arr = s.split("&");
            String username = arr[0].split("=")[1];
            String password = arr[1].split("=")[1];
            list.add(new User(username,password));
        }
    }

    public void initView() {
        //1. 添加用户名文字
        JLabel usernameText = new JLabel(new ImageIcon("image\\login\\用户名.png"));
        usernameText.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameText);

        //2.添加用户名输入框
        username.setBounds(195, 134, 200, 30);
        username.addMouseListener(this);
        this.getContentPane().add(username);

        //3.添加密码文字
        JLabel passwordText = new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordText);

        //4.密码输入框
        password.setBounds(195, 195, 200, 30);
        password.addMouseListener(this);
        this.getContentPane().add(password);

        //验证码提示
        JLabel codeText = new JLabel(new ImageIcon("image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);

        //验证码的输入框
        code.setBounds(195, 256, 100, 30);
        code.addMouseListener(this);
        this.getContentPane().add(code);

        //设置内容
        rightCode.setText(codeStr);
        //位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        rightCode.addMouseListener(this);
        //添加到界面
        this.getContentPane().add(rightCode);

        //5.添加登录按钮
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        //去除按钮的默认边框
        login.setBorderPainted(false);
        //去除按钮的默认背景
        login.setContentAreaFilled(false);
        login.addMouseListener(this);
        this.getContentPane().add(login);

        //6.添加注册按钮
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        //去除按钮的默认边框
        register.setBorderPainted(false);
        //去除按钮的默认背景
        register.setContentAreaFilled(false);
        //给注册按钮绑定鼠标事件
        register.addMouseListener(this);
        this.getContentPane().add(register);

        //7.添加背景图片
        JLabel background = new JLabel(new ImageIcon("image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);
    }


    public void initJFrame() {
        this.setSize(488, 430);//设置宽高
        this.setTitle("拼图游戏 V1.0登录");//设置标题
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
    }


    //要展示用户名或密码错误
    public void showJDialog(String content) {
        //创建一个弹框对象
        JDialog jDialog = new JDialog();
        //给弹框设置大小
        jDialog.setSize(200, 150);
        //让弹框置顶
        jDialog.setAlwaysOnTop(true);
        //让弹框居中
        jDialog.setLocationRelativeTo(null);
        //弹框不关闭永远无法操作下面的界面
        jDialog.setModal(true);

        //创建Jlabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        //让弹框展示出来
        jDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == register) {
            System.out.println("点击了注册按钮");
            //关闭当前的登录界面
            this.setVisible(false);
            //打开注册界面
            new RegisterJFrame(list);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if(source==login){
            login.setIcon(new ImageIcon("image\\login\\登录按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();
        if(source==login){
            login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
            String codeText = code.getText();
            if(codeText.length()==0){
                showJDialog("验证码不能为空");
                return;
            }

            if(username.getText().equals("")|| new String(password.getPassword()).equals("")){
                showJDialog("用户名和密码不能为空");
                return;
            }
            if(!userContains()){
                username.setText("");
                password.setText("");
                showJDialog("用户名和密码错误，请重新输入");
            }
            if(!codeText.equalsIgnoreCase(codeStr)){
                code.setText("");
                codeStr=CodeUtil.getCode();
                rightCode.setText(codeStr);
                showJDialog("验证码错误");
                return;
            }
            else {
                this.setVisible(false);
                new GameJFrame();
            }
        } else if (source==rightCode) {
            codeStr=CodeUtil.getCode();
            rightCode.setText(codeStr);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean userContains(){
        for (int i = 0; i < list.size(); i++) {
            if(username.getText().equals(list.get(i).getUsername())){
                if(new String(password.getPassword()).equals(list.get(i).getPassword())){
                    return true;
                }else return false;
            }
        }
        return false;
    }
}
