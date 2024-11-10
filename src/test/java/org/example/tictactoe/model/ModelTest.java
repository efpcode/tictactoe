package org.example.tictactoe.model;

import javafx.application.Platform;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;


import static org.example.tictactoe.model.GameState.*;

class ModelTest {
    private static FakeRandom fakeRandom = new FakeRandom();
    private Model model = new Model(fakeRandom);

    @BeforeAll
    static void runJavaFX() {
        Platform.startup(() -> {
        });
    }

    @Nested
    class ticTocToeStartUpFace {
        @Test
        void TestThatInitialGameStateIsPause() {
            var getCurrentState = model.getGameState();
            Assertions.assertEquals(PAUSE, getCurrentState);
        }

        @DisplayName("AssureThatGameTileIsEmptyBeforeClickingStartButton")
        @Test
        public void assureThatGameTileIsEmptyBeforeClickingStartButton() {
            var tiles = model.gameResults.getPlayers();
            Assertions.assertTrue(tiles.isEmpty());
        }

        @DisplayName("Pressing PlayerVSPlayer Alters GameType To PvsP")
        @Test
        public void pressingPlayerVSPlayerAltersGameTypeToPvsP() {
            var expectedGametype = PvsP;
            var gameModeChangeInstance = GameModePvP.createGameModePvP(model);
            model.setGameMode(gameModeChangeInstance);
            Assertions.assertEquals(expectedGametype, model.getGameMode().getGameType());
        }

        @DisplayName("Pressing Player VS CPU Alters GameType To PvsCPU")
        @Test
        public void pressingPlayerVSCPUAltersGameTypeToPvsCPU() {
            var expectedGametype = PvsCPU;
            var gameModeChangeInstance = GameModePvsCPU.createGameModePvsCPU(model);
            model.setGameMode(gameModeChangeInstance);
            Assertions.assertEquals(expectedGametype, model.getGameMode().getGameType());
        }

        @DisplayName("Pressing Start Button Alters GameState Pause To Play")
        @Test
        public void pressingStartButtonAltersGameStatePauseToPlay() {
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();
            Assertions.assertEquals(PLAY, model.getGameState());
        }

        @DisplayName("Pressing Start Button PreSelects Circle As Current Player")
        @Test
        public void pressingStartButtonPreSelectsCircleAsCurrentPlayer() {
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            var expected = PlayerToken.CIRCLE;
            model.startGame();
            var getCurrentPlay = model.getCurrentPlayer();
            Assertions.assertEquals(expected, getCurrentPlay);
        }

        @DisplayName("Pressing Start Button Creates EmptyTiles")
        @Test
        public void pressingStartButtonCreatesEmptyTiles() {
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();
            Assertions.assertEquals(9, model.getGameResults().getPlayers().stream()
                    .map(Player::token)
                    .filter(p -> p.equals(PlayerToken.EMPTY))
                    .count());



        }
    }

    @Nested
    class ticTacToeInGameMechanics {
        @BeforeEach
        public void tictactoeInitializer() {
            var gameModeSelected = GameModePvsCPU.createGameModePvsCPU(model);
            model.setGameMode(gameModeSelected);
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();


        }

        @DisplayName("First Tile Put At Center Changes Token Value From Empty to Circle")
        @Test
        public void firstTilePutAtCenterChangesTokenValueFromEmptyToCircle() {
            model.playerMove(new SquaredMatrixCoordinates(1, 1));
            var gameTileChanged = model.getGameResults().getPlayers().get(4);
            Assertions.assertEquals(PlayerToken.CIRCLE, gameTileChanged.token());
        }

        @DisplayName("Tile Occupied By Either Circle Or Cross Token Is Not Playable")
        @Test
        public void tileOccupiedByEitherCircleOrCrossTokenIsNotPlayable() {
            model.playerMove(new SquaredMatrixCoordinates(1, 1));
            var gameTileChanged = model.getGameResults().getPlayers().get(4);
            model.playerMove(new SquaredMatrixCoordinates(1, 1));
            Assertions.assertEquals(PlayerToken.CIRCLE, gameTileChanged.token());

        }

        @DisplayName("Player Switch From Circle to Cross After Move")
        @Test
        public void playerSwitchFromCircleToCrossAfterMove() {
            var circlePlayer = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(1, 1));
            var crossPlayer = model.getCurrentPlayer();

            Assertions.assertEquals(PlayerToken.CIRCLE, circlePlayer);
            Assertions.assertEquals(PlayerToken.CROSS, crossPlayer);
        }

