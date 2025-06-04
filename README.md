
# 🔐 PasswordManagerSecure

PasswordManagerSecure é um gerenciador de senhas seguro, desenvolvido em Java, com foco em **segurança, criptografia, autenticação 2FA e usabilidade** via interface gráfica com Swing. O projeto armazena as credenciais dos usuários de forma criptografada em um backend Firebase, garantindo proteção contra vazamentos e acessos não autorizados.

---

## 🛡️ Funcionalidades

- 🔑 Cadastro e login de usuários com **hash seguro de senhas (bcrypt)**
- ✅ **Autenticação em duas etapas (2FA)** via código enviado por email
- 🔒 **Criptografia AES** para armazenar senhas de forma segura
- 📬 Envio de emails via **Jakarta Mail**
- 🧠 **Verificação de senhas vazadas** usando a API HaveIBeenPwned
- 📚 Armazenamento de credenciais de serviços (nome do serviço, login, senha criptografada)
- 🧾 Listagem e exclusão de senhas salvas
- 🛠️ Gerador de senhas seguras com letras, números e símbolos
- 📦 Integração com **Firebase Admin SDK** para autenticação e armazenamento

---

## 🧰 Tecnologias Utilizadas

- **Java 17+**
- **Swing** (interface gráfica)
- **Firebase Admin SDK**
- **bcrypt (org.mindrot.jbcrypt)**
- **AES (criptografia simétrica com chave embutida)**
- **Jakarta Mail** (envio de email para 2FA)
- **HaveIBeenPwned API** (verificação de senhas vazadas)
- **GSON** (manipulação de JSON)
- **JUnit 5** (testes)
- **Apache Commons Lang** (geração de senhas aleatórias)

---

## 🧱 Estrutura do Projeto

```
PasswordManagerSecure/
├── app/src/main/java/passwordmanagersecure/
│   ├── Main.java
│   ├── auth/                      # Autenticação de usuário + 2FA
│   ├── firebase/                  # Firebase Database + Auth
│   ├── models/                    # Classe Credential
│   ├── security/                  # Criptografia AES, verificação de vazamentos
│   ├── ui/                        # Interfaces gráficas (Swing)
│   └── utils/                      # Email, gerador de senhas, sessão
├── resources/
│   └── serviceAccountKey.json (⚠️ criptografado, usado internamente)
├── build.gradle
```

---

## 🔐 Segurança

