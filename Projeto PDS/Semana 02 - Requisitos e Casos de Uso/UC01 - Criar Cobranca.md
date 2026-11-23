# Caso de Uso UC01 — Criar Cobrança

**Projeto:** FluxPay  
**Ator principal:** Comerciante  
**Objetivo:** registrar uma nova cobrança e obter um link que possa ser compartilhado com o pagador.  
**Prioridade:** Alta

## Pré-condições

- O comerciante possui cadastro no FluxPay.
- O comerciante está autenticado.
- O serviço de criação de cobranças está disponível.

## Pós-condições

- Uma cobrança é persistida com identificador único.
- O status inicial é **Aberta**.
- Um link de checkout é associado à cobrança e apresentado ao comerciante.

## Fluxo principal

1. O comerciante acessa a opção **Nova cobrança** no painel.
2. O sistema apresenta o formulário de criação.
3. O comerciante informa o valor e a descrição.
4. O comerciante confirma a criação.
5. O sistema valida se o valor é maior que zero e se a descrição foi preenchida.
6. O sistema identifica o comerciante responsável pela sessão autenticada.
7. O sistema cria a cobrança com status **Aberta** e registra data e hora.
8. O sistema gera um identificador e um link exclusivo de checkout.
9. O sistema salva a cobrança e o link.
10. O sistema apresenta a confirmação e permite copiar o link.

## Fluxos alternativos e de exceção

**FA01 — Valor inválido**
1. No passo 5, o sistema identifica valor igual ou inferior a zero.
2. O sistema informa que o valor deve ser positivo.
3. O formulário permanece disponível para correção; nenhuma cobrança é criada.

**FA02 — Descrição não informada**
1. No passo 5, o sistema identifica a descrição vazia.
2. O sistema solicita o preenchimento da descrição.
3. Nenhuma cobrança é criada até a correção.

**FE01 — Falha ao salvar**
1. No passo 9, ocorre uma falha de persistência.
2. O sistema informa que não foi possível criar a cobrança.
3. O sistema não apresenta um link como se a operação tivesse sido concluída.

## Regras relacionadas

- RN01: somente comerciante autenticado pode criar cobranças.
- RN02: o valor deve ser maior que zero.
- RN03: a cobrança inicia como **Aberta**.
- RF03: cada cobrança possui identificador e link únicos.

## Dados principais

- Valor (decimal, obrigatório, maior que zero).
- Descrição (texto, obrigatório).
- Identificador da cobrança (gerado pelo sistema).
- Data e hora de criação (geradas pelo sistema).
- Status (inicia como Aberta).
- Comerciante proprietário (obtido da sessão).
