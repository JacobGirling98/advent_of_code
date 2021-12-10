from _pytest.fixtures import fixture

from main import SmokeBasin


@fixture
def basin():
    return SmokeBasin()


def test_low_point_returns_true(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 1, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert basin.is_low_point(1, 1)


def test_y_0_low_point_returns_true(basin):
    basin.heightmap = [
        [2, 1, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert basin.is_low_point(0, 1)


def test_y_max_low_point_returns_true(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 1, 2, 2]
    ]

    assert basin.is_low_point(3, 1)


def test_x_0_low_point_returns_true(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [1, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert basin.is_low_point(1, 0)


def test_x_max_low_point_returns_true(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 2, 2, 1],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert basin.is_low_point(1, 3)


def test_top_left_corner_low_point(basin):
    basin.heightmap = [
        [1, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert basin.is_low_point(0, 0)


def test_top_right_corner_low_point(basin):
    basin.heightmap = [
        [2, 2, 2, 1],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert basin.is_low_point(0, 3)


def test_bottom_right_corner_low_point(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 1]
    ]

    assert basin.is_low_point(3, 3)


def test_bottom_left_corner_low_point(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [1, 2, 2, 2]
    ]

    assert basin.is_low_point(3, 0)


def test_low_point_below(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 1, 2, 2],
        [2, 2, 2, 2]
    ]

    assert not basin.is_low_point(1, 1)


def test_low_point_above(basin):
    basin.heightmap = [
        [2, 1, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert not basin.is_low_point(1, 1)


def test_low_point_left(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [1, 2, 2, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert not basin.is_low_point(1, 1)


def test_low_point_right(basin):
    basin.heightmap = [
        [2, 2, 2, 2],
        [2, 2, 1, 2],
        [2, 2, 2, 2],
        [2, 2, 2, 2]
    ]

    assert not basin.is_low_point(1, 1)


def test_risk_levels(basin):
    basin.heightmap = [
        [4, 2, 4, 2],
        [2, 3, 2, 3],
        [5, 1, 5, 4],
        [2, 2, 6, 3]
    ]

    assert basin.get_risk_levels() == 18
