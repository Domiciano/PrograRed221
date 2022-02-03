package communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import java.io.IOException;

public class TCP {
    // Singleton Design Model
    private static TCP tcp;

    private TCP(int port) {

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static synchronized TCP getTCP(int port) {
        if (tcp == null) {
            tcp = new TCP(port);
            return tcp;
        } else {
            return tcp;
        }
    }

    // Interfaces de comportamiento
    private ServerSocket server;

    private OnHandShake handShakeListener;
    private OnInboxListener onInboxListener;
    private Responder availableListener;

    public void waitRequest(int port) {

        new Thread(() -> {
            try {
                System.out.println("Esperando...");
                Socket socket = server.accept();

                handShakeListener.onHandShake(true);

                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                String line = br.readLine();

                if (line != "rtt\n" || line != "speed\n") {
                    bw.write(onInboxListener.onMessage(line + "\n"));
                    bw.flush();
                } else {
                    Gson gson = new Gson();
                    String[] array = gson.fromJson(line, String[].class);
                    if(array.length == 1024){
                        line = "rtt\n";
                    } else {
                        line = "speed\n";
                    }
                    bw.write(onInboxListener.onMessage(line + "\n"));
                    bw.flush();
                }

                br.close();
                bw.close();

                availableListener.available();
                System.out.println("Cierre de conexión");

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                handShakeListener.onHandShake(false);
                availableListener.available();
            }
        }).start();
    }

    public void setHandShakeListener(OnHandShake handShakeListener) {
        this.handShakeListener = handShakeListener;
    }

    public void setOnInboxListener(OnInboxListener onInboxListener) {
        this.onInboxListener = onInboxListener;
    }

    public void setResponderListener(Responder responder) {
        this.availableListener = responder;
    }

}
