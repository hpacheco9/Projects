import alojamentos
import alojamento
import Clientes
import cliente

alj = alojamentos.Alojamentos()
ut = Clientes.Utilizadores()
idd = ""


def line():
    print("=" * 55)


def avalia():
    global idd
    ids = ut.get_id_atual(idd)
    alj.avalia(ids)


def menu_cibernauta():
    while True:
        print("1: Listar alojamentos\n2: Consultar alojamento\n0: sair")
        op = str(input("Sua opção: "))
        match op:
            case '1':
                alj.listar()
            case '2':
                print("1: Tipologia\n2: Quantidade de hospedes\n3: Preço\n4: Data Check-in\n0 : Sair")
                op2 = str(input("Sua opção : "))
                match op2:
                    case '1':
                        tipo = str(input("Tipologia: "))
                        alj.consulta_tipo(tipo)
                        pass
                    case '2':
                        hosp = int(input("Quantidade de hospedes desejada: "))
                        alj.consulta_hospedes(hosp)
                    case '3':
                        preco_min = int(input("Preço minimo que deseja: "))
                        preco_max = int(input("Preço maximo que deseja: "))
                        alj.consulta_preco(preco_min, preco_max)
                    case '4':
                        checkin = str(input("Data de checkin yyyy-mm-dd:   "))
                        alj.checkdate(checkin)
                    case '0':
                        pass
                    case default:
                        print("Opção inválida")
            case '0':
                break
            case default:
                print("Opção inválida")


def menu_utilizador():
    while True:
        line()
        print("MENU Utilizador".center(55))
        line()
        print("1 - Listar Alojamentos locais\n"
              "2 - Consultar um Alojamento local\n"
              "3 - Alugar um Alojamento local\n"
              "4 - Classificar a experiência num alojamento local\n"
              "5 - Sair")
        line()
        option = str(input("Sua Opção: "))
        match option:
            case '1':
                alj.listar()
            case '2':
                print("1: Tipologia\n2: Quantidade de hospedes\n3: Preço\n4: Data Check-in\n0 : Sair")
                op2 = str(input("Sua opção : "))
                match op2:
                    case '1':
                        tipo = str(input("Tipologia: "))
                        alj.consulta_tipo(tipo)
                        pass
                    case '2':
                        hosp = int(input("Quantidade de hospedes desejada: "))
                        alj.consulta_hospedes(hosp)
                    case '3':
                        preco_min = int(input("Preço minimo que deseja: "))
                        preco_max = int(input("Preço maximo que deseja: "))
                        alj.consulta_preco(preco_min, preco_max)
                    case '4':
                        checkin = str(input("Data de checkin yyyy-mm-dd:   "))
                        alj.checkdate(checkin)
                    case '0':
                        pass
                    case default:
                        print("Opção inválida")
            case '3':
                res = alj.reserva()
                ut.reserva_alj(idd, res)

            case '4':
                avalia()
            case '5':
                break
            case default:
                pass


def verifica(user: str, pw: str) -> bool:
    global idd
    i = 0
    for u in ut._utilizadores:
        if (ut._utilizadores[u].getuser() != user) or (ut._utilizadores[u].getpass() != pw):
            i += 1
        else:
            if i == len(ut._utilizadores):
                return False
            else:
                print(f'Bem vindo {user}')
                idd = ut._utilizadores[u].getnum()
                return True


