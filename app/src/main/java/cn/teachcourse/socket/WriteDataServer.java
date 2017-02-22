package cn.teachcourse.socket;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by postmaster@teachcourse.cn on 2017/2/14.
 */

public class WriteDataServer {
    private String ip;

    private int port;

    private Socket socket = null;

    private DataOutputStream out = null;

    private DataInputStream getMessageStream = null;

    public WriteDataServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /** */
    /**
     * 创建socket连接
     *
     * @throws Exception exception
     */
    public void createConnection(){
        try {
            socket = new Socket(ip, port);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
//            if (socket != null)
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
        }
    }

    public void sendMessage(String sendMessage) throws Exception {
        try {
            OutputStream os = socket.getOutputStream();
            if (os == null) {
                System.out.print("Socket建立连接失败");
                return;
            }
            out = new DataOutputStream(socket.getOutputStream());
            if (sendMessage.equals("Windows")) {
                out.writeByte(0x1);
                out.flush();
                return;
            }
            if (sendMessage.equals("Unix")) {
                out.writeByte(0x2);
                out.flush();
                return;
            }
            if (sendMessage.equals("Linux")) {
                out.writeByte(0x3);
                out.flush();
            } else {
                out.writeUTF(sendMessage);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (out != null)
                out.close();
            throw e;
        } finally {
        }
    }

    public DataInputStream getMessageStream() throws Exception {
        try {
            getMessageStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            return getMessageStream;
        } catch (Exception e) {
            e.printStackTrace();
            if (getMessageStream != null)
                getMessageStream.close();
            throw e;
        } finally {
        }
    }

    public void shutDownConnection() {
        try {
            if (out != null)
                out.close();
            if (getMessageStream != null)
                getMessageStream.close();
            if (socket != null)
                socket.close();
        } catch (Exception e) {

        }
    }
}
