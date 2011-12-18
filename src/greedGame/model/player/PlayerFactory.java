package greedGame.model.player;

import java.util.Arrays;
import java.util.List;

import greedGame.model.GreedGameModel;

/**
 * <code>PlayerFactory</code> handles creating all player types known to it. It
 * also provides a list of the player types it supports.
 */
public class PlayerFactory {

	// Used to give each player a unique name
	private int humanPlayerCount = 0;
	private int cowardAIPlayerCount = 0;
	private int gamblerAIPlayerCount = 0;
	private int randomAIPlayerCount = 0;

	private GreedGameModel model;

	// Supported player types
	private String[] playerTypes = { "Human player", "Coward AI player",
			"Gambler AI player", "Random AI player" };

	/**
	 * Creates a player factory for creating players for the given model. Not
	 * all player types need the model, but some do.
	 * 
	 * @param model
	 *            The model the players should use, if applicable
	 */
	public PlayerFactory(GreedGameModel model) {
		this.model = model;
	}

	/**
	 * Create a player representing a human using the GUI.
	 * 
	 * @return a human player using the GUI
	 */
	private HumanPlayer createHumanPlayer() {
		return new HumanPlayer("Player #" + ++humanPlayerCount);
	}

	/**
	 * Create a coward AI player.
	 * 
	 * @return a coward AI player tied to the model
	 */
	private CowardAIPlayer createCowardAIPlayer() {
		return new CowardAIPlayer("Coward AI #" + ++cowardAIPlayerCount, model);
	}

	/**
	 * Create a gambler AI player.
	 * 
	 * @return a gambler AI player tied to the model
	 */
	private GamblerAIPlayer createGamblerAIPlayer() {
		return new GamblerAIPlayer("Gambler AI #" + ++gamblerAIPlayerCount,
				model);
	}

	/**
	 * Create a randomly deciding AI player, not a player of a random type.
	 * 
	 * @return a randomly deciding AI player
	 */
	private RandomAIPlayer createRandomAIPlayer() {
		return new RandomAIPlayer("Random AI #" + ++randomAIPlayerCount, model);
	}

	/**
	 * Create a player of the given player type, if type is valid. Valid types
	 * can be found with <code>getPlayerTypes</code>.
	 * 
	 * @param playerType
	 *            String representation of the desired player type
	 * @return <code>null</code> if player type not recognized, otherwise a new
	 *         player of the given type
	 *         
	 * @see #getPlayerTypes()
	 */
	public Player createPlayer(String playerType) {

		if (playerTypes[0].equals(playerType))
			return createHumanPlayer();

		else if (playerTypes[1].equals(playerType))
			return createCowardAIPlayer();

		else if (playerTypes[2].equals(playerType))
			return createGamblerAIPlayer();

		else if (playerTypes[3].equals(playerType))
			return createRandomAIPlayer();

		else
			return null;
	}

	/**
	 * Gets the supported player types.
	 * 
	 * @return List of string representations of supported player types
	 */
	public List<String> getPlayerTypes() {
		return Arrays.asList(playerTypes);
	}
}
