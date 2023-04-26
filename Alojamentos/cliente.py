class Cliente:
    def __init__(self, num:str,name: str, username: str, password: str,alj : list) -> None:
        self._num = num
        self.name = name
        self._username = username
        self._password = password
        self.alj = alj

    def getname(self) -> str:
        return self.name

    def setname(self,name) -> None:
        self.name = name

    def getuser(self) -> str:
        return self._username

    def setuser(self, user: str):
        self._username = user

    def getpass(self) -> str:
        return self._password

    def setpass(self, passwd: str) -> None:
        self._password = passwd

    def getnum(self) -> str:
        return self._num

    def getalj(self) -> list:
        return self.alj

    def setalj(self, reserva : int):
        self.alj.append(reserva)
        return None

    def __str__(self) -> str:
        return str(self.getname()) + " " + str(self.getuser()) + " " + str(self.getpass() + " " + str(self.getalj()))