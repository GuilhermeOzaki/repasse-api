# repasse-api

Quando é feito um pedido de 100 reais no iFood, não são esses 100 reais que vão para o restaurante. Esta API calcula o
valor de repasse, descontando comissão, taxa de pagamento e cupom.

Também tem um agente de IA (Gemini) que responde perguntas em português sobre o repasse. O agente não faz contas
sozinho: ele usa a mesma calculadora da API como ferramenta (function calling).

Projeto feito para estudo.

**API online (Swagger):** https://repasse-api-3ohs.onrender.com/swagger-ui.html
(plano grátis: a primeira requisição pode demorar uns 50 segundos)

## Regras de negócio
| Item                        | Valor                                 |
|-----------------------------|---------------------------------------|
| Comissão iFood / própria    | 27% / 12%                             |
| Taxa Pix / crédito / débito | 0,99% / 3,2% / 2,0%                   |
| Cupom                       | Restaurante paga metade do cupom      |
| Percentuais                 | Incidem sobre o valor total do pedido |
| Arredondamento              | 2 casas decimais por componente, meio para cima |
| Cupom > Pedido              | Pedido recusado (erro 400)            |

## Tecnologias
- Java 21
- Spring Boot
- Spring AI
- Gemini (gemini-3.5-flash-lite)
- AssertJ
- Maven
- JUnit
- JPA
- Hibernate
- PostgreSQL
- Swagger (springdoc-openapi)
- Docker
- Render (deploy)
- Neon (banco PostgreSQL na nuvem)

## Como rodar
Precisa de Java 21, PostgreSQL instalado com um banco chamado `repasse` e uma chave da API do Gemini
(grátis em https://aistudio.google.com).

1 - Abra o terminal

2 - Rode:
```
git clone https://github.com/GuilhermeOzaki/repasse-api
```

3 - Rode:
```
cd repasse-api
```

4 - Informe a senha do usuário postgres e a chave do Gemini (PowerShell):
```
$env:DB_PASSWORD="sua_senha"
$env:GEMINI_API_KEY="sua_chave"
```

5 - Rode:
```
./mvnw spring-boot:run
```

6 - Para ver e testar endpoints acesse no navegador:
```
http://localhost:8080/swagger-ui.html
```

## Endpoints

### POST /repasses
Calcula e salva um repasse.

Requisição:
```json
{"valorPedido": 100, "modalidadeEntrega": "ENTREGA_IFOOD", "formaPagamento": "CREDITO"}
```

Resposta:
```json
{"valorRepasse": 69.80}
```

Se o cupom for maior que o pedido, retorna erro 400:

Requisição:
```json
{"valorPedido": 10, "modalidadeEntrega": "ENTREGA_IFOOD", "formaPagamento": "CREDITO", "valorCupom": 30}
```

Resposta:
```
Valor do cupom inválido
```

### GET /repasses
Lista todos os repasses salvos.

Resposta:
```json
[
  {
    "id": 1,
    "valorPedido": 100.00,
    "valorCupom": 0.00,
    "valorRepasse": 69.80,
    "modalidadeEntrega": "ENTREGA_IFOOD",
    "formaPagamento": "CREDITO"
  }
]
```

### POST /chat
Faz uma pergunta ao agente de IA. Envia texto puro e recebe texto.

Exemplo (PowerShell):
```
Invoke-RestMethod -Uri https://repasse-api-3ohs.onrender.com/chat -Method Post -ContentType "text/plain; charset=utf-8" -Body "Quanto recebo num pedido de 100 reais, entrega do iFood, pago no pix, sem cupom?"
```

Resposta:
```
O valor líquido que você vai receber é de R$ 72,01.
```

Se faltar alguma informação (valor, entrega ou pagamento), o agente pergunta antes de calcular.

Cada pergunta é independente: o agente não lembra das mensagens anteriores.

## Como o agente funciona
1. A pergunta chega no `/chat` e vai para o Gemini junto com as instruções do agente.
2. O Gemini identifica os dados (valor, entrega, pagamento, cupom) e pede para chamar a ferramenta `calcularRepasse`.
3. O Java executa a conta na `CalculadoraRepasse` (a mesma do `POST /repasses`).
4. O Gemini recebe o resultado e escreve a resposta.

Assim a conta é sempre feita pelo código, e não pelo modelo.

## Avaliação do agente
20 perguntas escritas de jeitos diferentes, em `src/test/resources/avaliacao.csv`:
- 16 com todos os dados: a resposta precisa ter o valor certo.
- 4 com dados faltando: o agente precisa perguntar em vez de calcular.

Resultado: **19 de 20 (95%)**.

O único erro foi na pergunta "Calcule meu repasse.". O agente pediu os dados certos, mas escreveu "Por favor, me
informe:" sem ponto de interrogação, e o avaliador conta como pergunta só respostas com "?". Foi um erro do critério
de avaliação, não do agente, mas o resultado foi mantido como saiu.

Para rodar (com a aplicação rodando em `localhost:8080`), execute a classe `AvaliacaoAgente` em
`src/test/java/com/portfolio/repasse/agente`.

## Testes

Testa a conta da Calculadora de Repasse e valida as respostas do Controlador de Repasses

Para rodar:
```
./mvnw test
```