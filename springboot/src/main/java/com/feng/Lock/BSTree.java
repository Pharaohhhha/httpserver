package com.feng.Lock;

/**
 * Created by TanChun on 2018/12/12.
 */
public class BSTree<T extends Comparable<T>> {

        private BSTNode<T> mRoot;    // 根结点

        public class BSTNode<T extends Comparable<T>> {
            T key;                // 关键字(键值)
            BSTNode<T> left;      // 左孩子
            BSTNode<T> right;     // 右孩子
            BSTNode<T> parent;    // 父结点

            public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right) {
                this.key = key;
                this.parent = parent;
                this.left = left;
                this.right = right;
            }
        }
    private void preOrder(BSTNode<T> tree) {
        if(tree != null) {
            System.out.print(tree.key+" ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }
    public void preOrder() {
        preOrder(mRoot);
    }
    public static void main(String[] args) {
        BSTree tbsTree = new BSTree<>();
        tbsTree.preOrder();
    }
}

