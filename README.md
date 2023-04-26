<div align="center">
  <h1>Intelligent Agents - Assignments and Quick Reference Notes</h1>
  
  <p>
    Repository to maintain assignments and notes from NTU's CZ4046 course - Intelligent Agents
  </p>
  
  
<!-- Badges -->
<p>
  <a href="">
    <img src="https://img.shields.io/github/last-commit/aish21/CZ4046-Intelligent-Agents" alt="last commit" />
  </a>
  <a href="https://github.com/aish21/CZ4046-Intelligent-Agents/stargazers/">
    <img src="https://img.shields.io/github/stars/aish21/CZ4046-Intelligent-Agents" alt="stars" />
  </a>
  <a href="https://github.com/aish21/CZ4046-Intelligent-Agents/issues/">
    <img src="https://img.shields.io/github/issues/aish21/CZ4046-Intelligent-Agents" alt="open issues" />
  </a>
</p>
   
<h4>
    <a href="Assignment 1/">Assignment 1</a>
  <span> · </span>
    <a href="Assignment 2/">Assignment 2</a>
  </h4>
</div>

<!-- Table of Contents -->
# :star2: Table of Contents

- [Lecture 1: Why Agents](#why-agents)
- [Lecture 2: Intelligent Agents](#intelligent-agents)
- [Lecture 3: Agent Decision Making](#agent-decision-making)  
- [Lecture 8: Multi-Agent Interaction](#multiagent-interaction)

## Why Agents
* 5 trends - 
  - Ubiquity - continual reduction in cost of computing - introduce processing power in many devices (Pervasiveness)
  - Interconnection - Computer systems  networked into large distributed systems
  - Intelligence - The complexity of tasks that we are capable of automating and delegating to computers has grown steadily
  - Delegation - giving control to computers - without our intervention, ties with intelligence
  - Human Orientation - movement away from machine-oriented views of programming - higher level understanding - human abstractions
* Agent - An agent is a computer system that is capable of independent action on behalf of its user, situated in some environment capable of autonomous action to meet delegated objectives. 
* Multi-Agent Systems - A system that consists of a number of agents which interact with one another - cooperate, coordinate and negotiate - computational, information processing entities.
* MAS - Interdisciplinary - infusing the methodologies of different fields 
* Views - 
  - Paradigm for software engineering - interaction is the most important characteristic of complex software - develop tools in which interaction is the norm
  - Tools for understanding human societies - MAS provide novel ways of simulating societies - shed light on social processes.
* Objections - 
  - Distributed Systems? Agents are autonomous, capable of making independent decisions - mechanisms to coordinate their activities. Self-interested for their interactions are economic
  - AI? Classical AI ignores social aspects of agency - important parts of intelligent activity in real-world setting
  - Economics / Game Theory? Game Theory is descriptive concepts - does not tell us how to compute solutions (need resource bounded, computational agents) - some assumptions also may not be valid 
  - Social Science? No reason to believe that artificial societies are constructed the same way human societies are - not subsumption
* Applications - 
  - Individual agents - autonomous actions - high risk simulations unsuitable for humans - spacecraft control, personal software agents - e-commerce, proactive - degree of autonomy varies -> as agents get better, research issues arise
  - MAS - for processes where control, data and expertise is distributed, centralised control is impractical and nodes have conflicting objectives - Air traffic control, business process management
 
## Intelligent Agents
* System receives input from environment and produces output for the same
* Flexible action - 
  - Reactive - With guaranteed environment, no need to worry about success or failure - execute blindly - maintains an ongoing interaction with its environment, and responds to the changes that occur in it.
  - Proactive - Goal directed behaviour - attempting to achieve goals, takes initiative not just driven by events - recognizes opportunities
  - Social - Take others into account, ability to interact with other agents via some form of communication and perhaps cooperate 
  - Other Properties - 
  - Mobility - move around in an electronic network 
  - Veracity - Not knowingly communicate false information
  - Benevolence - Do not have conflicting goals - do what its asked to do
  - Rationality - Act in order to achieve goals - as much as its beliefs permit
  - Learning - improve performance over time
* Balance properties - we want agents to respond timely to stimuli and also work towards long-term goals.
* Agents vs Objects - 
  - Agents are autonomous - decide for themselves whether or not to perform an action
  - Agents are smart - capable of flexible behaviour
  - Agents are active - MAS is multi-threaded, each agent is assumed to have at least one thread of active control.
  - Agents do it for money/because they want to
* Agents vs AI - 
  - Limited domain
  - Pick the right action
  - Do not have to solve all the problems of AI
* Agents vs Expert Systems - 
  - Expert systems are disembodied expertise about some domain
  - Agents are in an environment
  - Agents act
  - Some real time expert systems are agents
* Agents as Intentional Systems - 
  - Physical Stance: Concerned with mass, energy, velocity
  - Design Stance: Purpose, function and design
  - Intentional Stance: Thinking, intent and beliefs 
  - Attitudes employed in folk psychological descriptions are called intentional notions (believed, wanted)
  - Intentional system - behaviour can be predicted by method of attributing belief, desires
  - The more we know about a system, the less we need to rely on intentional explanations of its behaviour - but mechanistic explanations for complex systems may not be feasible
  - As computer systems become more flexible, we need more powerful abstractions to explain their operation - intentional stance
  - Intentional notions - abstraction tools - provide us with a convenient and familiar way of describing, explaining, and predicting the behaviour of complex systems
  - Start from the view of agents as intentional systems
* Environments -
  - Accessible vs Inaccessible - agent can obtain complete, accurate, up-to-date information about the environment’s state - the more of the former, easier it is to build the agent
  - Deterministic vs Nondeterministic - Any action has a single guaranteed effect - no uncertainty about the state that will result 
  - Episodic vs Non-Episodic - The performance of an agent is dependent on a number of discrete episodes - no link between performance in different scenarios - simpler as we only decide on the action based on current scenario
  - Static vs Dynamic - Environment remains unchanged except the actions of performed by the agent - no other processes operating on it (that could interfere with the agent’s actions)
  - Discrete vs Continuous - Fixed, finite number of actions and percepts in it
* Abstract Architecture - 
  - Environment is a set of discrete, instantaneous states -> E = {e, e’, e’’, …}
  - Agents have a set of actions available, which transform the state of the environment -> Ac = {a, a’, a’’, …}
  - Run is a sequence of interleaved environment states and actions > r: e0 -a0> e1 -a1> e2 …
  - R is the set of all possible finite sequences, RAc is a subset that will end in an action and Re is a subset that will end with an environment state
  - State transformer function represents the behaviour of the environment - convert current environment state to a new state
  - Environments are history dependent and non-deterministic 
  - When transformer is null set, system has ended its run
  - Env is a triple Env =〈E,e0,τ〉 where: E is a set of environment states, e0∈ E is the initial state, and τ is a state transformer function
  - Agent is a function that maps runs into actions -> agent makes decisions about what action to perform based on the history of the system. AG is the set of all agents
  - System - will have associated with it a set of possible runs ->  R(Ag, Env), contains only terminated runs
* Purely Reactive Agents - base their decisions entirely based on the present - action:E->Ac
* Perception - see function - agent’s ability to observe its environment, action represents the agent’s decision making process. Output of see is a percept. see:E->Per. Action is now a function, action:Per*->A, mapping sequence of percepts to actions
* State - internal data structure, record information about the environment’s state and history. Perception is unchanged, action:I->Ac, which is an action-selection function which maps internal states to actions. Additional function next is introduced which maps internal state and percept to an internal state > next: I x Per -> I
* Agent Control Loop - Agent starts with initial state i0 > Observes environment state e and generates percept see(e) > Internal state updated next(i0, see(e)) > action selected by the agent is action(next) > Step 2
* We want agents to carry out tasks for us without telling them how to do it - associate utilities with individual states - bring states that maximise utilities u : E -> Real Num
* Value of a run? Difficult to specify a long term view assigning utilities to individual states, assign utilities to runs > u: R -> Real Num, inherently takes the long term view
* AgOPT (maximise expected utility) = argmax[Sum(R(Ag,Env)) u(r)*P(r | Ag, env)]
* Some agents have resource constrictions - bounded optimal agents, replace with AGm
* Predicate task specifications - Ψ : R → {0, 1}, 1 for successful run, 0 for failed
* Task Environment specifies - properties of the system the agent will inhabit, criteria by which an agent will be judged. Ag succeeds in task environment if RΨ(Ag, Env) = R(Ag,Env)
* P(Ψ | Ag,Env) = Summation P(r | Ag,Env)
* Achievement - achieve state of affairs φ, set of good or goal states - The agent succeeds if it is guaranteed to bring about at least one of these states
* Maintenance - maintain state of affairs Ψ, set of bad B states - The agent succeeds in a particular environment if it manages to avoid all states in B — if it never performs actions which result in any state in B occurring
* Agent Synthesis - goal is to have a program that will take a task environment, and from this task environment automatically generate an agent that succeeds in this environment - complete (guaranteed to return an agent whenever there exists an agent that will succeed in the task environment given as input) and sound (whenever it returns an agent, then this agent succeeds in the task environment that is passed as input)

## Multiagent Interaction
* Game Theory studies how individuals or entities make decisions in strategic situations, where the outcome of their choices depends on the choices of others, and aims to model and analyze the strategic interactions between rational decision-makers.
* We assume we have 2 agents that are self-iterested (preferences over how the environment is), set of outcomes that agents have preferences over.
* We capture preferences via utility functions and this results to preference orderings over outcomes
* Environment behaviour is given by state transformer function, where outcome is a product of the 2 agents' actions
* Agent prefers outcomes that arise through action's utility over all outcomes that arise through another action's utility - characterized via a payoff matrix.
* Agents -> Column Player vs Row Player with each of their actions
* Rational Agent Strategies - 
  - Dominant Strategies: Equilibrium is where each player chooses its dominant strategy. s1 > s2 for i if every outcome possible by i playing s1 > every outcome possible by i playing s2 - never play a dominated strategy
  - Nash Equilibrium: Strategies s1 for i and s2 for j are in nash equilibrium if i plays s1 and j cannot do better than to play s2 and j plays s2 and i can do no better than to play s1 -> neither has incentive to deviate but not every scenario will follow this, some have more than one pure strategy (matching pennies - mixed strategies where H and T have a probability of 0.5 - play k with prob pk hence p1 + p2 + pk = 1) - every finite game has a Nash in mixed strategies
  - Pareto Optimal: if there is no other outcome that makes one agent better off without making another agent worse off. w is the pareto optimal such that there is no w* that  ui(w*) >= ui(w) and same for j and same for OR of them. If the outcome is Pareto optimal, then at least one agent will be reluctant to move, if not there is another outcome that will make everyone happy/happier.
  - Strategies that maximise social welfare: social welfare of an outcome is the sum of utilities that each agent gets from the outcome
* Zero-sum interactions - utilites sum to 0, strictly competitive 
* Prisoner's dilemma, dominant - D, DD is nash, everything else is Pareto and CC is max social welfare
* AXELROD - dont be envious, be nice, retaliate appropriately, dont hold grudges
