def increaseInSlidingDepths(filename: str) -> int:
    with open(filename, "r") as file:
        data: list[int] = [int(line.strip("\n")) for line in file.readlines()]

    counter: int = 0
    for i in range(3, len(data)):
        if data[i] + data[i - 1] + data[i - 2] > data[i - 1] + data[i - 2] + data[i - 3]:
            counter += 1
    return counter
