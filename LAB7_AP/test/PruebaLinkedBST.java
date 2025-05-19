package test;

import bstreelinklistinterfgeneric.LinkedBST;
import exceptions.ItemDuplicated;
import exceptions.ItemNotFound;
import exceptions.ExceptionIsEmpty;

public class PruebaLinkedBST {
    public static void main(String[] args) {
        LinkedBST<Integer> bst = new LinkedBST<>();

        System.out.println("== PRUEBA DE ARBOL BINARIO DE BÚSQUEDA ==\n");

        try {
            System.out.println("Insertando nodos...");
            int[] datos = { 50, 30, 70, 20, 40, 60, 80 };
            for (int dato : datos) {
                bst.insert(dato);
                System.out.println("Insertado: " + dato);
            }

            System.out.println("\nRecorridos:");
            System.out.println("InOrder     : " + bst.showInOrder());
            System.out.println("PreOrder    : " + bst.showPreOrder());
            System.out.println("PostOrder   : " + bst.showPostOrder());
            System.out.println("LevelOrder  : " + bst.showLevelOrder());

            System.out.println("\nMínimo: " + bst.getMin());
            System.out.println("Máximo: " + bst.getMax());

            System.out.println("\nAltura total del árbol: " + bst.height());
            System.out.println("¿Está balanceado?: " + bst.isBalanced());

            System.out.println("\nCantidad de nodos (todos): " + bst.countAllNodes());
            System.out.println("Cantidad de nodos internos: " + bst.countNodes());

            int nodoAltura = 70;
            System.out.println("Altura del subárbol con raíz " + nodoAltura + ": " + bst.height(nodoAltura));

            int nivel = 2;
            System.out.println("Amplitud del nivel " + nivel + ": " + bst.amplitude(nivel));

            System.out.println("Área del árbol (hojas * altura): " + bst.areaBST());

            System.out.println("\nDibujo del árbol (rotado 90°):");
            bst.drawBST();

            System.out.println("\nRepresentación parentizada:");
            bst.parenthesize();

            System.out.println("\nBuscando nodo 60: " + bst.search(60));

            System.out.println("\nEliminando nodo 70...");
            bst.delete(70);
            System.out.println("Nuevo InOrder: " + bst.showInOrder());

            System.out.println("\nDestruyendo el árbol...");
            bst.destroyNodes();
            System.out.println("Árbol destruido. InOrder actual: " + bst.showInOrder());

        } catch (ItemDuplicated e) {
            System.err.println("Error: Dato duplicado -> " + e.getMessage());
        } catch (ItemNotFound e) {
            System.err.println("Error: Dato no encontrado -> " + e.getMessage());
        } catch (ExceptionIsEmpty e) {
            System.err.println("Error: Árbol vacío -> " + e.getMessage());
        }
    }
}