- **Chave AES criptografada** no código via `KeyManager`, protegida por uma senha mestra embutida.
- **Email e senha do remetente** também criptografados no código.
- O arquivo `serviceAccountKey.json` está **criptografado internamente** no código — não é necessário mantê-lo exposto.
- As senhas dos usuários são armazenadas com **hash bcrypt**, impossibilitando sua reversão mesmo com acesso ao banco.
- A verificação de senhas vazadas é feita diretamente com a API oficial do [HaveIBeenPwned](https://haveibeenpwned.com/API/v3#PwnedPasswords).

---

## ⚠️ Vulnerabilidades Encontradas e Considerações de Segurança

### 🔍 **Open Source Security — Dependências**

Durante a análise com **Snyk**, foram identificadas vulnerabilidades em bibliotecas de terceiros, principalmente nas dependências:

- **Protobuf (com.google.protobuf)**  
  - ⚠️ *Stack-based Buffer Overflow*  
  - 🔎 Essa vulnerabilidade persiste até a versão `3.25.4` (mais recente da linha 3.x). O problema só foi tratado na migração para a linha `4.x`, que é uma versão que possui mudanças que podem quebrar compatibilidade com outras bibliotecas, como o **Firebase Admin SDK**, que atualmente depende de Protobuf 3.x.  
  🔧 Portanto, atualizar diretamente para a linha 4 não é viável no momento sem um grande refatoração.

- **Netty (io.netty)**  
  - ⚠️ *Improper Validation of Specified Quantity in Input*  
  - ⚠️ *Denial of Service (DoS)*  
  - 🔎 Mesmo atualizando para a versão mais recente (`4.1.112.Final`), algumas vulnerabilidades são de natureza arquitetural e exigem cuidados no uso da biblioteca, como validação de entradas, controle de buffer e limites explícitos na comunicação de rede.

➡️ **Ações tomadas:**  
- As dependências foram atualizadas para as versões mais recentes que mantêm a compatibilidade com Firebase e demais bibliotecas do projeto.  
- As vulnerabilidades remanescentes são conhecidas e decorrem de limitações nas dependências transitivas que não possuem correções disponíveis no momento.

> ✔️ **Importante:** Essas vulnerabilidades não são diretamente exploráveis no contexto deste projeto, uma vez que o PasswordManagerSecure não expõe endpoints públicos na web, nem processa dados externos vindos de terceiros além do próprio Firebase e APIs seguras.

---

### 🔒 **Code Security — Pontos Sensíveis no Código**

O Snyk DeepCode também apontou alguns pontos de segurança no código, especialmente nas classes:

- **KeyManager.java** (3 problemas)  
➡️ Por lidar com gerenciamento de chaves, armazenamento da chave AES e a senha mestra embutida no código. Isso, apesar de ser criptografado, não é considerado ideal em ambientes de produção.  

- **CryptoUtil.java** (2 problemas)  
➡️ Uso direto de algoritmos de criptografia e gerenciamento manual de vetores de inicialização (IV) e chaves. Embora funcional, isso pode gerar riscos se não for implementado corretamente, especialmente em escalabilidade ou ambientes expostos.

- **PasswordLeakChecker.java** (1 problema)  
➡️ Faz chamadas externas para a API do HaveIBeenPwned. Aqui o ponto de atenção é tratar corretamente possíveis exceções e garantir que dados sensíveis não sejam logados.

➡️ **Ações tomadas:**  
- O projeto mantém a chave AES criptografada internamente para dificultar engenharia reversa.  
- O uso da chave mestra embutida é uma decisão projetada para simplicidade acadêmica, **não sendo recomendado para ambientes de produção real.**  
- Foram adicionados tratamentos de exceção e revisões de logs para garantir que dados sensíveis não sejam expostos.

---

## 🧪 Telas da Interface

- **LoginUI**: Tela de login
- **UserRegisterUI**: Cadastro de novo usuário
- **TwoFAUI**: Tela para validação do código 2FA
- **MainUI**: Tela principal com menu de navegação
- **CadastroUI**: Cadastro de novas credenciais
- **ListarSenhasUI**: Exibição das senhas salvas
- **ExcluirUI**: Exclusão de senhas
- **GerarSenhasUI**: Geração de senhas seguras
- **VerificarVazadaUI**: Verificação de senha vazada

---

## ▶️ Como Rodar o Projeto

### ✅ Requisitos

- Java 17 ou superior
- Gradle 8.x (ou usar `./gradlew`)
- Acesso à internet (para conexão com Firebase já configurado no projeto)
- Conta de email para envio 2FA (credenciais já embutidas no código)

---

### 📝 Passo a Passo para Rodar Localmente

#### 1. Clone o repositório

```bash
git clone https://github.com/LeondeFranca/PasswordManagerSecure.git
cd PasswordManagerSecure
```

#### 2. Compile o projeto

```bash
./gradlew build
```

#### 3. Execute o projeto

```bash
./gradlew run
```

> A interface gráfica será aberta automaticamente. Você poderá fazer login, cadastro, usar 2FA, gerenciar senhas, verificar senhas vazadas e tudo mais, sem precisar configurar Firebase ou email — tudo já está configurado no projeto.
>
> ⚠️ **Observação:** O envio do código de autenticação 2FA pode levar cerca de **10 segundos** para chegar no seu email. Aguarde esse tempo antes de tentar reenviar.
---

## 🧠 Notas Importantes

- O Firebase Admin SDK já está configurado para uso interno com os dados criptografados.
- Os dados criptografados não são armazenados em `.env`, tornando o projeto compatível com execução em terminal sem arquivos externos sensíveis.
- Nenhuma senha é armazenada em texto plano — todas as senhas são cifradas antes do envio ao Firebase.

---

## 👤 Autor

Desenvolvido por **Leon de França**

- GitHub: [@LeondeFranca](https://github.com/LeondeFranca)
- Projeto acadêmico com foco em segurança e criptografia aplicada

---

## 📜 Licença

Este projeto é de uso acadêmico e educacional. Consulte o autor antes de reutilizar com fins comerciais.
