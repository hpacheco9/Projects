from Pontos.pontointeresse import Ponto
from constantes.constantes import MENU, SOBRE
from os import system
from Funções.funcoes import verifica_strings, verifica_avaliacao, verifica_floats
import time
from Sistema.sistema import Sistema
from Ticket.Ticket import Ticket


def menu(sistema: Sistema):
    while True:
        print(MENU)
        ans = str(input('Escolha a opção >> '))
        match ans:
            case '1':
                system('cls')
                _id: int = sistema.get_last_id("Pontos")
                designacao: str = verifica_strings("Designação > ")
                morada: str = verifica_strings("Morada > ")
                latitude: float = verifica_floats("Latitude > ")
                longitude: float = verifica_floats("Longitude > ")
                print("\nCategorias: ", sistema.get_categorias())
                categoria: str = verifica_strings("Categoria > ")
                if categoria not in sistema.get_categorias():
                    print("Categoria não existe.\n")
                    continue
                access: str = verifica_strings("Acesso > ")
                geo: str = verifica_strings("Geografica >  ")
                sugestao: str = verifica_strings("Sugestão > ")
                ponto: Ponto = Ponto(_id, designacao, morada, latitude, longitude, [categoria], [access], 0, [], [geo],
                                     [sugestao])
                sistema.adicionar_ponto(ponto)
                print('\nPonto de interesse adicionado!\n')
                time.sleep(1)
                system('cls')
            case '2':
                system('cls')
                sistema.listar_pontos()
                _id = verifica_id(sistema)
                sistema.alterar(_id)
            case '3':
                system('cls')
                print("Categorias disponiveis: ", sistema.get_categorias())
                categoria = verifica_strings("Categoria > ")
                sistema.pesquisar_pontos(categoria)
            case '4':
                system('cls')
                sistema.listar_pontos()
                _id = verifica_id(sistema)
                avaliacao = verifica_avaliacao()
                sistema.assinalar_avaliar_ponto(_id, avaliacao)
                print('\nPonto de interesse avaliado!\n')
                time.sleep(1)
                system('cls')
            case '5':
                system('cls')
                sistema.consultar_estatisticas()
            case '6':
                system('cls')
                latitude = verifica_floats("Latitude > ")
                longitude = verifica_floats("Longitude > ")
                sistema.obter_sugestoes(latitude, longitude)
            case '7':
                system('cls')
                sistema.gerir_rede_circulacao()
            case '8':
                system('cls')
                sistema.consultar_rede_circulacao()
            case '9':
                system('cls')
                sistema.consultar_pontos_criticos()
            case '10':
                system('cls')
                print(sistema.rede_circulacao.get_edges())
                while True:
                    quant_vias = int(input("Quantas vias deseja interromper > "))
                    if quant_vias <= len(sistema.rede_circulacao.get_edges()):
                        break
                    else:
                        print("Não existe essa quantidade de vias disponiveis")
                for _ in range(quant_vias):
                    print(sistema.rede_circulacao.get_edges())
                    print("Introduza dois pontos para interromper a via ")
                    while True:
                        from_label = sistema.verifica_vertices("Ponto Inicial > ")
                        to_label = sistema.verifica_vertices("Ponto Final > ")
                        if (from_label, to_label) not in sistema.rede_circulacao.get_edges():
                            print("Introduza um ponto de origem e destino válidos")
                        else:
                            break
                    print("Indique uma origem e um Destino")
                    origem = sistema.verifica_vertices("Origem > ")
                    destino = sistema.verifica_vertices("Destino > ")
                    sistema.interromper_via_circulacao(from_label, to_label, origem, destino)
            case '11':
                system('cls')
                sistema.listar_pontos()
                from_label = sistema.verifica_vertices("Ponto Incial > ")
                to_label = sistema.verifica_vertices("Ponto Final > ")
                sistema.obter_itinerario(from_label, to_label)
            case '12':
                system('cls')
                while True:
                    pontos_a_ver = int(input("Quantos pontos deseja observar > "))
                    if pontos_a_ver == len(sistema.rede_circulacao.get_vertices()):
                        for vertice in sistema.rede_circulacao.get_vertices():
                            sistema.consultar_rotas(vertice)
                    elif pontos_a_ver < len(sistema.rede_circulacao.get_vertices()):
                        break
                    else:
                        print("Não existe essa quantidade de pontos turisticos")
                for _ in range(pontos_a_ver):
                    origem = sistema.verifica_vertices("Origem > ")
                    sistema.consultar_rotas(origem)
            case '14':
                system('cls')
                print(SOBRE)
            case '15':
                system('cls')
                id_ticket = sistema.get_last_id("Tickets")
                nome = input(str("Nome ('' - Anónimo) > "))
                avaliacao = verifica_avaliacao()
                mensagem = verifica_strings("Mensagem > ")
                ticket = Ticket(id_ticket, avaliacao, mensagem, nome)
                sistema.criar_ticket(ticket)
            case '0':
                sistema.grava()
                break
            case _:
                system('cls')
                print("\nOpção inválida!\n")


def verifica_id(_sistema: Sistema):
    while True:
        try:
            _id = int(input("ID > "))
            if _sistema.verifica_id(_id):
                return _id
            else:
                print('\nNão existe nenhum ponto de interesse com esse ID\n')
        except ValueError:
            print('\nIntroduza um número válido.\n')
