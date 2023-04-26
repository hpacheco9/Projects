import json
import cliente


class Utilizadores:
    def __init__(self):
        self._utilizadores = {}
        with open("users.json", "r") as f:
            data = json.load(f)
            for a in data:
                ut = cliente.Cliente(data[a]['id'], data[a]['name'], data[a]['username'], data[a]['passwd'], data[a]['alj'])
                self._utilizadores.update({a: ut})

    def listar(self):
        for a in self._utilizadores:
            print(a, " ", self._utilizadores[a])

    def adicionar(self, u: cliente):
        num = u._num
        self._utilizadores.update({num: u})
        self.grava()

    def grava(self):
        with open("users.json", "r") as f:
            data = json.load(f)
            for a in self._utilizadores:
                data.update({a: {"id": self._utilizadores[a].getnum(), "name": self._utilizadores[a].getname(), "username": self._utilizadores[a].getuser(), "passwd": self._utilizadores[a].getpass(), "alj": self._utilizadores[a].getalj()}})
            with open("users.json", "w+") as file:
                json.dump(data, file, indent=2)

    def remover(self, num: str):
        if num not in self._utilizadores:
            print("Esse id não existe")
        else:
            self._utilizadores.pop(num)
            with open("users.json", "r") as f:
                data = json.load(f)
                data.pop(num)
                ok = json.dumps(data, indent=2)
                with open("users.json", "w") as file:
                    file.write(ok)

    def alterar(self, num: str, user: str, passwd: str):
        if num not in self._utilizadores:
            print("Não existe")
        else:
            self._utilizadores[num].setuser(user)
            self._utilizadores[num].setpass(passwd)
            self.grava()

    def getlastid(self) -> int:
        with open("users.json", "r") as f:
            data = json.load(f)
            for a in data:
                continue
            return a

    def reserva_alj(self, id_ut: str, alj_codigo: str):
        if alj_codigo in self._utilizadores[id_ut].alj:
            pass
        else:
            self._utilizadores[id_ut].setalj(alj_codigo)
        self.grava()

    def get_id_atual(self, codigo: str) -> list:
        return self._utilizadores[codigo].getalj()
