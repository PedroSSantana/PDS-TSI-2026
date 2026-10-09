# Semana 10 — Diagramas de Comunicação

Esta pasta contém os cinco diagramas de comunicação dos casos de uso disparados pelo Gerente no Sistema de Vendas da Cantina Escolar:

1. Cadastrar Produto
2. Alterar Preço
3. Atualizar Estoque
4. Remover Produto
5. Consultar Vendas

Os diagramas seguem o estilo do exemplo fornecido pelo professor: objetos, links com mensagens direcionadas, numeração hierárquica, guardas e iteração indicadas nas mensagens.

## Observação sobre o modelo

O diagrama de classes da Semana 7 não define classes de interface/controlador nem uma operação para consultar o conjunto de vendas. Por isso, `TelaGerente`, `ControladorProduto` e `ControladorVendas` são objetos de apoio assumidos para representar a interação. No caso de `Consultar Vendas`, `reunirDadosVenda(filtro)` também é uma mensagem de apoio inferida para expressar o comportamento do caso de uso; ela não aparece no diagrama de classes original.

Os diagramas representam uma proposta de comunicação coerente com os casos de uso, mas esses objetos auxiliares devem ser alinhados com o professor caso ele exija usar exclusivamente as classes já definidas na Semana 7.