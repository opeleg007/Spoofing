public class Spoofing {
    // UseCase 0: check exact match of the url (www.netflix.com = www.netflix.com)
    // UseCase 1: dropping letter/letters from the original url (www.netfix.com)
    // UseCase 2: adding letter/letters to the original url (www.netfliix.com)
    // UseCase 3: exchanging a letter with a resembling symbol (E = 3, O = 0)
    // UseCase 4: adding dashes (www.net-flix.com)
    // UseCase 5: exchanging the "middle name" of the url by the keyword
    // UseCase 6: exchanging between Vowels (a, e, o, u, i)

    public static UrlKeyPair findExactMatch(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        UrlKeyPair returnedPair = new UrlKeyPair();
        for (int i = 0; i < urlKeyPairs.length; i++) {
            if (suspectedString.equals(urlKeyPairs[i].url)) {
                returnedPair = urlKeyPairs[i];
                break;
            }
        }
        return returnedPair;
    }

    public static UrlKeyPair findSpoofedWebsite(UrlKeyPair [] urlKeyPairs, String suspectedString) {
        UrlKeyPair returnedPair = findExactMatch(urlKeyPairs, suspectedString);
        if (!returnedPair.url.equals("")) {
            returnedPair.setKeyWord("No Spoofing");
            return returnedPair;
        }

        int minDistance = EditDistance.editDistDP(urlKeyPairs[0].url, suspectedString,
                                                  urlKeyPairs[0].url.length(), suspectedString.length());
        returnedPair = urlKeyPairs[0];
        returnedPair.setKeyWord("Spoofing");
//        System.out.println("calculated distance %d for url %s vs spoofed url %s"
//                .formatted(minDistance, returnedPair.url, suspectedString));
        for (int i = 1; i < urlKeyPairs.length; i++) {
            int tmpDistance = EditDistance.editDistDP(urlKeyPairs[i].url, suspectedString,
                                                      urlKeyPairs[i].url.length(), suspectedString.length());
//            System.out.println("calculated distance %d for url %s vs spoofed url %s"
//                    .formatted(tmpDistance, urlKeyPairs[i].url, suspectedString));
            if (minDistance > tmpDistance) {
                minDistance = tmpDistance;
                returnedPair = urlKeyPairs[i];
                returnedPair.setKeyWord("Spoofing");
            }
        }
        return returnedPair;
    }
}
