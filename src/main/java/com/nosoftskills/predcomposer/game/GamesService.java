package com.nosoftskills.predcomposer.game;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class GamesService implements Serializable {

    private static final long serialVersionUID = -6809263012190548690L;

    @PersistenceContext
    EntityManager entityManager;

    public Game findGameById(Long gameId) {
        return entityManager.find(Game.class, gameId);
    }

    public Game toggleLockedMode(Game game) {
        Game changedGame = entityManager.merge(game);
        changedGame.setLocked(!game.isLocked());
        return changedGame;
    }

    public Game storeGame(Game game, Competition competition) {
        if (game.getId() == null) {
            competition.getGames().add(game);
            entityManager.persist(game);
            return game;
        } else {
            return entityManager.merge(game);
        }
    }
}
