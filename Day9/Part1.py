class SmokeBasin:
    basin: list[list[int]] = []

    def generate_basin(self, file_path: str) -> None:
        with open(file_path, "r") as file:
            self.basin = [[int(vent) for vent in row if vent != "\n"] for row in file.readlines()]

    def is_low_point(self, y: int, x: int) -> bool:
        if x != 0 and self.basin[y][x - 1] <= self.basin[y][x]:
            return False
        if x != len(self.basin[0]) - 1 and self.basin[y][x + 1] <= self.basin[y][x]:
            return False
        if y != 0 and self.basin[y - 1][x] <= self.basin[y][x]:
            return False
        if y != len(self.basin) - 1 and self.basin[y + 1][x] <= self.basin[y][x]:
            return False
        return True

    def get_risk_levels(self) -> int:
        risk_levels: int = 0
        for y in range(len(self.basin)):
            for x in range(len(self.basin[0])):
                if self.is_low_point(y, x):
                    risk_levels += self.basin[y][x] + 1
        return risk_levels


if __name__ == "__main__":
    smoke_basin = SmokeBasin()
    smoke_basin.generate_basin("basin.txt")
    print(smoke_basin.get_risk_levels())
