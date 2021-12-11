import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DumboOctopiTest {

    val dumboOctopi = DumboOctopi()
    var wasCalled = 0;

    init {
        dumboOctopi.octopi = mutableListOf(
            mutableListOf(Octopus(5, false), Octopus(6, false), Octopus(2, false), Octopus(6, false)),
            mutableListOf(Octopus(8, false), Octopus(7, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(9, false), Octopus(1, false), Octopus(2, false), Octopus(5, false)),
            mutableListOf(Octopus(2, false), Octopus(4, false), Octopus(4, false), Octopus(3, false)),
        )
    }

    fun dummyMethod(y: Int, x: Int) {
        wasCalled++
    }

    @Test
    fun isAboveEnergyThreshold() {
        assertTrue(dumboOctopi.aboveEnergyThreshold(10))
    }

    @Test
    fun isNotAboveEnergyThreshold() {
        assertFalse(dumboOctopi.aboveEnergyThreshold(8))
    }

    @Test
    fun canIncreaseEnergy() {
        dumboOctopi.increaseEnergy(2, 2)
        assertEquals(3, dumboOctopi.octopi[2][2].energy)
    }

    @Test
    fun xIsInGrid() {
        assertTrue(dumboOctopi.xInGrid(0))
        assertTrue(dumboOctopi.xInGrid(3))
    }

    @Test
    fun xIsNotInGrid() {
        assertFalse(dumboOctopi.xInGrid(-1))
        assertFalse(dumboOctopi.xInGrid(4))
    }

    @Test
    fun yIsInGrid() {
        assertTrue(dumboOctopi.yInGrid(0))
        assertTrue(dumboOctopi.yInGrid(3))
    }

    @Test
    fun yIsNotInGrid() {
        assertFalse(dumboOctopi.yInGrid(-1))
        assertFalse(dumboOctopi.yInGrid(4))
    }

    @Test
    fun cycleRadiallyInCentreCallsMethods() {
        dumboOctopi.cycleRadially(1, 1, ::dummyMethod)
        assertEquals(8, wasCalled)
    }

    @Test
    fun cycleRadiallyAtEdge() {
        dumboOctopi.cycleRadially(0, 1, ::dummyMethod)
        assertEquals(5, wasCalled)
    }

    @Test
    fun cycleRadiallyAtCorner() {
        dumboOctopi.cycleRadially(0, 0, ::dummyMethod)
        assertEquals(3, wasCalled)
    }

    @Test
    fun energyFlashCorrectlyChangesGrid() {
        dumboOctopi.octopi = mutableListOf(
            mutableListOf(Octopus(5, false), Octopus(6, false), Octopus(2, false), Octopus(6, false)),
            mutableListOf(Octopus(8, false), Octopus(7, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(9, false), Octopus(1, false), Octopus(2, false), Octopus(5, false)),
            mutableListOf(Octopus(2, false), Octopus(4, false), Octopus(4, false), Octopus(3, false)),
        )
        val expectedGrid = mutableListOf(
            mutableListOf(Octopus(5, false), Octopus(6, false), Octopus(2, false), Octopus(6, false)),
            mutableListOf(Octopus(9, false), Octopus(8, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(0, true), Octopus(2, false), Octopus(2, false), Octopus(5, false)),
            mutableListOf(Octopus(3, false), Octopus(5, false), Octopus(4, false), Octopus(3, false))
        )

        dumboOctopi.energyFlash(2, 0)

        assertEquals(expectedGrid, dumboOctopi.octopi)
    }

    @Test
    fun chainedEnergyFlashCorrectlyChangesGrid() {
        dumboOctopi.octopi = mutableListOf(
            mutableListOf(Octopus(5, false), Octopus(8, false), Octopus(2, false), Octopus(6, false)),
            mutableListOf(Octopus(9, false), Octopus(9, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(9, false), Octopus(1, false), Octopus(2, false), Octopus(5, false)),
            mutableListOf(Octopus(2, false), Octopus(4, false), Octopus(4, false), Octopus(3, false))
        )
        val expectedGrid = mutableListOf(
            mutableListOf(Octopus(8, false), Octopus(0, true), Octopus(4, false), Octopus(6, false)),
            mutableListOf(Octopus(0, true), Octopus(0, true), Octopus(7, false), Octopus(7, false)),
            mutableListOf(Octopus(0, true), Octopus(4, false), Octopus(3, false), Octopus(5, false)),
            mutableListOf(Octopus(3, false), Octopus(5, false), Octopus(4, false), Octopus(3, false))
        )

        dumboOctopi.energyFlash(2, 0)

        assertEquals(expectedGrid, dumboOctopi.octopi)
    }

    @Test
    fun correctlySimulateOctopiForOneTurn() {
        dumboOctopi.octopi = mutableListOf(
            mutableListOf(Octopus(5, false), Octopus(8, false), Octopus(2, false), Octopus(6, false)),
            mutableListOf(Octopus(9, false), Octopus(9, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(9, false), Octopus(1, false), Octopus(2, false), Octopus(5, false)),
            mutableListOf(Octopus(2, false), Octopus(4, false), Octopus(4, false), Octopus(3, false))
        )
        val expectedGrid = mutableListOf(
            mutableListOf(Octopus(9, false), Octopus(0, true), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(0, true), Octopus(0, true), Octopus(8, false), Octopus(8, false)),
            mutableListOf(Octopus(0, true), Octopus(5, false), Octopus(4, false), Octopus(6, false)),
            mutableListOf(Octopus(4, false), Octopus(6, false), Octopus(5, false), Octopus(4, false))
        )

        dumboOctopi.simulateOctopi(1)

        assertEquals(expectedGrid, dumboOctopi.octopi)
        assertEquals(4, dumboOctopi.flashes)
    }

    @Test
    fun correctlySimulateOctopiForTwoTurns() {
        dumboOctopi.octopi = mutableListOf(
            mutableListOf(Octopus(5, false), Octopus(8, false), Octopus(2, false), Octopus(6, false)),
            mutableListOf(Octopus(9, false), Octopus(9, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(9, false), Octopus(1, false), Octopus(2, false), Octopus(5, false)),
            mutableListOf(Octopus(2, false), Octopus(4, false), Octopus(4, false), Octopus(3, false))
        )
        val expectedGrid = mutableListOf(
            mutableListOf(Octopus(0, true), Octopus(2, false), Octopus(6, false), Octopus(8, false)),
            mutableListOf(Octopus(2, false), Octopus(2, false), Octopus(9, false), Octopus(9, false)),
            mutableListOf(Octopus(1, false), Octopus(6, false), Octopus(5, false), Octopus(7, false)),
            mutableListOf(Octopus(5, false), Octopus(7, false), Octopus(6, false), Octopus(5, false))
        )

        dumboOctopi.simulateOctopi(2)

        assertEquals(expectedGrid, dumboOctopi.octopi)
        assertEquals(5, dumboOctopi.flashes)
    }
}