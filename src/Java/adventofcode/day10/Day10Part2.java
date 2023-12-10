package adventofcode.day10;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10Part2 {

    Day10Part1 day10Part1 = new Day10Part1();
    List<List<Character>> pipeMaze;
    List<Position> edges = new ArrayList<>();
    private final List<Character> DOWNS = List.of('7', 'F');

    private void findAllEdges(){
        Position sPosition = day10Part1.findS(pipeMaze);
        List<Position> nextPositionsFromS = sPosition.findNextPositions(pipeMaze);

        Position firstPath = nextPositionsFromS.getFirst();
        Position secondPath = nextPositionsFromS.getLast();

        edges.add(firstPath);
        edges.add(secondPath);

        while (!firstPath.equals(secondPath)){
            firstPath = firstPath.findNextPositions(pipeMaze).getFirst();
            secondPath = secondPath.findNextPositions(pipeMaze).getFirst();
            edges.add(firstPath);
            edges.add(secondPath);
        }
    }

    private int checkEveryTile(){
        int tilesWithIn = 0;
        for (int i = 0; i < pipeMaze.size(); i++) {
            for (int j = 0; j < pipeMaze.get(i).size(); j++) {
                 tilesWithIn += isTileInside(j, i);
            }
        }
        return tilesWithIn;
    }

    private int isTileInside(int x, int y){
        int isTileWithIn = 0;
        List<Position> slicedPositions = new ArrayList<>();

        Position tile = new Position(x, y, null);
        if(!edges.contains(tile)){
            for (int i = x  + 1; i < pipeMaze.get(y).size(); i++) {
                slicedPositions.add(new Position(i, y, pipeMaze.get(y).get(i)));
            }

            List<Character> crossoverTiles = new ArrayList<>(slicedPositions.stream()
                    .filter(slice -> edges.contains(slice) || slice.getTile() == 'S')
                    .map(Position::getTile)
                    .filter(sliceTile -> List.of('|', 'F', 'J', '7', 'L', 'S').contains(sliceTile))
                    .toList());

            //My S turns into and 7, a method should be used to calculate the tile for S
            crossoverTiles = new ArrayList<>(crossoverTiles.stream()
                    .map(c -> c == 'S' ? '7' : c)
                    .toList());

            int crossoverCount = 0;
            crossoverCount += Collections.frequency(crossoverTiles, '|');
            crossoverTiles.removeIf(tileValue -> tileValue == '|');
            crossoverCount += upsAndDownsToCrossover(crossoverTiles);

            boolean isWithIn = crossoverCount % 2 != 0;

            if(isWithIn){
                isTileWithIn = 1;
            }
        }

        return isTileWithIn;
    }

    private int upsAndDownsToCrossover(List<Character> upsAndDowns){
        int ups = 0;
        int downs = 0;
        int upsAndDownPairs = 0;

        for (Character c: upsAndDowns) {
            if(isDowns(c)) downs++;
                else ups++;
        }
        while (ups > 0 && downs > 0){
            ups--;
            downs--;
            upsAndDownPairs++;
        }

        //Should never be uneven
        ups = roundToNearestEven(ups);
        downs = roundToNearestEven(downs);

        return upsAndDownPairs + ups + downs;
    }

    private static int roundToNearestEven(int value) {
        if (value % 2 == 0) {
            return value;
        }
        return (value + 1) / 2 * 2;
    }

    private boolean isDowns(Character character){
        return DOWNS.contains(character);
    }

    private void findResult(){
        pipeMaze = InputReader.readInputByLine("adventofcode/day10/input.txt", this.getClass())
                .stream()
                .map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();

        findAllEdges();

        int result = checkEveryTile();
        System.out.println("Result of day 10 part 2: " + result);
    }

    public static void main(String[] args) {
        Day10Part2 day10Part21 = new Day10Part2();
        Timer.timeChecker(day10Part21::findResult);
    }
}
