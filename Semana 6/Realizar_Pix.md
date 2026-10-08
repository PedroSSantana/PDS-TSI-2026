# Descrição Textual — Realizar Pix

**Nome:** Realizar Pix

**Ator principal:** Cliente

**Ator secundário:** Sistema BACEN

**Objetivo:** Permitir que o cliente realize uma transferência via Pix para uma chave válida, desde que esteja autenticado e possua saldo suficiente.

## Pré-condições

- O cliente deve estar cadastrado no IFBank;
- O cliente deve estar autenticado no aplicativo;
- O cliente deve possuir uma conta ativa;
- A conta deve possuir saldo suficiente para realizar a transferência.

## Pós-condições

- O valor do Pix é debitado da conta do cliente;
- A transação é registrada no extrato;
- Um comprovante é gerado;
- O comprovante é apresentado ao cliente.

## Fluxo Principal

1. O cliente acessa a área Pix do aplicativo.
2. O sistema verifica se o cliente está autenticado.
3. O cliente informa a chave Pix de destino.
4. O sistema envia a chave Pix ao Sistema BACEN para validação.
5. O Sistema BACEN verifica a existência da chave Pix.
6. O Sistema BACEN retorna os dados do destinatário.
7. O sistema apresenta os dados do destinatário ao cliente.
8. O cliente informa o valor da transferência.
9. O sistema verifica se o cliente possui saldo suficiente.
10. O sistema apresenta os dados da transferência para confirmação.
11. O cliente confirma a operação.
12. O sistema debita o valor da conta do cliente.
13. O sistema registra a transação no extrato.
14. O sistema gera o comprovante do Pix.
15. O sistema apresenta o comprovante ao cliente.
16. O caso de uso é encerrado.

## Fluxos de Exceção

### FE-01 — Saldo Insuficiente

1. O sistema verifica o saldo da conta.
2. O sistema identifica que o saldo é insuficiente.
3. O sistema informa ao cliente que o saldo é insuficiente.
4. A transferência não é realizada.
5. O caso de uso é encerrado.

### FE-02 — Chave Pix Inválida

1. O cliente informa a chave Pix de destino.
2. O sistema envia a chave para o Sistema BACEN.
3. O Sistema BACEN informa que a chave é inválida ou não foi encontrada.
4. O sistema informa ao cliente que a chave Pix é inválida.
5. A transferência não é realizada.
6. O sistema solicita uma nova chave Pix.

### FE-03 — Cliente Não Autenticado

1. O sistema verifica que o cliente não está autenticado.
2. O sistema solicita a autenticação.
3. O cliente realiza a autenticação.
4. O sistema permite o acesso à funcionalidade Pix.