# Semana 05 — Visão Arquitetural e Consolidação

**Projeto:** FluxPay — Gateway de Pagamentos  
**Aluno:** Pedro Henrique de Sousa Santana

## 1. Objetivo da arquitetura

Organizar o protótipo em componentes com responsabilidades claras e mostrar onde eles serão executados. A arquitetura separa interface, regras de aplicação, integração simulada e persistência.

## 2. Componentes

- **Painel Web:** interface usada pelo comerciante para autenticar-se, criar cobranças e consultar o histórico.
- **Checkout Web:** interface pública acessada pelo pagador através do link de cobrança.
- **API REST:** recebe requisições HTTP, valida entradas e encaminha as operações aos serviços.
- **Autenticação:** valida credenciais do comerciante e controla o acesso às funcionalidades restritas.
- **Serviço de Cobranças:** cria cobranças, gera links e consulta cobranças pertencentes ao comerciante.
- **Serviço de Pagamentos:** cria transações, solicita processamento e aplica o resultado às entidades.
- **Serviço de Comprovantes:** gera comprovante demonstrativo após aprovação.
- **ProcessadorPagamento:** contrato de integração que define como solicitar o processamento.
- **Simulador de Processamento:** implementação acadêmica que devolve resultados fictícios.
- **Adaptador de Persistência:** concentra o acesso aos dados.
- **PostgreSQL:** armazena comerciantes, cobranças, transações e comprovantes.

## 3. Organização em camadas

A camada de apresentação contém o painel e o checkout. A camada de aplicação contém a API e os serviços de autenticação, cobrança, pagamento e comprovantes. A integração é representada pelo contrato ProcessadorPagamento e sua implementação simulada. A camada de dados contém o adaptador e o banco.

Essa separação reduz o acoplamento: o serviço de pagamentos depende de uma interface e não precisa conhecer detalhes de uma instituição financeira específica.

## 4. Implantação

O comerciante e o pagador utilizam navegadores em seus próprios dispositivos. As interfaces são entregues pelo ambiente de aplicação na nuvem e as chamadas são realizadas por HTTPS. A API e seus módulos são executados no servidor de aplicação. O simulador fica no mesmo ambiente, como componente interno. O PostgreSQL é representado em um servidor de dados separado.

## 5. Decisões arquiteturais

- Comunicação navegador-servidor por HTTPS.
- API REST como ponto de entrada das operações.
- Separação entre checkout público e painel autenticado.
- Persistência centralizada em PostgreSQL.
- Processamento simulado por meio de interface substituível.
- Valores monetários modelados com tipo decimal.
- Senhas armazenadas como hash.
- Dados fictícios de pagamento; nenhum cartão real ou credencial bancária é armazenado.

## 6. Limitações e evolução

A arquitetura representa um protótipo acadêmico e não um gateway pronto para produção. Uma integração real exigiria credenciais seguras, validação do provedor, idempotência, notificações autenticadas, observabilidade, controles antifraude, requisitos regulatórios e testes de segurança. Esses itens não são implementados nem simulados como se fossem recursos reais neste trabalho.

