from Pontos.pontointeresse import Ponto
from Estruturas.DoubleNode.doublenode import LinkedList


ponto = Ponto(1, 'Lagoa', 'Rua do Carvão', 37.99, 23.8, 'natureza', )
ponto2 = Ponto(2, 'Lagoa Do Fogo', 'Rua do Car', 37.76, 25.8, 'natureza', )

lista = LinkedList()

lista.add(ponto)
lista.add(ponto2)
lista.print_lista()

lista.pesquisa(2)

lista.altera(2, 'restauraçao', 'escadas')

lista.pesquisa(2)

print(lista.get_last_id())







