from dataclasses import dataclass

import numpy as np


@dataclass
class Position:
    height: int
    basin: bool


class SmokeBasin:
    heightmap: list[list[Position]] = []
    basins: list[int] = []

    def generate_heightmap(self, file_path: str) -> None:
        with open(file_path, "r") as file:
            self.heightmap = [[Position(int(vent), False) for vent in row if vent != "\n"] for row in file.readlines()]

    def is_low_point(self, y: int, x: int) -> bool:
        if self.heightmap[y][x].basin:
            return False
        if x != 0 and self.heightmap[y][x - 1].height <= self.heightmap[y][x].height:
            return False
        if x != len(self.heightmap[0]) - 1 and self.heightmap[y][x + 1].height <= self.heightmap[y][x].height:
            return False
        if y != 0 and self.heightmap[y - 1][x].height <= self.heightmap[y][x].height:
            return False
        if y != len(self.heightmap) - 1 and self.heightmap[y + 1][x].height <= self.heightmap[y][x].height:
            return False
        return True

    def get_basins(self) -> None:
        for y in range(len(self.heightmap)):
            for x in range(len(self.heightmap[0])):
                if self.is_low_point(y, x):
                    self.heightmap[y][x].basin = True
                    basin_size = 1 + self.expand(y, x)
                    self.basins.append(basin_size)

    def expand(self, y: int, x: int) -> int:
        expanded_into = 0
        expanded_into += self.expand_right(y, x)
        expanded_into += self.expand_up(y, x)
        expanded_into += self.expand_left(y, x)
        expanded_into += self.expand_down(y, x)
        return expanded_into

    def expand_right(self, y: int, x: int) -> int:
        if x + 1 != len(self.heightmap[0]) and not self.heightmap[y][x + 1].basin and self.heightmap[y][x].height <= \
                self.heightmap[y][x + 1].height != 9:
            self.heightmap[y][x + 1].basin = True
            return self.expand(y, x + 1) + 1
        else:
            return 0

    def expand_left(self, y: int, x: int) -> int:
        if x - 1 != -1 and not self.heightmap[y][x - 1].basin and self.heightmap[y][x].height <=\
                self.heightmap[y][x - 1].height != 9:
            self.heightmap[y][x - 1].basin = True
            return self.expand(y, x - 1) + 1
        else:
            return 0

    def expand_up(self, y: int, x: int) -> int:
        if y - 1 != -1 and not self.heightmap[y - 1][x].basin and self.heightmap[y][x].height <=\
                self.heightmap[y - 1][x].height != 9:
            self.heightmap[y - 1][x].basin = True
            return self.expand(y - 1, x) + 1
        else:
            return 0

    def expand_down(self, y: int, x: int) -> int:
        if y + 1 != len(self.heightmap) and not self.heightmap[y + 1][x].basin and self.heightmap[y][x].height <= \
                self.heightmap[y + 1][x].height != 9:
            self.heightmap[y + 1][x].basin = True
            return self.expand(y + 1, x) + 1
        else:
            return 0

    def biggest_basin_product(self) -> int:
        self.basins.sort(reverse=True)
        return np.product(self.basins[:3])


if __name__ == "__main__":
    smoke_basin = SmokeBasin()
    smoke_basin.generate_heightmap("basin.txt")
    smoke_basin.get_basins()
    print(smoke_basin.biggest_basin_product())
