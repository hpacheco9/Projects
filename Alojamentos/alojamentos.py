import alojamento
import alojamento as al
import json
import datetime


class Alojamentos:
    def __init__(self):
        self._alojamentos = {}
        with open("alojamentos.json", "r") as f:
            data = json.load(f)
            for a in data:
                alj = alojamento.Alojamento(data[a]['codigo'], data[a]['nome'], data[a]['proprietario'],
                                            data[a]['morada'], data[a]['tipologia'],
                                            data[a]['quantidade de hospedes'], data[a]['preco'], data[a]['checkin'],
                                            data[a]['checkout'], data[a]['avaliacao'], data[a]['reservado'])
                self._alojamentos.update({a: alj})

    def acrescentar(self, a: al.Alojamento) -> None:
        self._alojamentos.update({a.get_codigo(): a})
        self.gravar()
        return None

    def remover(self, codigo: str) -> None:
        if codigo not in self._alojamentos:
            print("Esse alojamento nao existe")
        else:
            self._alojamentos.pop(codigo)
            with open("alojamentos.json", "r") as f:
                data = json.load(f)
                data.pop(codigo)
                ok = json.dumps(data, indent=2)
                with open("alojamentos.json", "w") as file:
                    file.write(ok)
            return None

    def pesquisar_codigo(self, codigo: str) -> None:
        for a in self._alojamentos:
            if self._alojamentos[a].get_codigo() == codigo:
                print(self._alojamentos[a])
        return

    def alterar(self, codigo: str, nome: str, proprietario: str, morada: str, tipologia: str, qnthospedes: str, preco: str, checkin: str, checkout: str,  avaliacao: str, reservado: bool):
        i = 0
        for a in self._alojamentos:
            if self._alojamentos[a].get_codigo() != codigo:
                i += 1
        if i == len(self._alojamentos):
            print("esse codigo nao exite")
        else:
            self._alojamentos[codigo].set_nome(nome)
            self._alojamentos[codigo].set_proprietario(proprietario)
            self._alojamentos[codigo].set_morada(morada)
            self._alojamentos[codigo].set_tipologia(tipologia)
            self._alojamentos[codigo].set_qnthospedes(qnthospedes)
            self._alojamentos[codigo].set_preco(preco)
            self._alojamentos[codigo].set_checkin(checkin)
            self._alojamentos[codigo].set_checkout(checkout)
            self._alojamentos[codigo].set_avaliacao(avaliacao)
            self._alojamentos[codigo].set_reservado(reservado)
            self.gravar()

    def listar(self) -> None:
        for a in self._alojamentos:
            print(self._alojamentos[a].get_codigo(), self._alojamentos[a].get_nome(), self._alojamentos[a].get_proprietario(), self._alojamentos[a].get_morada(), self._alojamentos[a].get_tipologia(), self._alojamentos[a].get_qnthospedes(), self._alojamentos[a].get_preco() + "€", self._alojamentos[a].get_checkin())

    def consulta_tipo(self, tipo):
        i = 0
        if (2 < len(tipo) > 2) and 't' not in tipo[0]:
            print("Tipologia inválida")
        else:
            for a in self._alojamentos:
                if self._alojamentos[a].get_tipologia().lower() == tipo.lower() and not self._alojamentos[a].get_reservado():
                    print(self._alojamentos[a].get_codigo(), self._alojamentos[a].get_nome(), self._alojamentos[a].get_proprietario(),
                          self._alojamentos[a].get_morada(),
                          self._alojamentos[a].get_tipologia(), self._alojamentos[a].get_qnthospedes(),
                          self._alojamentos[a].get_preco() + "€", self._alojamentos[a].get_checkin())
                else:
                    i += 1
            if i >= len(self._alojamentos):
                print("Não foi encontrado nenhum alojamento")

    def consulta_hospedes(self, hospede: int):
        i = 0
        for a in self._alojamentos:
            if self._alojamentos[a].get_reservado():
                pass
            else:
                if int(self._alojamentos[a].get_qnthospedes()) >= hospede:
                    print(self._alojamentos[a].get_codigo(), self._alojamentos[a].get_nome(), self._alojamentos[a].get_proprietario(),
                          self._alojamentos[a].get_morada(), self._alojamentos[a].get_tipologia(), self._alojamentos[a].get_qnthospedes(),
                          self._alojamentos[a].get_preco() + "€", self._alojamentos[a].get_checkin())
                else:
                    i += 1
        if i >= len(self._alojamentos):
            print("Não Existe alojamentos com essa quantidade de hospedes")

    def consulta_preco(self, preco_min: int, preco_max: int) -> None:
        i = 0
        for a in self._alojamentos:
            if self._alojamentos[a].get_reservado():
                pass
            else:
                preco = int(self._alojamentos[a].get_preco())
                if preco_min <= preco <= preco_max:
                    print(self._alojamentos[a].get_codigo(), self._alojamentos[a].get_nome(), self._alojamentos[a].get_proprietario(), self._alojamentos[a].get_morada(), self._alojamentos[a].get_tipologia(), self._alojamentos[a].get_qnthospedes(), self._alojamentos[a].get_preco() + "€", self._alojamentos[a].get_checkin())
                else:
                    i += 1
        if i >= len(self._alojamentos):
            print("Não existe alojamentos dentro desses preços")

    @staticmethod
    def lastid() -> str:
        with open("alojamentos.json", "r") as f:
            data = json.load(f)
            for a in data:
                continue
            return a

    def gravar(self):
        with open("alojamentos.json", "r") as f:
            data = json.load(f)
            for a in self._alojamentos:
                data.update({a: {"codigo": str(self._alojamentos[a].get_codigo()), "nome": self._alojamentos[a].get_nome(), "proprietario": self._alojamentos[a].get_proprietario(), "morada": self._alojamentos[a].get_morada(), "tipologia": self._alojamentos[a].get_tipologia(),
                                 "quantidade de hospedes": self._alojamentos[a].get_qnthospedes(), "preco": self._alojamentos[a].get_preco(), "checkin": self._alojamentos[a].get_checkin(),
                                 "checkout": self._alojamentos[a].get_checkout(), "avaliacao": self._alojamentos[a].get_avaliacao(), "reservado": self._alojamentos[a].get_reservado()}})
            with open("alojamentos.json", "w") as file:
                json.dump(data, file, indent=2)

    def gerar_relatorio(self):
        maximo = 0
        minimo = 0
        with open("rel.json", "r") as f:
            data = json.load(f)
            for a in self._alojamentos:
                for i in self._alojamentos[a].get_avaliacao():
                    if i > maximo:
                        maximo = i
                        minimo = maximo
                    if minimo > i:
                        minimo = i
                data.update({a: {"codigo": str(self._alojamentos[a].get_codigo()), "nome": self._alojamentos[a].get_nome(), "proprietario": self._alojamentos[a].get_proprietario(), "morada": self._alojamentos[a].get_morada(), "tipologia": self._alojamentos[a].get_tipologia(), "quantidade de hospedes": self._alojamentos[a].get_qnthospedes(), "preco": self._alojamentos[a].get_preco(), "Maior avaliacao": maximo, "Menor Avaliacao": minimo, "reservado": self._alojamentos[a].get_reservado()}})
                maximo = 0
                minimo = 0
            with open("rel.json", "w") as file:
                json.dump(data, file, indent=2)

    def checkdate(self, checkin) -> None:
        for a in self._alojamentos:
            if self._alojamentos[a].get_reservado():
                pass
            else:
                alj_checkin = datetime.datetime(int(self._alojamentos[a].get_checkin()[0:4]), int(self._alojamentos[a].get_checkin()[6:7]), int(self._alojamentos[a].get_checkin()[8:10]))
                date = datetime.datetime(int(checkin[0:4]), int(checkin[6:7]), int(checkin[8:10]))
                if date > alj_checkin:
                    pass
                else:
                    print(self._alojamentos[a].get_codigo(), self._alojamentos[a].get_nome(), self._alojamentos[a].get_tipologia(), self._alojamentos[a].get_qnthospedes(), self._alojamentos[a].get_preco() + "€")

    def reserva(self) -> str:
        nao_reservados = []
        for a in self._alojamentos:
            if self._alojamentos[a].get_reservado():
                pass
            else:
                nao_reservados.append(self._alojamentos[a].get_codigo())
        for alj in nao_reservados:
            print(self._alojamentos[alj].get_codigo(), self._alojamentos[alj].get_nome(), self._alojamentos[alj].get_morada(), self._alojamentos[alj].get_tipologia(), self._alojamentos[alj].get_qnthospedes(), self._alojamentos[alj].get_preco() + "€")
        num = str(input("id do alojamento"))
        if num not in nao_reservados:
            print("Este alojamento já se encontra reservado")
        else:
            checkin = str(input("Data de checkin yyyy-mm-dd: "))
            dias = int(input("Dias a ficar"))
            alj_checkin = datetime.datetime(int(self._alojamentos[num].get_checkin()[0:4]), int(self._alojamentos[num].get_checkin()[6:7]), int(self._alojamentos[num].get_checkin()[8:10]))
            date = datetime.datetime(int(checkin[0:4]), int(checkin[6:7]), int(checkin[8:10]))
            data = date + datetime.timedelta(days=dias)
            if alj_checkin > date:
                print("Desculpe mas este alojamento só esta disponivel apartir de :", alj_checkin)
                return "0"
            else:
                self._alojamentos[num].set_checkout(str(data))
                self._alojamentos[num].set_checkin(str(data + datetime.timedelta(days=1)))
                self._alojamentos[num].set_reservado(True)
                self.gravar()
                print("Alojamento reservado com sucesso")
                return self._alojamentos[num].get_codigo()

    def avalia(self, ids: list):
        for a in ids:
            if a not in self._alojamentos:
                pass
            else:
                print(self._alojamentos[a].get_codigo(), self._alojamentos[a].get_nome(), self._alojamentos[a].get_morada(), self._alojamentos[a].get_tipologia(), self._alojamentos[a].get_qnthospedes(), self._alojamentos[a].get_preco() + "€")

        op = str(input("Qual alojamento deseja avaliar: "))
        if op not in ids:
            print("Desculpe mas nao reservou esse alojamento")
        else:
            print("1 - Muito Insatisfeito\n"
                  "2 - Insatisfeito\n"
                  "3 - Indiferente\n"
                  "4 - Satisfeito\n"
                  "5 - Muito Sasisfeito")
            op2 = int(input("Digite sua opção: "))
            self._alojamentos[op].set_avaliacao(op2)
            self.gravar()
            print("Muito Obrigado pela sua Avaliação")
