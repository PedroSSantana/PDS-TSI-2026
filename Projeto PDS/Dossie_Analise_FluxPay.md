# Dossiê de Análise do Projeto — FluxPay

**Disciplina:** Projeto e Desenvolvimento de Sistemas  
**Aluno:** Pedro Henrique de Sousa Santana  
**Equipe:** a definir, conforme a composição confirmada pelo aluno  
**Aplicativo:** FluxPay — Gateway de Pagamentos (nome provisório)  
**Tema:** criação, processamento simulado e acompanhamento de cobranças digitais

---

## 1. Apresentação do projeto

O FluxPay é uma proposta de protótipo web para pequenos comerciantes criarem cobranças e acompanharem os resultados dos pagamentos em um único ambiente. O comerciante acessa um painel autenticado, registra uma cobrança e compartilha o link de checkout. O pagador abre esse link, escolhe Pix ou cartão e conclui uma tentativa de pagamento em ambiente demonstrativo.

Um simulador representa o processador de pagamentos e pode retornar aprovação, recusa, pendência ou falha técnica. O sistema registra o resultado e atualiza a cobrança conforme as regras definidas neste dossiê.

**Limite fundamental:** o FluxPay é um trabalho acadêmico. Não realiza transferências, liquida valores, movimenta dinheiro ou se conecta a bancos e processadoras reais. Todos os dados de pagamento usados nos testes são fictícios.

## 2. Problema identificado e solução proposta

Pequenos comerciantes podem receber pagamentos por diferentes meios e ter dificuldade para acompanhar cada cobrança em um único lugar. Quando informações ficam espalhadas entre mensagens, comprovantes e sistemas diferentes, torna-se mais difícil identificar se uma tentativa está pendente, foi aprovada ou foi recusada, além de consultar o histórico.

A solução proposta centraliza a criação e o acompanhamento de cobranças. O comerciante utiliza login e painel web; cada cobrança possui identificador e link exclusivo; o pagador usa um checkout simples; e o simulador demonstra o retorno do processamento. O comerciante acompanha os resultados e consulta o comprovante demonstrativo quando houver aprovação.

## 3. Escopo do sistema

### 3.1 Funcionalidades incluídas

- Autenticação do comerciante por e-mail e senha.
- Painel com resumo e histórico de cobranças.
- Criação de cobrança com valor e descrição.
- Geração de identificador e link de checkout.
- Checkout para seleção de Pix ou cartão com dados fictícios.
- Processamento simulado com resultados aprovado, recusado, pendente ou falha técnica.
- Consulta de status e detalhes das cobranças e transações.
- Cancelamento de cobrança que ainda esteja Aberta.
- Geração de comprovante demonstrativo após aprovação.

### 3.2 Fora do escopo

- Transferências ou recebimentos financeiros reais.
- Integração real com bancos, adquirentes ou instituições de pagamento.
- Armazenamento de número real de cartão, CVV ou credenciais bancárias.
- Liquidação, saque, split de pagamento ou gestão de saldo.
- Antifraude avançado e operação em produção.

---

## 4. Semana 02 — Requisitos e visão do usuário

### 4.1 Atores

| Ator | Responsabilidade |
|---|---|
| Comerciante | Autentica-se, cria cobranças, consulta resultados e pode cancelar cobranças abertas. |
| Pagador | Acessa o checkout por link, escolhe o meio de pagamento e acompanha o resultado. |
| Processador de Pagamentos (Simulador) | Sistema externo representado no modelo que devolve o resultado fictício da tentativa. |

### 4.2 Histórias de usuário

