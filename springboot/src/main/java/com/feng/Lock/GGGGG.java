package com.feng.Lock;

/**
 * Created by TanChun on 2018/12/18.
 */
public class GGGGG {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (root.val == val) {
            return root;
        } else if (root.val > val) { // 在左子树中查找
            return searchBST(root.left, val);
        } else { // 在右子树中查找
            return searchBST(root.right, val);
        }
    }
    }

