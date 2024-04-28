package org.example;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlayfairCipherKeyHandler {

    private static char[] ALPHABETS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static char[][] generatePlayfairMatrix(String secretKey){
        secretKey = validateSecretKey(secretKey);

        LinkedHashMap<Character, Integer> matrixValues = new LinkedHashMap<>();
        char[] splitKey = secretKey.toCharArray();

        int keyEndIndexCounter = 0;
        // Adding secret key to matrix map
        for (char value : splitKey) {
            if (!matrixValues.containsKey(value)) {
                matrixValues.put(value, keyEndIndexCounter);
                keyEndIndexCounter += 1;
            }
        }

        // Filling up matrix map with remaining alphabets
        for(int i = 0; i < 25; i++){
            if (!matrixValues.containsKey(ALPHABETS[i])) {
                matrixValues.put(ALPHABETS[i], keyEndIndexCounter);
                keyEndIndexCounter += 1;
            }
        }

        // Adding matrix values to a 5x5 matrix
        char[][] playfairMatrix = new char[5][5];
        keyEndIndexCounter = 0;
        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++){
                playfairMatrix[row][col] = getMapKeyValue(matrixValues, keyEndIndexCounter);
                keyEndIndexCounter += 1;
            }
        }

        displayPlayFairMatrix(playfairMatrix);

        return playfairMatrix;

    }

    // Method to get the key (alphabet) based on its associated integer value
    private static char getMapKeyValue(LinkedHashMap<Character, Integer> matrixValues, int targetValue){
        for (Map.Entry<Character, Integer> matrixValue : matrixValues.entrySet()) {
            if (matrixValue.getValue() == targetValue) {
                return matrixValue.getKey();
            }
        }
        return '0';
    }

    // validates secret key
    private static String validateSecretKey(String secretKey){
        // convert secret key to uppercase
        secretKey = secretKey.toUpperCase();

        // if secret key has I and J, then replace J with I
        if(secretKey.contains("I") && secretKey.contains("J")){
            secretKey = secretKey.replaceAll("J", "I");
        }
        // if secret key has J, then replaces I in alphabet array with J
        else if(secretKey.contains("J")){
            ALPHABETS[8] = 'J';
        }

        return secretKey;
    }

    public static void displayPlayFairMatrix(char[][] playfairMatrix){
        // Iterate over the matrix and print each element
        for (char[] matrix : playfairMatrix) {
            for (char c : matrix) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

}
