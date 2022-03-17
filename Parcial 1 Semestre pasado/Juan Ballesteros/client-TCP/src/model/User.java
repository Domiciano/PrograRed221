package model;

import java.util.LinkedList;
import java.util.Queue;

public class User {
	
	public String type = "User";
	private String id;
	private int numProblem;

	private Queue<Problem> problems;
	private boolean finish;
	private CounterTime time;
	
	private transient Game game;
	
	public User() {
		numProblem = 1;
		problems = new LinkedList<>();
		finish =  false;
		time = new CounterTime();
	}
	
	public User(String id) {
		this.id = id;
		numProblem = 1;
		problems = new LinkedList<>();
	}
	
	public String getId() {
		return id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getStatus() {
		String status = Game.getNumProblems() + " x " + numProblem;
		return status;
	}

	public int getNumProblem() {
		return numProblem;
	}

	public void setNumProblem(int numProblem) {
		this.numProblem = numProblem;
	}

	public Queue<Problem> getProblems() {
		return problems;
	}

	public void setProblems(Queue<Problem> problems) {
		this.problems = problems;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public CounterTime getTime() {
		return time;
	}

	public void setTime(CounterTime time) {
		this.time = time;
	}

}
