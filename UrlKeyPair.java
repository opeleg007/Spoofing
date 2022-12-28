public class UrlKeyPair {
    String url;
    String keyWord;

    UrlKeyPair() {
        this.url = "";
        this.keyWord = "";
    }

    UrlKeyPair(String url, String keyWord) {
        this.url = url;
        this.keyWord = keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
