# repasse-api

Quando é feito um pedido de 100 reais no iFood, não são esses 100 reais que vão para o restaurante. Esta API calcula o
valor de repasse, descontando comissão, taxa de pagamento e cupom.

Projeto feito para estudo.

**API online (Swagger):** https://repasse-api-scpm.onrender.com/swagger-ui.html
(plano grátis: a primeira requisição pode demorar uns 50 segundos)

## Regras de negócio
| Item                        | Valor                      |
|-----------------------------|----------------------------|
| Comissão iFood / própria    | 27% / 12%                  |
| Taxa Pix / crédito / débito | 0,99% / 3,2% / 2,0%        |
| Cupom                       |     Restaurante paga metade do cupom             |
| Percentuais                 | Incidem sobre o valor total do pedido |
| Arredondamento              | 2 casas decimais por componente, meio para cima    |
| Cupom > Pedido              | Pedido recusado (erro 400)                 |

## Tecnologias
- Java 21
- Spring Boot
- AssertJ
- Maven
- JUnit
- JPA
- Hibernate
- PostgreSQL
- Swagger (springdoc-openapi)
- Docker
- Render (deploy)

## Como rodar
Precisa de Java 21 e PostgreSQL instalado, com um banco chamado `repasse`.

1 - Abra o terminal

2 - Rode:
```
git clone https://github.com/GuilhermeOzaki/repasse-api
```

3 - Rode:
```
cd repasse-api
```

4 - Informe a senha do usuário postgres (PowerShell):
```
$env:DB_PASSWORD="sua_senha"
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

## Testes

Testa a conta da Calculadora de Repasse e valida as respostas do Controlador de Repasses

Para rodar:
```
./mvnw test
```