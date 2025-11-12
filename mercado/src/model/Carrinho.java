package model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
 private List<Produto> produtos;

 public Carrinho() {
     produtos = new ArrayList<>();
 }

 public void adicionarProduto(Produto p) {
     produtos.add(p);
 }

 public void removerProduto(Produto p) {
     produtos.remove(p);
 }

 public double calcularTotal() {
     double total = 0;
     for (Produto p : produtos) {
         total += p.getPreco();
     }
     return total;
 }

 public List<Produto> getProdutos() {
     return produtos;
 }
}