        @DisplayName("Row Out Of Bound Error")
        @Test
        public void rowOutOfBoundError() {
            Exception exception = Assertions.assertThrows(
                    IllegalArgumentException.class, () -> {
                        model.playerMove(new SquaredMatrixCoordinates(3, 1));
                    });
            String expectedMessage = "Row and column must be greater than zero and not greater than matrix length";
            String actualMessage = exception.getMessage();
            Assertions.assertTrue(actualMessage.contains(expectedMessage));
        }

        @DisplayName("Column Out Of Bound Error")
        @Test
        public void columnOutOfBoundError() {
            Exception exception = Assertions.assertThrows(
                    IllegalArgumentException.class, () -> {
                        model.playerMove(new SquaredMatrixCoordinates(1, 3));
                    });
            String expectedMessage = "Row and column must be greater than zero and not greater than matrix length";
            String actualMessage = exception.getMessage();
            Assertions.assertTrue(actualMessage.contains(expectedMessage));
        }


    }

    @Nested
    class ticTacToeGameOutcomes {
        @BeforeEach
        public void setupTicTacToeGameOutcomes() {
            var gameModeSelected = GameModePvsCPU.createGameModePvsCPU(model);
            model.setGameMode(gameModeSelected);
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();

        }

        @DisplayName("Initial Empty Values Differ From Draw Condition")
        @Test
        public void initialEmptyValuesDifferFromDrawCondition() {
            boolean expected = false;
            Assertions.assertEquals(expected, model.detectDraw());
        }

        @DisplayName("Board Contains A Single Empty Value Draw Impossible")
        @Test
        public void boardContainsASingleEmptyValueDrawImpossible() {
            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CROSS));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CROSS));

            boolean expected = false;
            Assertions.assertEquals(expected, model.detectDraw());
        }

        @DisplayName("All Tiles Occupied With Either Circle or Cross Draw Possible")
        @Test
        public void allTilesOccupiedWithEitherCircleOrCrossDrawPossible() {
            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CROSS));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CROSS));

            boolean expected = true;
            boolean isWin = false;
            Assertions.assertEquals(expected, model.detectDraw());
            Assertions.assertEquals(isWin, model.detectWinner(PlayerToken.CIRCLE));
            Assertions.assertEquals(isWin, model.detectWinner(PlayerToken.CROSS));

        }

        @DisplayName("Column Winner Circle Detection")
        @Test
        public void columnWinnerCircleDetection() {
            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CROSS));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CROSS));

            Assertions.assertTrue(model.detectWinner(PlayerToken.CIRCLE));
            Assertions.assertFalse(model.detectWinner(PlayerToken.CROSS));

        }

        @DisplayName("Row Winner Circle Detection")
        @Test
        public void rowWinnerCircleDetection() {
            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CROSS));

            Assertions.assertTrue(model.detectWinner(PlayerToken.CIRCLE));
            Assertions.assertFalse(model.detectWinner(PlayerToken.CROSS));



        }

        @DisplayName("Diagonal Winner Circle Detection")
        @Test
        public void diagonalWinnerCircleDetection() {
            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));

            Assertions.assertTrue(model.detectWinner(PlayerToken.CIRCLE));
            Assertions.assertFalse(model.detectWinner(PlayerToken.CROSS));

        }

        @DisplayName("Inverted Diagonal Winner Circle")
        @Test
        public void invertedDiagonalWinnerCircle() {
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CIRCLE));

            Assertions.assertTrue(model.detectWinner(PlayerToken.CIRCLE));
            Assertions.assertFalse(model.detectWinner(PlayerToken.CROSS));

        }

    }

    @Nested
    class ticTacToeCircleWinsAllGames{
        @BeforeEach
        public void setupTicTacToeCircleWins() {
            var gameModeSelected = GameModePvsCPU.createGameModePvsCPU(model);
            model.setGameMode(gameModeSelected);
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();

        }

        @DisplayName("Circle Wins Row By Move")
        @Test
        public void circleWinsRowByMove() {
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CROSS));
            var getCurrent = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(0,0));
            Assertions.assertEquals(WIN, model.getGameState());
            Assertions.assertEquals(PlayerToken.CIRCLE, getCurrent);
        }

        @DisplayName("Circle Wins Column By Move")
        @Test
        public void circleWinsColumnByMove() {

            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.EMPTY));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));
            var getCurrent = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(0,2));
            Assertions.assertEquals(WIN, model.getGameState());
            Assertions.assertEquals(PlayerToken.CIRCLE, getCurrent);

        }

        @DisplayName("Circle Wins Diagonal By Move")
        @Test
        public void circleWinsDiagonalByMove() {
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));
            var getCurrent = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(0,0));
            Assertions.assertEquals(WIN, model.getGameState());
            Assertions.assertEquals(PlayerToken.CIRCLE, getCurrent);
            Assertions.assertEquals(PlayerToken.CROSS, model.getCurrentPlayer());

        }

        @DisplayName("Cicle Wins Inverted Diagnoal By Move")
        @Test
        public void circleWinsInvertedDiagonalByMove() {
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CIRCLE));
            var getCurrent = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(0,2));
            Assertions.assertEquals(WIN, model.getGameState());
            Assertions.assertEquals(PlayerToken.CIRCLE, getCurrent);
            Assertions.assertEquals(PlayerToken.CROSS, model.getCurrentPlayer());



        }

    }

    @Nested
    class ticTacToeDraw{
        @BeforeEach
        public void setupTicTacToeDraw() {
            var gameModeSelected = GameModePvsCPU.createGameModePvsCPU(model);
            model.setGameMode(gameModeSelected);
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();

        }

        @DisplayName("CPU Cross Draws by Move")
        @Test
        public void cpuCrossDrawsByMove() {
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CROSS));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));
            model.setCurrentPlayer(PlayerToken.CROSS);
            var getCurrent = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(0,2));
            Assertions.assertEquals(DRAW, model.getGameState());
            Assertions.assertEquals(PlayerToken.CROSS, getCurrent);
            Assertions.assertEquals(PlayerToken.CROSS, model.getGameResults().getPlayers().getFirst().token());

        }

        @DisplayName("Board Occupied CPU Tries A Move")
        @Test
        public void boardOccupiedTriesAMove() {

            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CROSS));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));
            model.setCurrentPlayer(PlayerToken.CROSS);
            var getCurrent = model.getCurrentPlayer();
            model.playerMove(new SquaredMatrixCoordinates(0,2));
            Assertions.assertEquals(DRAW, model.getGameState());
            Assertions.assertEquals(PlayerToken.CROSS, getCurrent);

        }

    }
    @Nested
    class ticTacToeBanner{
        @BeforeEach
        public void setupTicTacToeBanner() {
            var gameModeSelected = GameModePvsCPU.createGameModePvsCPU(model);
            model.setGameMode(gameModeSelected);
            model.playerLeftSelected(PlayerToken.CIRCLE);
            model.playerRightSelected(PlayerToken.CROSS);
            model.startGame();


        }

        @DisplayName("Initial Banner And Banner After Move Outcome Draw")
        @Test
        public void initialBannerAndBannerAfterMoveOutcomeDraw() {
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.CROSS));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.CIRCLE));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));

            var expected = model.getRoundSPlayed();
            model.playerMove(new SquaredMatrixCoordinates(0,0));

            var expectedAfterMove = model.getRoundSPlayed();

            Assertions.assertEquals(expected, "Round 0");
            Assertions.assertEquals(expectedAfterMove, "Round: 1 || DRAW");


        }

        @DisplayName("Banner Circle Wins")
        @Test
        public void bannerCircleWins() {
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.EMPTY));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.EMPTY));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CIRCLE));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.EMPTY));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.EMPTY));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.EMPTY));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CIRCLE));

            model.playerMove(new SquaredMatrixCoordinates(0,0));

            var expectedAfterMove = model.getRoundSPlayed();

            Assertions.assertEquals(expectedAfterMove, "Round: 1 || Winner is: CIRCLE");

        }

        @DisplayName("Banner Cross Wins")
        @Test
        public void bannerCrossWins() {

            model.getGameResults().addPlayer(Player.valueOf(0, 0, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(0, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(0, 2, PlayerToken.EMPTY));

            model.getGameResults().addPlayer(Player.valueOf(1, 0, PlayerToken.EMPTY));
            model.getGameResults().addPlayer(Player.valueOf(1, 1, PlayerToken.CROSS));
            model.getGameResults().addPlayer(Player.valueOf(1, 2, PlayerToken.EMPTY));

            model.getGameResults().addPlayer(Player.valueOf(2, 0, PlayerToken.EMPTY));
            model.getGameResults().addPlayer(Player.valueOf(2, 1, PlayerToken.EMPTY));
            model.getGameResults().addPlayer(Player.valueOf(2, 2, PlayerToken.CROSS));

            model.setCurrentPlayer(PlayerToken.CROSS);

            model.playerMove(new SquaredMatrixCoordinates(0,0));

            var expectedAfterMove = model.getRoundSPlayed();

            Assertions.assertEquals(expectedAfterMove, "Round: 1 || Winner is: CPU");

        }





    }
}
