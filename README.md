# 🪙 Cliente REST CoinGecko — Java + Maven + NetBeans

Aplicação desktop em **Java Swing** que consome a **API REST da CoinGecko** para listar criptomoedas, exibir detalhes e mostrar o ícone da moeda.  
Ideal para estudos de integração com APIs REST usando Java.

---

## ✅ Tecnologias
- Java 11+
- Maven
- NetBeans
- Java Swing
- HTTP Client (java.net.http)
- Jackson (JSON)

---

## ✅ Funcionalidades
- Lista as principais criptomoedas (nome, símbolo, preço, market cap).
- Exibe detalhes da moeda selecionada.
- Mostra a imagem/ícone da moeda.
- Interface gráfica simples e intuitiva.

---

## ✅ Endpoints da API CoinGecko

### 🔹 1. Listar criptomoedas  
**GET** 
```bash
https://api.coingecko.com/api/v3/coins/markets
```

Exemplo de resposta:
```json
{
  "id": "bitcoin",
  "symbol": "btc",
  "name": "Bitcoin",
  "current_price": 68000,
  "market_cap": 1200000000000,
  "image": "https://assets.coingecko.com/coins/images/1.png"
}
```
### 🔹 2. Detalhes da moeda
**GET**
```bash
https://api.coingecko.com/api/v3/coins/{id}
```

### ✅ Estrutura do Projeto
```bash
src/main/java/coingecko/
 ├── Crypto.java
 ├── CoinGeckoClient.java
 ├── CryptoViewer.java
 └── Main.java
pom.xml
```
### ✅ Como Executar no NetBeans
Clone o repositório:
```bash
git clone https://github.com/Raffaleite/coingecko-java-client
```
Abra no NetBeans:
```bash
File → Open Project
```
Selecione o arquivo Main.java, clique com o botão direito -> "Run file" ou Execute com (F6).
