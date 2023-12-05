package adventofcode.day5;

import java.util.ArrayList;
import java.util.List;

public class StepConverter {

    private final List<List<Long>> rules = new ArrayList<>();

    public void addRule(List<Long> rule){
        rules.add(rule);
    }

    private static boolean isWithinRange(long number, long lowerBound, long upperBound) {
        return number >= lowerBound && number < upperBound;
    }

    public long useRule(long input){

        for (List<Long> rule: rules) {
            long destinationRangeStart = rule.get(0);
            long sourceRangeStart = rule.get(1);
            long range = rule.get(2);

            if(isWithinRange(input, sourceRangeStart, sourceRangeStart + range)){
                return (input - sourceRangeStart) + destinationRangeStart;
            }
        }
        return input;
    }

    @Override
    public String toString() {
        return "StepConverter{" +
                "rules=" + rules +
                '}';
    }
}
