from _pytest.fixtures import fixture

from main import SmokeBasin, Position


@fixture
def basin():
    return SmokeBasin()


def test_expand_right(basin):
    basin.heightmap = [
        [Position(4, False), Position(5, False), Position(4, False), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, False), Position(2, False), Position(5, False), Position(8, False)],
        [Position(4, False), Position(1, True), Position(2, False), Position(4, False), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]

    actual_expanded_into = basin.expand(2, 1)

    assert basin.heightmap == [
        [Position(4, False), Position(5, False), Position(4, False), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, False), Position(2, False), Position(5, False), Position(8, False)],
        [Position(4, False), Position(1, True), Position(2, True), Position(4, True), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]
    assert actual_expanded_into == 2


def test_expand_right_up(basin):
    basin.heightmap = [
        [Position(4, False), Position(5, False), Position(8, False), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, False), Position(6, False), Position(2, False), Position(8, False)],
        [Position(4, False), Position(1, True), Position(4, False), Position(2, False), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]

    actual_expanded_into = basin.expand(2, 1)

    assert basin.heightmap == [
        [Position(4, False), Position(5, True), Position(8, True), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, True), Position(6, True), Position(2, False), Position(8, False)],
        [Position(4, False), Position(1, True), Position(4, True), Position(2, False), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]
    assert actual_expanded_into == 5


def test_expand_right_up_left(basin):
    basin.heightmap = [
        [Position(4, False), Position(5, False), Position(8, False), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, False), Position(6, False), Position(2, False), Position(8, False)],
        [Position(4, False), Position(1, True), Position(4, False), Position(2, False), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]

    actual_expanded_into = basin.expand(2, 1)

    assert basin.heightmap == [
        [Position(4, False), Position(5, True), Position(8, True), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, True), Position(6, True), Position(2, False), Position(8, False)],
        [Position(4, True), Position(1, True), Position(4, True), Position(2, False), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]
    assert actual_expanded_into == 6


def test_expand_right_up_left_down(basin):
    basin.heightmap = [
        [Position(4, False), Position(5, False), Position(8, False), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, False), Position(6, False), Position(2, False), Position(8, False)],
        [Position(4, False), Position(1, True), Position(4, False), Position(2, False), Position(9, False)],
        [Position(5, False), Position(6, False), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, False), Position(7, False), Position(3, False), Position(4, False), Position(6, False)]
    ]

    actual_expanded_into = basin.expand(2, 1)

    assert basin.heightmap == [
        [Position(4, False), Position(5, True), Position(8, True), Position(9, False), Position(8, False)],
        [Position(9, False), Position(3, True), Position(6, True), Position(2, False), Position(8, False)],
        [Position(4, True), Position(1, True), Position(4, True), Position(2, False), Position(9, False)],
        [Position(5, True), Position(6, True), Position(2, False), Position(2, False), Position(4, False)],
        [Position(8, True), Position(7, True), Position(3, False), Position(4, False), Position(6, False)]
    ]
    assert actual_expanded_into == 10