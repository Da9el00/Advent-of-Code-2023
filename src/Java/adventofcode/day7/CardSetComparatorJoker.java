package adventofcode.day7;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardSetComparatorJoker implements Comparator<String> {

    private static final String RANK_ORDER = "AKQT98765432J";

    @Override
    public int compare(String cardSet1, String cardSet2) {
        List<Comparator<String>> comparators = Arrays.asList(
                this::compareFiveOfAKind,
                this::compareFourOfAKind,
                this::compareFullHouse,
                this::compareThreeOfAKind,
                this::compareTwoPair,
                this::comparePair,
                this::highCardComparator
        );

        for (Comparator<String> comparator : comparators) {
            int result = comparator.compare(cardSet1, cardSet2);
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

    private int compareFiveOfAKind(String cardSet1, String cardSet2) {
        return Boolean.compare(containsFiveOfAKind(cardSet1), containsFiveOfAKind(cardSet2));
    }

    private int compareFourOfAKind(String cardSet1, String cardSet2) {
        boolean isFourOfAKind1 = containsFourOfAKind(cardSet1);
        boolean isFourOfAKind2 = containsFourOfAKind(cardSet2);
        int comparisonResult = Boolean.compare(isFourOfAKind1, isFourOfAKind2);
        if (comparisonResult == 0 && isFourOfAKind1 && isFourOfAKind2) {
            return highCardComparator(cardSet1, cardSet2);
        }

        return comparisonResult;
    }

    private int compareFullHouse(String cardSet1, String cardSet2) {
        boolean isFullHouse1 = containsFullHouse(cardSet1);
        boolean isFullHouse2 = containsFullHouse(cardSet2);
        int comparisonResult = Boolean.compare(isFullHouse1, isFullHouse2);
        if (comparisonResult == 0 && isFullHouse1 && isFullHouse2) {
            return highCardComparator(cardSet1, cardSet2);
        }

        return comparisonResult;
    }

    private int compareThreeOfAKind(String cardSet1, String cardSet2) {
        boolean isThreeOfKind1 = containsThreeOfAKind(cardSet1);
        boolean isThreeOfKind2 = containsThreeOfAKind(cardSet2);
        int comparisonResult = Boolean.compare(isThreeOfKind1, isThreeOfKind2);
        if (comparisonResult == 0 && isThreeOfKind1 && isThreeOfKind2) {
            return highCardComparator(cardSet1, cardSet2);
        }

        return comparisonResult;
    }

    private int compareTwoPair(String cardSet1, String cardSet2) {
        return Integer.compare(countPairs(cardSet1), countPairs(cardSet2));
    }

    private int comparePair(String cardSet1, String cardSet2) {
        return Boolean.compare(containsPair(cardSet1), containsPair(cardSet2));
    }

    private int highCardComparator(String cardSet1, String cardSet2) {
        Map<Character, Integer> rankMap = createRankMap();

        for (int i = 0; i < cardSet1.length(); i++) {
            char digit1 = cardSet1.charAt(i);
            char digit2 = cardSet2.charAt(i);

            int rank1 = rankMap.get(digit1);
            int rank2 = rankMap.get(digit2);

            int result = Integer.compare(rank1, rank2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private int countPairs(String cardSet) {
        String sortedCardSet = sortCharsInString(cardSet);
        Pattern pattern = Pattern.compile("(.)(\\1|J)");
        Matcher matcher = pattern.matcher(sortedCardSet);
        return (int) matcher.results().count();
    }

    private Map<Character, Integer> createRankMap() {
        Map<Character, Integer> rankMap = new HashMap<>();
        for (int i = RANK_ORDER.length() - 1; i >= 0; i--) {
            char rank = RANK_ORDER.charAt(i);
            rankMap.put(rank, RANK_ORDER.length() - i);
        }
        return rankMap;
    }

    private boolean containsPair(String cardSet){
        String sortedCardSet = sortCharsInString(cardSet);
        String regex = "(.).*(\\1|J)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sortedCardSet);
        return matcher.find();
    }

    private String sortCharsInString(String input) {
        Character[] charArray = toCharacterArray(input.toCharArray());
        Arrays.sort(charArray, customComparator());
        return new String(toCharArray(charArray));
    }

    private static Character[] toCharacterArray(char[] chars) {
        Character[] result = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[i];
        }
        return result;
    }

    private static char[] toCharArray(Character[] characters) {
        char[] result = new char[characters.length];
        for (int i = 0; i < characters.length; i++) {
            result[i] = characters[i];
        }
        return result;
    }

    private static Comparator<Character> customComparator() {
        String customOrder = "AKQT98765432J";
        return Comparator.comparingInt(customOrder::indexOf);
    }

    private boolean containsThreeOfAKind(String cardSet){
        String sortedCardSet = sortCharsInString(cardSet);
        String regex = "(.).*(\\1|J).*(\\1|J)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sortedCardSet);
        return matcher.find();
    }

    private boolean containsFourOfAKind(String cardSet){
        String sortedCardSet = sortCharsInString(cardSet);
        String regex = "(.).*(\\1|J).*(\\1|J).*(\\1|J)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sortedCardSet);
        return matcher.find();
    }

    private boolean containsFiveOfAKind(String cardSet){
        String sortedCardSet = sortCharsInString(cardSet);
        String regex = "(.)(\\1|J)(\\1|J)(\\1|J)(\\1|J)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sortedCardSet);
        return matcher.find();
    }

    private boolean containsFullHouse(String cardSet){
        String sortedCardSet = sortCharsInString(cardSet);
        String regex1 = "(.)(\\1|J)(\\1|J)(.)(\\4|J)";
        String regex2 = "(.)(\\1|J)(.)(\\3|J)(\\3|J)";
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher1 = pattern1.matcher(sortedCardSet);
        Matcher matcher2 = pattern2.matcher(sortedCardSet);
        return matcher1.find() || matcher2.find();
    }

    //For testing
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("AAKAJ", "AAAA5"));
        list.sort(new CardSetComparatorJoker());
        System.out.println(list);
        System.out.println(new CardSetComparatorJoker().containsFourOfAKind("JJJ34"));
    }
}