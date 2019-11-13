package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        @SuppressWarnings("unchecked")
        T data = (T) o;
        Node<T> par = root;
        Node<T> cur = root;
        boolean IsLeftChild = false;
        while (cur.value != data) {
            par = cur;
            if (data.compareTo(cur.value) < 0) {
                cur = cur.left;
                IsLeftChild = true;
            }
            else {
                cur = cur.right;
                IsLeftChild = false;
            }
        }
        if (cur == null) {
            if (cur == root) {
                root = cur.left;
            } else if (IsLeftChild){
                par.left = cur.right;
            } else {
                par.right = cur.right;
            }
        } else if (cur.right == null) {
            if (cur == root) {
                root = cur.left;
            } else if (IsLeftChild) {
                par.left = cur.left;
            } else {
                par.right = cur.left;
            }
        } else {
            Node<T> successor = cur.right;
            Node<T> successorPar = cur;

            while (successor.left != null) {
                successorPar = successor;
                successor = successor.left;
            }
            if (successor != cur.right) {
                successorPar.left = successor.right;
                successor.right = cur.right;
            }
            successor.left = cur.left;
            if (cur == root) {
                root = successor;
            } else if (IsLeftChild) {
                par.left = successor;
            } else {
                par.right = successor;
            }
        }
        size--;
        return true;
}

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> cur = null;
        private int location = 0;
        private List<Node<T>> list;
        private BinaryTreeIterator() {
            list = new ArrayList<>();
            addToList(root);
        }
        private void addToList(Node<T> node) {
            if (node != null) {
                addToList(node.left);
                list.add(node);
                addToList(node.right);
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return location < list.size();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            return list.get(location++);
        }
        }
        @Override
        public T next() {
            cur = findNext();
            if (cur == null) try {
                throw new NoSuchFieldException();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return cur.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            BinaryTree.this.remove(list.get(location - 1).value);
            list.remove(location -1);
            location--;

        }
    }

        @NotNull
        @Override
        public Iterator<T> iterator() {
        return new BinaryTreeIterator();
        }

        @Override
        public int size() {
        return size;
        }

        @Nullable
        @Override
        public Comparator<? super T> comparator() {
        return null;
        }

        /**
         * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
         * Очень сложная
         */
        @NotNull
        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            // TODO
            throw new NotImplementedError();
        }

        /**
         * Найти множество всех элементов меньше заданного
         * Сложная
         */
        @NotNull
        @Override
        public SortedSet<T> headSet(T toElement) {
            // TODO
            throw new NotImplementedError();
        }

        /**
         * Найти множество всех элементов больше или равных заданного
         * Сложная
         */
        @NotNull
        @Override
        public SortedSet<T> tailSet(T fromElement) {
            // TODO
            throw new NotImplementedError();
        }

        @Override
        public T first() {
            if (root == null) throw new NoSuchElementException();
            Node<T> current = root;
            while (current.left != null) {
                current = current.left;
            }
            return current.value;
        }

        @Override
        public T last() {
            if (root == null) throw new NoSuchElementException();
            Node<T> current = root;
            while (current.right != null) {
                current = current.right;
            }
            return current.value;
        }
    }

