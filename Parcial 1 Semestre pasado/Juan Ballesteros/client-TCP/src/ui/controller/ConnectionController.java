package ui.controller;

import com.*;

import javafx.application.Platform;
import ui.view.ConnectionView;
import ui.view.GameView;

public class ConnectionController implements TCPConnection.OnConnectionListener {

    private ConnectionView view;
    private TCPConnection tcp;

    public ConnectionController(ConnectionView view) {
        this.view = view;
        btnActions();
    }

    private void btnActions() {
        tcp = TCPConnection.getInstance();
        tcp.setConnectionListener(this);

        view.getCloseBtn().setOnAction(e -> {
            System.exit(0);
        });

        view.getLocalBtn().setOnAction(e -> {
            Platform.runLater(() -> {
                view.getIpTF().setText("127.0.0.1");
                view.getPortTF().setText("5000");
            });

        });

        view.getPlayBtn().setOnAction(e -> {
            try {
                String ip = view.getIpTF().getText();
                String puerto = view.getPortTF().getText();
                int puertoInt = Integer.parseInt(puerto);
                tcp.setIp(ip);
                tcp.setPuerto(puertoInt);
                tcp.start();
            } catch (NumberFormatException num) {
                view.warmingLabelVisible();
                view.setLabelText(view.getWarmingLabel(), view.getWarmingLabel().getText() + " Error in port number");
            }
        });
    }

    @Override
    public void onConnection(boolean connected) {
        if (connected) {
            Platform.runLater(() -> {
                GameView gameView = new GameView();
                view.close();
                gameView.show();
            });
        } else {
            if (view.getWarmingLabel().isVisible()) {
                view.setLabelText(view.getWarmingLabel(), view.getWarmingLabel().getText() + " Error to connect");
            } else {
                view.warmingLabelVisible();
            }
        }
    }

}
