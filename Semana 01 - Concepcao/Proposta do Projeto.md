# Semana 01 — Proposta do Projeto

**Disciplina:** Projeto e Desenvolvimento de Sistemas  
**Aluno:** Pedro Henrique de Sousa Santana  
**Equipe:** A definir (aluno informado: Pedro Henrique de Sousa Santana)  
**Aplicativo:** FluxPay — Gateway de Pagamentos  
**Tema:** Criação e acompanhamento de cobranças digitais

## 1. Problema identificado

Pequenos comerciantes que vendem pela internet podem receber pagamentos por diferentes meios e ter dificuldade para acompanhar cada cobrança em um único lugar. Quando as informações ficam espalhadas entre comprovantes, mensagens e sistemas diferentes, torna-se mais difícil identificar se uma transação está pendente, foi aprovada ou foi recusada, além de consultar o histórico de vendas.

O problema escolhido é, portanto, a falta de uma visão simples e centralizada para criar cobranças e acompanhar o resultado do processamento dos pagamentos.

## 2. Solução proposta

O FluxPay será um protótipo web de gateway de pagamentos voltado ao comerciante. Após realizar login, o comerciante poderá registrar uma cobrança com valor e descrição e compartilhar o link gerado. O pagador acessará o checkout, escolherá Pix ou cartão e acompanhará o resultado retornado por um módulo simulador de processamento.

O sistema apresentará o status da transação, manterá um histórico das cobranças e permitirá consultar os detalhes e o comprovante demonstrativo de cada operação.

## 3. Funcionalidades previstas no escopo inicial

- Tela de login para acesso do comerciante.
- Painel inicial com resumo das cobranças.
- Criação de cobrança com valor e descrição, com geração de link de checkout.
- Simulação de processamento por Pix ou cartão.
- Exibição de resultado: pendente, aprovado ou recusado.
- Consulta do histórico e dos detalhes das transações.
- Cancelamento de cobranças que ainda estejam abertas.
- Geração de comprovante demonstrativo.

## 4. Delimitação do escopo

A primeira versão será um protótipo acadêmico. As respostas de bancos ou processadoras serão simuladas para demonstrar o fluxo. O projeto não realizará transferências reais, não fará liquidação ou saque de valores e não utilizará credenciais bancárias reais. Integrações reais, mecanismos avançados antifraude e outros recursos poderão ser considerados fora do escopo inicial.

## 5. Resultado esperado

Entregar uma documentação clara de um gateway de pagamentos de pequeno porte, demonstrando como o comerciante acessa o sistema, cria uma cobrança e acompanha o processamento até obter um resultado. A modelagem das funcionalidades será desenvolvida nas próximas etapas da disciplina.

> **Nota sobre a equipe:** o guia da atividade recomenda a formação de dupla ou trio. Por enquanto, foi registrado o aluno informado. Caso o trabalho tenha outros integrantes, seus nomes deverão ser acrescentados antes da entrega.
