package adventofcode.day1;

import adventofcode.utill.InputReader;
import java.util.List;


public class Day1Part1 {
    private final List<Character> numberChars = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9');

    protected String findCalibrationValue(String calibrationstring){
        String firstDigit = null;
        String lastDigit = null;

        for (int i = 0; i <= calibrationstring.length() - 1; i++) {
            if(firstDigit != null && lastDigit != null){
                break;
            }

            if (numberChars.contains(calibrationstring.charAt(i)) && firstDigit == null){
                firstDigit = String.valueOf(calibrationstring.charAt(i));
            }
            int lastDigitIndex = (calibrationstring.length() - 1) - i;
            if (numberChars.contains(calibrationstring.charAt(lastDigitIndex)) && lastDigit == null){
                lastDigit = String.valueOf(calibrationstring.charAt(lastDigitIndex));
            }
        }
        return firstDigit + lastDigit;
    }

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day1/input.txt", this.getClass());

        int result = input.stream().map(this::findCalibrationValue).map(Integer::parseInt).reduce(0, Integer::sum);

        System.out.println("Result of day 1 part 1: " + result);
    }

    public static void main(String[] args) {
        Day1Part1 day1Part1 = new Day1Part1();

        //Test case
//        String test_1 = "treb7uchet";
//        //Expected 77
//        System.out.println(day1.findCalibrationValue(test_1));

//        String test_2 = "5fourzllbmcgkxsevengkrzkpvcmvgtxlrv6";
//        System.out.println(day1.findCalibrationValue(test_2));

        //Correct: 54697
        day1Part1.findResult();
    }
}