package suggestedProducts.problem;

import java.util.ArrayList;
import java.util.List;

public class SearchSuggestionsSystem {
    private class TrieNode{
        boolean end = false;
        String str = null;
        int count = 0;
        TrieNode[] children = new TrieNode[26];
    }

    private class Trie{
        TrieNode root = new TrieNode();
        public void insert(String[] products){
            for(String str : products){
                insertWord(str);
            }
        }
        private void insertWord(String products){
            TrieNode node = root;
            for(char c : products.toCharArray()){
                if(node.children[c - 'a'] == null){
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            if(node.end != true){
                node.end = true;
                node.str = products;
            }
            node.count++;
        }
        public List<List<String>> searchWord(String word){
            List<List<String>> result = new ArrayList<>();
            for(int i = 1; i <= word.length(); i++){
                result.add(search(word.substring(0, i)));
            }
            return result;
        }
        private List<String> search(String pattern){
            List<String> result = new ArrayList<>();
            TrieNode node = root;
            for(char c : pattern.toCharArray()){
                if(node.children[c - 'a'] == null){
                    return result;
                }
                node = node.children[c - 'a'];
            }
            Solution(node, result);
            return result;
        }
        private void Solution(TrieNode root, List<String> result){
            if(root.end){
                for(int i = 0; i < root.count; i++){
                    result.add(root.str);
                    if(result.size() == 3){
                        return;
                    }
                }
            }
            for(TrieNode node : root.children){
                if(node != null){
                    Solution(node, result);
                }
                if(result.size() == 3){
                    return;
                }
            }
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie trie = new Trie();
        trie.insert(products);
        return trie.searchWord(searchWord);
    }
}

//作者：ChopinXBP
//        链接：https://leetcode.cn/problems/search-suggestions-system/solution/zi-dian-shu-dfsjava-by-chopinxbp/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
