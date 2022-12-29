public class UnitTest {

    private static boolean testEditDistance(String str1, String str2, int dist) {
        int calculatedDist = Auxilary.editDistDP(str1, str2);
        if (dist != calculatedDist) {
            System.out.println("FAIL %s, %s expected %d got %d"
                                .formatted(str1, str2, dist, calculatedDist));
            return false;
        }
        System.out.println("SUCCESS %s, %s, %d"
                .formatted(str1, str2, calculatedDist));
        return true;
    }

    private static boolean testExactMatch(UrlKeyPair [] urlKeyPairs, String strToMatch) {
        UrlKeyPair urlKeyPair = Spoofing.findExactMatch(urlKeyPairs, strToMatch);
        if (!urlKeyPair.url.equals(strToMatch)) {
            System.out.println("FAIL expected %s got %s"
                    .formatted(strToMatch, urlKeyPair.url));
            return false;
        }
        System.out.println("SUCCESS matched %s"
                .formatted(strToMatch));
        return true;
    }

    private static boolean testFindSpoofedWebsite(UrlKeyPair [] urlKeyPairs, String suspectedString,
                                                  String spoofedWebsite) {
        UrlKeyPair urlKeyPair = Spoofing.findSpoofedWebsite(urlKeyPairs, suspectedString);
        if (!urlKeyPair.url.equals(spoofedWebsite)) {
            System.out.println("FAIL expected %s got %s"
                    .formatted(spoofedWebsite, urlKeyPair.url));
            return false;
        }
        System.out.println("SUCCESS %s spoofing %s"
                .formatted(suspectedString, spoofedWebsite));
        return true;
    }

    private static void simulateEditDistanceTests() {
        String [] urls = {"www.n3tfl!x.com", "www.google.com", "www.apple.com"};
        String [] urls2 = {"www.netflix.com", "www.oogleg.com", "www.apple.com"};
        int [] distances = {2, 2, 0};
        System.out.println("-----------------------");
        System.out.println("TESTING editDistDP");
        System.out.println("-----------------------");
        for (int i = 0; i < urls.length; i++) {
            testEditDistance(urls[i], urls2[i], distances[i]);
        }
    }

    private static void simulateExactMatchTests() {
        UrlKeyPair [] urls = {new UrlKeyPair("www.netflix.com", ""),
                               new UrlKeyPair("www.google.com", ""),
                               new UrlKeyPair("www.apple.com", "")};
        String [] urls2 = {"www.google.com", "www.apple.com", "www.netflix.com"};
        System.out.println("-----------------------");
        System.out.println("TESTING findExactMatch");
        System.out.println("-----------------------");
        for (int i = 0; i < urls.length; i++) {
            testExactMatch(urls, urls2[i]);
        }
    }

    private static void simulateFindSpoofedWebsite() {
        UrlKeyPair [] urlKeyPairs = {new UrlKeyPair("www.netflix.com", ""),
                new UrlKeyPair("www.google.com", ""),
                new UrlKeyPair("www.apple.com", ""),
                new UrlKeyPair("www.g.com", "")};
        String [] spoofedUrls = {"www.goooooooooooooooooooggggle.com", "www.apleeeee.com", "www.netfl!x.com"};
        String [] originalWebsites = {"www.google.com", "www.apple.com", "www.netflix.com"};
        System.out.println("-----------------------");
        System.out.println("TESTING findSpoofedWebsite");
        System.out.println("-----------------------");
        for (int i = 0; i < spoofedUrls.length; i++) {
            testFindSpoofedWebsite(urlKeyPairs, spoofedUrls[i], originalWebsites[i]);
        }
    }

    public static void main(String[] args) {
        simulateEditDistanceTests();
        simulateExactMatchTests();
        simulateFindSpoofedWebsite();
    }
}
