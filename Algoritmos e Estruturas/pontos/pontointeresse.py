from Coordenada.coordenada import Coordenada
from typing import Optional


class Ponto:

    def __init__(self, id_ponto: int, desigancao: str, morada: str, latitude: float, longitude: float, categoria: list,
                 acessibilidade: Optional['list'], visitas=0, avaliacao=None, geografica=None, sugestoes=None, ):
        self._id_ponto: int = id_ponto
        self._desgignacao: str = desigancao
        self._morada: str = morada
        self._coordenada: Coordenada = Coordenada(latitude, longitude)
        self._categoria: tuple = tuple(categoria)
        self._acessibilidade = acessibilidade
        self._geografica: list = geografica
        self._sugestoes: list = sugestoes
        self._avaliacao: list = avaliacao
        self._visitas: int = visitas

    def get_id(self) -> int:
        """
        Retorna o id do ponto de interesse
        :return:
        """
        return self._id_ponto

    def get_designacao(self) -> str:
        """
        Retorna a designação do ponto de interesse
        :return:
        """
        return self._desgignacao

    def get_morada(self) -> str:
        """
        Retorna a morada do ponto de interesse
        :return:
        """
        return self._morada

    def get_acessibilidade(self) -> list | str:
        """
        Retorna os acessos do ponto de interesse
        :return:
        """
        return self._acessibilidade

    def get_categoria(self) -> tuple:
        """
        Retorna a categoria em que o Ponto se refere
        :return:
        """
        return self._categoria

    def set_acessibilidade(self, item: str) -> None:
        """
        Adiciona um acesso ao ponto de interesse
        :param item:
        :return:
        """
        self._acessibilidade.append(item)

    def set_categoria(self, nova_categoria: tuple) -> None:
        """
        Cria um novo tuplo de categorias
        :param nova_categoria:
        :return:
        """
        self._categoria = nova_categoria

    def set_morada(self, morada: str) -> None:
        """
        Cria uma nova morada do ponto de interesse
        :param morada:
        :return:
        """
        self._morada = morada

    def set_coordenada(self, latitude, longitude) -> None:
        """
        Cria uma nova coordenada do ponto de interesse
        :param latitude:
        :param longitude:
        :return:
        """
        self._coordenada = Coordenada(latitude, longitude)

    def get_coordenadas(self) -> Coordenada:
        """
        Retorna a coordenada do ponto de interesse
        :return :
        """
        return self._coordenada

    def get_visitas(self) -> int:
        """
        Retorna o numero total de visitas ao ponto de interesse
        :return:
        """
        return self._visitas

    def get_avaliacao(self) -> list:
        """
        Retorna a lista de avaliações do ponto de interesse
        :return:
        """
        return self._avaliacao

    def set_avaliacao(self, avaliacao: int) -> None:
        """
        Adiciona uma avaliação ao ponto de interesse
        :param avaliacao:
        :return:
        """
        self._avaliacao.append(avaliacao)

    def set_visitas(self) -> None:
        """
        Incrementa as visitas do ponto de interesse
        :return:
        """
        self._visitas += 1

    def get_sugestoes(self) -> list | None:
        """
        Retorna a lista de sugestões do ponto de interesse
        :return:
        """
        return self._sugestoes

    def get_geo(self) -> list | str:
        """
        Retorna a lista de geo
        :return:
        """
        return self._geografica

    def set_acess(self, acess: str) -> None:
        """
        Adiciona um acesso ao ponto de interesse
        :param acess:
        :return:
        """
        self._acessibilidade.append(acess)

    def __str__(self) -> str:
        return f'\nID: {self._id_ponto} \nDesignação: {self._desgignacao} \nCategoria: {str(self._categoria)}' \
               f' \nMorada: {self._morada}' \
               f' \nCoordenadas: {self._coordenada} \nAcessibilidade: {str(self._acessibilidade)}' \
               f' \nGeográfica: {str(self._geografica)} \nSugestões: {str(self._sugestoes)}' \
               f'\nAvaliação: {str(self._avaliacao)} \nVisitas: {self._visitas}\n'
