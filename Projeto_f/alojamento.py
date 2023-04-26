class Alojamento:
    def __init__(self, codigo: str, nome: str, proprietario: str, morada: str, tipologia: str, qnthospedes: str, preco: str, checkin: str, checkout: str, avaliacao: list, reservado: bool) -> None:
        self._codigo = codigo
        self._proprietario = proprietario
        self._morada = morada
        self._preco = preco
        self._tipologia = tipologia
        self._qnthospedes = qnthospedes
        self._nome = nome
        self._checkin = checkin
        self._checkout = checkout
        self._reservado = reservado
        self._avaliacao = avaliacao

    def get_codigo(self) -> str:
        return self._codigo

    def set_codigo(self, codigo: str) -> None:
        self._codigo = codigo

    def get_proprietario(self) -> str:
        return self._proprietario

    def set_proprietario(self, proprietario: str) -> None:
        self._proprietario = proprietario

    def get_morada(self) -> str:
        return self._morada

    def set_morada(self, morada: str) -> None:
        self._morada = morada

    def get_preco(self) -> str:
        return self._preco

    def set_preco(self, preco: str) -> None:
        self._preco = preco

    def get_tipologia(self) -> str:
        return self._tipologia

    def set_tipologia(self, tipologia: str) -> None:
        self._tipologia = tipologia

    def get_qnthospedes(self) -> str:
        return self._qnthospedes

    def set_qnthospedes(self, qnthospedes: str) -> None:
        self._qnthospedes = qnthospedes

    def get_nome(self) -> str:
        return self._nome

    def set_nome(self, nome) -> None:
        self._nome = nome

    def get_checkin(self) -> str:
        return self._checkin

    def set_checkin(self, checkin) -> None:
        self._checkin = checkin

    def get_checkout(self) -> str:
        return self._checkout

    def set_checkout(self, checkout) -> None:
        self._checkout = checkout

    def get_reservado(self) -> bool:
        return self._reservado

    def set_reservado(self, reservado) -> None:
        self._reservado = reservado
        
    def get_avaliacao(self) -> list:
        return self._avaliacao
    
    def set_avaliacao(self, avaliacao) -> None:
        self._avaliacao.append(avaliacao)

    def __str__(self) -> str:
        return str(self.get_codigo()) + ' ' + str(self.get_nome()) + ' ' + str(self.get_proprietario()) + ' ' + str(self.get_morada()) + ' ' + str(self.get_tipologia()) + ' ' + str(self.get_qnthospedes()) + ' ' + str(self.get_preco()) + ' ' + str(self.get_checkin()) + ' ' + str(self.get_checkout()) + ' ' + str(self.get_avaliacao()) + ' ' + (str(self.get_reservado()))
