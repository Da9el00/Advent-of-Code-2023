package adventofcode.day10;

import java.util.*;

public class Position {

    private final int x;
    private final int y;

    private final Integer previousX;
    private final Integer previousY;

    public Character getTile() {
        return tile;
    }

    private final Character tile;

    private final List<Character> VALID_ABOVE = List.of('|', '7', 'F', 'S');
    private final List<Character> VALID_BELOW = List.of('|', 'J', 'L', 'S');
    private final List<Character> VALID_RIGHT = List.of('-', '7', 'J', 'S');
    private final List<Character> VALID_LEFT = List.of('-', 'F', 'L', 'S');

    public Position(int x, int y, Integer previousX, Integer previousY, Character tile) {
        this.x = x;
        this.y = y;
        this.previousX = previousX;
        this.previousY = previousY;
        this.tile = tile;
    }

    public Position(int x, int y, Character tile ) {
        this.x = x;
        this.y = y;
        this.previousX = null;
        this.previousY = null;
        this.tile = tile;
    }

    public Optional<Position> getValueBelow(List<List<Character>> pipeMaze){
        int newX = x;
        int newY = y + 1;

        return (newY < pipeMaze.size() && VALID_BELOW.contains(pipeMaze.get(newY).get(newX)))
                ? Optional.of(new Position(newX, newY, x, y, pipeMaze.get(newY).get(newX)))
                : Optional.empty();
    }

    public Optional<Position> getValueAbove(List<List<Character>> pipeMaze){
        int newX = x;
        int newY = y - 1;

        return (newY >= 0 && VALID_ABOVE.contains(pipeMaze.get(newY).get(newX)))
                ? Optional.of(new Position(newX, newY, x, y, pipeMaze.get(newY).get(newX)))
                : Optional.empty();
    }

    public Optional<Position> getValueRight(List<List<Character>> pipeMaze){
        int newX = x + 1;
        int newY = y;

        return (newY < pipeMaze.get(y).size() && VALID_RIGHT.contains(pipeMaze.get(newY).get(newX)))
                ? Optional.of(new Position(newX, newY, x, y, pipeMaze.get(newY).get(newX)))
                : Optional.empty();
    }

    public Optional<Position> getValueLeft(List<List<Character>> pipeMaze){
        int newX = x - 1;
        int newY = y;

        return (newX >= 0 && VALID_LEFT.contains(pipeMaze.get(newY).get(newX)))
                ? Optional.of(new Position(newX, newY, x, y, pipeMaze.get(newY).get(newX)))
                : Optional.empty();
    }

    public List<Position> findNextPositions(List<List<Character>> pipeMaze) {
        List<Position> possiblePositions = new ArrayList<>();

        if(VALID_ABOVE.contains(this.tile)) this.getValueBelow(pipeMaze).ifPresent(possiblePositions::add);
        if(VALID_BELOW.contains(this.tile)) this.getValueAbove(pipeMaze).ifPresent(possiblePositions::add);
        if(VALID_LEFT.contains(this.tile)) this.getValueRight(pipeMaze).ifPresent(possiblePositions::add);
        if(VALID_RIGHT.contains(this.tile)) this.getValueLeft(pipeMaze).ifPresent(possiblePositions::add);

        //Remove previous positions as possible next positions
        if(previousX != null && previousY != null){
            possiblePositions = possiblePositions.stream().filter(this::wasNotPrevious).toList();
        }

        return possiblePositions;
    }

    private boolean wasNotPrevious(Position possiblePrevious){
        return previousX != possiblePrevious.x || previousY != possiblePrevious.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position that = (Position) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", tile=" + tile +
                '}';
    }
}
