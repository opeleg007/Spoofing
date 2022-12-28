public class UnitTest {

    private static boolean testEditDistance(String str1, String str2, int dist) {
        int calculatedDist = EditDistance.editDistDP(str1, str2, str1.length(), str2.length());
        if (dist != calculatedDist) {
            System.out.println("FAIL %s, %s expected %d got %d"
                                .formatted(str1, str2, dist, calculatedDist));
            return false;
        }
        System.out.println("SUCCESS %s, %s, %d"
                .formatted(str1, str2, calculatedDist));
        return true;
    }
    private static void simulateEditDistanceTests(String [] urls, String [] urls2, int [] distances) {
        System.out.println("-----------------------");
        System.out.println("TESTING editDistDP");
        System.out.println("-----------------------");
        for (int i = 0; i < urls.length; i++) {
            testEditDistance(urls[i], urls2[i], distances[i]);
        }
    }
    public static void main(String[] args) {
        String [] urls = {"www.n3tfl!x.com", "www.google.com", "www.apple.com"};
        String [] urls2 = {"www.netflix.com", "www.google.com", "www.apple.com"};
        int [] distances = {2, 0, 0};
        simulateEditDistanceTests(urls, urls2, distances);
    }
}
