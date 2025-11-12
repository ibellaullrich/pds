package controller;

import java.util.List;

import model.Carrinho;
import model.Produto;

public class CarrinhoController {
 private Carrinho carrinho;
 private ProdutoController produtoController;

 public CarrinhoController() {
     this.carrinho = new Carrinho();
     this.produtoController = new ProdutoController();
 }

 public void adicionarProduto(Produto p) {
     if (p.getQuantidade() > 0) {
         carrinho.adicionarProduto(p);

         produtoController.atualizarEstoque(p.getId(), p.getQuantidade() - 1);
         p.setQuantidade(p.getQuantidade() - 1);
     }
 }

 public void removerProduto(Produto p) {
     if (carrinho.getProdutos().contains(p)) {
         carrinho.removerProduto(p);
         // Reverte o estoque no banco
         produtoController.atualizarEstoque(p.getId(), p.getQuantidade() + 1);
         p.setQuantidade(p.getQuantidade() + 1);
     }
 }

 public double calcularTotal() {
     return carrinho.calcularTotal();
 }

 public List<Produto> getProdutosCarrinho() {
     return carrinho.getProdutos();
 }
}
