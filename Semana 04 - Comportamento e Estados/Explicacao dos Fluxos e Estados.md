# Semana 04 — Comportamento e Ciclo de Vida

**Aluno:** Pedro Henrique de Sousa Santana

## 1. Objetivo

Os diagramas desta semana mostram como os participantes colaboram para executar os casos de uso e como os objetos mudam de estado durante o processamento. Os fluxos foram derivados dos casos de uso UC01 e UC02 da Semana 02 e das classes definidas na Semana 03.

## 2. Diagrama de sequência — Criar cobrança

O comerciante preenche o formulário no Painel Web. O controlador recebe a solicitação e encaminha a operação ao serviço de cobrança. O serviço valida a sessão e os dados, cria a entidade Cobranca, gera identificador e link e solicita a persistência. Somente após a confirmação do repositório o painel apresenta o link.

O fragmento `alt` distingue dados válidos de dados inválidos. Assim, não existe cobrança persistida quando o valor ou a descrição não passam pela validação.

## 3. Diagrama de sequência — Processar pagamento

O pagador abre o link e o checkout solicita os dados da cobrança. Depois da seleção do método de pagamento, o serviço valida se a cobrança está Aberta e cria uma Transacao. A solicitação é encaminhada ao contrato ProcessadorPagamento, implementado nesta atividade pelo simulador.

O fragmento `alt` apresenta quatro respostas possíveis: aprovada, recusada, pendente e falha técnica. Somente a aprovação altera a cobrança para Paga e permite gerar o comprovante. Recusa, pendência e falha técnica ficam registradas, mas não devem ser exibidas como pagamento concluído.

## 4. Diagrama de estados — Cobrança

O ciclo começa quando a cobrança é criada e entra em **Aberta**. A partir daí, pode receber tentativas de pagamento. Quando uma transação é aprovada, passa para **Paga**. O comerciante também pode cancelar uma cobrança ainda aberta, levando-a para **Cancelada**.

Uma tentativa recusada ou pendente não altera o estado da cobrança. Essa regra permite que o pagador tente novamente sem criar outra cobrança.

## 5. Diagrama de estados — Transação

Cada tentativa começa em **Criada** e passa para **Em Processamento** quando é enviada ao simulador. O retorno pode gerar **Aprovada**, **Recusada**, **Pendente** ou **Falha Técnica**.

Uma transação pendente pode receber confirmação posterior de aprovação ou recusa. Não se emite comprovante de aprovação enquanto estiver pendente. Uma falha técnica encerra aquela tentativa, mas não cancela automaticamente a cobrança.

## 6. Regras observadas nos diagramas

- Uma cobrança pode ter diversas transações, mas apenas uma aprovação é necessária para marcá-la como Paga.
- Uma transação pertence a uma única cobrança.
- O status de uma cobrança não deve ser alterado apenas porque uma solicitação foi enviada.
- A resposta do simulador precisa ser registrada antes de comunicar o resultado ao usuário.
- O sistema não deve gerar comprovante de aprovação para transações recusadas, pendentes ou com falha técnica.

## 7. Relação com as semanas anteriores

Os nomes dos atores e os objetivos vêm das histórias de usuário e dos casos de uso da Semana 02. Os participantes técnicos e as entidades correspondem às classes da Semana 03. Essa correspondência mantém a documentação coerente e permite rastrear cada fluxo até a necessidade que o originou.
