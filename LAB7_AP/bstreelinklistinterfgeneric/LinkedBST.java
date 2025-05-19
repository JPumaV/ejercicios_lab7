package bstreelinklistinterfgeneric;

import bstreeinterface.BinarySearchTree;

import exceptions.ItemDuplicated;
import exceptions.ItemNotFound;
import exceptions.ExceptionIsEmpty;
import java.util.LinkedList;
import java.util.Queue;

public class LinkedBST<E extends Comparable<E>> implements BinarySearchTree<E> {

    private class Node {
        E data;
        Node left, right;

        Node(E data) {
            this.data = data;
        }
    }

    private Node root;

    @Override
    public void insert(E data) throws ItemDuplicated {
        root = insert(root, data);
    }

    private Node insert(Node node, E data) throws ItemDuplicated {
        if (node == null)
            return new Node(data);
        int cmp = data.compareTo(node.data);
        if (cmp < 0)
            node.left = insert(node.left, data);
        else if (cmp > 0)
            node.right = insert(node.right, data);
        else
            throw new ItemDuplicated("Dato duplicado: " + data);
        return node;
    }

    @Override
    public E search(E data) throws ItemNotFound {
        Node result = search(root, data);
        if (result == null)
            throw new ItemNotFound("Dato no encontrado: " + data);
        return result.data;
    }

    private Node search(Node node, E data) {
        if (node == null)
            return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0)
            return search(node.left, data);
        else if (cmp > 0)
            return search(node.right, data);
        else
            return node;
    }

    @Override
    public void delete(E data) throws ExceptionIsEmpty, ItemNotFound {
        if (root == null)
            throw new ExceptionIsEmpty("El árbol está vacío.");
        root = delete(root, data);
    }

    private Node delete(Node node, E data) throws ItemNotFound {
        if (node == null)
            throw new ItemNotFound("Dato no encontrado: " + data);
        int cmp = data.compareTo(node.data);
        if (cmp < 0)
            node.left = delete(node.left, data);
        else if (cmp > 0)
            node.right = delete(node.right, data);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            Node min = findMinNode(node.right);
            node.data = min.data;
            node.right = delete(node.right, min.data);
        }
        return node;
    }

    private Node findMinNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node findMaxNode(Node node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    public E getMin() throws ItemNotFound {
        if (root == null)
            throw new ItemNotFound("Árbol vacío");
        return findMinNode(root).data;
    }

    public E getMax() throws ItemNotFound {
        if (root == null)
            throw new ItemNotFound("Árbol vacío");
        return findMaxNode(root).data;
    }

    public String showInOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString().trim();
    }

    public String showPreOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        return sb.toString().trim();
    }

    public String showPostOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(root, sb);
        return sb.toString().trim();
    }

    public String showLevelOrder() {
        StringBuilder sb = new StringBuilder();
        if (root == null)
            return "";
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            sb.append(current.data).append(" ");
            if (current.left != null)
                queue.offer(current.left);
            if (current.right != null)
                queue.offer(current.right);
        }
        return sb.toString().trim();
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null)
            return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null)
            return true;
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return Math.abs(leftHeight - rightHeight) <= 1 &&
                isBalanced(node.left) &&
                isBalanced(node.right);
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node != null) {
            inOrder(node.left, sb);
            sb.append(node.data).append(" ");
            inOrder(node.right, sb);
        }
    }

    private void preOrder(Node node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.data).append(" ");
            preOrder(node.left, sb);
            preOrder(node.right, sb);
        }
    }

    private void postOrder(Node node, StringBuilder sb) {
        if (node != null) {
            postOrder(node.left, sb);
            postOrder(node.right, sb);
            sb.append(node.data).append(" ");
        }
    }

    @Override
    public String toString() {
        return "InOrder: " + showInOrder();
    }

    public void destroyNodes() throws ExceptionIsEmpty {
        if (root == null)
            throw new ExceptionIsEmpty("El árbol ya está vacío.");
        root = null;
    }

    public int countAllNodes() {
        return countAllNodes(root);
    }

    private int countAllNodes(Node node) {
        if (node == null)
            return 0;
        return 1 + countAllNodes(node.left) + countAllNodes(node.right);
    }

    public int countNodes() {
        return countNonLeafNodes(root);
    }

    private int countNonLeafNodes(Node node) {
        if (node == null || (node.left == null && node.right == null))
            return 0;
        return 1 + countNonLeafNodes(node.left) + countNonLeafNodes(node.right);
    }

    public int height(E x) {
        Node current = root;
        while (current != null) {
            int cmp = x.compareTo(current.data);
            if (cmp < 0)
                current = current.left;
            else if (cmp > 0)
                current = current.right;
            else
                return height(current);
        }
        return -1;
    }

    public int amplitude(int nivel) {
        if (root == null || nivel < 0)
            return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int currentLevel = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (currentLevel == nivel)
                return size;
            for (int i = 0; i < size; i++) {
                Node current = queue.poll();
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            currentLevel++;
        }
        return 0;
    }

    public int areaBST() {
        if (root == null)
            return 0;
        int leafCount = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.left == null && current.right == null)
                leafCount++;
            if (current.left != null)
                queue.offer(current.left);
            if (current.right != null)
                queue.offer(current.right);
        }
        return leafCount * height();
    }

    public void drawBST() {
        drawBST(root, 0);
    }

    private void drawBST(Node node, int depth) {
        if (node == null)
            return;
        drawBST(node.right, depth + 1);
        for (int i = 0; i < depth; i++)
            System.out.print("    ");
        System.out.println(node.data);
        drawBST(node.left, depth + 1);
    }

    public void parenthesize() {
        parenthesize(root, 0);
    }

    private void parenthesize(Node node, int depth) {
        if (node == null)
            return;
        for (int i = 0; i < depth; i++)
            System.out.print("  ");
        System.out.println("(" + node.data);
        parenthesize(node.left, depth + 1);
        parenthesize(node.right, depth + 1);
        for (int i = 0; i < depth; i++)
            System.out.print("  ");
        System.out.println(")");
    }
}