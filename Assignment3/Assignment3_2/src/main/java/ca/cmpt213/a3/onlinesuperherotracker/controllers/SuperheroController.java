package ca.cmpt213.a3.onlinesuperherotracker.controllers;

import ca.cmpt213.a3.onlinesuperherotracker.model.Superhero;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Online superhero tracker system using Spring Boot REST API.
 * The system(the server) responds to messages from the client
 * using curl commands
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
@RestController
public class SuperheroController {
    private List<Superhero> superheroes = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @GetMapping("/hello")
    public String getHelloMessage() {
        return "Hello welcome to Superhero Tracker Online!";
    }

    /**
     * List the heroes in the array
     */
    @GetMapping("/listAll")
    public List<Superhero> getAllHeroes() {
        return superheroes;
    }

    /**
     * Add a superhero to the list and give it an id
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) //status 201
    public Superhero addHero(@RequestBody Superhero superhero) {
        if ((superhero.getHeightInCm() < 0) || (superhero.getCivilianSaveCount() < 0)) {
            throw new IllegalArgumentException();
        }
        //Set superhero to have next ID:
        superhero.setId(nextId.incrementAndGet());
        superheroes.add(superhero);
        return superhero;
    }

    /**
     * Remove a superhero from the list
     */
    @PostMapping("/remove/{id}")
    public List<Superhero> removeHero(@PathVariable("id") long HeroId) {
        for (Superhero superhero : superheroes) {
            if (superhero.getId() == HeroId) {
                superheroes.remove(superhero);
                return superheroes;
            }
        }
        throw new SuperheroNotFoundException();
    }

    /**
     * Update a superhero from the list
     */
    @PostMapping("/update/{id}")
    public Superhero updateHero (
            @PathVariable("id") long HeroId,
            @RequestBody Superhero newHero) {
        if ((newHero.getHeightInCm() < 0) || (newHero.getCivilianSaveCount() < 0)) {
            throw new IllegalArgumentException();
        }
        for (Superhero superhero : superheroes) {
            if (superhero.getId() == HeroId) {
                superheroes.remove(superhero);
                newHero.setId(HeroId);
                superheroes.add(((int) HeroId - 1), newHero);
                return newHero;
            }
        }
        throw new SuperheroNotFoundException();
    }

    //Create Exception Handler
    //bad_request = 400
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Invalid values.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        //Nothing to do
    }

}
