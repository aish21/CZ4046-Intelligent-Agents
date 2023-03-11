/*
 * This class defines the constants used in the iteration process of the value and policy iteration algorithms. 
 * It includes rewards for different cells, transition probabilities, discount factor, maximum reward, maximum error, 
 * number of times the Bellman algorithm is executed, and the upper bound on utility values.
 */

package controller;

public final class IterationConstants {
    
    // Define rewards for different cells
    public static final double REWARD_WHITE = -0.040;
    public static final double REWARD_GREEN = 1.000;
    public static final double REWARD_BROWN = -1.000;
    public static final double WALL_COLLISION = 0.000;

    // Define the transition probabilities for the agent
    public static final double INTENDED_PROBABILITY = 0.800;
    public static final double RIGHT_PROBABILITY = 0.100;
    public static final double LEFT_PROBABILITY = 0.100;

    // Define the discount factor
    public static final double DISCOUNT_FACTOR = 0.990;

    // Define the maximum value of reward    
    public static final double MAXIMUM_REWARD = 1.000;

    // Define the parameters for maximum error permitted  
    public static final double MAXIMUM_ALLOWED_ERROR = 30;
    public static final double MAXIMUM_ALLOWABLE_DISCOUNTED_ERROR = MAXIMUM_ALLOWED_ERROR * MAXIMUM_REWARD;

    // Define the number of times the Bellman algorithm is executed
    public static final int K = 10;

    // Define the upper bound on utility values
    public static final double UTILITY_UPPER_BOUND = MAXIMUM_REWARD / (1 - DISCOUNT_FACTOR);

}
