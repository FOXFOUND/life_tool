package trie.problem;

class Trie {

    Trie[] triNode;

    String value;

    public Trie() {
        triNode = new Trie[26];
    }

    public void insert(String word) {

        Trie point = this;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (point.triNode[index] == null) {
                point.triNode[index] = new Trie();
            }
            point = point.triNode[index];
        }
        point.value = word;

    }

    public boolean search(String word) {
        Trie point = this;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (point.triNode[index] == null) {
                return false;
            }
            point = point.triNode[index];
        }
        String tempValue = point.value;
        if (tempValue == null) {
            return false;
        }
        return true;


    }

    public boolean startsWith(String prefix) {
        Trie point = this;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            if (point.triNode[index] == null) {
                return false;
            }
            point = point.triNode[index];
        }
        return true;
    }
}