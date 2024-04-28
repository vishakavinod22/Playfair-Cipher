// References:
// [1] “Pattern (java platform SE 8 ),” Oracle.com. [Online]. Available: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html. [Accessed: March 23, 2024].

package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayfairCipherEncryptionDecryptionHandler {

    public static String encryptPlaintext(String plaintext, char[][] secretKeyMatrix){
        plaintext = validatePlaintext(plaintext);

        LinkedHashMap<Integer, String> pairList = generatePairs(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        int p1_row, p1_col, p2_row, p2_col;
        int[] indexPair1, indexPair2;

        for (Map.Entry<Integer, String> pairItem : pairList.entrySet()) {
            String p1, p2;
            String pairValue = pairItem.getValue();

            // split pair and get their rows and columns and set as p1_row  p1_col | p2_row p2_col
            char[] pair = pairValue.toCharArray();
            indexPair1 = findIndexInMatrix(secretKeyMatrix, pair[0]);
            p1_row = indexPair1[0];
            p1_col = indexPair1[1];
            indexPair2 = findIndexInMatrix(secretKeyMatrix, pair[1]);
            p2_row = indexPair2[0];
            p2_col = indexPair2[1];

            // handle when the letters are in the same column
            if(p1_col == p2_col){
                int nextRowP1 = (p1_row + 1 <= 4) ? p1_row + 1 : 0;
                int nextRowP2 = (p2_row + 1 <= 4) ? p2_row + 1 : 0;
                 p1 = String.valueOf(secretKeyMatrix[nextRowP1][p1_col]);
                 p2 = String.valueOf(secretKeyMatrix[nextRowP2][p2_col]);
            }
            // handle when the letters are in the same row
            else if(p1_row ==  p2_row){
                int nextColP1 = (p1_col + 1 <= 4) ?  p1_col + 1 : 0;
                int nextColP2 = (p2_col + 1 <= 4) ?  p2_col + 1 : 0;
                p1 = String.valueOf(secretKeyMatrix[p1_row][nextColP1]);
                p2 = String.valueOf(secretKeyMatrix[p2_row][nextColP2]);
            }
            // handle when not in same row or column
            else {
                p1 = String.valueOf(secretKeyMatrix[p1_row][p2_col]);
                p2 = String.valueOf(secretKeyMatrix[p2_row][p1_col]);
            }
            ciphertext.append(p1).append(p2);

        }

        return ciphertext.toString();

    }

    public static String decryptCiphertext(String ciphertext, char[][] secretKeyMatrix){
        LinkedHashMap<Integer, String> pairList = generatePairs(ciphertext);
        StringBuilder plaintext = new StringBuilder();

        int p1_row, p1_col, p2_row, p2_col;
        int[] indexPair1, indexPair2;

        for (Map.Entry<Integer, String> pairItem : pairList.entrySet()) {
            String p1, p2;
            String pairValue = pairItem.getValue();

            // split pair and get their rows and columns and set as p1_row  p1_col | p2_row p2_col
            char[] pair = pairValue.toCharArray();
            indexPair1 = findIndexInMatrix(secretKeyMatrix, pair[0]);
            p1_row = indexPair1[0];
            p1_col = indexPair1[1];
            indexPair2 = findIndexInMatrix(secretKeyMatrix, pair[1]);
            p2_row = indexPair2[0];
            p2_col = indexPair2[1];

            // handle when the letters are in the same column
            if(p1_col == p2_col){
                int nextRowP1 = (p1_row - 1 >= 0) ? p1_row - 1 : 4;
                int nextRowP2 = (p2_row - 1 >= 0) ? p2_row - 1 : 4;
                p1 = String.valueOf(secretKeyMatrix[nextRowP1][p1_col]);
                p2 = String.valueOf(secretKeyMatrix[nextRowP2][p2_col]);
            }
            // handle when the letters are in the same row
            else if(p1_row ==  p2_row){
                int nextColP1 = (p1_col - 1 >= 0) ? p1_col - 1 : 4 ;
                int nextColP2 = (p2_col - 1 >= 0) ? p2_col - 1 : 4 ;
                p1 = String.valueOf(secretKeyMatrix[p1_row][nextColP1]);
                p2 = String.valueOf(secretKeyMatrix[p2_row][nextColP2]);
            }
            // handle when not in same row or column
            else {
                p1 = String.valueOf(secretKeyMatrix[p1_row][p2_col]);
                p2 = String.valueOf(secretKeyMatrix[p2_row][p1_col]);
            }
            plaintext.append(p1).append(p2);

        }
        return plaintext.toString();
    }

    private static String validatePlaintext(String plaintext){
        // convert secret key to uppercase
        plaintext = plaintext.toUpperCase();

        // remove any next lines in the string
        plaintext = plaintext.replaceAll("\n", "");

        // remove all spaces and punctuations [1]
        plaintext = plaintext.replaceAll("\\s+|\\p{Punct}", "");

        // remove all spaces
        plaintext = plaintext.replaceAll(" ", "");

        // replace all the letters J with I
        plaintext = plaintext.replaceAll("J", "I");

        return plaintext;
    }

    private static LinkedHashMap<Integer, String> generatePairs(String plaintext){
        LinkedHashMap<Integer, String> pairList = new LinkedHashMap<>();
        char[] plaintextArray = plaintext.toCharArray();

        int pairIndexCounter = 0;
        String pair;
        for(int i = 0; i < plaintextArray.length; i+=2){
            // check if i+1 is greater than array length to handle odd total number
            if(i+1 >= plaintextArray.length){
                pair = plaintextArray[i] + "Z";
                pairList.put(pairIndexCounter, pair);
            }
            // check if i == i+1 to handle repeated characters in a pair
            else if(plaintextArray[i] == plaintextArray[i+1]){
                pair = plaintextArray[i] + "Q";
                pairList.put(pairIndexCounter, pair);
                i = i-1;
            }
            // handle all other pairs
            else {
                pair = "" + plaintextArray[i] + plaintextArray[i+1];
                pairList.put(pairIndexCounter, pair);
            }
            pairIndexCounter+=1;
        }
        System.out.println("PairList: " + pairList);
        System.out.println("---------------------------");
        return pairList;
    }

    private static int[] findIndexInMatrix(char[][] matrix, char value){
        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++){
                if(matrix[row][col] == value){
                    return new int[]{row, col};
                }
            }
        }
        // return -1 if index is not found
        return new int[]{-1};
    }

}
