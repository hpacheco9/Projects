class ViaCirculacao:
    def __init__(self, distancia: int, velocidade_min: int, velocidade_max: int):
        """
        Constrói um objeto ViaCirculacao.

        Args:
            distancia (int): A distância da via de circulação em metros.
            velocidade_min (int): A velocidade mínima permitida na via em km/h.
            velocidade_max (int): A velocidade máxima permitida na via em km/h.
        """
        self._distancia: int = distancia
        self._velocidade_min: int = velocidade_min
        self._velocidade_max: int = velocidade_max
        self._velocidade_media_circulacao: float = (velocidade_max + velocidade_min) / 2

    def get_distancia(self) -> int:
        """
        Retorna a distância da via de circulação.

        Returns:
            int: A distância da via de circulação em metros.
        """
        return self._distancia

    def get_velocidade_media_circulacao(self) -> float:
        """
        Retorna a velocidade média de circulação na via.

        Returns:
            float: A velocidade média de circulação na via em km/h.
        """
        return self._velocidade_media_circulacao

    def get_velocidade_min(self) -> int:
        """
        Retorna a velocidade mínima permitida na via.

        Returns:
            int: A velocidade mínima permitida na via em km/h.
        """
        return self._velocidade_min

    def get_velocidade_max(self) -> int:
        """
        Retorna a velocidade máxima permitida na via.

        Returns:
            int: A velocidade máxima permitida na via em km/h.
        """
        return self._velocidade_max
