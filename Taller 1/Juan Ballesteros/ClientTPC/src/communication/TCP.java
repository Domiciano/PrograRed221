package communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

import java.io.IOException;

public class TCP {
    // Singleton Design Model
    private static TCP tcp;

    private TCP() {
    }

    public static synchronized TCP getTCP() {
        if (tcp == null) {
            tcp = new TCP();
            return tcp;
        } else {
            return tcp;
        }
    }

    // Object TCP Actions

    private Socket socket;

    private BufferedReader br;
    private BufferedWriter bw;

    // Interfaces de comportamiento
    private OnHandShake handShakeListener;
    private OnInboxListener onInboxListener;

    public void sendRequest(String ip, int port, String request) {

        new Thread(() -> {

            try {
                socket = new Socket(ip, port);
                handShakeListener.onHandShake(true);

                // Inicia Lectura
                InputStream is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                OutputStream os = socket.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os));

                bw.write(request + "\n");
                bw.flush();

                String line = br.readLine();
                onInboxListener.onMessage(line);

                bw.close();
                br.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                handShakeListener.onHandShake(false);
            }
        }).start();
    }

    public void sendRequestFile(String ip, int port, String[] array) {

        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                handShakeListener.onHandShake(true);

                // Inicia Lectura
                InputStream is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                OutputStream os = socket.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os));

                Gson gson =  new Gson();
                String jsonString = gson.toJson(array);
                
                long start = System.currentTimeMillis();

                bw.write(jsonString + "\n");
                bw.flush();
                String line = br.readLine();

                long time = System.currentTimeMillis() - start;

                if(array.length == 1024){
                    onInboxListener.onMessage((time) + " milis");
                } else {
                    double transferRate = (double) ((8192*2)/(time));
                    onInboxListener.onMessage((transferRate) + " KB/s");
                }
                

                bw.close();
                br.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                handShakeListener.onHandShake(false);
            }
        }).start();
    }

    public void setHandShakeListener(OnHandShake handShakeListener) {
        this.handShakeListener = handShakeListener;
    }

    public void setOnInboxListener(OnInboxListener onInboxListener) {
        this.onInboxListener = onInboxListener;
    }

}
