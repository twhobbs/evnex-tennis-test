fun main(args: Array<String>) {
    val match = Match("Player 1", "Player 2")
    match.pointWonBy("Player 1")
    match.pointWonBy("Player 2")
    println(match.score());

    match.pointWonBy("Player 1");
    match.pointWonBy("Player 1");
    // this will return "0-0, 40-15"
    println(match.score());

    match.pointWonBy("Player 2");
    match.pointWonBy("Player 2");
    // this will return "0-0, Deuce"
    println(match.score());

    match.pointWonBy("Player 1");
    // this will return "0-0, Advantage player 1"
    println(match.score());

    match.pointWonBy("Player 1");
    // this will return "1-0"
    println(match.score());
}