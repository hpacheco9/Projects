class Ticket:

    def __init__(self, id_ticket: int, avaliacao: int, mensagem: str, nome: str = ""):
        self.id_ticket: int = id_ticket
        self.nome: str = nome
        self.avaliacao: int = avaliacao
        self.mensagem: str = mensagem
        if self.nome == "":
            self.nome = "Anonimo"

    def get_avaliacao(self) -> int:
        return self.avaliacao

    def get_mensagem(self) -> str:
        return self.mensagem

    def get_id_ticket(self) -> int:
        return self.id_ticket

    def get_nome(self) -> str:
        return self.nome

    def __str__(self) -> str:
        return f"Nome: {self.nome}\nAvaliação: {self.avaliacao}\nMensagem: {self.mensagem}\n"