- **HU01 — Acessar o painel:** como comerciante, quero entrar com minhas credenciais para administrar minhas cobranças com acesso restrito.
- **HU02 — Criar cobrança:** como comerciante, quero informar valor e descrição para enviar ao cliente um link de pagamento.
- **HU03 — Compartilhar cobrança:** como comerciante, quero copiar o link gerado para encaminhá-lo ao pagador.
- **HU04 — Pagar cobrança:** como pagador, quero abrir o link, escolher Pix ou cartão e confirmar a tentativa de pagamento.
- **HU05 — Consultar resultado:** como comerciante, quero visualizar o status da cobrança e da transação.
- **HU06 — Consultar histórico:** como comerciante, quero consultar cobranças anteriores.
- **HU07 — Visualizar comprovante:** como comerciante ou pagador, quero visualizar um comprovante demonstrativo quando houver aprovação.
- **HU08 — Cancelar cobrança:** como comerciante, quero cancelar uma cobrança ainda aberta para impedir novas tentativas.

### 4.3 Requisitos funcionais

| Código | Requisito |
|---|---|
| RF01 | Autenticar o comerciante por e-mail e senha. |
| RF02 | Permitir a criação de cobrança com valor positivo e descrição. |
| RF03 | Gerar identificador e link únicos para cada cobrança. |
| RF04 | Apresentar no checkout os dados essenciais da cobrança. |
| RF05 | Permitir selecionar Pix ou cartão em ambiente de simulação. |
| RF06 | Encaminhar a solicitação ao processador simulado. |
| RF07 | Registrar resultado aprovado, recusado ou pendente, além de falha técnica. |
| RF08 | Atualizar a cobrança para Paga quando houver aprovação. |
| RF09 | Permitir consultar lista e detalhes das cobranças do comerciante. |
| RF10 | Gerar comprovante demonstrativo para transações aprovadas. |
| RF11 | Permitir cancelar cobranças que ainda estejam Abertas. |

### 4.4 Requisitos não funcionais

- **RNF01 — Segurança:** senhas não podem ser armazenadas em texto puro.
- **RNF02 — Privacidade:** não solicitar nem armazenar número real de cartão, CVV ou credenciais bancárias.
- **RNF03 — Usabilidade:** apresentar mensagens claras para validações e falhas.
- **RNF04 — Integridade:** manter cada transação associada à cobrança correta.
- **RNF05 — Rastreabilidade:** gerar identificadores únicos e registrar data e hora.
- **RNF06 — Disponibilidade demonstrativa:** informar indisponibilidade do simulador sem apresentar uma aprovação fictícia.

### 4.5 Casos de uso principais

**UC01 — Criar cobrança**  
Ator principal: Comerciante.  
Pré-condições: comerciante cadastrado e autenticado.  
Fluxo resumido: abrir formulário; informar valor e descrição; confirmar; validar os dados; criar cobrança Aberta; gerar identificador e link; persistir; apresentar confirmação e link.  
Exceções: valor menor ou igual a zero, descrição vazia ou falha de persistência. Em qualquer desses casos, não apresentar uma cobrança como criada.

**UC02 — Processar pagamento**  
Ator principal: Pagador. Ator secundário: Processador de Pagamentos (Simulador).  
Pré-condições: link correspondente a uma cobrança existente e Aberta.  
Fluxo resumido: abrir checkout; conferir valor e descrição; escolher Pix ou cartão; informar dados fictícios; confirmar; o sistema cria a transação e a envia ao simulador; registrar a resposta; apresentar o resultado.  
Alternativas: recusado, pendente ou falha técnica. Nenhuma dessas respostas deve marcar a cobrança como Paga. Somente a aprovação altera a cobrança e permite o comprovante.

**Outros casos de uso:** autenticar-se, consultar cobranças e transações, visualizar detalhes, acessar checkout, validar cobrança, solicitar processamento, registrar resultado, visualizar comprovante e cancelar cobrança.

**Diagrama:** [Fonte PlantUML](Semana%2002%20-%20Requisitos%20e%20Casos%20de%20Uso/Diagrama_Casos_de_Uso.puml) | [Imagem PNG](Semana%2002%20-%20Requisitos%20e%20Casos%20de%20Uso/Diagrama_Casos_de_Uso.png)

