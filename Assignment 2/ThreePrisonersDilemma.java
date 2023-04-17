public class ThreePrisonersDilemma {
	
	/* 
	 This Java program models the two-player Prisoner's Dilemma game.
	 We use the integer "0" to represent cooperation, and "1" to represent 
	 defection. 
	 
	 Recall that in the 2-players dilemma, U(DC) > U(CC) > U(DD) > U(CD), where
	 we give the payoff for the first player in the list. We want the three-player game 
	 to resemble the 2-player game whenever one player's response is fixed, and we
	 also want symmetry, so U(CCD) = U(CDC) etc. This gives the unique ordering
	 
	 U(DCC) > U(CCC) > U(DDC) > U(CDC) > U(DDD) > U(CDD)
	 
	 The payoffs for player 1 are given by the following matrix: */
	
	static int[][][] payoff = {  
		{{6,3},  //payoffs when first and second players cooperate 
		 {3,0}}, //payoffs when first player coops, second defects
		{{8,5},  //payoffs when first player defects, second coops
	     {5,2}}};//payoffs when first and second players defect
	
	/* 
	 So payoff[i][j][k] represents the payoff to player 1 when the first
	 player's action is i, the second player's action is j, and the
	 third player's action is k.
	 
	 In this simulation, triples of players will play each other repeatedly in a
	 'match'. A match consists of about 100 rounds, and your score from that match
	 is the average of the payoffs from each round of that match. For each round, your
	 strategy is given a list of the previous plays (so you can remember what your 
	 opponent did) and must compute the next action.  */
	
	
	abstract class Player {
		// This procedure takes in the number of rounds elapsed so far (n), and 
		// the previous plays in the match, and returns the appropriate action.
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			throw new RuntimeException("You need to override the selectAction method.");
		}
		
		// Used to extract the name of this player class.
		final String name() {
			String result = getClass().getName();
			return result.substring(result.indexOf('$')+1);
		}
	}
	
	/* Here are four simple strategies: */

	class CombinedPlayer extends Player {
		// Parameters for GradualPlayer's strategy
		private int numDefections = 0;
		private int consecutiveDefections = 0;
		private boolean opponentIsForgiving = true;
	
		// Parameters for FirmButFairPlayer's strategy
		private boolean cooperate = true;
		private boolean firstMove = true;
	
		// Parameters for GrimTriggerPlayer's strategy
		private boolean triggered = false;
	
		@Override
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			// Check if the opponent has ever defected
			boolean opponentHasDefected = false;
			for (int i = 0; i < n; i++) {
				if (oppHistory1[i] == 1 || oppHistory2[i] == 1) {
					opponentHasDefected = true;
					break;
				}
			}
	
			if (opponentHasDefected) {
				// Apply GradualPlayer's strategy
				if (oppHistory1[n - 1] == 1 || oppHistory2[n - 1] == 1) {
					// Opponent defected on the previous move
					numDefections++;
					consecutiveDefections++;
					cooperate = false;
					return 1;
				} else {
					// Opponent cooperated on the previous move
					if (numDefections > 0 && consecutiveDefections == numDefections) {
						// Calm down the opponent after N consecutive defections
						consecutiveDefections = 0;
						numDefections = 0;
						opponentIsForgiving = true;
						cooperate = true;
						return 0;
					} else if (opponentIsForgiving) {
						// Cooperate if the opponent is forgiving
						cooperate = true;
						return 0;
					} else {
						// Defect if the opponent is not forgiving
						cooperate = false;
						consecutiveDefections++;
						return 1;
					}
				}
			} else {
				// Apply FirmButFairPlayer's strategy
				if (firstMove) {
					firstMove = false;
					return 0; // cooperate on the first move
				} else if (!cooperate) {
					cooperate = oppHistory1[n - 1] == 0 && oppHistory2[n - 1] == 0;
					// try to cooperate again after (D|D)
					return cooperate ? 0 : 1;
				} else {
					cooperate = oppHistory1[n - 1] == 0 && oppHistory2[n - 1] == 0;
					// continue to cooperate until the other side defects
					return cooperate ? 0 : 1;
				}
			}
		}
	}
	


	// Best - 1
	class GrimTriggerPlayer extends Player {
		boolean triggered = false;
	
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (!triggered) {
				for (int i = 0; i < n; i++) {
					if (oppHistory1[i] == 1 || oppHistory2[i] == 1) {
						triggered = true;
						break;
					}
				}
			}
	
			if (triggered) {
				return 1; // defect
			} else {
				return 0; // cooperate
			}
		}
	}

	// 2
	class FirmButFairPlayer extends Player {
		boolean cooperate = true;
		boolean firstMove = true;
	
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (firstMove) {
				firstMove = false;
				return 0;  // cooperate on the first move
			} else if (!cooperate) {
				cooperate = oppHistory1[n-1] == 0 && oppHistory2[n-1] == 0;
				// try to cooperate again after (D|D)
				return cooperate ? 0 : 1;
			} else {
				cooperate = oppHistory1[n-1] == 0 && oppHistory2[n-1] == 0;
				// continue to cooperate until the other side defects
				return cooperate ? 0 : 1;
			}
		}
	}

	// 3
	class GradualPlayer extends Player {
		private int numDefections = 0;
		private int consecutiveDefections = 0;
		private boolean opponentIsForgiving = true;
	
		@Override
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n == 0) {
				// Cooperate on the first move
				return 0;
			}
	
			// Check if the opponent has ever defected
			boolean opponentHasDefected = false;
			for (int i = 0; i < n; i++) {
				if (oppHistory1[i] == 1 || oppHistory2[i] == 1) {
					opponentHasDefected = true;
					break;
				}
			}
	
			if (!opponentHasDefected) {
				// Keep cooperating if the opponent has never defected
				return 0;
			}
	
			// If the opponent has defected at least once, start applying the gradual strategy
			if (oppHistory1[n - 1] == 1 || oppHistory2[n - 1] == 1) {
				// Opponent defected on the previous move
				numDefections++;
				consecutiveDefections++;
				return 1;
			} else {
				// Opponent cooperated on the previous move
				if (numDefections > 0 && consecutiveDefections == numDefections) {
					// Calm down the opponent after N consecutive defections
					consecutiveDefections = 0;
					numDefections = 0;
					opponentIsForgiving = true;
					return 0;
				} else if (opponentIsForgiving) {
					// Cooperate if the opponent is forgiving
					return 0;
				} else {
					// Defect if the opponent is not forgiving
					consecutiveDefections++;
					return 1;
				}
			}
		}
	}

	// Okay strat - 4
	class TitForTatPlayer2 extends Player {
		//TitForTatPlayer cooperates in the first round, and then copies
		//the previous action of the opponent with the highest total payoff
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			//cooperate in the first round
			if (n == 0) {
				return 0;
			}
			//determine which opponent has the highest total payoff so far
			int payoff1 = 0;
			int payoff2 = 0;
			for (int i = 0; i < n; i++) {
				payoff1 += payoff[myHistory[i]][oppHistory1[i]][oppHistory2[i]];
				payoff2 += payoff[oppHistory1[i]][oppHistory2[i]][myHistory[i]];
			}
			if (payoff1 >= payoff2) {
				//copy opponent 1's previous action
				return oppHistory1[n-1];
			} else {
				//copy opponent 2's previous action
				return oppHistory2[n-1];
			}
		}
	}
	
	// 5
	class ReverseT4TPlayer extends Player {
		// ReverseT4TPlayer defects on the first move,
		// then plays the reverse of the opponent's last move.
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n == 0) {
				return 1; // Defect on the first move
			} else {
				int lastOpponentMove = oppHistory1[n-1]; // Get opponent's last move
				return lastOpponentMove == 0 ? 1 : 0; // Reverse opponent's last move
			}
		}
	}

	// 6
	class SoftGrudgerPlayer extends Player {
		boolean didOpponentDefect = false;
		int consecutiveDefects = 0;
	
		@Override
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (didOpponentDefect) {
				if (consecutiveDefects < 4) {
					consecutiveDefects++;
					return 1; // D
				} else if (consecutiveDefects == 4) {
					consecutiveDefects++;
					return 0; // C
				} else if (consecutiveDefects == 5) {
					didOpponentDefect = false;
					consecutiveDefects = 0;
					return 0; // C
				}
			} else {
				return 0; // C
			}
			return 0; // C (shouldn't get here)
		}
	}
	
	// 7
	class HardMajorityPlayer extends Player {
		// Hard Majority Player defects on the first move and
		// defects if the number of defections of the opponent
		// is greater than or equal to the number of times it has cooperated.
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n == 0) {
				return 1; // Defect on the first move
			} else {
				int numDefects = 0;
				int numCoops = 0;
				for (int i = 0; i < n; i++) {
					if (oppHistory1[i] == 1 || oppHistory2[i] == 1) {
						numDefects++;
					} else {
						numCoops++;
					}
				}
				if (numDefects >= numCoops) {
					return 1; // Defect if numDefects >= numCoops
				} else {
					return 0; // Cooperate otherwise
				}
			}
		}
	}
	
	// 8
	class SuspiciousTitForTatPlayer extends Player {
		boolean firstRound = true;
		int lastOpponentAction = 1;
	
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (firstRound) {
				firstRound = false;
				return 1; // defect on the first move
			} else {
				if (oppHistory1[n-1] == 1 || oppHistory2[n-1] == 1) {
					lastOpponentAction = 1;
					return 1;
				} else {
					lastOpponentAction = 0;
					return 0;
				}
			}
		}
	}
	
	class NicePlayer extends Player {
		//NicePlayer always cooperates
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return 0; 
		}
	}
	
	class NastyPlayer extends Player {
		//NastyPlayer always defects
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return 1; 
		}
	}
	
	class RandomPlayer extends Player {
		//RandomPlayer randomly picks his action each time
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (Math.random() < 0.5)
				return 0;  //cooperates half the time
			else
				return 1;  //defects half the time
		}
	}
	
	class TolerantPlayer extends Player {
		//TolerantPlayer looks at his opponents' histories, and only defects
		//if at least half of the other players' actions have been defects
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			int opponentCoop = 0;
			int opponentDefect = 0;
			for (int i=0; i<n; i++) {
				if (oppHistory1[i] == 0)
					opponentCoop = opponentCoop + 1;
				else
					opponentDefect = opponentDefect + 1;
			}
			for (int i=0; i<n; i++) {
				if (oppHistory2[i] == 0)
					opponentCoop = opponentCoop + 1;
				else
					opponentDefect = opponentDefect + 1;
			}
			if (opponentDefect > opponentCoop)
				return 1;
			else
				return 0;
		}
	}
	
	class FreakyPlayer extends Player {
		//FreakyPlayer determines, at the start of the match, 
		//either to always be nice or always be nasty. 
		//Note that this class has a non-trivial constructor.
		int action;
		FreakyPlayer() {
			if (Math.random() < 0.5)
				action = 0;  //cooperates half the time
			else
				action = 1;  //defects half the time
		}
		
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return action;
		}	
	}

	class T4TPlayer extends Player {
		//Picks a random opponent at each play, 
		//and uses the 'tit-for-tat' strategy against them 
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n==0) return 0; //cooperate by default
			if (Math.random() < 0.5)
				return oppHistory1[n-1];
			else
				return oppHistory2[n-1];
		}	
	}

	class TL_Strategy extends Player {
		// Calculate percentage of cooperation by a certain player
		float calculateCoopPercentage(int[] history) {
			int cooperates = 0;
			for (int i = 0; i < history.length; i++)
				if (history[i] == 0)
					cooperates++;
			return (float) cooperates / history.length * 100;
		}

		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n == 0) {
				return 0; // cooperate by default
			}
			
			// defection by default if either opponent defects last round
			if (oppHistory1[n - 1] == 1 || oppHistory2[n - 1] == 1) {
				return 1;
			}
			
			// Cooperate if both opponents have been mostly cooperating
			float opp1C = calculateCoopPercentage(oppHistory1);
			float opp2C = calculateCoopPercentage(oppHistory2);
			if (opp1C > 90 && opp2C > 90) {
				return 0;
			}
			
			// Otherwise default to defection
			return 1;
		}
	}
	
	/* In our tournament, each pair of strategies will play one match against each other. 
	 This procedure simulates a single match and returns the scores. */
	float[] scoresOfMatch(Player A, Player B, Player C, int rounds) {
		int[] HistoryA = new int[0], HistoryB = new int[0], HistoryC = new int[0];
		float ScoreA = 0, ScoreB = 0, ScoreC = 0;
		
		for (int i=0; i<rounds; i++) {
			int PlayA = A.selectAction(i, HistoryA, HistoryB, HistoryC);
			int PlayB = B.selectAction(i, HistoryB, HistoryC, HistoryA);
			int PlayC = C.selectAction(i, HistoryC, HistoryA, HistoryB);
			ScoreA = ScoreA + payoff[PlayA][PlayB][PlayC];
			ScoreB = ScoreB + payoff[PlayB][PlayC][PlayA];
			ScoreC = ScoreC + payoff[PlayC][PlayA][PlayB];
			HistoryA = extendIntArray(HistoryA, PlayA);
			HistoryB = extendIntArray(HistoryB, PlayB);
			HistoryC = extendIntArray(HistoryC, PlayC);
		}
		float[] result = {ScoreA/rounds, ScoreB/rounds, ScoreC/rounds};
		return result;
	}
	
