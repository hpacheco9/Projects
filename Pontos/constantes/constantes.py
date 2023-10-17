MENU = """──────────── MENU ────────────

1:  Adicionar ponto de interesse
2:  Alterar ponto de interesse
3:  Pesquisar ponto de interesse
4:  Assinalar e Avaliar ponto de interesse
5:  Consultar estatisticas das visistas aos ponto de interesse
6:  Obter sugestões de visitas a pontos de interesse
7:  Gerir rede de circulação
8:  Consultar rede de circulação
9:  Consultar pontos criticos da via de circulação
10: Interromper via de circulação
11: Obter itinerário
12: Consultar rotas para percursos de carro
14: Sobre
15: Criar Ticket
0: Sair

──────────── END ────────────
"""

R = 6371

FICHEIRO_JSON = "../pontos-interesse.json"
FICHEIRO_REDE = "../ViaCirculacao/rede.json"
FICHEIRO_TICKET = "../Ticket/ticket.json"

OPCAO = "\nOp > "
ERRO = "\nIntroduza uma opção válida\n"
MENU_ALT = """\n────── Alterações ──────

1: Alterar categorias
2: Alterar acessibilidade
0: Voltar

────────── END  ────────"""

MENU_CAT = """\n────────── Categoria ──────────

1: Adicionar categoria
2: Remover categoria
0: Voltar

──────────    END    ──────────"""
MENU_ACESS = """\n────────── Acessibilidade ──────────

1: Adicionar acessibilidade
2: Remover acessibilidade
0: Voltar

──────────      END       ──────────"""


MENU_REDE = """\n────────── REDE ──────────

1: Gerir Arestas
2: Gerir Vertices
0: Voltar

──────────  END  ─────────"""

MENU_ARESTAS = """\n────────── ARESTAS ──────────

1: Adicionar Aresta
2: Consultar Aresta
3: Remover Aresta
0: Voltar

───────────  END   ──────────"""

MENU_VERTICE = """\n────────── VERTICES ──────────

1: Adicionar Vertice
2: Consultar Vertice
3: Remover Vertice
0: Voltar

──────────    END    ─────────"""

SOBRE = """\n
                    *- Geografia -*
                    
A Lagoa é uma cidade na costa sul da Ilha de São Miguel e constituída por 5 freguesias: Água de Pau, Cabouco, Nossa 
Senhora do Rosário, Ribeira Chã e Santa Cruz.
    
                    *- História -*

Começou a ser povoada logo depois da Ilha de São Miguel ter sido descoberta, tendo os seus fundadores se estabelecido na
zona onde hoje é a Igreja de Santa Cruz e junto a uma lagoa existente ali. Daí provém o seu nome. A partir do Porto dos 
Carneiros chegaram os primeiros animais domésticos à Ilha, como gado e carneiros.

              
                *- Pontos de Interesse -*

A Lagoa possui vários pontos de interesse, como por exemplo:
- Lagoa do Fogo  
- Caldeira Velha  
- Piscinas Naturais 
- Miradouros 
- Praias
- Parques
- e.t.c

                *- Links úteis -*
- https://www.visitazores.com/explorar?category=experiences&island=sao-miguel

"""
