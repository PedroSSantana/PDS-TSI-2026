# Caso de Uso UC02 — Processar Pagamento

**Projeto:** FluxPay  
**Ator principal:** Pagador  
**Ator secundário:** Processador de Pagamentos (Simulador)  
**Objetivo:** solicitar o processamento simulado de uma cobrança e registrar o resultado retornado.  
**Prioridade:** Alta

## Pré-condições

- Existe uma cobrança identificada pelo link acessado.
- A cobrança está com status **Aberta**.
- O pagador está no checkout correspondente à cobrança.

## Pós-condições

- Uma transação fica associada à cobrança.
- O resultado é registrado como **Aprovado**, **Recusado** ou **Pendente**.
- A cobrança passa para **Paga** somente quando o resultado é aprovado.
- Para resultado aprovado, um comprovante demonstrativo pode ser visualizado.

## Fluxo principal

1. O pagador abre o link de checkout recebido.
2. O sistema localiza a cobrança pelo identificador do link.
3. O sistema verifica se a cobrança existe e permanece Aberta.
4. O sistema apresenta descrição, valor e métodos disponíveis.
5. O pagador seleciona Pix ou cartão.
6. O sistema apresenta campos demonstrativos apropriados ao método selecionado.
7. O pagador informa dados fictícios de teste e confirma.
8. O sistema valida os dados mínimos e cria uma transação em estado **Criada**.
9. O sistema envia a solicitação ao Processador de Pagamentos (Simulador).
10. O simulador avalia o cenário de teste e retorna um resultado.
11. O sistema registra o resultado e os dados de retorno.
12. Se aprovado, o sistema marca a transação como **Aprovada** e a cobrança como **Paga**.
13. O sistema apresenta a confirmação ao pagador.
14. O sistema disponibiliza o comprovante demonstrativo.

## Fluxos alternativos e de exceção

**FA01 — Pagamento recusado**
1. No passo 10, o simulador retorna resultado recusado.
2. O sistema registra a transação como **Recusada**.
3. A cobrança permanece **Aberta**.
4. O checkout informa que o pagamento não foi aprovado.

**FA02 — Processamento pendente**
1. No passo 10, o simulador retorna resultado pendente.
2. O sistema registra a transação como **Pendente**.
3. A cobrança permanece **Aberta** até uma confirmação posterior.
4. O checkout informa que o resultado ainda está em processamento.

**FA03 — Cobrança inexistente, cancelada ou não aberta**
1. No passo 2 ou 3, o sistema não encontra a cobrança ou identifica status diferente de Aberta.
2. O sistema bloqueia a tentativa e apresenta uma mensagem adequada.
3. Nenhuma transação é processada.

**FE01 — Indisponibilidade do simulador**
1. No passo 9 ou 10, o simulador não responde.
2. O sistema registra a falha técnica sem marcar o pagamento como aprovado.
3. O checkout informa que não foi possível concluir o processamento.

## Regras relacionadas

- RN04: somente transação aprovada altera a cobrança para Paga.
- RN05: transação recusada não aprova a cobrança.
- RN06: pendência não equivale a aprovação.
- RN07: todos os dados e resultados são demonstrativos.

## Observação de segurança

O protótipo deve utilizar dados fictícios. Não deve solicitar credenciais bancárias, número real de cartão ou CVV, nem armazenar dados financeiros reais.
