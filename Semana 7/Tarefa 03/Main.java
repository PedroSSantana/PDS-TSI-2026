
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

enum TipoPagamento {
    DINHEIRO, PIX, CREDITO, DEBITO
}

class Produto {
    private int codigo;
    private String nome;
    private String categoria;
    private BigDecimal preco;
    private int estoque;
    private boolean ativo;

    public Produto(int codigo, String nome, String categoria,
                   BigDecimal preco, int estoque) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0
                || estoque < 0) {
            throw new IllegalArgumentException("Preço ou estoque inválido.");
        }

        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = true;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void atualizarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço inválido.");
        }
        this.preco = preco;
    }

    public void atualizarEstoque(int quantidade) {
        if (this.estoque + quantidade < 0) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        this.estoque += quantidade;
    }

    public boolean temEstoque(int quantidade) {
        return ativo && quantidade > 0 && estoque >= quantidade;
    }

    public void vender(int quantidade) {
        if (!temEstoque(quantidade)) {
            throw new IllegalStateException(
                "Estoque insuficiente para: " + nome
            );
        }
        estoque -= quantidade;
    }

    public void desativar() {
        ativo = false;
    }
}

class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        if (produto == null || !produto.isAtivo() || quantidade <= 0) {
            throw new IllegalArgumentException("Item do pedido inválido.");
        }

        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return produto.getPreco().multiply(
            BigDecimal.valueOf(quantidade)
        );
    }
}

class Pagamento {
    private TipoPagamento tipo;
    private BigDecimal valorPago;

    public Pagamento(TipoPagamento tipo, BigDecimal valorPago) {
        if (tipo == null || valorPago == null
                || valorPago.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Pagamento inválido.");
        }

        this.tipo = tipo;
        this.valorPago = valorPago;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public boolean validarPagamento(BigDecimal total) {
        return valorPago.compareTo(total) >= 0;
    }

    public BigDecimal calcularTroco(BigDecimal total) {
        if (!validarPagamento(total)) {
            throw new IllegalStateException("Pagamento insuficiente.");
        }

        if (tipo != TipoPagamento.DINHEIRO) {
            return BigDecimal.ZERO;
        }

        return valorPago.subtract(total);
    }
}

class Pedido {
    private List<ItemPedido> itens;
    private LocalDateTime dataHora;
    private boolean finalizado;

    public Pedido() {
        itens = new ArrayList<>();
        finalizado = false;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (finalizado) {
            throw new IllegalStateException("Pedido já finalizado.");
        }

        if (produto == null || !produto.temEstoque(quantidade)) {
            throw new IllegalArgumentException(
                "Produto inválido ou estoque insuficiente."
            );
        }

        itens.add(new ItemPedido(produto, quantidade));
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : itens) {
            total = total.add(item.calcularSubtotal());
        }

        return total;
    }

    public boolean finalizar(Pagamento pagamento) {
        if (finalizado) {
            throw new IllegalStateException("Pedido já finalizado.");
        }

        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido não possui itens.");
        }

        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento obrigatório.");
        }

        // Verifica todo o estoque antes de efetuar qualquer baixa.
        for (ItemPedido item : itens) {
            Produto produto = item.getProduto();

            if (!produto.temEstoque(item.getQuantidade())) {
                throw new IllegalStateException(
                    "Estoque insuficiente para: " + produto.getNome()
                );
            }
        }

        BigDecimal total = calcularTotal();

        if (!pagamento.validarPagamento(total)) {
            return false;
        }

        // Baixa o estoque somente após todas as validações.
        for (ItemPedido item : itens) {
            item.getProduto().vender(item.getQuantidade());
        }

        dataHora = LocalDateTime.now();
        finalizado = true;
        return true;
    }

    public String gerarComprovante(Pagamento pagamento) {
        if (!finalizado || pagamento == null) {
            throw new IllegalStateException(
                "Finalize o pedido antes de gerar o comprovante."
            );
        }

        DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        StringBuilder comprovante = new StringBuilder();

        comprovante.append("\n===== CANTINA ESCOLAR =====\n");
        comprovante.append("Data: ")
                   .append(dataHora.format(formato)).append("\n");
        comprovante.append("---------------------------\n");

        for (ItemPedido item : itens) {
            comprovante.append(item.getProduto().getNome())
                .append(" x").append(item.getQuantidade())
                .append(" = R$ ")
                .append(item.calcularSubtotal().toPlainString())
                .append("\n");
        }

        BigDecimal total = calcularTotal();

        comprovante.append("---------------------------\n");
        comprovante.append("Total: R$ ")
                   .append(total.toPlainString()).append("\n");
        comprovante.append("Pagamento: ")
                   .append(pagamento.getTipo()).append("\n");
        comprovante.append("Valor pago: R$ ")
                   .append(pagamento.getValorPago().toPlainString())
                   .append("\n");
        comprovante.append("Troco: R$ ")
                   .append(pagamento.calcularTroco(total).toPlainString())
                   .append("\n");
        comprovante.append("===========================\n");

        return comprovante.toString();
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public boolean isFinalizado() {
        return finalizado;
    }
}

public class Main {
    public static void main(String[] args) {
        Produto coxinha = new Produto(
            1, "Coxinha", "Lanche",
            new BigDecimal("6.50"), 20
        );

        Produto suco = new Produto(
            2, "Suco", "Bebida",
            new BigDecimal("5.00"), 15
        );

        Pedido pedido = new Pedido();

        pedido.adicionarItem(coxinha, 2);
        pedido.adicionarItem(suco, 1);

        System.out.println("Total do pedido: R$ "
            + pedido.calcularTotal().toPlainString());

        Pagamento pagamento = new Pagamento(
            TipoPagamento.DINHEIRO,
            new BigDecimal("20.00")
        );

        if (pedido.finalizar(pagamento)) {
            System.out.println(pedido.gerarComprovante(pagamento));
        } else {
            System.out.println(
                "Pagamento insuficiente. Venda não finalizada."
            );
        }

        System.out.println("Estoque restante de coxinhas: "
            + coxinha.getEstoque());

        System.out.println("Estoque restante de sucos: "
            + suco.getEstoque());
    }
}