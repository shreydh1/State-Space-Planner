package edu.uno.ai.planning.bfs;

import edu.uno.ai.planning.Plan;
import edu.uno.ai.planning.State;
import edu.uno.ai.planning.Step;
import edu.uno.ai.planning.ss.StateSpaceNode;
import edu.uno.ai.planning.ss.StateSpaceProblem;
import edu.uno.ai.planning.ss.StateSpaceSearch;
import edu.uno.ai.planning.util.MinPriorityQueue;

/* This class uses informed greedy Search*/

public class Search extends StateSpaceSearch{

    private final MinPriorityQueue<StateSpaceNode> queue = new MinPriorityQueue<>();
    public Search(StateSpaceProblem problem) {
        super(problem);
        queue.push(root, Double.POSITIVE_INFINITY);

    }

    public double getHeuristic(State myState){
        double value = new Heuristics(problem).calculate(myState);
        return value;
    }

    @Override
    public Plan findNextSolution(){
        while(!queue.isEmpty()){
            StateSpaceNode currentNode = queue.pop();
            if(problem.goal.isTrue(currentNode.state))
                return currentNode.plan;
            else{
                for(Step step : problem.steps){
                    if(step.precondition.isTrue(currentNode.state)){
                        StateSpaceNode nextNode = currentNode.expand(step);
                        queue.push(nextNode, getHeuristic(nextNode.state) );
                    }
                }
            }

        }
        return null;
    }
}
