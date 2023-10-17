import networkx
from matplotlib import pyplot as p
from ViaCirculacao.ViaCirculacao import ViaCirculacao
from Estruturas.Queue.queueinterface import Queue


class Grafo:

    def __init__(self):
        """
        Inicializa a classe Grafo.
        """
        self._vertices: dict[str, list[list[str, ViaCirculacao]]] = {}

    def __iter__(self):
        """
        Iterador do grafo.
        """
        return iter(self._vertices)

    def adicionar_vertice(self, label: str) -> str:
        """
        Adiciona um vértice ao grafo.

        Args:
            label (str): O rótulo do vértice a ser adicionado.

        Returns:
            str: Uma mensagem informando se o vértice foi adicionado com sucesso ou se ele já existe.
        """
        if label not in self._vertices:
            self._vertices[label] = []
            return 'Vertice Adicionado'
        else:
            return 'O Ponto já se encontra na rede'

    def remover_vertice(self, label: str) -> str:
        """
        Remove um vértice do grafo.

        Args:
            label (str): O rótulo do vértice a ser removido.

        Returns:
            str : Uma mensagem informando se o vértice foi removido com sucesso ou se ele não pertence à rede.
        """
        if label in self._vertices:
            for vertice in self._vertices:
                self.remover_aresta(vertice, label)
            self._vertices.pop(label)
            return 'Vértice removido com sucesso'
        else:
            return 'Vértice não percente à rede!'

    def adicionar_aresta(self, from_label: str, to_label: str, via: ViaCirculacao) -> str:
        """
        Adiciona uma aresta ao grafo.

        Args:
            from_label (str): O rótulo do vértice de origem.
            to_label (str): O rótulo do vértice de destino.
            via (ViaCirculacao): O objeto ViaCirculacao que representa a aresta.

        Returns:
            str: Uma mensagem informando se a aresta foi adicionada com sucesso ou se ela já existe.
        """
        if from_label in self._vertices and to_label in self._vertices:
            if to_label not in self._vertices[from_label]:
                self._vertices[from_label].append([to_label, via])
                return 'Aresta adicionada com sucesso'
            else:
                return 'Aresta já existe'

    def remover_aresta(self, from_label, to_label) -> str:
        """
       Remove uma aresta do grafo.

       Args:
           from_label (str): O rótulo do vértice de origem.
           to_label (str): O rótulo do vértice de destino.

       Returns:
           str: Uma mensagem informando se a aresta foi removida com sucesso ou se ela é inválida.
       """
        if (from_label, to_label) in self.get_edges():
            for aresta in self._vertices[from_label]:
                if to_label in aresta:
                    self._vertices[from_label].remove(aresta)
            return 'Aresta removida com Sucesso'
        else:
            return 'Aresta inválida!'

    def adjacents(self, label: str) -> list[str]:
        """
        Retorna os vértices adjacentes ao vértice dado.

        Args:
            label (str): O rótulo do vértice.

        Returns:
            list: Uma lista com os rótulos dos vértices adjacentes.
        """
        if label in self._vertices:
            adjacentes = []
            for aresta in self._vertices[label]:
                adjacentes.append(aresta[0])

            return adjacentes

    def __str__(self) -> str:
        """
        Retorna uma representação em string do grafo.

        Returns:
            str: A representação em string do grafo.
        """
        s = ''
        for v in self._vertices:
            s += f'{v} -> {self._vertices[v]}\n'
        return s

    def get_vertices(self) -> set[str]:
        """
        Retorna um conjunto com os rótulos de todos os vértices do grafo.

        Returns:
            set[str]: O conjunto de rótulos dos vértices.
        """
        return set(self._vertices)

    def get_edges(self) -> set[tuple[str, list[str, ViaCirculacao]]]:
        """
        Retorna um conjunto com as arestas do grafo.

        Returns:
            set[tuple[str, list[str, ViaCirculacao]]]: O conjunto de arestas do grafo.
        """
        edges: set[tuple[str, list[str, ViaCirculacao]]] = set()
        for v in self._vertices:
            for adj, i in self._vertices[v]:
                if (v, adj) not in edges and (adj, v) not in edges:
                    edges.add((v, adj))

        return edges

    def get_grafo(self) -> dict[str, list[list[str, ViaCirculacao]]]:
        """
        Retorna um dicionário com os vértices e arestas do grafo.

        Returns:
            dict[str, list[list[str, ViaCirculacao]]]: O dicionário de vértices e arestas do grafo.
        """
        return self._vertices

    def draw_graph(self) -> None:
        """
        Desenha o grafo.
        """
        g = networkx.DiGraph()  # g é do tipo de Networkx
        g.add_nodes_from(self.get_vertices())
        g.add_edges_from(self.get_edges())
        pos = networkx.shell_layout(g)
        networkx.draw(g, pos=pos, with_labels=True, arrows=True)
        p.show()

    def get_distancia_vertices(self, from_label: str, to_label: str) -> float:
        """
        Retorna a distância entre dois vértices do grafo.

        Args:
            from_label (str): O rótulo do vértice de origem.
            to_label (str): O rótulo do vértice de destino.

        Returns:
            float: A distância entre os vértices.
        """
        for aresta in self._vertices[from_label]:
            if to_label == aresta[0]:
                via = aresta[1]
                return via.get_distancia()

    def get_velocidade_media(self, from_label: str, to_label: str) -> float:
        """
        Retorna a velocidade média de circulação entre dois vértices do grafo.

        Args:
            from_label (str): O rótulo do vértice de origem.
            to_label (str): O rótulo do vértice de destino.

        Returns:
            float: A velocidade média de circulação entre os vértices.
        """
        for aresta in self._vertices[from_label]:
            if to_label == aresta[0]:
                via = aresta[1]
                return via.get_velocidade_media_circulacao()

    def caminhos_possiveis(self, from_label: str, to_label: str) -> list[list[str]]:
        """
        Retorna todos os caminhos possíveis entre dois pontos da rede.

        Args:
            from_label (str): O rótulo do vértice de origem.
            to_label (str): O rótulo do vértice de destino.

        Returns:
            list: Uma lista contendo todos os caminhos possíveis entre os pontos da rede.
        """

        fila = Queue([[from_label]])
        caminhos = []
        if from_label in self._vertices and to_label in self._vertices:
            while fila:
                caminho_atual = fila.remove()
                vertice_atual = caminho_atual[-1]

                if vertice_atual == to_label:
                    caminhos.append(caminho_atual)

                for lista in self._vertices[vertice_atual]:
                    if lista[0] not in caminho_atual:
                        fila.add(caminho_atual + [lista[0]])

        return caminhos

    def calcula_caminho(self, from_label: str, to_label: str) -> dict | str:
        """
        Retorna o caminho mais curto entre dois pontos da rede.

        Args:
            from_label (str): O rótulo do vértice de origem.
            to_label (str): O rótulo do vértice de destino.

        Returns:
            dict | str: Um dicionário contendo informações sobre o caminho mais curto ou uma mensagem de erro.
        """

        caminhos_possiveis: list = self.caminhos_possiveis(from_label, to_label)
        resultado: dict = {}

        for caminho in caminhos_possiveis:

            distancia = 0
            tempo_carro = 0
            for i in range(len(caminho) - 1):
                distancia += self.get_distancia_vertices(caminho[i], caminho[i + 1])
                tempo_carro += self.get_velocidade_media(caminho[i], caminho[i + 1])

            resultado[distancia] = [caminho, tempo_carro]

        if len(resultado) <= 0:
            return 'Não há caminhos possíveis entre os pontos.'

        chaves: list = list(resultado.keys())
        for i in range(1, len(chaves)):
            chave = chaves[i]
            j = i - 1
            while j >= 0 and chave > chaves[j]:
                chaves[j + 1] = chaves[j]
                j -= 1
            chaves[j + 1] = chave

        resultado_ordenado: dict = {}
        for chave in chaves:
            resultado_ordenado[chave] = resultado[chave]

        return resultado_ordenado

    def pontos_saidas(self) -> dict[int, list[str]]:
        """
        Retorna o(s) ponto(s) que contém mais saídas.

        Returns:
            str: O(s) ponto(s) que contém mais saídas.
        """
        pontos = {}

        for vertice in self._vertices:
            if len(self._vertices[vertice]) not in pontos:
                pontos[len(self._vertices[vertice])] = [vertice]
            else:
                pontos[len(self._vertices[vertice])] += [vertice]
        return pontos

    def pontos_entradas(self) -> dict[int, list[str]]:
        """
        Retorna o(s) ponto(s) que contém mais entradas.

        Returns:
            str: O(s) ponto(s) que contém mais entradas.
        """
        pontos = {}
        count = 0
        for vertice in self._vertices:
            for aresta in self.get_edges():
                if vertice in aresta[1]:
                    count += 1
            if count not in pontos:
                pontos[count] = [vertice]
            else:
                pontos[count] += [vertice]
            count = 0
        return pontos

    def arvore(self, from_label: str) -> None:
        """
        Desenha uma árvore a partir de um vértice inicial.

        Args:
            from_label (str): O rótulo do vértice inicial.
        """

        travessia = self.travessia_largura(from_label)
        g = networkx.DiGraph()
        g.add_nodes_from(travessia)
        i = 0
        while i < len(travessia):
            adjacentes = self.adjacents(travessia[i])

            arestas = []
            for adjacente in adjacentes:
                arestas.append((travessia[i], adjacente))

            g.add_edges_from(arestas)
            i += 1

        pos = networkx.shell_layout(g)
        # draw Grafo na forma de arvore
        networkx.draw(g, pos=pos, with_labels=True, arrows=True)
        p.show()

    def travessia_largura(self, vertice_inicial: str) -> list:
        """
        Algoritmo de travessia em largura de um grafo.
        :param vertice_inicial: Vértice por onde irá ser iniciada a travessia.
        :return:
            List:
                []: Caso o vértice não pertencer ao grafo.
                [travessia]: Lista de vértices visitados.
        """
        visitados: list = []  # Lista de vértices visitados.

        # Verifica se o vértice pertence ao grafo.
        if vertice_inicial in self._vertices.keys():

            # Fila que contém o primeiro vértice.
            fila: Queue = Queue([vertice_inicial])

            # Enquanto a fila não estiver vazia.
            while fila:
                # Remove o vértice da fila.
                vertice = fila.remove()

                # Se o vértice não estiver na lista de visitados.
                if vertice not in visitados:
                    # É adicionado à lista de visitados.
                    visitados.append(vertice)

                    vizinhos = []  # Lista de vértices ligados ao vértice (vizinhos).
                    for aresta in self._vertices[vertice]:
                        vizinhos.append(aresta[0])

                    for vizinho in vizinhos:
                        # Se o vértice vizinho não estiver contido na lista de vértices visitados.
                        if vizinho not in visitados:
                            # É adicionado à fila.
                            fila.add(vizinho)

        return visitados