def menu_administrador():
    while True:
        line()
        print("MENU Administrador".center(55))
        line()
        print("1 - Gerir Alojamentos locais\n"
              "2 - Gerir Clientes\n"
              "3 - Listar Alojamentos locais\n"
              "4 - Consultar um Alojamento local\n"
              "5 - Alugar um Alojamento local\n"
              "6 - Classificar a experiência num alojamento local\n"
              "7 - Gerar relatórios\n"
              "8 - Sair")
        line()
        option = str(input("Sua Opção: "))
        match option:
            case '1':
                line()
                line()
                print("1 - Consultar\n"
                      "2 - Adicionar\n"
                      "3 - Alterar\n"
                      "4 - Remover")
                line()
                op = str(input("Sua Opção: "))
                match op:
                    case '1':
                        alj.listar()
                    case '2':
                        _id = int(alj.lastid()) + 1
                        nome = str(input("Nome:"))
                        prop = str(input("Proprietario"))
                        morada = str(input("Morada"))
                        tipologia = str(input("Tipologia: "))
                        qtthosp = str(input("Hospedes: "))
                        preco = str(input("Preço: "))
                        al = alojamento.Alojamento(str(_id), nome, prop, morada, tipologia, qtthosp, preco, "2023-01-01", "2023-12-29", [], False)
                        alj.acrescentar(al)
                    case '3':
                        alj.listar()
                        num = str(input("Qual alojamento deseja alterar?"))
                        nome = str(input("Nome:"))
                        prop = str(input("Proprietario"))
                        morada = str(input("Morada"))
                        tipologia = str(input("Tipologia: "))
                        qtthosp = str(input("Hospedes: "))
                        preco = str(input("Preço: "))
                        alj.alterar(num, nome, prop, morada, tipologia, qtthosp, preco, "2023-01-01", "2023-12-29", "0", False)
                    case '4':
                        alj.listar()
                        num = str(input("Qual alojamento deseja remover?"))
                        alj.remover(num)
            case '2':
                line()
                print("Gerir Clientes".center(55))
                line()
                print("1 - Consultar\n"
                      "2- Adicionar\n"
                      "3 - Alterar\n"
                      "4 - Remover")
                line()
                op = str(input("Sua Opção: "))
                match op:
                    case '1':
                        ut.listar()
                    case '2':
                        _id = int(ut.getlastid()) + 1
                        name = str(input("Nome:"))
                        user = str(input("Username"))
                        pw = str(input("Password: "))
                        u = cliente.Cliente(str(_id), name, user, pw, [])
                        ut.adicionar(u)
                    case '3':
                        ut.listar()
                        _id = str(input("Qual id deseja altera?"))
                        user = str(input("Username"))
                        pw = str(input("Password: "))
                        ut.alterar(_id, user, pw)
                    case '4':
                        ut.listar()
                        _id = str(input("Qual id deseja remover?"))
                        ut.remover(_id)
            case '3':
                alj.listar()
            case '4':
                pass
            case '5':
                pass
            case '6':
                pass
            case '7':
                alj.gerar_relatorio()
            case '8':
                break
            case default:
                pass


def criar_ut():
    _id = int(ut.getlastid()) + 1
    name = str(input("Nome: "))
    username = str(input("Username: "))
    pw = str(input("Password: "))
    u = cliente.Cliente(str(_id), name, username, pw, [])
    ut.adicionar(u)


def menu():
    while True:
        line()
        print("Bem-Vindo".center(55))
        line()
        print("1 - Login as Administrador\n"
              "2 - Login as Utilizador\n"
              "3 - Cibernauta\n"
              "0 - Sair")
        line()
        option = str(input("Sua Opção: "))
        line()
        match option:
            case '1':
                print("Administrador".center(55))
                line()
                print("1 - Login\n0 - Voltar\n")
                op = str(input("Sua opção"))
                match op:
                    case '1':
                        password = str(input("Digite sua Password: "))
                        match password:
                            case 'adm':
                                menu_administrador()
                                break
                            case _:
                                print("Invalid Password")
                        pass
                    case '0':
                        pass
            case '2':
                print("Utilizador".center(55))
                line()
                registrado = str(input("Já se encontra regitrado no Sistema? [yes/no]: ").lower())
                match registrado:
                    case "yes":
                        user = str(input("Username: "))
                        pw = str(input("Password:  "))
                        if verifica(user, pw):
                            menu_utilizador()
                        else:
                            print("utilizador não encontrado")
                        pass
                    case "no":
                        criar_ut()
                    case _:
                        print("Invalid")
            case '3':
                menu_cibernauta()
            case '0':
                return True
