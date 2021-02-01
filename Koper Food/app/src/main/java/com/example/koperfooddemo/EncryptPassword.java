package com.example.koperfooddemo;

import java.util.Arrays;

class EncryptPassword {

    public static int[] ksa(String k) {

        // Creating and initializing s as the identity permutation
        int[] s = new int[256];
        for (int i = 0; i < 256; i++) {
            s[i] = i;
        }

        // Mixing the permutation
        int j = 0, temp;
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + k.charAt(i % k.length())) % 256;

            // swapping s[i] and s[j]
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
        return s;
    }

    // Pseudo-random Generator Algorithm function
    public static String encrypt(String p, int[] s) {
        int i = 0, j = 0, temp;
        int[] keystream = new int[p.length()];
        int[] ciphertext = new int[p.length()];

        System.out.print("Keystream: ");
        for (int n = 0; n < p.length(); n++) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;

            // swapping s[i] and s[j]
            temp = s[i];
            s[i] = s[j];
            s[j] = temp;

            // Generating keystream and ciphertext
            keystream[n] = (s[(s[i] + s[j]) % 256]);
            System.out.print(Integer.toHexString(keystream[n]));
            ciphertext[n] = keystream[n] ^ p.charAt(n);
        }
        String result = Arrays.toString(ciphertext);
        result = result.substring(1, result.length()-1);
        result = result.replaceAll(", ", "");
        return result;
    }
}