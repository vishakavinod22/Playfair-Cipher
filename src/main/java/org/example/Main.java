package org.example;

import static org.example.PlayfairCipherEncryptionDecryptionHandler.decryptCiphertext;
import static org.example.PlayfairCipherEncryptionDecryptionHandler.encryptPlaintext;
import static org.example.PlayfairCipherKeyHandler.generatePlayfairMatrix;

public class Main {
    public static void main(String[] args) {

        String secretKey, plaintext, ciphertext;

        secretKey = "RAYQUAZA";
        plaintext = "POKEMON TOWER DEFENSE\n" +
                "YOUR MISSION IN THIS FUN STRATEGY TOWER DEFENSE GAME IS TO HELP PROFESSOR OAK TO STOP ATTACKS OF WILD RATTATA. SET OUT ON YOUR OWN POKEMON JOURNEY, TO CATCH AND TRAIN ALL POKEMON AND TRY TO SOLVE THE MYSTERY BEHIND THESE ATTACKS. YOU MUST PLACE POKEMON CHARACTERS STRATEGICALLY ON THE BATTLEFIELD SO THAT THEY STOP ALL WAVES OF ENEMY ATTACKER\n" +
                "DURING THE BATTLE YOU WILL LEVEL UP AND EVOLVE YOUR POKEMON. YOU CAN ALSO CAPTURE OTHER POKEMON DURING THE BATTLE AND ADD THEM TO YOUR TEAM. USE YOUR MOUSE TO PLAY THE GAME. GOOD LUCK\n";

        char[][] playfairMatrix = generatePlayfairMatrix(secretKey);
        System.out.println("---------------------------");
        ciphertext = encryptPlaintext(plaintext, playfairMatrix);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("---------------------------");
        String newp = decryptCiphertext(ciphertext, playfairMatrix);
        System.out.println("Plaintext: " + newp);
    }
}