def verifica_strings(txt: str) -> str:
    """
    Pede ao utilizador para inserir um valor e só termina quando este for válido.
    :return: str
    """
    while True:
        op = str(input(txt))
        if op == '':
            print('\nIntroduza uma designacão válida.\n')
        else:
            break

    return op


def verifica_avaliacao() -> int:
    """
    Pede ao utilizador para introduzir um valor e só termina quando esta for 1, 2, 3 ou 4.
    :return:  int
    """
    while True:
        try:
            avaliacao = int(input('Avaliação [1-4] > '))
            if avaliacao > 4 or avaliacao < 1:
                print('\nIntroduza um valor entre 1 e 4.\n')
            else:
                break
        except ValueError:
            print('\nIntroduza uma avaliação válida.\n')

    return avaliacao


def verifica_floats(txt: str) -> float:
    """
    Pede ao utilizador para introduzir um valor e só termina quando este for válido.
    :return: float
    """
    while True:
        try:
            op = float(input(txt))
            break
        except ValueError:
            print('\nIntroduza uma latitude válida.\n')

    return op


def verifica_ints(txt: str) -> int:
    """
    Pede ao utilizador para introduzir um valor e só termina quando este for válido.
    :return: int
    """
    while True:
        try:
            op = int(input(txt))
            break
        except ValueError:
            print('\nIntroduza um valor válido.\n')

    return op
