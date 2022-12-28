public class Spoofing {
    // UseCase 0: check exact match of the url (www.netflix.com = www.netflix.com)
    // UseCase 1: dropping letter/letters from the original url (www.netfix.com)
    // UseCase 2: adding letter/letters to the original url (www.netfliix.com)
    // UseCase 3: exchanging a letter with a resembling symbol (E = 3, O = 0)
    // UseCase 4: adding dashes (www.net-flix.com)
    // UseCase 5: exchanging the "middle name" of the url by the keyword
    // UseCase 6: exchanging between Vowels (a, e, o, u, i)

    private UrlKeyPair findExactMatch(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        UrlKeyPair returnedPair = new UrlKeyPair();
        for (int i = 0; i < urlKeyPairs.length; i++) {
            if (suspectedString.equals(urlKeyPairs[i])) {
                returnedPair = urlKeyPairs[i];
                break;
            }
        }
        return returnedPair;
    }

    public UrlKeyPair findSpoofedWebsite(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        UrlKeyPair returnedPair = findExactMatch(urlKeyPairs, suspectedString);
        if (returnedPair != new UrlKeyPair()) {
            returnedPair.setKeyWord("No Spoofing");
            return returnedPair;
        }

        int minDistance = EditDistance.editDistDP(urlKeyPairs[0].url, suspectedString,
                                                  urlKeyPairs[0].url.length(), suspectedString.length());
        returnedPair = urlKeyPairs[0];
        returnedPair.setKeyWord("Spoofing");
        for (int i = 0; i < urlKeyPairs.length; i++) {
            int tmpDistance = EditDistance.editDistDP(urlKeyPairs[i].url, suspectedString,
                                                      urlKeyPairs[i].url.length(), suspectedString.length());
            if (minDistance > tmpDistance) {
                minDistance = tmpDistance;
                returnedPair.setUrl(urlKeyPairs[i].url);
            }
        }
        return returnedPair;
    }
}
