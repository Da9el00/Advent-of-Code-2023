package adventofcode.day6;

import adventofcode.utill.InputReader;

import java.util.List;

public class Day6Part2 {

    Day6Part1 day6Part1 = new Day6Part1();

    public void findResult(){
        List<String> input = List.of(InputReader.readInputAsString("adventofcode/day6/input.txt", this.getClass())
                .split("\\r\\n"));

        long time = Long.parseLong(input.get(0).split(":")[1].trim().replaceAll("\\s+", ""));
        long currentWinningDistance = Long.parseLong(input.get(1).split(":")[1].trim().replaceAll("\\s+", ""));

        int result = day6Part1.findWaysToWin(time, currentWinningDistance);

        System.out.println("Result of day 6 part 1: " + result);
    }

    public static void main(String[] args) {
        Day6Part2 day6Part2 = new Day6Part2();
        // too low: 23890798
        day6Part2.findResult();
    }
}
