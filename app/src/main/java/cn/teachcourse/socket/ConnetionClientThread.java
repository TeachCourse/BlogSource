package cn.teachcourse.socket;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;


public class ConnetionClientThread extends Thread {

    private WriteDataServer cs = null;

//  private String ip = "localhost";// 设置成服务器IP 

  private String ip = "192.168.11.121";// 设置成服务器IP

//    private String ip = "10.0.2.2";// 设置成服务器IP


    private int port = 8080;


    private String sendMessage = "send_voice";

    private int passedlen = 0;

    private long len = 0;


    //构造方法
    public ConnetionClientThread() {

    }

    //发送链接服务请求
    private boolean createConnection() {
        cs = new WriteDataServer(ip, port);
        try {
            cs.createConnection();
            System.out.println("连接服务器成功!" + "\n");
            return true;
        } catch (Exception e) {
            System.out.println("连接服务器失败!" + "\n" + e);
            return false;
        }

    }

    //发送设置消息
    private void sendMessage() {
        if (cs == null)
            return;
        try {
            cs.sendMessage(sendMessage);
        } catch (Exception e) {
            System.out.println("发送消息失败!" + "\n");
        }
    }

    //获取服务端返回的消息,并将其保存在本地
    private void getMessage() {
        if (cs == null)
            return;
        DataInputStream inputStream = null;
        try {
            inputStream = cs.getMessageStream();
        } catch (Exception e) {
            System.out.println("接收消息缓存错误\n");
            return;
        }

        try {
            //本地保存路径，文件名会自动从服务器端继承而来。  
            String savePath = "storage/sdcard/";
            int bufferSize = 1024 * 10;
            byte[] buf = new byte[bufferSize];
            savePath += inputStream.readUTF();
            len = inputStream.readLong();
            this.setLen(len);
            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savePath)));
            System.out.println("文件的长度为:" + len + "\n");
            System.out.println("开始接收文件!" + "\n");
            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                passedlen += read;
                if (read == -1) {
                    break;
                }
                this.setPassedlen(passedlen);
                //下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比  
                System.out.println("文件接收了" + (passedlen * 100 / len) + "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" + savePath + "\n");

            fileOut.close();
        } catch (Exception e) {
            System.out.println("接收消息错误" + "\n");
            return;
        }
    }

    @Override
    public void run() {
        try {
            if (createConnection()) {
                sendMessage();
                getMessage();
            }
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void close() {
        cs.shutDownConnection();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public int getPassedlen() {
        return passedlen;
    }

    public void setPassedlen(int passedlen) {
        this.passedlen = passedlen;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

}
