package model;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    public String type = "Game";

    private User[] users;  
    private User winner;

    private Queue<Problem> problems;

    private static final int NUM_PROBLEMS = 5;

    public Game() {
        users = new User[2];
        problems = new LinkedList<>();

        createProblems(NUM_PROBLEMS);
    }

    private void createProblems(int num){
        for (int i = 0; i < num; i++) {
            Problem p = new Problem();
            problems.add(p);
        }
    }

    public User[] getUsers() {
        return users;
    }
    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public boolean isFull() {
        if(users[0] != null && users[1] != null){
            return true;
        } else {
            return false;
        }
    }

    public Queue<Problem> getProblems() {
        return problems;
    }

    public static int getNumProblems() {
        return NUM_PROBLEMS;
    }

}
