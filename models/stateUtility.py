'''
Python class that defines the utility of a state given the action taken by the agent
'''

class Utility:

    # Constructor function that initializes the Action and Utility
    def __init__(self, action=None, util=0.0):
        self.action = action
        self.util = util

    # Returns the Action 
    def getAction(self):
        return self.action

    # Returns the string value of Action
    def getActionStr(self):
        return self.action.toString() if self.action is not None else " Wall"

    # Sets the value of Action
    def setAction(self, action):
        self.action = action

    # Returns the value of Utility
    def getUtil(self):
        return self.util

    # Sets the value of Utility
    def setUtil(self, util):
        self.util = util

    # Function to compare to Utility objects based on their values
    def __lt__(self, other):
        return self.util > other.util

    # Function to check if two Utility objects are equal based on their values
    def __eq__(self, other):
        return self.util == other.util