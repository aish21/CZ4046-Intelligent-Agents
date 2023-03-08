'''
Python class that defines the states
'''

class State:

    # Constructor method that initializes the State object
    def init(self, reward=0.0):
        self.reward = reward
        self.hasWall = False
    
    # Returns the reward value of the State
    def getReward(self):
        return self.reward

    # Sets the reward value of the State
    def setReward(self, reward):
        self.reward = reward

    # Returns T/F if its/its not a wall
    def isWall(self):
        return self.hasWall

    # Sets the hasWall attribute of the State object to T if it is a wall, F otherwise.
    def setAsWall(self, hasWall):
        self.hasWall = hasWall

    # Returns T if the reward and hasWall attributes of the two States being compared are the same
    def __eq__(self, other):
        if isinstance(other, State):
            return self.reward == other.reward and self.hasWall == other.hasWall
        return False

    # Returns a hash value based on the reward and hasWall attributes of the State, which is used to store States in dictionaries.
    def __hash__(self):
        return hash((self.reward, self.hasWall))