package edu.uno.ai.planning.bfs;

import java.util.LinkedList;
import java.util.Queue;

import edu.uno.ai.planning.Plan;
import edu.uno.ai.planning.Step;
import edu.uno.ai.planning.ss.StateSpaceNode;
import edu.uno.ai.planning.ss.StateSpaceProblem;
import edu.uno.ai.planning.ss.StateSpaceSearch;

/**
 * This class performs breadth-first search through the space of states.
 * 
 * @author Stephen G. Ware
 */
public class BreadthFirstSearch extends StateSpaceSearch {

	/** A queue to keep track of nodes on the frontier */
	private final Queue<StateSpaceNode> queue = new LinkedList<>();
	
	/**
	 * Constructs a new search process for a given problem.
	 * 
	 * @param problem the problem to solve
	 */
	public BreadthFirstSearch(StateSpaceProblem problem) {
		super(problem);
		queue.offer(root);
	}

	@Override
	public Plan findNextSolution() {
		// Keep working until there are no nodes on the frontier.
		while(!queue.isEmpty()) {
			// Get the first node from the frontier.
			StateSpaceNode node = queue.poll();
			// Check every possible step...
			for(Step step : problem.steps) {
				// Check if the step's precondition is satisfied...
				if(step.precondition.isTrue(node.state)) {
					// If so, generate the state that would result from taking
					// that step and add it to the queue.
					queue.add(node.expand(step));
				}
			}
			// If the current state satisfies the goal, the plan leading to
			// this state is a solution.
			if(problem.goal.isTrue(node.state)) {
				// Return the plan leading to the current state.
				return node.plan;
			}
		}
		return null;
	}
}
