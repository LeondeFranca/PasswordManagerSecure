
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
│   ├── models/                   # Classe Credential
│   ├── security/                 # Criptografia AES, verificação de vazamentos
│   ├── ui/                       # Interfaces gráficas (Swing)
│   └── utils/                    # Email, gerador de senhas, sessão
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
