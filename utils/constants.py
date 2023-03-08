'''
Python class that defines various constants related to the grid world question
'''

class GridConsts:

    # Define Grid Dimensions - First row and column starts at 0
    numCols = 6
    numRows = 6

    # Define separators for row-col and grid
    cellSep = ";"
    colRowSep = ","
    
    # Define Agent's start position
    startRow = 3
    startCol = 2

    # Define the grid - placements of the different coloured cells
    greenCell = "0,0; 2,0; 5,0; 3,1; 4,2; 5,3"
    brownCell = "1,1; 5,1; 2,2; 3,3; 4,4"
    wallCell = "1,0; 4,1; 1,4; 2,4; 3,4"

    # Define rewards for different cells
    whiteReward = -0.04
    greenReward = 1.0
    brownReward = -1.0
    wallCollision = 0.0

    # Define the transition probabilities for the agent 
    intendedProb = 0.8
    rightProb = 0.1
    leftProb = 0.1

    # Define the discount factor
    discFactor =  0.99

    # Define the maximum value of reward    
    maxReward = 1.0

    # Define the parameters for maximum error permitted  
    const = 30
    maxError = maxReward * const

    # Define the number of times the Bellman algorithm is executed
    k = 10

    # Define the upper bound on utility values
    UTILITY_UPPER_BOUND = maxReward / (1 - discFactor)