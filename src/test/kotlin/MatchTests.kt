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
    fun realWorldNamesTest() {
        val match = Match("Djokovic", "Federer")
        match.pointWonBy("Djokovic")
        match.pointWonBy("Federer")
        assertEquals("0-0, 15-15", match.score())

        match.pointWonBy("Djokovic");
        match.pointWonBy("Djokovic");
        assertEquals("0-0, 40-15", match.score());

        match.pointWonBy("Federer");
        match.pointWonBy("Federer");
        assertEquals("0-0, Deuce", match.score());

        match.pointWonBy("Djokovic");
        assertEquals("0-0, Advantage Djokovic", match.score());

        match.pointWonBy("Djokovic");
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

    fun winGame(match: Match, playerName: String) {
        for (i in 0 until 4) {
            match.pointWonBy(playerName);
        }
    }

    fun createTieBreakMatch(leadingPlayerName: String): Match {
        val match = Match("player 1", "player 2")

        for (i in 0 until 5) {
            assertEquals("$i-$i", match.score());

            winGame(match, "player 1");
            winGame(match, "player 2");
        }

        assertEquals("5-5", match.score());

        winGame(match, leadingPlayerName);

        return match
    }

    @Test
    // Test Player 1 winning, from a leading position (no tie-break)
    fun tieBreakEvadeTest() {
        val match = createTieBreakMatch("player 1")
        assertEquals("6-5", match.score());

        winGame(match, "player 1");

        assertEquals("7-5", match.score());
    }

    @Test
    fun tieBreakTest() {

        val match = createTieBreakMatch("player 1")
        assertEquals("6-5", match.score());

        winGame(match, "player 2");
        assertEquals("6-6", match.score());

        // Game score does not undergo the conversion to points in the tie breaker
        match.pointWonBy("player 2");
        match.pointWonBy("player 2");
        assertEquals("6-6, 0-2", match.score());

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");
        assertEquals("6-6, 2-2", match.score());

        // Check for normal Deuce functionality
        match.pointWonBy("player 1");
        match.pointWonBy("player 2");
        assertEquals("6-6, Deuce", match.score());

        match.pointWonBy("player 1");
        match.pointWonBy("player 1");

        assertEquals("6-6, 5-3", match.score());

        match.pointWonBy("player 1");
        assertEquals("6-6, 6-3", match.score());

        // Test Advantage
        match.pointWonBy("player 2");
        match.pointWonBy("player 2");
        assertEquals("6-6, Advantage player 1", match.score());

        // Test tie break win
        match.pointWonBy("player 1");
        assertEquals("7-6", match.score());
    }
}