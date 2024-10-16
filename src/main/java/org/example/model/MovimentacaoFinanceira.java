package org.example.model;

import java.math.BigDecimal;
import java.util.Date;

public class MovimentacaoFinanceira {
    private Date data;
    private String descricao;
    private BigDecimal valor;
    private String tipoPagamento;
    private String categoria;
    private String tipoConta;

    public MovimentacaoFinanceira(Date data, String descricao, BigDecimal valor, String tipoPagamento,
                                  String categoria, String tipoConta) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
        this.categoria = categoria;
        this.tipoConta = tipoConta;
    }

    public Date getData() {return data;}
    public String getDescricao() {return descricao;}
    public BigDecimal getValor() {return valor;}
    public String getTipoPagamento() {return tipoPagamento;}
    public String getCategoria() {return categoria;}
    public String getTipoConta() {return tipoConta;}
}
