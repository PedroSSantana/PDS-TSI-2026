# Semana 02 — A Visão do Usuário

**Projeto:** FluxPay — Gateway de Pagamentos  
**Aluno:** Pedro Henrique de Sousa Santana

## 1. Objetivo da semana

Traduzir a proposta da Semana 01 em necessidades observáveis dos usuários. As histórias descrevem o que cada ator precisa fazer e o benefício esperado. Os requisitos funcionais indicam o comportamento que o sistema deverá oferecer.

## 2. Atores identificados

- **Comerciante:** usuário autenticado que cria cobranças e acompanha os pagamentos.
- **Pagador:** pessoa que recebe o link de cobrança e realiza o pagamento no checkout.
- **Processador de Pagamentos (simulador):** sistema externo representado no modelo, responsável por devolver um resultado fictício de processamento.

## 3. Histórias de usuário

**HU01 — Acessar o painel**  
Como comerciante, quero entrar no FluxPay com minhas credenciais para administrar minhas cobranças com acesso restrito.

**HU02 — Criar cobrança**  
Como comerciante, quero informar valor e descrição de uma cobrança para enviar ao meu cliente um link de pagamento.

**HU03 — Compartilhar cobrança**  
Como comerciante, quero copiar o link gerado para encaminhá-lo ao pagador por um canal externo.

**HU04 — Pagar cobrança**  
Como pagador, quero abrir o link, escolher Pix ou cartão e confirmar o pagamento para quitar a cobrança.

**HU05 — Consultar resultado**  
Como comerciante, quero visualizar o status da cobrança e da transação para saber se o pagamento foi aprovado, recusado ou ainda está pendente.

**HU06 — Consultar histórico**  
Como comerciante, quero consultar minhas cobranças anteriores para acompanhar as operações realizadas.

**HU07 — Visualizar comprovante**  
Como comerciante e pagador, quero visualizar um comprovante demonstrativo quando o pagamento for aprovado para registrar o resultado da operação.

**HU08 — Cancelar cobrança**  
Como comerciante, quero cancelar uma cobrança que ainda esteja aberta para impedir novas tentativas de pagamento.

## 4. Requisitos funcionais

- **RF01:** o sistema deve autenticar o comerciante por e-mail e senha.
- **RF02:** o sistema deve permitir que o comerciante autenticado crie uma cobrança com valor positivo e descrição.
- **RF03:** o sistema deve gerar um identificador e um link único para cada cobrança.
- **RF04:** o checkout deve apresentar os dados essenciais da cobrança ao pagador.
- **RF05:** o pagador deve poder selecionar Pix ou cartão no ambiente de simulação.
- **RF06:** o sistema deve encaminhar a solicitação ao processador simulado.
- **RF07:** o sistema deve registrar o resultado como Aprovado, Recusado ou Pendente.
- **RF08:** o sistema deve atualizar o status da cobrança quando houver aprovação.
- **RF09:** o comerciante deve consultar a lista e os detalhes de suas cobranças.
- **RF10:** o sistema deve gerar comprovante demonstrativo para transações aprovadas.
- **RF11:** o comerciante deve poder cancelar uma cobrança que ainda esteja Aberta.

## 5. Requisitos não funcionais

- **RNF01 — Segurança:** senhas não devem ser armazenadas em texto puro.
- **RNF02 — Privacidade:** o protótipo não deve solicitar nem armazenar números reais de cartão, CVV ou credenciais bancárias.
- **RNF03 — Usabilidade:** formulários devem apresentar mensagens claras para validação e falhas.
- **RNF04 — Integridade:** o resultado de uma transação deve estar associado à cobrança correta.
- **RNF05 — Rastreabilidade:** cada cobrança e transação deve possuir identificador único e data/hora de registro.
- **RNF06 — Disponibilidade demonstrativa:** o sistema deve informar quando o simulador não puder responder, sem apresentar uma aprovação fictícia como sucesso.

## 6. Regras de negócio iniciais

- **RN01:** somente comerciante autenticado pode criar e consultar cobranças vinculadas à sua conta.
- **RN02:** o valor da cobrança deve ser maior que zero.
- **RN03:** uma cobrança recém-criada inicia com status **Aberta**.
- **RN04:** uma cobrança só passa para **Paga** quando uma transação associada for aprovada pelo simulador.
- **RN05:** uma transação recusada não altera a cobrança para Paga.
- **RN06:** resultado Pendente não pode ser tratado como aprovação.
- **RN07:** o protótipo utiliza dados fictícios e não movimenta dinheiro real.
- **RN08:** somente cobranças com status Aberta podem ser canceladas pelo comerciante.

## 7. Critérios de aceite essenciais

1. Com credenciais válidas, o comerciante acessa o painel; com credenciais inválidas, recebe uma mensagem de erro.
2. Uma cobrança com valor positivo gera identificador e link; valor zero, negativo ou descrição vazia são rejeitados.
3. O checkout apresenta o valor e a descrição da cobrança correspondente ao link.
4. O resultado retornado pelo simulador fica registrado e pode ser consultado.
5. Apenas uma transação aprovada marca a cobrança como Paga e libera o comprovante demonstrativo.
6. O comerciante consegue cancelar uma cobrança Aberta; cobranças Pagas ou já Canceladas não podem ser canceladas novamente.
