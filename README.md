# Estante

A ideia é uma estante de livros pessoal, offline: você cadastra o que já
leu, dá uma nota, e o próprio app usa essas notas pra sugerir o próximo
livro parecido — tudo com base na sua lista, sem depender de nenhuma API
externa de recomendação.

Essa entrega é a parcial da disciplina, então só tem duas telas em Android
Views (XML puro) com navegação entre elas. O formulário de cadastro e o
algoritmo de recomendação em si ficam pra Etapa 2, já em Compose.

## As telas

**Minha Estante** é a tela inicial: lista os livros já cadastrados, cada um
com uma "lombada" colorida, autor, gênero e nota em estrelas. Tocando em
qualquer livro da lista, abre o **Detalhe do livro**, com as informações
completas e um botão pra voltar.

A navegação entre as duas é feita com Intent explícita, passando o id do
livro clicado — é assim que a tela de detalhe sabe qual livro mostrar.

## Rodando o projeto

Abre a pasta no Android Studio, deixa o Gradle sincronizar e roda o app
normal, num emulador ou num aparelho físico. Não precisa de chave de API
nem nenhuma configuração extra: os livros são todos mockados direto no
Kotlin (`MainActivity.kt`), então a tela já abre populada.

## Bibliotecas usadas

- **RecyclerView** (AndroidX), pra lista de livros da Minha Estante.
- **Material Components**, pro botão "Buscar livros parecidos" e pro FAB
  de adicionar livro.
- **ViewBinding**, pra conectar as views ao Kotlin — não uso `findViewById`
  em nenhum lugar do projeto.
- **Figtree** e **Newsreader**, duas fontes do Google Fonts, embutidas em
  `res/font/`.

## O que essa entrega cobre

Os dados exibidos vêm de um `data class Livro` imutável, tratando os
campos que nem todo livro tem preenchido (nem todo livro tem nota ou
número de páginas cadastrado, por exemplo — o app trata isso sem quebrar).

Dos itens opcionais da parcial, só o de usar ViewBinding em vez de
`findViewById` já tá feito. Componente XML reutilizável e uma tela em
Fragment ainda não entraram nessa etapa.
