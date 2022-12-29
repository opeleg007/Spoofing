public class Spoofing {
    // UseCase 0: check exact match of the url (www.netflix.com = www.netflix.com) - V
    // UseCase 1: dropping letter/letters from the original url (www.netfix.com)
    // UseCase 2: adding letter/letters to the original url (www.netfliix.com)
    // UseCase 3: exchanging a letter with a resembling symbol (E = 3, O = 0)
    // UseCase 4: adding dashes (www.net-flix.com)
    // UseCase 5: exchanging the "middle name" of the url by the keyword
    // UseCase 6: exchanging between Vowels (a, e, o, u, i)

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
            int editDistance = Auxilary.editDistDP(tmpUrl, suspectedString);
            editDistances[i] = editDistance;
        }
        return editDistances;
    }

    public static int [] getUncommonChars(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        int [] uncommonChars = new int[urlKeyPairs.length];
        for (int i = 0; i < urlKeyPairs.length; i++) {
            String tmpUrl= urlKeyPairs[i].url;
            uncommonChars[i] = Math.max(tmpUrl.length(), suspectedString.length()) - Auxilary.commonChars(tmpUrl, suspectedString);
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

        /* TODO: 28/12/2022 -
            1. add function to calculate resemblance between strings (how many common letters) - V
            2. Extract functions from findSpoofedWebsite - V
            3. Combine resemblance with distance - V
            4. minimum of both is the spoofed Website - V
            5. Unit test new functions individually - V
         */

        /* TODO: 29/12/2022
            1. divide suspected URL to
                (a) Protocol
                (b) Domain
                (c) SubFolder
            2. Apply similarity/Difference functions on the Domain
                (a) Against website's Domains
                (b) Against website's keyword
         */
    }
}
