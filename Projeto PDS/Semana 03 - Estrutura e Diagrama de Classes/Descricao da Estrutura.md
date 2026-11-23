# Semana 03 — A Estrutura do FluxPay

**Aluno:** Pedro Henrique de Sousa Santana  
**Artefato principal:** `Diagrama_de_Classes.puml`

## 1. Objetivo

O diagrama de classes representa a estrutura estática do sistema: quais informações cada classe mantém, quais operações executa e como as classes se relacionam. A modelagem foi derivada das histórias e casos de uso da Semana 02.

## 2. Classes de domínio

- **Comerciante:** representa a pessoa ou empresa usuária do painel. Mantém dados de acesso e situação da conta. A senha é representada por hash, nunca por texto puro.
- **Cobranca:** representa o pedido de pagamento criado pelo comerciante. Armazena valor, descrição, data, link e status.
- **Transacao:** representa cada tentativa de pagamento vinculada a uma cobrança. Registra meio de pagamento, datas e resposta do processador.
- **Comprovante:** representa o documento demonstrativo gerado para uma transação aprovada.
- **ResultadoProcessamento:** encapsula o status, código e mensagem retornados pelo simulador.
- **AutenticacaoService:** valida as credenciais e a sessão do comerciante.
- **CobrancaController e PagamentoController:** recebem as solicitações das interfaces e encaminham aos serviços.
- **ComprovanteService:** centraliza a emissão do comprovante após uma aprovação.

## 3. Classes de serviço e contratos

- **CobrancaService:** valida e coordena a criação e consulta de cobranças.
- **PagamentoService:** coordena a tentativa de pagamento, registra seu resultado e solicita a emissão do comprovante.
- **ProcessadorPagamento:** interface que define o contrato de processamento.
- **SimuladorProcessador:** implementação acadêmica da interface, que retorna respostas fictícias.
- **RepositorioCobranca e RepositorioTransacao:** contratos para persistir e consultar os dados, sem prender as regras de negócio a uma tecnologia específica.

## 4. Relacionamentos e multiplicidades

- Um **Comerciante** pode criar zero ou muitas **Cobrancas**; cada cobrança pertence a exatamente um comerciante.
- Uma **Cobranca** pode registrar zero ou muitas **Transacoes**, pois o primeiro pagamento pode ser recusado ou ficar pendente e o pagador pode tentar novamente enquanto a cobrança continuar aberta.
- Uma **Transacao** pode possuir zero ou um **Comprovante**. O comprovante só é criado após aprovação.
- **SimuladorProcessador** implementa a interface **ProcessadorPagamento**.
- **PagamentoService** depende da interface de processamento, e não diretamente de uma instituição financeira específica. Isso permite substituir o simulador futuramente sem alterar o contrato principal.

## 5. Encapsulamento e tipos

Os atributos são privados (sinal `-`) e as operações públicas (sinal `+`). Valores monetários utilizam `BigDecimal`, evitando imprecisão típica de ponto flutuante. Identificadores usam `UUID`; datas e horários usam `LocalDateTime`.

Os enumeradores **StatusCobranca**, **StatusTransacao** e **FormaPagamento** limitam os valores possíveis e evitam estados escritos de maneiras diferentes.

## 6. Decisões e limites do modelo

O diagrama diferencia cobrança de transação: a cobrança é a solicitação comercial, enquanto a transação é uma tentativa concreta de pagamento. Assim, uma cobrança recusada pode continuar aberta sem ser confundida com uma cobrança paga.

A classe de simulação não representa uma integração bancária real. Não existe armazenamento de número real de cartão, CVV ou credenciais bancárias. Os dados de pagamento são fictícios e servem apenas para demonstrar a modelagem.

## 7. Justificativa da ausência de herança

A possibilidade de utilizar herança foi avaliada, mas o modelo não apresenta uma relação natural de generalização entre as classes de domínio. **Comerciante**, **Cobranca**, **Transacao** e **Comprovante** representam conceitos diferentes e possuem responsabilidades próprias; não existe uma classe mais geral da qual as demais sejam especializações do tipo “é um”. Criar uma superclasse apenas para agrupar essas entidades acrescentaria complexidade sem reaproveitamento real de atributos ou comportamentos.

As diferenças de meio de pagamento e de situação são representadas por **FormaPagamento**, **StatusCobranca** e **StatusTransacao**, que são enumeradores, pois restringem valores possíveis e não representam famílias de objetos com comportamentos especializados. Da mesma forma, os papéis de comerciante e pagador são atores do sistema e não justificam, por si só, uma hierarquia de classes.

Há, contudo, uma abstração por contrato: **SimuladorProcessador** implementa a interface **ProcessadorPagamento**. Essa relação é uma realização de interface, que permite substituir a implementação do processamento sem introduzir herança entre as classes de domínio. Portanto, a ausência de herança é uma decisão consciente, baseada nas responsabilidades e relações efetivamente identificadas no problema.
