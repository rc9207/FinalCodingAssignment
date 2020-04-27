package bin;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

  public static void main(String[] args) {

    String path = "./src/main/java/bin/messages.txt";

    Map<Set<String>, Integer> bigramList;
    BiGram biGram = new BiGram();
    AffinityAnalysis affinityAnalysis = new AffinityAnalysis();

    Scanner scanner = new Scanner(System.in);

    try {

      // Get the user's input.
      System.out.println("Type a word...\n");
      String userInput = scanner.nextLine();

      // Create the Bigram from the message.txt file.
      bigramList = biGram.createBiGram(path);

      // Get the support for the next suggested word.
      affinityAnalysis.getSupport(userInput, bigramList);

    } catch (IOException ie) {
      ie.printStackTrace();
    }

    scanner.close();
  }
}
