package word.rectangle.lcci.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    class Trie{
        boolean isEnd = false;
        Trie[] child = new Trie[26];
    }
    void add(String s){
        Trie cur = root;
        for(char c : s.toCharArray()){
            int n = c - 'a';
            if(cur.child[n] == null) cur.child[n] = new Trie();
            cur = cur.child[n];
        }
        cur.isEnd = true;
    }
    Trie root;
    //用max保存当前最大矩形面积
    int max = -1;
    //用哈希表保存各个长度字符串的集合
    Map<Integer, List<String>> map = new HashMap<>();
    List<String> ans = new ArrayList<>();
    public String[] maxRectangle(String[] words) {
        this.root = new Trie();
        int n = words.length;
        for(String w : words){
            add(w);
            map.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        }
        //按照字符串长度从大到小排序
        Arrays.sort(words, (a, b) -> b.length() - a.length());
        for(int i = 0; i < n; i++){
            Trie cur = root;
            String w = words[i];
            //用curList保存矩形的每个字符串
            List<String> curList = new ArrayList<>();
            curList.add(w);
            //用list保存当前可能形成矩形的字符串在字典树中的当前结点
            List<Trie> list = new ArrayList<>();
            int len = w.length();
            //如果当前长度的积不大于最大矩形面积，则退出
            //因为从最大到最小排序，其中4 * 3的字符串矩阵和3 * 4的矩阵是相同的，可以进行去重
            if(len * len <= max) break;
            //check检测是否能组成矩形，flag检测是否可能组成更大的矩形
            boolean check = true, flag = true;
            for(int j = 0; j < len; j++){
                int c = w.charAt(j) - 'a';
                if(cur.child[c] != null){
                    //判断当前字符串是否所有结点都为叶结点，即能组成字符串
                    if(!cur.child[c].isEnd) check = false;
                    list.add(cur.child[c]);
                }else{
                    //如果字符串没有对应子结点，则无法组成矩形
                    check = false;
                    flag = false;
                    break;
                }
            }
            //如果当前字符串可以组成矩形，则进入dfs
            if(flag){
                dfs(1, len, curList, list);
            }
            //如果当前字符串所有结点都可以为叶结点，则判断当前矩形是否最大
            if(check && max == -1){
                max = len;
                ans = curList;
            }
        }
        int size = ans.size();
        String[] strs = new String[size];
        for(int i = 0; i < size; i++) strs[i] = ans.get(i);
        // System.out.println(max);
        return strs;
    }
    void dfs(int cur, int len, List<String> curList, List<Trie> list){
        if(cur == len){
            return;
        }
        //各种结点判断同上
        for(String w : map.get(len)){
            boolean check = true, flag = true;
            //其中next保存下一个可能形成矩形的字典树结点集合
            List<Trie> next = new ArrayList<>();
            //nextList保存下一个可能形成矩形的字符串集合
            List<String> nextList = new ArrayList<>();
            for(int i = 0; i < len; i++){
                int c = w.charAt(i) - 'a';
                Trie ct = list.get(i);
                if(ct.child[c] != null){
                    if(!ct.child[c].isEnd) check = false;
                    next.add(ct.child[c]);
                }else{
                    check = false;
                    flag = false;
                    break;
                }
            }
            if(flag){
                // System.out.println(w);
                nextList.addAll(curList);
                nextList.add(w);
                dfs(cur + 1, len, nextList, next);
            }
            if(check && len * (cur + 1) > max){
                max = len * (cur + 1);
                ans = nextList;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String [] words = {"this", "real", "hard", "trh", "hea", "iar", "sld"};
        solution.maxRectangle(words);
    }

    /**
     *
     作者：lucian-6
     链接：https://leetcode.cn/problems/word-rectangle-lcci/solution/-by-lucian-6-azl3/
     来源：力扣（LeetCode）
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}