![Diagrama de Casos de Uso](Semana%2002%20-%20Requisitos%20e%20Casos%20de%20Uso/Diagrama_Casos_de_Uso.png)  
**Especificação detalhada:** [UC01 — Criar Cobrança](Semana%2002%20-%20Requisitos%20e%20Casos%20de%20Uso/UC01%20-%20Criar%20Cobranca.md) | [UC02 — Processar Pagamento](Semana%2002%20-%20Requisitos%20e%20Casos%20de%20Uso/UC02%20-%20Processar%20Pagamento.md)

---

## 5. Semana 03 — Estrutura do sistema

### 5.1 Classes principais

| Classe | Responsabilidade e dados principais |
|---|---|
| Comerciante | Identificador, nome, e-mail, hash da senha e situação da conta. |
| Cobranca | Identificador, descrição, valor, data de criação, link de checkout e status. |
| Transacao | Identificador, valor, meio de pagamento, status, datas e código/mensagem do retorno. |
| Comprovante | Identificador, código e data de emissão do documento demonstrativo. |
| ResultadoProcessamento | Status, código e mensagem retornados pelo simulador. |
| AutenticacaoService | Autenticar credenciais e validar a sessão do comerciante. |
| CobrancaController / PagamentoController | Receber requisições das interfaces e encaminhá-las aos serviços. |
| CobrancaService | Criar, consultar e cancelar cobranças; consultar transações relacionadas. |
| PagamentoService | Criar a tentativa, solicitar processamento e registrar o resultado. |
| ComprovanteService | Emitir comprovante após aprovação. |
| ProcessadorPagamento | Interface que define o contrato de processamento. |
| SimuladorProcessador | Implementação fictícia do contrato de processamento. |
| RepositorioCobranca / RepositorioTransacao | Contratos de persistência e consulta. |

### 5.2 Relacionamentos e multiplicidades

- Um Comerciante cria zero ou muitas Cobrancas; cada cobrança pertence a um comerciante.
- Uma Cobranca registra zero ou muitas Transacoes, pois pode haver tentativas recusadas ou pendentes antes de uma aprovação.
- Uma Transacao pode gerar zero ou um Comprovante. O comprovante só é emitido para uma transação aprovada.
- SimuladorProcessador implementa a interface ProcessadorPagamento.
- PagamentoService depende do contrato ProcessadorPagamento, sem acoplamento a uma instituição específica.
- Os serviços utilizam interfaces de repositório para persistir os dados.

### 5.3 Encapsulamento e tipos

Os atributos são privados e as operações públicas. Valores monetários usam BigDecimal para evitar imprecisão de ponto flutuante. Identificadores usam UUID e datas/horários usam LocalDateTime. Os enumeradores StatusCobranca, StatusTransacao e FormaPagamento restringem os valores possíveis.

**Diagrama:** [Fonte PlantUML](Semana%2003%20-%20Estrutura%20e%20Diagrama%20de%20Classes/Diagrama_de_Classes.puml) | [Imagem PNG](Semana%2003%20-%20Estrutura%20e%20Diagrama%20de%20Classes/Diagrama_de_Classes.png)

![Diagrama de Classes](Semana%2003%20-%20Estrutura%20e%20Diagrama%20de%20Classes/Diagrama_de_Classes.png)  
**Explicação:** [Descrição da Estrutura](Semana%2003%20-%20Estrutura%20e%20Diagrama%20de%20Classes/Descricao%20da%20Estrutura.md)

---

## 6. Semana 04 — Comportamento e ciclo de vida

### 6.1 Sequência — Criar cobrança

1. O comerciante abre o formulário no Painel Web.
2. Informa valor e descrição e confirma.
3. O controlador valida a sessão com o serviço de autenticação.
4. O serviço de cobrança valida os dados.
5. Uma cobrança é criada e recebe identificador e link.
6. O repositório salva a cobrança.
7. Após a confirmação da persistência, o painel apresenta o link ao comerciante.

Se os dados forem inválidos ou ocorrer falha ao salvar, o sistema apresenta erro e não afirma que a cobrança foi criada.

