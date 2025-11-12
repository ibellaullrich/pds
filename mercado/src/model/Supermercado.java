package model;

import java.util.List;

public class Supermercado {
 private List<Produto> produtos;

 public Supermercado(List<Produto> produtos) {
     this.produtos = produtos;
 }

 public List<Produto> getProdutos() {
     return produtos;
 }

 public void setProdutos(List<Produto> produtos) {
     this.produtos = produtos;
 }
}
