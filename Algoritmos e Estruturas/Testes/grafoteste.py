from Estruturas.Grafos.grafo import Grafo
from ViaCirculacao.ViaCirculacao import ViaCirculacao


def grafo_1(g: Grafo) -> None:
    print(g.calcula_caminho("Alberto", "F"))

    g.draw_graph()


def create_graph() -> Grafo:
    g = Grafo()
    vertices = ['Alberto', 'B', 'C', 'D', 'E', 'F']
    for vertice in vertices:
        g.adicionar_vertice(vertice)
    via1 = ViaCirculacao(6, 20, 30)
    via2 = ViaCirculacao(4, 20, 30)
    via3 = ViaCirculacao(123, 20, 30)
    via4 = ViaCirculacao(8, 20, 30)
    via5 = ViaCirculacao(10, 20, 30)
    via6 = ViaCirculacao(9, 22, 30)
    via7 = ViaCirculacao(56, 20, 30)
    via8 = ViaCirculacao(7, 21, 30)
    via9 = ViaCirculacao(34, 23, 30)

    arestas = [("Alberto", "B", via1), ("Alberto", "C", via2), ("Alberto", "E", via3), ("B", "C", via4),
               ("B", "D", via5), ("C", "E", via6), ("C", "D", via7), ("D", "F", via8), ("E", "F", via9)]
    for a in arestas:
        g.adicionar_aresta(a[0], a[1], a[2])
    return g


grafo_1(create_graph())