//	This is a helper function needed by scoresOfMatch.
	int[] extendIntArray(int[] arr, int next) {
		int[] result = new int[arr.length+1];
		for (int i=0; i<arr.length; i++) {
			result[i] = arr[i];
		}
		result[result.length-1] = next;
		return result;
	}
	
	/* The procedure makePlayer is used to reset each of the Players 
	 (strategies) in between matches. When you add your own strategy,
	 you will need to add a new entry to makePlayer, and change numPlayers.*/
	
	int numPlayers = 7;
	Player makePlayer(int which) {
		switch (which) {
		case 0: return new NicePlayer();
		case 1: return new NastyPlayer();
		case 2: return new RandomPlayer();
		case 3: return new TolerantPlayer();
		case 4: return new FreakyPlayer();
		case 5: return new T4TPlayer();
		case 6: return new TL_Strategy();
		}
		throw new RuntimeException("Bad argument passed to makePlayer");
	}
	
	/* Finally, the remaining code actually runs the tournament. */
	
	public static void main (String[] args) {
		ThreePrisonersDilemma instance = new ThreePrisonersDilemma();
		instance.runTournament();
	}
	
	boolean verbose = true; // set verbose = false if you get too much text output
	
	void runTournament() {
		float[] totalScore = new float[numPlayers];

		// This loop plays each triple of players against each other.
		// Note that we include duplicates: two copies of your strategy will play once
		// against each other strategy, and three copies of your strategy will play once.

		for (int i=0; i<numPlayers; i++) for (int j=i; j<numPlayers; j++) for (int k=j; k<numPlayers; k++) {

			Player A = makePlayer(i); // Create a fresh copy of each player
			Player B = makePlayer(j);
			Player C = makePlayer(k);
			int rounds = 90 + (int)Math.rint(20 * Math.random()); // Between 90 and 110 rounds
			float[] matchResults = scoresOfMatch(A, B, C, rounds); // Run match
			totalScore[i] = totalScore[i] + matchResults[0];
			totalScore[j] = totalScore[j] + matchResults[1];
			totalScore[k] = totalScore[k] + matchResults[2];
			if (verbose)
				System.out.println(A.name() + " scored " + matchResults[0] +
						" points, " + B.name() + " scored " + matchResults[1] + 
						" points, and " + C.name() + " scored " + matchResults[2] + " points.");
		}
		int[] sortedOrder = new int[numPlayers];
		// This loop sorts the players by their score.
		for (int i=0; i<numPlayers; i++) {
			int j=i-1;
			for (; j>=0; j--) {
				if (totalScore[i] > totalScore[sortedOrder[j]]) 
					sortedOrder[j+1] = sortedOrder[j];
				else break;
			}
			sortedOrder[j+1] = i;
		}
		
		// Finally, print out the sorted results.
		if (verbose) System.out.println();
		System.out.println("Tournament Results");
		for (int i=0; i<numPlayers; i++) 
			System.out.println(makePlayer(sortedOrder[i]).name() + ": " 
				+ totalScore[sortedOrder[i]] + " points.");
		
	} // end of runTournament()
	
} // end of class PrisonersDilemma