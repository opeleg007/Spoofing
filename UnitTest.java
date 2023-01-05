import java.net.URISyntaxException;

public class UnitTest {

    private static boolean testEditDistance(String str1, String str2, int dist) {
        int calculatedDist = Auxiliary.editDistDP(str1, str2);
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

    private static boolean testGetEditDistances(UrlKeyPair [] urlKeyPairs, String suspectedString, int [] distances) {
        int [] calculatedDistances = Spoofing.getEditDistances(urlKeyPairs, suspectedString);
        for (int i = 0; i < urlKeyPairs.length; i++) {
            if (calculatedDistances[i] != distances[i])
                return false;
        }
        return true;
    }

    private static boolean testUnCommonChars(UrlKeyPair [] urlKeyPairs, String suspectedString, int [] uncommonChars) {
        int [] calculatedUncommonChars = Spoofing.getUncommonChars(urlKeyPairs, suspectedString);
        for (int i = 0; i < urlKeyPairs.length; i++) {
            if (calculatedUncommonChars[i] != uncommonChars[i])
                return false;
        }
        return true;
    }

    private static boolean testExtractDomain(String url, String domain) {
        if (!Auxiliary.extractDomain(url).equals(domain))
            return false;
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
        UrlKeyPair [] urls = {new UrlKeyPair("www.netflix.com", "netflix"),
                               new UrlKeyPair("www.google.com", "google"),
                               new UrlKeyPair("www.apple.com", "apple")};
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
                new UrlKeyPair("www.google.com", "google"),
                new UrlKeyPair("www.apple.com", "apple"),
                new UrlKeyPair("www.g.com", "google")};
        String [] spoofedUrls = {"www.goooooooooooooooooooggggle.com", "www.apleeeee.com", "www.netfl!x.com"};
        String [] originalWebsites = {"www.google.com", "www.apple.com", "www.netflix.com"};
        System.out.println("-----------------------");
        System.out.println("TESTING findSpoofedWebsite");
        System.out.println("-----------------------");
        for (int i = 0; i < spoofedUrls.length; i++) {
            testFindSpoofedWebsite(urlKeyPairs, spoofedUrls[i], originalWebsites[i]);
        }
    }

    private static void simulateEditDistances() {
        UrlKeyPair [] urlKeyPairs = {new UrlKeyPair("www.netflix.com", "netflix"),
                new UrlKeyPair("www.google.com", "google"),
                new UrlKeyPair("www.apple.com", "apple")};
        String [] spoofedUrls = {"net", "app"};
        int [][] distances = {{4, 6, 5}, {7, 6, 2}};
        System.out.println("-------------------------");
        System.out.println("Testing getEditDistances");
        System.out.println("-------------------------");
        for (int i = 0; i < spoofedUrls.length; i++) {
            if (testGetEditDistances(urlKeyPairs, spoofedUrls[i], distances[i]))
                System.out.println("SUCCESS");
            else {
                System.out.println("FAIL");
            }
        }
    }

    private static void simulateUnCommonChars() {
        UrlKeyPair [] urlKeyPairs = {new UrlKeyPair("www.netflix.com", "netflix"),
                new UrlKeyPair("www.google.com", "google"),
                new UrlKeyPair("www.apple.com", "apple")};
        String [] spoofedUrls = {"net", "app"};
        int [][] uncommonChars = {{4, 5, 4}, {7, 6, 2}};
        System.out.println("-------------------------");
        System.out.println("Testing unCommonChars");
        System.out.println("-------------------------");
        for (int i = 0; i < spoofedUrls.length; i++) {
            if (testUnCommonChars(urlKeyPairs, spoofedUrls[i], uncommonChars[i]))
                System.out.println("SUCCESS");
            else {
                System.out.println("FAIL");
            }
        }
    }

    private static void simulateExtractDomain() {
        String [] urls = {"www.netflix.com", "www.google.com", "www.apple.com"};
        String [] domains = {"netflix", "google", "apple"};
        System.out.println("-------------------------");
        System.out.println("Testing extractDomain");
        System.out.println("-------------------------");
        for (int i = 0; i < urls.length; i++) {
            System.out.println(testExtractDomain(urls[i], domains[i]) ? "SUCCESS" :
                    "FAIL %s %s".formatted(urls[i], domains[i]));
        }
    }
    public static void main(String[] args) throws URISyntaxException {
        simulateEditDistanceTests();
        simulateExactMatchTests();
        simulateFindSpoofedWebsite();
        simulateEditDistances();
        simulateUnCommonChars();
        simulateExtractDomain();
    }
}
