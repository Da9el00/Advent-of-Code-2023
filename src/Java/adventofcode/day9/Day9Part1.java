package adventofcode.day9;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.*;
import java.util.stream.Collectors;

public class Day9Part1 {

    protected ArrayList<ArrayList<Integer>> findReportValues(ArrayList<Integer> reportValues, ValueAdder valueAdder){
        //End condition
        if (reportValues.stream().allMatch(value -> value == 0)) {
            return new ArrayList<>(List.of(reportValues));
        }

        ArrayList<Integer> nextValues = new ArrayList<>();

        for (int i = 0; i < (reportValues.size() - 1); i++) {
            nextValues.add(reportValues.get(i + 1) - reportValues.get(i));
        }
        ArrayList<ArrayList<Integer>> result = findReportValues(nextValues, valueAdder);
        if(result.size() >= 1){
            List<Integer> lastValues = getLastElement(result);
            valueAdder.addValues(reportValues, lastValues);
        }
        result.add(reportValues);

        return result;
    }

    protected  <T> T getLastElement(List<T> inputList) {
        if (inputList != null && !inputList.isEmpty()) {
            return inputList.get(inputList.size() - 1);
        } else {
            throw new IllegalArgumentException("Input list is null or empty");
        }
    }

    protected ArrayList<ArrayList<Integer>> findReportData() {
        List<String> input = InputReader.readInputByLine("adventofcode/day9/input.txt", this.getClass());
        return input.stream()
                .map(reportValues -> Arrays.stream(reportValues.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void findResult(){
        ArrayList<ArrayList<Integer>> report = findReportData();

        int result = report.stream()
                .map(reportData -> findReportValues(reportData, (reportValues, lastValues) ->
                        reportValues.add(getLastElement(reportValues) + getLastElement(lastValues))
                ))
                .map(solvedReports -> getLastElement(getLastElement(solvedReports)))
                .reduce(0, Integer::sum);

        System.out.println("Result of day 9 part 1: " + result);
    }

    public static void main(String[] args) {
        Day9Part1 day9Part1 = new Day9Part1();
        Timer.timeChecker(day9Part1::findResult);
    }
}

interface ValueAdder {
    void addValues(ArrayList<Integer> reportValues, List<Integer> lastValues);
}