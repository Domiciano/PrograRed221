package model;

import java.util.ArrayList;
import java.util.Collections;

public class Quiz {

	private ArrayList<Question> questions;

	public Quiz() {

		questions = new ArrayList<>();

	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void initQuestions() {
		ArrayList<String> operators = new ArrayList<>();
		operators.add("+");
		operators.add("-");
		operators.add("/");
		operators.add("x");
		operators.add(operators.get((int)(Math.random() * 3 + 0)));
		Collections.shuffle(operators);
		double result = 0;
		String statement = "";
		int x =  (int )(Math.random() * 1000 + 1);
		int y =  (int)(Math.random() * 1000 + 1);
		for(int i = 0; i<operators.size();i++) {
			switch(operators.get(i)) {

			case "+":
				x =  (int )(Math.random() * 1000 + 1);
				y =  (int)(Math.random() * 1000 + 1);
				statement = x+" + "+y;
				result = x+y;
				Question q = new Question(i+1,statement,result);
				questions.add(q);
				break;

			case "-":		
				x =  (int )(Math.random() * 1000 + 1);
				y =  (int)(Math.random() * 1000 + 1);
				statement = x+" - "+y;
				result = x-y;
				Question q2 = new Question(i+1,statement,result);
				questions.add(q2);

				break;
			case "x":
				x =  (int )(Math.random() * 1000 + 1);
				y =  (int)(Math.random() * 1000 + 1);
				statement = x+" x "+y;
				result = x*y;
				Question q3 = new Question(i+1,statement,result);
				questions.add(q3);
				break;
			case "/":
				double x4 =  (int )(Math.random() * 1000 + 1);
				double y4 =  (int)(Math.random() * 1000 + 1);
				int sx4 = (int)x4;
				int sy4 = (int)y4;
				statement = sx4+" / "+sy4;
				result = (double)Math.round((x4/y4) * 100) / 100;
				Question q4 = new Question(i+1,statement,result);
				questions.add(q4);
				break;	      
			}


		}


	}

}

