import java.lang.IllegalArgumentException

class Match(private val player1Name: String, private val player2Name: String) {

    private val setScores = arrayOf(0, 0)
    private var gameScores = arrayOf(0, 0)

    private fun currentGameWonByPlayer(playerIndex: Int): Boolean {
        val otherPlayerIndex = 1 - playerIndex
        return gameScores[playerIndex] >= 4 && gameScores[playerIndex] >= gameScores[otherPlayerIndex] + 2
    }

    private fun currentGameWon(): Boolean {
        return currentGameWonByPlayer(0) || currentGameWonByPlayer(1)
    }

    fun pointWonBy(playerName: String) {
        val playerIndex = when (playerName) {
            player1Name -> 0
            player2Name -> 1
            else -> throw IllegalArgumentException("No such player '${playerName}' in this match")
        }

        gameScores[playerIndex]++

        if (currentGameWon()) {
            setScores[playerIndex]++
            gameScores = arrayOf(0, 0)
        }
    }

    private fun gameScoreToPoints(score: Int): Int {
        return when(score) {
            0 -> 0
            1 -> 15
            2 -> 30
            3 -> 40
            else -> throw IllegalArgumentException("Impossible Game Points: $score")
        }
    }

    fun gameScore(): String? {
        return if (gameScores[0] == gameScores[1]) {
            when (gameScores[0]) {
                0 -> null
                1, 2 -> "${gameScoreToPoints(gameScores[0])}-${gameScoreToPoints(gameScores[1])}"
                else -> "Deuce"
            }
        }
        else if (gameScores[0] > gameScores[1] && gameScores[0] > 3) {
            "Advantage $player1Name"
        }
        else if (gameScores[1] > gameScores[0] && gameScores[1] > 3) {
            "Advantage $player2Name"
        }
        else {
            "${gameScoreToPoints(gameScores[0])}-${gameScoreToPoints(gameScores[1])}"
        }
    }

    fun score(): String {
        var formattedGameScore = gameScore()
        formattedGameScore = formattedGameScore?.let {", $it"} ?: ""

        return "${setScores[0]}-${setScores[1]}${formattedGameScore}"
    }
}