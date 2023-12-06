package adventofcode.day6;

import adventofcode.utill.InputReader;

import java.util.Arrays;
import java.util.List;

public class Day6Part1 {

    protected int findWaysToWin(long time, long currentBestDistance){
        int winners = 0;
        for (int i = 0; i <= time; i++) {
            if((i * (time - i)) > currentBestDistance) winners++;
        }
        return winners;
    }

    public void findResult(){
        List<String> input = List.of(InputReader.readInputAsString("adventofcode/day6/input.txt", this.getClass())
                .split("\\r\\n"));

        List<Integer> time = Arrays.stream(input.get(0).split(":")[1].trim().split("\\s+")).map(Integer::parseInt).toList();
        List<Integer> distance = Arrays.stream(input.get(1).split(":")[1].trim().split("\\s+")).map(Integer::parseInt).toList();

        int result = 1;
        for (int i = 0; i < time.size(); i++) {
            result *= findWaysToWin(time.get(i), distance.get(i));
        }

        System.out.println("Result of day 6 part 1: " + result);
    }

    public static void main(String[] args) {
        Day6Part1 day6Part1 = new Day6Part1();

        //result:
        day6Part1.findResult();
    }
}
