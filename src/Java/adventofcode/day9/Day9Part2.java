package adventofcode.day9;

import java.util.ArrayList;

public class Day9Part2 {

    Day9Part1 day9Part1 = new Day9Part1();

    public void findResult(){
        ArrayList<ArrayList<Integer>> report = day9Part1.findReportData();

        int result = report.stream()
                .map(reportData -> day9Part1.findReportValues(reportData, (reportValues, lastValues) ->
                        reportValues.add(0, reportValues.getFirst() - lastValues.getFirst())
                ))
                .map(solvedReports -> day9Part1.getLastElement(solvedReports).getFirst())
                .reduce(0, Integer::sum);

        System.out.println("Result of day 9 part 2 " + result);
    }

    public static void main(String[] args) {
        Day9Part2 day9Part2 = new Day9Part2();
        day9Part2.findResult();
    }
}
