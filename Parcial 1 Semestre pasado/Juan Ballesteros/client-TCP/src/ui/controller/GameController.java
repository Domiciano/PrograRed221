package ui.controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import com.Receptor;
import com.TCPConnection;
import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ui.view.GameView;
import model.*;

public class GameController implements Receptor.OnMessageListener {

    private GameView view;
    private User user;
    private User opponent;

    private Gson gson;
    private TCPConnection tcp;

    public GameController(GameView view) {
        gson = new Gson();
        this.view = view;
        tcp = TCPConnection.getInstance();
        tcp.getReceptor().setListener(this);
        btnActions();
        sendUser();

    }

    @Override
    public void onMessage(String msg) {
        Generic obj = gson.fromJson(msg, Generic.class);
        switch (obj.getType()) {
            case "Game":

                Game game = gson.fromJson(msg, Game.class);

                if (game.isFull()) {
                    opponent = (game.getUsers()[0].getId().equals(user.getId())) ? game.getUsers()[1]
                            : game.getUsers()[0];
                    user = (game.getUsers()[0].getId().equals(user.getId())) ? game.getUsers()[0] : game.getUsers()[1];
                    user.setGame(game);
                    giveProblems(game);

                    if (user.isFinish() && game.getWinner() == null && !opponent.isFinish()) {
                        gameWindowInWaiting();
                    } else if (game.getWinner() == null && !user.isFinish()) {
                        gameWindowInGame();
                    } else {
                        gameWindowWinner(game);
                    }
                } else {
                    gameWindowNoFull();
                }

                break;

            case "Reject":
                gameWindowReject();
                close();
                break;

            default:
                break;
        }
    }

    private void btnActions() {
        view.getValidateBtn().setOnAction(e -> {

            view.cleanAnswer();

            if (validateAnswer()) {
                user.getProblems().poll();
                if (user.getProblems().isEmpty()) {
                    user.setFinish(true);
                    user.getTime().stopTime();
                } else {
                    user.setNumProblem(user.getNumProblem() + 1);
                }
                Game game = user.getGame();
                String json = gson.toJson(game);
                tcp.getEmisor().sendMessage(json);

            } else {
                alert(AlertType.WARNING, "Incorrect Answer", "Please try again");
            }
        });
    }

    private boolean validateAnswer() {
        try {
            int answer = Integer.parseInt(view.getAnswerTF().getText());
            int result = user.getProblems().peek().getResult();
            if (answer == result) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            alert(AlertType.ERROR, "Number Format Exception", "Pleas write a number");
            return false;
        }

    }

    private void giveProblems(Game game) {
        if (user.getProblems().size() == 0 && !user.isFinish()) {
            Queue<Problem> temp = new LinkedList<>(game.getProblems());
            user.setProblems(temp);
            user.getTime().startTime();
        }
    }

    private void gameWindowNoFull() {
        view.buttonDisable(view.getValidateBtn(), true);
        view.setLabelText(view.getOppStatus(), "Waiting opponent");
        view.setLabelText(view.getProblemLabel(), "You are ready!");
        view.setLabelText(view.getOwnStatus(), "...");
    }

    private void gameWindowWinner(Game game) {

        view.getValidateBtn().setDisable(true);

        if (game.getWinner().getId().equals(user.getId())) {

            view.setLabelText(view.getOppStatus(), "Loser");
            view.setLabelText(view.getProblemLabel(), "You Win!");
            view.setLabelText(view.getOwnStatus(), "Winner");

        } else {
            view.setLabelText(view.getOppStatus(), "Winner");
            view.setLabelText(view.getProblemLabel(), "You Lose :(");
            view.setLabelText(view.getOwnStatus(), "Loser");
        }

        close();
    }

    private void gameWindowInGame() {
        view.buttonDisable(view.getValidateBtn(), false);
        view.setLabelText(view.getOppStatus(), opponent.getStatus());
        view.setLabelText(view.getProblemLabel(), user.getProblems().peek().getText());
        view.setLabelText(view.getOwnStatus(), user.getStatus());
    }

    private void gameWindowInWaiting() {
        view.buttonDisable(view.getValidateBtn(), true);
        view.setLabelText(view.getOppStatus(), opponent.getStatus());
        view.setLabelText(view.getProblemLabel(), "Waiting opponent to finish");
        view.setLabelText(view.getOwnStatus(), user.getStatus());
    }

    private void gameWindowReject() {
        view.buttonDisable(view.getValidateBtn(), true);
        view.setLabelText(view.getOppStatus(), "...");
        view.setLabelText(view.getProblemLabel(), "Game Busy, try latter");
        view.setLabelText(view.getOwnStatus(), "...");
    }

    private void close() {
        new Thread(() -> {
            try {
                Thread.sleep(4000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendUser() {
        String id = UUID.randomUUID().toString();
        user = new User(id);
        String json = gson.toJson(user);
        tcp.getEmisor().sendMessage(json);
    }

    private void alert(AlertType type, String title, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.show();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    alert.close();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
