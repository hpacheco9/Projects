class Queue:
    """
    Implementa uma fila (queue) usando uma lista em Python.
    """

    def __init__(self, sourcecollection=None):
        """
        Cria uma nova fila (queue) com os elementos opcionais da coleção de origem.

        Args:
            sourcecollection (iterável): Coleção de elementos para inicializar a fila. Padrão: None.
        """
        self._queue: list = []
        if sourcecollection is not None:
            for item in sourcecollection:
                self._queue.append(item)

    def add(self, item) -> None:
        """
        Adiciona um elemento à fila.

        Args:
            item: O elemento a ser adicionado à fila.
        """
        self._queue.append(item)

    def remove(self):
        """
        Remove e retorna o próximo elemento da fila.

        Returns:
            O próximo elemento da fila.
        """
        return self._queue.pop(0)

    def is_empty(self) -> bool:
        """
        Verifica se a fila está vazia.

        Returns:
            True se a fila estiver vazia, False caso contrário.
        """
        return len(self._queue) == 0

    def size(self) -> int:
        """
        Retorna o número de elementos na fila.

        Returns:
            O número de elementos na fila.
        """
        return len(self._queue)

    def __str__(self) -> str:
        """
        Retorna uma representação em string da fila.

        Returns:
            Uma string representando os elementos da fila.
        """
        return str(self._queue)

    def __iter__(self):
        """
        Cria um iterador para percorrer os elementos da fila.

        Returns:
            Um iterador para os elementos da fila.
        """
        return iter(self._queue)

    def __len__(self) -> int:
        """
        Retorna o número de elementos na fila.

        Returns:
            O número de elementos na fila.
        """
        return len(self._queue)

    def clear(self) -> None:
        """
        Remove todos os elementos da fila.
        """
        self._queue = []

    def get_max(self) -> int:
        """
        Retorna o elemento com o maior valor na fila.

        Returns:
            O elemento com o maior valor na fila.
        """

        maximo = 0
        for i in range(0, len(self._queue) - 1):
            if self._queue[i].get_id_ticket() > maximo:
                maximo = self._queue[i].get_id_ticket()
        return maximo + 1
