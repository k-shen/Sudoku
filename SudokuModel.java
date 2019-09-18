import java.util.ArrayList;
import java.util.Collections;

public class SudokuModel {
    public static int[][] solution;
    public static int[][] game;

    public SudokuModel() {
        solution = new int[9][9];
        generateSolution(0);
        game = copy(solution);
        generateGame();
    }

    private int[][] copy(int[][] game) {
        int[][] copy = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                copy[y][x] = game[y][x];
        }
        return copy;
    }

    private int[][] generateSolution(int index) {
        if (index > 80)
            return solution;

        int x = index % 9;
        int y = index / 9;

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) numbers.add(i);
        Collections.shuffle(numbers);

        while (numbers.size() > 0) {
            int number = nextPossibleNum(solution,x, y, numbers);
            if (number == -1)
                return null;

            solution[y][x] = number;
            int[][] tmpGame = generateSolution(index + 1);
            if (tmpGame != null)
                return tmpGame;
            solution[y][x] = 0;

        }

        return null;
    }


    private int nextPossibleNum(int[][] puzzle,int x, int y, ArrayList<Integer> numbers) {
        while (numbers.size() > 0) {
            int num = numbers.remove(0);
            if (yCheck(puzzle,x, num) && xCheck(puzzle,y, num) && blockCheck(puzzle,x, y, num)) {
                return num;
            }
        }
        return -1;
    }

    private boolean yCheck(int[][] puzzle,int x, int number) {
        for (int y = 0; y < 9; y++) {
            if (puzzle[y][x] == number)
                return false;
        }
        return true;
    }

    private boolean xCheck(int[][] puzzle,int y, int number) {
        for (int x = 0; x < 9; x++) {
            if (puzzle[y][x] == number)
                return false;
        }
        return true;
    }

    private boolean blockCheck(int[][] puzzle,int x, int y, int number) {
        int x1 = x < 3 ? 0 : x < 6 ? 3 : 6;
        int y1 = y < 3 ? 0 : y < 6 ? 3 : 6;
        for (int yy = y1; yy < y1 + 3; yy++) {
            for (int xx = x1; xx < x1 + 3; xx++) {
                if (puzzle[yy][xx] == number)
                    return false;
            }
        }
        return true;
    }


    private void generateGame() {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 81; i++)
            positions.add(i);
        Collections.shuffle(positions);
        generateGame(positions);
    }

    private void generateGame(ArrayList<Integer> positions) {
        while (positions.size() > 0) {
            int position = positions.remove(0);
            int x = position % 9;
            int y = position / 9;
            int temp = game[y][x];
            game[y][x] = 0;
            if (!isValid()) {
                game[y][x] = temp;
            }
        }
    }

    private boolean isValid() {
        return isValid(0, new int[] { 0 });
    }


    private boolean isValid(int index, int[] numberOfSolutions) {
        if (index > 80)
            return ++numberOfSolutions[0] == 1;

        int x = index % 9;
        int y = index / 9;

        if (game[y][x] == 0) {
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++)
                numbers.add(i);

            while (numbers.size() > 0) {
                int number = nextPossibleNum(game, x, y, numbers);
                if (number == -1)
                    break;
                game[y][x] = number;

                if (!isValid(index + 1, numberOfSolutions)) {
                    game[y][x] = 0;
                    return false;
                }
                game[y][x] = 0;
            }
        } else if (!isValid( index + 1, numberOfSolutions))
            return false;

        return true;
    }


}
