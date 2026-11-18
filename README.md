# Começo de Tudo

Aplicativo Android que simula o fluxo de uma solicitação de financiamento de veículo, desde o cadastro do cliente até o resultado da análise de crédito.

## Finalidade

O app conduz o usuário por uma sequência de telas:

1. **Início** (`MainActivity`) – tela de abertura.
2. **Cadastro** (`CadastroActivity`) – dados pessoais.
3. **Contato** (`ContatoActivity`) – contato e endereço (com seleção de estado).
4. **Análise** (`AnaliseActivity`) – análise de perfil.
5. **Simulado** (`SimuladoActivity`) – simulação de veículos.
6. **Financiamento** (`FinanciamentoActivity`) – condições do financiamento.
7. **Aprovação** (`AprovacaoActivity`) – avaliação do crédito.
8. **Resultado** (`FinalAprovadoActivity` / `FinalReprovadoActivity`) – crédito aprovado ou reprovado.
9. **Resumo** (`ResumoActivity`) – resumo de todos os dados informados.

Os campos de entrada usam máscaras de formatação (`MaskTextWatcher`), e as transições entre telas usam animações de deslizamento.

## Tecnologias

- Java (compatibilidade Java 8)
- Android SDK – `compileSdk` / `targetSdk` 34, `minSdk` 24
- Android Gradle Plugin 8.6.0 com Gradle Wrapper 8.7
- AndroidX: Core, AppCompat, ConstraintLayout, Activity
- Material Components for Android
- JUnit e Espresso (testes)

As versões das dependências estão centralizadas em `gradle/libs.versions.toml`.

## Requisitos

- Android Studio (versão compatível com AGP 8.6)
- JDK 17
- Android SDK Platform 34
- Emulador ou dispositivo com Android 7.0 (API 24) ou superior

## Como compilar e executar

### Pelo Android Studio

1. Abra a pasta do projeto em **File > Open**.
2. Aguarde a sincronização do Gradle. O arquivo `local.properties`, com o caminho do Android SDK, é gerado automaticamente.
3. Selecione um emulador ou dispositivo e clique em **Run**.

### Pela linha de comando

Crie o arquivo `local.properties` na raiz do projeto, caso não exista, com o caminho do SDK:

```properties
sdk.dir=C\:\\Users\\<usuario>\\AppData\\Local\\Android\\Sdk
```

Em seguida:

```bash
# Windows
gradlew.bat assembleDebug

# Linux / macOS
./gradlew assembleDebug
```

O APK de depuração é gerado em `app/build/outputs/apk/debug/`.

Para instalar em um dispositivo conectado:

```bash
gradlew.bat installDebug
```
