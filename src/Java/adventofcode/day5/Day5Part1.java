package adventofcode.day5;

import adventofcode.utill.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5Part1 {

    List<StepConverter> stepConverters = new ArrayList<>();
    List<Long> seeds;

    protected void setupSeedStepData(){
        String input = InputReader.readInputAsString("adventofcode/day5/input.txt", this.getClass());

        //Use "\\n\\n" on linux and "\\r\\n" on windows
        List<String> inputSections = List.of(input.split("\\r?\\n\\r?\\n"));

        //Seeds
        seeds = Arrays.stream(inputSections.get(0).split(":")[1].trim().split(" "))
                .map(Long::parseLong).toList();

        //Build seed steps by rules
        for (int i = 1; i < inputSections.size(); i++) {
            List<String> sectionLines = new ArrayList<>(List.of(inputSections.get(i).split("\\r\\n"))); // windwos: "\\r\\n" linux "\\n"
            StepConverter stepConverter = new StepConverter();
            for (int j = 1; j < sectionLines.size(); j++) {
                //Create and add each rule to the stepConverter
                stepConverter.addRule(Arrays.stream(sectionLines.get(j).split(" ")).map(Long::parseLong).toList());
            }
            stepConverters.add(stepConverter);
        }
    }

    protected long performSteps(long seed){
        long result = seed;

        for (StepConverter stepConverter: stepConverters) {
            result = stepConverter.useRule(result);
        }
        return result;
    }

    public void findResult(){
        setupSeedStepData();

        //-1 is returned if no data is present
        long result = seeds.stream().map(this::performSteps).min(Long::compareTo).orElse(-1L);

        System.out.println("Result of day 5 part 1: " + result);
    }

    public static void main(String[] args) {
        Day5Part1 day5Part1 = new Day5Part1();

        //result
        day5Part1.findResult();
    }
}
