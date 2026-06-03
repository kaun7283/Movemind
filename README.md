# 🟧 MoveMind — Diário de Treino & Foco

> **Status do Projeto:** 🚀 Concluído / Pronto para Entrega
> **Tecnologias:** Kotlin | Jetpack Compose | Material Design 3

O **MoveMind** é um aplicativo nativo Android projetado sob medida para o "lado atleta" do usuário, integrando de forma minimalista e de alta performance o monitoramento de atividades físicas (academia e corrida) ao desenvolvimento de hábitos diários e foco mental (como a escrita diária de código).

---

## 🎨 Identidade Visual (Design System)

Inspirado na estética limpa, agressiva e de alto contraste de aplicativos líderes do segmento esportivo (como *Nike Run Club*), o aplicativo opera sobre uma paleta de cores **"Energética"**:

* **Laranja Vibrante (`#FF6B00`):** Utilizado para acentos principais, botões de Call To Action (CTA), progresso ativo e destaque da marca.
* **Cinza Azulado (`#334155`):** Utilizado em containers secundários, bordas de formulários e divisores.
* **Fundo Dark (`#1E293B` / `#0F172A`):** Garante imersão de foco visual profundo, legibilidade extrema e conforto sob baixa luminosidade.
* **Branco Puro (`#FFFFFF`):** Tipografia primária e ícones de status.

---

## 📂 Estrutura Arquitetural do Repositório

O projeto segue rigorosamente a separação de responsabilidades e componentização reutilizável recomendada para o desenvolvimento moderno em Android:

```text
app/
├── MainActivity.kt              # Gerenciador de ciclo de vida e roteamento de navegação
├── ui/
│   ├── theme/
│   │   ├── Theme.kt             # Configuração de Tema Dark e mapeamento da paleta MoveMind
│   │   └── Typography.kt        # Escala de fontes (System Fonts: Black, Bold, SemiBold)
│   ├── components/
│   │   └── Components.kt        # UI reutilizável (MoveMindLogo, CustomTextField, HabitCard)
│   └── screens/
│       ├── LoginScreen.kt       # Camada de visualização da Tela de Autenticação
│       └── HomeScreen.kt        # Dashboard de consistência, hábitos e métricas
└── res/
    └── values/
        ├── themes.xml           # Configuração nativa Edge-to-Edge sem ActionBar
        └── colors.xml           # Ponte estática de recursos de cores do sistema
