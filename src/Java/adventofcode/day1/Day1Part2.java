package adventofcode.day1;

import adventofcode.utill.InputReader;

import java.util.List;
import java.util.Map;

public class Day1Part2 {

    private final Map<String, String> numberMap = Map.of("one", "one1one", "two", "two2two", "three", "three3three", "four", "four4four", "five", "five5five", "six", "six6six", "seven", "seven7seven", "eight", "eight8eight", "nine", "nine9nine");

    private String digitChecker(String input) {
        String changedInput = input;
        for (Map.Entry<String, String> entry : numberMap.entrySet()) {
            changedInput = changedInput.replace(entry.getKey(), entry.getValue());
        }
        return changedInput;
    }

    public void findResult(){
        Day1Part1 day1Part1 = new Day1Part1();
        List<String> input = InputReader.readInputByLine("adventofcode/day1/input.tcv", this.getClass());

        int result = input.stream().map(this::digitChecker).map(day1Part1::findCalibrationValue).map(Integer::parseInt).reduce(0, Integer::sum);

        System.out.println("Result of day 1 part 2: " + result);
    }


    public static void main(String[] args) {
        Day1Part2 day1Part2 = new Day1Part2();

//        System.out.println(day1Part2.digitTransformer("fivefour2hhtprpjndm4"));

        day1Part2.findResult();
    }
}
