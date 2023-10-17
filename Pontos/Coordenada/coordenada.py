class Coordenada:

    def __init__(self, latitude: float, longitude: float):
        """
        Inicializa uma nova instância da classe Coordenada com as coordenadas de latitude e longitude especificadas.
        :param latitude: Latitude do ponto de interesse.
        :param longitude: Longitude do ponto de interesse.
        """
        self._latitude: float = latitude
        self._longitutude: float = longitude

    def get_latitude(self) -> float:
        """
        Método de obtenção da latitude de um ponto de interesse.
        :return: Float: A latitude do ponto de interesse.
        """
        return self._latitude

    def get_longitude(self) -> float:
        """
        Método de obtenção da longitude de um ponto de interesse.
        :return: Float: A longitude do ponto de interesse.
        """
        return self._longitutude

    def __str__(self) -> str:
        """
        Método de obtenção str da coordenada do ponto de interesse.
        :return: Str: ‘string’ que representa a latitude e longitude do ponto de interesse.
        """
        return f'{self._latitude} {self._longitutude}'
