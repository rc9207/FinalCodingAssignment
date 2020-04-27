package bin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class BiGram {

  /** Default constructor. */
  public BiGram() {}

  /**
   * Count total instances of word A following word B in supplied text file.
   *
   * @param path String value of input file.
   * @throws IOException Throw all IOExceptions
   */
  public Map<Set<String>, Integer> createBiGram(String path) throws IOException {

    Path inputPath = Paths.get(path);

    Stream<String> inputLines = Files.lines(inputPath).filter(line -> !line.isEmpty());

    List<String> inputWords =
        inputLines
            .map(String::toLowerCase)
            .map(line -> line.split("\\s+"))
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());

    Map<Set<String>, Integer> bgrams = new HashMap<>();

    for (int i = 1; i < inputWords.size(); i++) {

      bgrams.merge(
          new HashSet<>(Arrays.asList(inputWords.get(i - 1), inputWords.get(i))), 1, Integer::sum);
    }

    System.out.println("\n\n");

    Map<Set<String>, Integer> sortedBgrams = topValues(bgrams);
    // sortedBgrams.forEach((key, value) -> System.out.println(key + ", " + value));

    return sortedBgrams;
  }

  /**
   * Sort the list by most used set of words.
   *
   * @param bgrams input Map<Set<String>, Integer>.
   * @return Map<Set<String>, Integer>
   */
  private Map<Set<String>, Integer> topValues(Map<Set<String>, Integer> bgrams) {

    // Create unsorted List from input hashmap.
    List<Map.Entry<Set<String>, Integer>> unsortedList =
        new LinkedList<Map.Entry<Set<String>, Integer>>(bgrams.entrySet());

    // Sort the unsortedList.
    Collections.sort(
        unsortedList,
        new Comparator<Entry<Set<String>, Integer>>() {
          @Override
          public int compare(
              Map.Entry<Set<String>, Integer> o1, Map.Entry<Set<String>, Integer> o2) {
            return (o2.getValue()).compareTo(o1.getValue());
          }
        });

    // Place the sorted data back into the hashMap.
    HashMap<Set<String>, Integer> sortedMap = new LinkedHashMap<Set<String>, Integer>();

    for (Map.Entry<Set<String>, Integer> temp : unsortedList) {
      sortedMap.put(temp.getKey(), temp.getValue());
    }
    return sortedMap;
  }

  /**
   * Print the key pairs and values.
   *
   * @param bigramList Desired Map<Set<String>, Integer> to print.
   */
  public void printHashMap(Map<Set<String>, Integer> bigramList) {

    bigramList.forEach((key, value) -> System.out.println(key + ", " + value));
  }
}
