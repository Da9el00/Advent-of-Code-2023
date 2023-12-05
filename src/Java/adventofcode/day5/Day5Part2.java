package adventofcode.day5;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Day5Part2 {
    List<StepConverter> stepConverters = new ArrayList<>();
    List<Long> seeds;

    protected void setupSeedStepData(){
        String input = InputReader.readInputAsString("adventofcode/day5/input.txt", this.getClass());

        //Use "\\n\\n" on linux and "\\r\\n" on windows
        List<String> inputSections = List.of(input.split("\\r?\\n\\r?\\n")); //Linux: \\n\\n

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

    private long performSteps(long seed){
        long result = seed;

        for (StepConverter stepConverter: stepConverters) {
            result = stepConverter.useRule(result);
        }
        return result;
    }

    long jump = 10000000;
        //4501055

    public void findResult(){
        setupSeedStepData();
        List<Long> smallestSoFar = new ArrayList<>();

        for (int i = 0; i <= seeds.size() / 2; i += 2) {
            long head = seeds.get(i);
            long tail = head + seeds.get(i + 1);
            long currentHead = head;
            long currentTail = Math.min(tail, currentHead + jump);

            while (currentHead < tail){
                long result = LongStream.range(currentHead , currentTail).boxed().toList().stream().map(this::performSteps).min(Long::compareTo).orElse(-1L);
                smallestSoFar.add(result);
                currentHead = currentTail;
                currentTail = Math.min(tail, currentTail + jump);
            }
            System.out.println("seed : " + i + " done");
        }

        System.out.println("Result of day 5 part 2: " + smallestSoFar.stream().min(Long::compareTo).orElse(-1L));
    }

    public static void main(String[] args) {
        Day5Part2 day5Part2 = new Day5Part2();

        //Seed checked: 2.398.198.298
        Timer.timeChecker(day5Part2::findResult);
    }
}
