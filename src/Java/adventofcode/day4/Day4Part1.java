package adventofcode.day4;

import adventofcode.utill.InputReader;

import java.util.Arrays;
import java.util.List;

public class Day4Part1 {

    protected long findWinningNumbers(String card){
        List<Integer> winningNumbers = Arrays.stream(card.split(":")[1]
                .split("\\|")[0]
                .trim()
                .replace("  ", " ")
                .split(" "))
                .map(Integer::parseInt)
                .toList();

        List<Integer> gameNumbers = Arrays.stream(card.split(":")[1]
                .split("\\|")[1]
                .trim()
                .replace("  ", " ")
                .split(" "))
                .map(Integer::parseInt)
                .toList();

        long winningNumbersCount = gameNumbers.stream().filter(winningNumbers::contains).count();
        return winningNumbersCount;
    }

    private int getPoints(long winnerCount){
        if(winnerCount == 0){
            return 0;
        }
        if(winnerCount == 1){
            return 1;
        }
        return getPoints(winnerCount - 1) * 2;
    }

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day4/input.txt", this.getClass());

        int result = input.stream()
                .map(this::findWinningNumbers)
                .map(this::getPoints)
                .reduce(0, Integer::sum);

        System.out.println("Result of day 4 part 1: " + result);
    }

    public static void main(String[] args) {
        Day4Part1 day4Part1 = new Day4Part1();
        day4Part1.findResult();
    }
}
