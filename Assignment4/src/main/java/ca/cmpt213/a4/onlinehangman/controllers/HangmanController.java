package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.UpdatePage;
import ca.cmpt213.a4.onlinehangman.model.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Online html-based Hangman game using Spring Boot and Thymeleaf.
 * Users can go to specific webpage using their web browser to play the game.
 * The system(game server) responds to path requests from the player(browser).
 * Users enter letters to guess the word.
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
@Controller
public class HangmanController {
    private int gameId;
    private static final int MAX_INCORRECT = 8;
    private AtomicLong nextId = new AtomicLong();
    UpdatePage updateInfo = new UpdatePage();

    private List<Game> game = new ArrayList<>();
    Game gameIndexId = new Game();
    Game sessionId = new Game();

    /**
     * Displays the welcome page
     */
    @GetMapping("/welcome")//url link
    public String showWelcome(Model model) {
        Game buttonPressed = new Game();
        model.addAttribute("checkIfPressed", buttonPressed);
        return "welcome";//html file
    }

    /**
     * Handles user input and displays the game page. If max incorrect guesses reached
     * or all letters of word guessed display the gameover page
     */
    @PostMapping("/game")
    public String showGame(Model model, @ModelAttribute("checkIfPressed") Game buttonPressed, @ModelAttribute("game") Game userAnswer) throws FileNotFoundException {
        if (buttonPressed.isPressed()) {
            createNewGame();
            model.addAttribute("game", game.get(gameId - 1));
        } else {
            gameId = (int) sessionId.getId() - 1;
            Game session = game.get(gameId);
            session.setUserInput(userAnswer.getUserInput());
            if (!userAnswer.getUserInput().equals("")) {
                char userGuess = userAnswer.getUserInput().charAt(0);
                if (((userGuess >= 'a') && (userGuess <= 'z'))) {
                    boolean input = updateInfo.checkUserInput(session, userAnswer);
                    if (input || (session.getIncorrectGuesses() == MAX_INCORRECT)) {
                        updateInfo.updateGameStatus(session, input);
                        model.addAttribute("condition", session);
                        return "gameover";
                    }
                }
            }
            model.addAttribute("game", game.get(gameId));
        }
        return "game";
    }

    /**
     * Shows the gamenotfound page when GameNotFoundException is thrown
     */
    @ExceptionHandler(GameNotFoundException.class)
    public String errorFound() {
        return "gamenotfound";
    }

    /**
     * Shows the game session page depending on id. Displays
     * the gameover page if the game session was previously completed
     */
    @GetMapping("/game/{id}")
    public String showGameId(Model model, @PathVariable("id") long idNumber) {
        Boolean idExist = updateInfo.checkIdExist(idNumber, game);
        if (!idExist) {
            throw new GameNotFoundException();
        }
        for (Game value : game) {
            if (idNumber == value.getId()) {
                model.addAttribute("game", value);
            }
        }
        sessionId.setId(idNumber);
        String gameStatus = game.get((int) idNumber - 1).getStatus();
        if ((gameStatus.equals("won")) || (gameStatus.equals("lost"))) {
            model.addAttribute("condition", game.get((int) idNumber - 1));
            return "gameover";
        }
        return "game";
    }

    /**
     * Creates a new game session
     */
    private void createNewGame() throws FileNotFoundException {
        Game currentGame = new Game();
        currentGame.setId(nextId.incrementAndGet());
        gameId = (int) currentGame.getId();
        gameIndexId.setId(gameId);
        sessionId.setId(gameId);
        String curWord = updateInfo.getRandomWord();
        game.add(new Game(gameId, curWord, 0, 0, "Alive", 0));
        game.get(gameId - 1).setWordSpace(updateInfo.initializeSpacing(curWord));
    }

}