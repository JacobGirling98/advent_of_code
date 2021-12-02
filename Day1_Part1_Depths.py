def increaseInDepths(filename: str) -> int:
    with open(filename, "r") as file:
        data: list[int] = [int(line.strip("\n")) for line in file.readlines()]

    counter: int = 0
    for i in range(1, len(data)):
        if int(data[i]) > int(data[i - 1]):
            counter += 1
    return counter
