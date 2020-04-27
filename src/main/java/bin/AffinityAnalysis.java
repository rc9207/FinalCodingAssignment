package bin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AffinityAnalysis {

  /** Default constructor. */
  public AffinityAnalysis() {}

  /**
   * Get a suggested word to follow the user's input.
   *
   * @param userInput User's string input.
   * @param bigramList Map reduced list.
   */
  public void getSupport(String userInput, Map<Set<String>, Integer> bigramList) {

    Map<Set<String>, Integer> validResults = new HashMap<>();
    int totalUses = 0;

    // Check if bigramList one of the key pairs are equal to userInput.
    for (Set<String> key : bigramList.keySet()) {

      // Split the key pair into the two string components.
      String[] keyPair = key.toString().split(",");

      // Trim the string.
      keyPair[0] = keyPair[0].trim().substring(1, keyPair[0].length());
      keyPair[1] = keyPair[1].trim().substring(0, keyPair[1].length() - 2);

      // Compare each word in the keypair to the userInput.
      if (keyPair[0].compareToIgnoreCase(userInput) == 0
          || keyPair[1].compareToIgnoreCase(userInput) == 0) {

        validResults.put(key, bigramList.get(key));
        totalUses += bigramList.get(key);
      }
    }

    // Create a list to store word suggestions.
    List<String> wordSuggestions = new ArrayList<>();

    for (Set<String> key : validResults.keySet()) {

      // Split the key into two strings.
      String[] keyPair = key.toString().split(",");

      // Trim the string
      keyPair[0] = keyPair[0].trim().substring(1, keyPair[0].length());
      keyPair[1] = keyPair[1].trim().substring(0, keyPair[1].length() - 2);

      // Support of userInput = valid results / total results. If the support > 60%, suggest the
      // word.
      double support = (double) validResults.get(key) / totalUses;

      if (support >= 0.6) {

        if (userInput.compareToIgnoreCase(keyPair[0]) == 0) {

          wordSuggestions.add(keyPair[1]);
        } else {
          wordSuggestions.add(keyPair[0]);
        }
      }
    }

    // Add the default suggestions.
    if (wordSuggestions.size() < 3) {
      wordSuggestions.add("the");
      wordSuggestions.add("this");
      wordSuggestions.add("of");
    }

    for (int i = 0; i < wordSuggestions.size(); i++) {

      System.out.println("Your next word might be \"" + wordSuggestions.get(i) + "\".");
    }
  }
}
