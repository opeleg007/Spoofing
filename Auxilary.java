import java.util.HashMap;

public class Auxilary {
    static int min(int x, int y, int z)
    {
        if (x <= y && x <= z)
            return x;
        if (y <= x && y <= z)
            return y;
        else
            return z;
    }

    static int editDistDP(String str1, String str2)
    {
        /* Credit to www.Geeks for Geeks.com for the solution
        This code is contributed by Rajat Mishra*/

        int m = str1.length();
        int n = str2.length();
        // Create a table to store results of subproblems
        int dp[][] = new int[m + 1][n + 1];

        // Fill d[][] in bottom up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // If first string is empty, only option is
                // to insert all characters of second string
                if (i == 0)
                    dp[i][j] = j; // Min. operations = j

                    // If second string is empty, only option is
                    // to remove all characters of second string
                else if (j == 0)
                    dp[i][j] = i; // Min. operations = i

                    // If last characters are same, ignore last
                    // char and recur for remaining string
                else if (str1.charAt(i - 1)
                        == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];

                    // If the last character is different,
                    // consider all possibilities and find the
                    // minimum
                else
                    dp[i][j]
                            = 1
                            + min(
                            dp[i][j - 1], // Insert
                            dp[i - 1][j], // Remove
                            dp[i - 1][j - 1]); // Replace
            }
        }

        return dp[m][n];
    }

    static HashMap<Character, Integer> fillCharacterCount(String str) {
        HashMap<Character, Integer> countCharacters = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            Character tmpChar = str.charAt(i);
            if (countCharacters.containsKey(tmpChar))
                countCharacters.put(tmpChar, countCharacters.get(tmpChar) + 1);
            else
                countCharacters.put(tmpChar, 1);
        }
        return  countCharacters;
    }

    static int countCommonChars(HashMap<Character, Integer> first, HashMap<Character, Integer> second) {
        int commonCharCount = 0;
        for (Character c: first.keySet()) {
            if (second.containsKey(c))
                commonCharCount += Math.min(first.get(c), second.get(c));
        }
        return commonCharCount;
    }

    static int commonChars(String str1, String str2) {
        HashMap<Character, Integer> countCharactersFirstString = fillCharacterCount(str1);
        HashMap<Character, Integer> countCharactersSecondString = fillCharacterCount(str2);
        return countCommonChars(countCharactersFirstString, countCharactersSecondString);
    }

}