**Diagrama:** [Fonte PlantUML — Criar Cobrança](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Sequencia_Criar_Cobranca.puml) | [Imagem PNG](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Sequencia_Criar_Cobranca.png)

![Sequência — Criar Cobrança](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Sequencia_Criar_Cobranca.png)

### 6.2 Sequência — Processar pagamento

1. O pagador abre o link e o sistema localiza a cobrança.
2. O checkout apresenta valor e descrição.
3. O pagador seleciona Pix ou cartão e confirma com dados fictícios.
4. O sistema confirma que a cobrança está Aberta e cria uma Transacao.
5. A transação é enviada ao ProcessadorPagamento, implementado pelo simulador.
6. O sistema registra o resultado retornado.
7. Se aprovado, atualiza a cobrança para Paga e solicita o comprovante.
8. Se recusado ou pendente, registra o status sem marcar a cobrança como Paga.
9. Se ocorrer falha técnica, registra a falha e informa que a tentativa não foi concluída.

**Diagrama:** [Fonte PlantUML — Processar Pagamento](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Sequencia_Processar_Pagamento.puml) | [Imagem PNG](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Sequencia_Processar_Pagamento.png)

![Sequência — Processar Pagamento](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Sequencia_Processar_Pagamento.png)

### 6.3 Estados da cobrança

| Estado atual | Evento | Próximo estado |
|---|---|---|
| Inicial | Criar cobrança | Aberta |
| Aberta | Transação aprovada | Paga |
| Aberta | Comerciante cancela | Cancelada |

Uma tentativa recusada, pendente ou com falha técnica não encerra a cobrança. O pagador pode realizar uma nova tentativa enquanto ela permanecer Aberta.

**Diagrama:** [Fonte PlantUML — Estados da Cobrança](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Estados_Cobranca.puml) | [Imagem PNG](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Estados_Cobranca.png)

![Estados da Cobrança](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Estados_Cobranca.png)

### 6.4 Estados da transação

| Estado atual | Evento ou retorno | Próximo estado |
|---|---|---|
| Inicial | Criar tentativa | Criada |
| Criada | Enviar solicitação | Em Processamento |
| Em Processamento | Retorno aprovado | Aprovada |
| Em Processamento | Retorno recusado | Recusada |
| Em Processamento | Retorno pendente | Pendente |
| Em Processamento | Simulador indisponível | Falha Técnica |
| Pendente | Confirmação de aprovação | Aprovada |
| Pendente | Confirmação de recusa | Recusada |

Uma transação Pendente ainda não tem resultado definitivo. A falha técnica encerra aquela tentativa, mas a cobrança pode continuar Aberta e receber outra tentativa.

**Diagrama:** [Fonte PlantUML — Estados da Transação](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Estados_Transacao.puml) | [Imagem PNG](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Estados_Transacao.png)

![Estados da Transação](Semana%2004%20-%20Comportamento%20e%20Estados/Diagrama_Estados_Transacao.png)  
**Explicação:** [Explicação dos Fluxos e Estados](Semana%2004%20-%20Comportamento%20e%20Estados/Explicacao%20dos%20Fluxos%20e%20Estados.md)

---

## 7. Semana 05 — Arquitetura e implantação

### 7.1 Componentes

- **Painel Web:** interface autenticada do comerciante.
- **Checkout Web:** interface acessada pelo pagador por meio do link.
- **API REST:** ponto de entrada das requisições HTTP.
- **Autenticação:** valida credenciais e sessões do comerciante.
- **Serviço de Cobranças:** cria, consulta e cancela cobranças.
- **Serviço de Pagamentos:** coordena transações e chama o processador.
- **Serviço de Comprovantes:** gera comprovantes demonstrativos.
- **ProcessadorPagamento / Simulador:** contrato e implementação simulada do processamento.
- **Adaptador de Persistência:** isola o acesso aos dados.
- **PostgreSQL:** banco proposto para armazenar comerciantes, cobranças, transações e comprovantes.

