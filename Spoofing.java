public class Spoofing {


    public static UrlKeyPair findExactMatch(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        UrlKeyPair returnedPair = new UrlKeyPair();
        for (int i = 0; i < urlKeyPairs.length; i++) {
            String tmpUrl = urlKeyPairs[i].url;
            if (suspectedString.equals(tmpUrl)) {
                returnedPair.setUrl(tmpUrl);
                returnedPair.setKeyWord("No Spoofing");
                break;
            }
        }
        return returnedPair;
    }

    public static int [] getEditDistances(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        int [] editDistances = new int[urlKeyPairs.length];
        for (int i = 0; i < urlKeyPairs.length; i++) {
            String tmpUrl = urlKeyPairs[i].url;
            String keyWord = urlKeyPairs[i].keyWord;
            int editDistanceUrl = Auxiliary.editDistDP(tmpUrl, suspectedString);
            int editDistanceKeyword;
            if (!keyWord.equals(""))
                editDistanceKeyword = Auxiliary.editDistDP(keyWord, Auxiliary.extractDomain(suspectedString));
            else
                editDistanceKeyword = Math.max(tmpUrl.length(), suspectedString.length());
            editDistances[i] = Math.min(editDistanceUrl, editDistanceKeyword);
        }
        return editDistances;
    }

    public static int [] getUncommonChars(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        int [] uncommonChars = new int[urlKeyPairs.length];
        for (int i = 0; i < urlKeyPairs.length; i++) {
            String url= urlKeyPairs[i].url;
            String keyWord = urlKeyPairs[i].keyWord;
            int commonCharsOnUrl = Auxiliary.commonChars(url, suspectedString);
            int commonCharsOnKeyWord;
            if (!keyWord.equals(""))
                commonCharsOnKeyWord = Auxiliary.commonChars(keyWord, Auxiliary.extractDomain(suspectedString));
            else
                commonCharsOnKeyWord = 0;
            uncommonChars[i] = Math.max(suspectedString.length(), Auxiliary.extractDomain(url).length()) - Math.max(commonCharsOnUrl, commonCharsOnKeyWord);
        }
        return uncommonChars;
    }

    public static int getLeastDifferences(int [] editDistances, int [] uncommonChars) {
        int minimumDifference = editDistances[0] + uncommonChars[0];
        int minimumIndex = 0;
        for (int i = 1; i < editDistances.length; i++) {
            if (minimumDifference > editDistances[i] + uncommonChars[i]) {
                minimumDifference = editDistances[i] + uncommonChars[i];
                minimumIndex = i;
            }
        }
        return minimumIndex;
    }

    public static UrlKeyPair findSpoofedWebsite(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        UrlKeyPair returnedPair = findExactMatch(urlKeyPairs, suspectedString);
        if (returnedPair.url.equals("")) {
            int [] editDistances = getEditDistances(urlKeyPairs, suspectedString);
            int [] uncommonChars = getUncommonChars(urlKeyPairs, suspectedString);
            returnedPair = urlKeyPairs[getLeastDifferences(editDistances, uncommonChars)];
            returnedPair.setKeyWord("Spoofing");
        }
        return returnedPair;

        /* TODO: 27/12/2022
            1. function to check for exact match - V
               *UseCase 0: check exact match of the url (www.netflix.com = www.netflix.com) - V
            2. Dynamic Programming edit distance - V
            3. Testing - V
         */

        /* TODO: 28/12/2022
            1. add function to calculate resemblance between strings (how many common letters) - V
               *UseCase 1: dropping letter/letters from the original url (www.netfix.com) - V
               *UseCase 2: adding letter/letters to the original url (www.netfliix.com) - V
            2. Extract functions from findSpoofedWebsite - V
            3. Combine resemblance with distance - V
            4. minimum of both is the spoofed Website - V
            5. Unit test new functions individually - V
         */

        /* TODO: 29/12/2022
            1. extract domain from URL to - V
            2. Apply similarity/Difference functions on the Domain - V
                (a) Against website's Domains
                (b) Against website's keyword
            *UseCase 5: exchanging the "middle name" of the url by the keyword - V
         */

        /* TODO: (if results arent as good)
            1. Add UseCases as optionals when comparing the spoofed url to the real urls
                UseCase 3: exchanging a letter with a resembling symbol (E = 3, O = 0)
                UseCase 4: adding dashes (www.net-flix.com)
                UseCase 6: exchanging between Vowels (a, e, o, u, i)
                (**) When doing so, spoofed url gets advantage when the "spoofed" character gets replaced with an original
            2. Add Image Processing and comparison - Future Research
                (a) find a way to create jpg/png from Strings
                (b) compare the pictures
                (**) the methodology should capture visual resemblance
         */
    }
}
