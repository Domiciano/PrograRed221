package model;

public class Problem {

    private static final int RANGE_NUM = 20;

    private int num1;
    private int num2;
    private int operator;
    private int result;
    private String text;

    public Problem() {
        num1 = generateRandom(RANGE_NUM, 0);
        num2 = generateRandom(RANGE_NUM, 0);
        operator = generateRandom(4, 0);

        operation(operator);

    }

    private int generateRandom(int max, int min) {
        return (int) (Math.random() * max + min);
    }

    private void operation(int operation) {
        switch (operation) {
            case 0:
                result = num1 + num2;
                text = num1 + " + " + num2;
                break;
            case 1:
                result = num1 - num2;
                text = num1 + " - " + num2;
                break;
            case 2:
                result = num1 * num2;
                text = num1 + " * " + num2;
                break;
            case 3:
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    result = 0;
                }
                text = num1 + " / " + num2;

                break;

            case 4:
                result = num1 ^ num2;
                text = num1 + "^" + num2;
                break;
            default:
                break;
        }
    }

    public int getResult() {
        return result;
    }

    public String getText() {
        return text;
    }

}
