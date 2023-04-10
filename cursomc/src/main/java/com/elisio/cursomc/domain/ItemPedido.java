package com.elisio.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;


@Entity
public class ItemPedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId//Como essa classe é uma auxiliar, essa notação representa isso
    private ItemPedidoPK id = new ItemPedidoPK(); //essa classe vai ser o id

    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        super();
        id.setPedido(pedido);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @JsonIgnore//para não gerar referencia ciclica, o que começa com get, não vai aparecer no json
    public Pedido getPedido() {
        return id.getPedido();
    }

    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

    public double getSubtotal(){
        return (preco - desconto) * quantidade;
    }

    public  Produto getProuto() {
        return id.getProduto();
    }

    public void setProduto(Produto produto) {
        id.setProduto(produto);
    }

    public Produto getProduto() {
        return id.getProduto();
    }

    public ItemPedidoPK getId() {
        return id;
    }

    public void setId(ItemPedidoPK id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public double getSubTotal() {
        return (preco - desconto) * quantidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemPedido other = (ItemPedido) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        StringBuilder builder = new StringBuilder();
        builder.append(getProduto().getNome());
        builder.append(", Qte: ");
        builder.append(getQuantidade());
        builder.append(", Preço unitário: ");
        builder.append(nf.format(getPreco()));
        builder.append(", Subtotal: ");
        builder.append(nf.format(getSubTotal()));
        builder.append("\n");
        return builder.toString();
    }
}
