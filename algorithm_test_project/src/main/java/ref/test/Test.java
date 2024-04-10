package ref.test;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<TreeNode> treeNodeList = new ArrayList<>();
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNodeLeft = new TreeNode(2);
        TreeNode treeNodeRight = new TreeNode(3);

        treeNode.left =treeNodeLeft;
        treeNode.right = treeNodeRight;
        treeNodeList.add(treeNode);
        treeNodeLeft = null;
        System.out.println(treeNode.left.val);

    }
}