**Diagrama:** [Fonte PlantUML — Componentes](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Diagrama_de_Componentes.puml) | [Imagem PNG](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Diagrama_de_Componentes.png)

![Diagrama de Componentes](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Diagrama_de_Componentes.png)

### 7.2 Implantação

O comerciante e o pagador utilizam navegadores em dispositivos separados. As interfaces são acessadas pela Internet usando HTTPS. O frontend e os módulos da aplicação ficam no ambiente de aplicação/servidor em nuvem. O simulador é executado como módulo interno do ambiente de aplicação. O banco PostgreSQL fica representado em um servidor de dados separado.

**Diagrama:** [Fonte PlantUML — Implantação](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Diagrama_de_Implantacao.puml) | [Imagem PNG](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Diagrama_de_Implantacao.png)

![Diagrama de Implantação](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Diagrama_de_Implantacao.png)  
**Explicação:** [Visão Arquitetural](Semana%2005%20-%20Arquitetura%20e%20Consolidacao/Visao%20Arquitetural.md)

---

## 8. Regras de negócio consolidadas

1. Somente comerciante autenticado pode criar e consultar suas cobranças.
2. O valor de uma cobrança deve ser maior que zero e a descrição é obrigatória.
3. Toda cobrança recém-criada começa com status Aberta.
4. Cada tentativa de pagamento gera uma transação associada à cobrança.
5. Uma cobrança só passa para Paga quando uma transação é aprovada.
6. Uma transação recusada não altera a cobrança para Paga.
7. Uma transação pendente não equivale a uma aprovação.
8. O comprovante demonstrativo só é emitido após aprovação.
9. Somente cobranças Abertas podem ser canceladas.
10. O simulador retorna resultados fictícios e não movimenta dinheiro real.

## 9. Rastreabilidade entre requisitos e modelos

| Necessidade | História / requisito | Caso de uso | Classes / diagrama |
|---|---|---|---|
| Acesso do comerciante | HU01 / RF01 | Autenticar-se | AutenticacaoService |
| Criar cobrança | HU02 / RF02–RF03 | UC01 | Comerciante, Cobranca, CobrancaService |
| Abrir checkout e pagar | HU04 / RF04–RF06 | UC02 | PagamentoController, PagamentoService, ProcessadorPagamento |
| Registrar resultado | HU05 / RF07–RF08 | UC02 | Transacao, ResultadoProcessamento |
| Consultar histórico | HU05–HU06 / RF09 | Consultar cobranças e transações | Repositorios e CobrancaService |
| Exibir comprovante | HU07 / RF10 | Visualizar comprovante | Comprovante, ComprovanteService |
| Cancelar cobrança | HU08 / RF11 | Cancelar cobrança | Cobranca, CobrancaService |

## 10. Conclusão

A análise transforma o problema escolhido em uma proposta de sistema com escopo definido. As histórias de usuário e os requisitos identificam o que os atores precisam realizar. O diagrama de classes define as entidades, serviços e relacionamentos. Os diagramas de sequência demonstram as interações dos fluxos principais, enquanto os diagramas de estados documentam as regras de mudança de situação. Por fim, os diagramas de componentes e implantação mostram a organização lógica e a distribuição proposta da solução.

Os artefatos foram elaborados de maneira encadeada: cada diagrama comportamental utiliza os atores, casos de uso e classes definidos nas etapas anteriores. O resultado é uma base de análise para orientar uma futura implementação acadêmica do FluxPay.

## 11. Observações finais para entrega

- O nome FluxPay está provisório e pode ser alterado se o aluno preferir outro nome.
- A composição da equipe ainda precisa ser confirmada, caso existam outros integrantes.
- As imagens PNG dos diagramas já foram geradas e estão junto aos arquivos .puml, que permanecem disponíveis como fontes editáveis.
- A documentação segue as entregas indicadas no guia das Semanas 01 a 05. Confirme se o professor solicita capa institucional, formato específico ou convenção adicional de nomenclatura.
