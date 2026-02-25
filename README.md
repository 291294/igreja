# Sistema de Gerenciamento para Igrejas Pequenas

**Release:** v1.0.0 — Plataforma SaaS inicial (Planos, Assinaturas, JWT Multi-Tenant)


## 📋 Descrição

Sistema web para gerenciar igrejas pequenas (até 200 membros) com funcionalidades de:
- Gestão de membros da igreja
- Registro de dízimos e ofertas
- Relatórios financeiros mensais

## 🏗️ Arquitetura

O projeto segue padrão profissional em camadas:

```
com.seuprojeto.igreja/
├── controller/      # Endpoints HTTP
├── service/         # Lógica de negócio
├── repository/      # Acesso ao banco de dados
├── model/           # Entidades JPA
└── config/          # Configurações (Segurança, etc)
```

## 📦 Entidades

### 1. Igreja
- `id` - Identificador único
- `nome` - Nome da igreja
- `email` - Email para login (único)
- `senha` - Senha encriptada
- `dataCadastro` - Data de cadastro

### 2. Membro
- `id` - Identificador único
- `nome` - Nome do membro
- `telefone` - Telefone
- `email` - Email
- `dataNascimento` - Data de nascimento
- `dataBatismo` - Data do batismo
- `ativo` - Status do membro (ativo/inativo)
- `igreja_id` - Relação com Igreja

### 3. Contribuição
- `id` - Identificador único
- `tipo` - DIZIMO ou OFERTA
- `valor` - Valor da contribuição
- `data` - Data da contribuição
- `observacao` - Observações
- `membro_id` - Relação com Membro (opcional)
- `igreja_id` - Relação com Igreja

## 🔗 Relacionamentos

```
Igreja (1) ──── (N) Membros
Igreja (1) ──── (N) Contribuições
Membro (1) ──── (N) Contribuições
```

## 🚀 Instalação

### Pré-requisitos
- Java 25 ou superior
- Maven 3.9.6+
- PostgreSQL 12+

### Passos

1. **Clonar o repositório**
```bash
cd c:\Users\wemer\Desktop\IGREJA
```

2. **Criar banco de dados PostgreSQL**
```sql
CREATE DATABASE igreja_db;
```

3. **Compilar o projeto**
```bash
mvn clean compile
```

4. **Construir o projeto**
```bash
mvn clean package -DskipTests
```

5. **Executar a aplicação**
```bash
mvn spring-boot:run
```

Ou:
```bash
java -jar target/igreja-1.0.0.jar
```

A aplicação roda em `http://localhost:8080/api`

## 📡 Endpoints da API

### Igrejas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/igrejas` | Criar nova igreja |
| GET | `/igrejas` | Listar todas as igrejas |
| GET | `/igrejas/{id}` | Buscar por ID |
| GET | `/igrejas/email/{email}` | Buscar por email |
| PUT | `/igrejas/{id}` | Atualizar |
| DELETE | `/igrejas/{id}` | Deletar |

### Membros

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/membros` | Criar novo membro |
| GET | `/membros/{id}` | Buscar por ID |
| GET | `/membros/igreja/{igrejaId}` | Listar por igreja |
| GET | `/membros/search?nome=X&igrejaId=Y` | Buscar por nome |
| PUT | `/membros/{id}` | Atualizar |
| DELETE | `/membros/{id}` | Deletar |
| PUT | `/membros/{id}/ativar` | Ativar membro |
| PUT | `/membros/{id}/desativar` | Desativar membro |

### Contribuições

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/contribuicoes` | Registrar contribuição |
| GET | `/contribuicoes/{id}` | Buscar por ID |
| GET | `/contribuicoes/igreja/{igrejaId}` | Listar por igreja |
| GET | `/contribuicoes/membro/{membroId}/igreja/{igrejaId}` | Listar por membro |
| GET | `/contribuicoes/periodo/{igrejaId}?dataInicio=X&dataFim=Y` | Listar por período |
| GET | `/contribuicoes/total/{igrejaId}?dataInicio=X&dataFim=Y` | Total por período |
| PUT | `/contribuicoes/{id}` | Atualizar |
| DELETE | `/contribuicoes/{id}` | Deletar |

## 📝 Exemplo de Requisições

### 1. Criar Igreja
```bash
curl -X POST http://localhost:8080/api/igrejas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Igreja Primeira Bênção",
    "email": "contato@igrejabencao.com",
    "senha": "senha123"
  }'
```

### 2. Criar Membro
```bash
curl -X POST http://localhost:8080/api/membros \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "telefone": "11999999999",
    "email": "joao@example.com",
    "dataNascimento": "1990-01-15",
    "dataBatismo": "2020-06-20",
    "ativo": true,
    "igreja": {
      "id": 1
    }
  }'
```

### 3. Registrar Dízimo
```bash
curl -X POST http://localhost:8080/api/contribuicoes \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "DIZIMO",
    "valor": 500.50,
    "data": "2026-02-23",
    "observacao": "Dízimo fevereiro",
    "membro": {
      "id": 1
    },
    "igreja": {
      "id": 1
    }
  }'
```

### 4. Relatório Financeiro Mensal
```bash
curl -X GET "http://localhost:8080/api/contribuicoes/total/1?dataInicio=2026-02-01&dataFim=2026-02-28"
```

## 🧪 Testando com Postman

1. Abra o Postman
2. Importe ou crie requisições conforme os exemplos acima
3. Configure o header `Content-Type: application/json`
4. Comece testando:
   - POST em `/igrejas` → Criar uma igreja
   - POST em `/membros` → Criar um membro
   - POST em `/contribuicoes` → Registrar uma contribuição
   - GET em `/contribuicoes/total/...` → Obter relatório

## 🔒 Segurança (Futuro)

Atualmente CSRF está desabilitado para testes. Em produção:
- Implementar validação extra e hardening de endpoints
- Reforçar CORS e proteção contra bruteforce

## 🔑 Autenticação JWT (atual)

- Endpoint público de registro: `POST /api/public/registro` — cria uma `Igreja`, assinatura FREE e usuário admin, retornando um token JWT.
- Como usar: inclua o header `Authorization: Bearer <token>` em requisições autenticadas.
- Claims úteis no token: `sub` (userId), `igrejaId`, `role` (ex.: `ADMIN`), `exp` (expiração).
- Swagger UI: `/swagger-ui.html` ou `/swagger-ui/index.html` — você pode autenticar as chamadas manualmente colocando o header `Authorization`.

Exemplo mínimo de corpo para registro (JSON):

```json
{
  "nomeIgreja": "Igreja Exemplo",
  "emailAdmin": "admin@exemplo.com",
  "senha": "senhaSegura123"
}
```

Resposta (exemplo):

```json
{
  "token": "<JWT>",
  "expiresAt": "2026-02-25T..."
}
```

Rodando localmente (opções):

```bash
mvn spring-boot:run
# ou
java -jar target/igreja-1.0.0.jar
```

## 📊 Próximas Versões

- [ ] Autenticação com JWT
- [ ] Dashboard financeiro visual
- [ ] Relatórios em PDF
- [ ] Notificações por email
- [ ] App mobile
- [ ] Backup automático

## 👨‍💻 Desenvolvedor

Projeto iniciado em 23/02/2026

## 📄 Licença

MIT
