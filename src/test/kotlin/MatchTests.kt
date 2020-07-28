import org.junit.Test
import kotlin.test.assertEquals

class MatchTests {
    @Test
    fun exampleResultsTest() {
        val match = Match("player 1", "player 2")
        match.pointWonBy("player 1")
        match.pointWonBy("player 2")
        assertEquals("0-0, 15-15", match.score())

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");
        assertEquals("0-0, 40-15", match.score());

        match.pointWonBy("player 2");
        match.pointWonBy("player 2");
        assertEquals("0-0, Deuce", match.score());

        match.pointWonBy("player 1");
        assertEquals("0-0, Advantage player 1", match.score());

        match.pointWonBy("player 1");
        assertEquals("1-0", match.score());
    }

    @Test
    fun veryHighScoreTest() {
        val match = Match("player 1", "player 2")

        for (i in 1..1000) {
            match.pointWonBy("player 1")
            match.pointWonBy("player 2")
        }

        assertEquals("0-0, Deuce", match.score());

        match.pointWonBy("player 1")
        assertEquals("0-0, Advantage player 1", match.score());

        match.pointWonBy("player 2")
        assertEquals("0-0, Deuce", match.score());

        match.pointWonBy("player 2")
        assertEquals("0-0, Advantage player 2", match.score());

        match.pointWonBy("player 2")
        assertEquals("0-1", match.score());
    }

    @Test
    fun gameWinTest() {
        val match = Match("player 1", "player 2")
        match.pointWonBy("player 1")
        assertEquals("0-0, 15-0", match.score())

        match.pointWonBy("player 1");
        assertEquals("0-0, 30-0", match.score());

        match.pointWonBy("player 1");
        assertEquals("0-0, 40-0", match.score());

        match.pointWonBy("player 1");
        assertEquals("1-0", match.score());
    }

    @Test
    fun multipleWinTest() {
        val match = Match("player 1", "player 2")

        //Win 5 matches for Player 2
        for (game in 0 until 5) {
            for (i in 0 until 4) {
                match.pointWonBy("player 2");
            }
        }

        assertEquals("0-5", match.score());
    }
}