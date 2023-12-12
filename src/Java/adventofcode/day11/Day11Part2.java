package adventofcode.day11;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11Part2 {
    ArrayList<Integer> expandPointsX;
    ArrayList<Integer> expandPointsY;

    private int calculateManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private void universeExpand(List<ArrayList<Character>> universe){
        expandPointsX = IntStream.range(0, universe.getFirst().size()).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        expandPointsY = IntStream.range(0, universe.size()).boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < universe.size(); i++) {
            for (int j = 0; j < universe.get(i).size(); j++) {
                if (universe.get(i).get(j) == '#') {
                    expandPointsX.remove(Integer.valueOf(j));
                    expandPointsY.remove(Integer.valueOf(i));
                }
            }
        }
    }

    public void findResult(){
        List<ArrayList<Character>> map = InputReader.readInputByLine("adventofcode/day11/input.txt", this.getClass())
                .stream()
                .map(line -> line.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));

        universeExpand(map);

        List<GalaxyPos> galaxyPosistions = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                char currentInput = map.get(i).get(j);
                if(currentInput == '#') galaxyPosistions.add(new GalaxyPos(j, i));
            }
        }

        galaxyPosistions.forEach(galaxyPos -> galaxyPos.expandPosition(expandPointsX, expandPointsY, 1000000));

        long galaxyDistanceSum = 0;

        for (int i = 0; i < galaxyPosistions.size(); i++) {
            GalaxyPos focusedGalaxy = galaxyPosistions.get(i);

            for (int j = i + 1; j < galaxyPosistions.size(); j++) {
                GalaxyPos galaxyForComparison = galaxyPosistions.get(j);
                galaxyDistanceSum += calculateManhattanDistance(
                        focusedGalaxy.getX(),
                        focusedGalaxy.getY(),
                        galaxyForComparison.getX(),
                        galaxyForComparison.getY());
            }
        }

        System.out.println("Result of day 11 part 1: " +  galaxyDistanceSum);
    }


    public static void main(String[] args) {
        Day11Part2 day11Part2 = new Day11Part2();
        Timer.timeChecker(day11Part2::findResult);
    }
}
