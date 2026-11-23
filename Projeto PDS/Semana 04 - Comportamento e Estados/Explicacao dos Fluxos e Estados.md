# Semana 04 — Comportamento e Ciclo de Vida

**Aluno:** Pedro Henrique de Sousa Santana

## 1. Objetivo

Os diagramas desta semana mostram como os participantes colaboram para executar os casos de uso e como os objetos mudam de estado durante o processamento. Os fluxos foram derivados dos casos de uso UC01 e UC02 da Semana 02 e das classes definidas na Semana 03.

## 2. Diagrama de sequência — Criar cobrança

O comerciante preenche o formulário no Painel Web. O controlador recebe a solicitação e encaminha a operação ao serviço de cobrança. O serviço valida a sessão e os dados, cria a entidade Cobranca, gera identificador e link e solicita a persistência. Somente após a confirmação do repositório o painel apresenta o link.

O primeiro fragmento `alt` distingue dados válidos de dados inválidos. Quando os dados são válidos, o serviço cria a cobrança e solicita sua persistência. Um segundo `alt` diferencia a confirmação do repositório de uma falha ao salvar. O link só é apresentado ao comerciante depois da confirmação; em caso de falha de persistência, o sistema informa o erro e não apresenta a cobrança como criada. Quando os dados são inválidos, nenhuma cobrança é persistida.

## 3. Diagrama de sequência — Processar pagamento

O pagador abre o link e o sistema localiza a cobrança. Antes de apresentar o checkout, o serviço verifica se ela existe e permanece Aberta. Se a cobrança estiver ausente, cancelada ou em outro estado, o sistema bloqueia o acesso ao fluxo de pagamento e não cria uma transação. Após a seleção do método, o serviço verifica novamente se a cobrança continua Aberta e valida os dados fictícios. Se a cobrança tiver mudado de estado ou os dados não atenderem aos requisitos mínimos, o processamento é bloqueado antes da criação de uma transação.

Com a cobrança ainda Aberta e os dados válidos, o serviço cria uma Transacao e encaminha a solicitação ao contrato ProcessadorPagamento, implementado nesta atividade pelo simulador. O fragmento `alt` apresenta quatro respostas possíveis: aprovada, recusada, pendente e falha técnica. Somente a aprovação altera a cobrança para Paga e permite gerar o comprovante. Recusa, pendência e falha técnica ficam registradas, mas não devem ser exibidas como pagamento concluído.

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
