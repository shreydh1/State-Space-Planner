package edu.uno.ai.planning.bfs;
import edu.uno.ai.planning.State;
import edu.uno.ai.planning.Step;
import edu.uno.ai.planning.logic.Conjunction;
import edu.uno.ai.planning.logic.Expression;
import edu.uno.ai.planning.ss.StateSpaceProblem;
import edu.uno.ai.planning.ss.StateSpaceSearch;
import edu.uno.ai.planning.logic.Literal;

/*author : Shrey Dhungana*/

import edu.uno.ai.planning.logic.Literal;

import java.nio.DoubleBuffer;
import java.util.HashMap;

public class Heuristics {

    private HashMap<Literal, Double> literalMap;
    private StateSpaceProblem problem;

    public Heuristics(StateSpaceProblem problem) {
        literalMap = new HashMap<Literal, Double>();
        this.problem = problem;

        //cost of a literal initially is positive infinity
        for (Literal literal :problem.literals ) {
            literalMap.put(literal, Double.POSITIVE_INFINITY);
        }
    }

    /* This method calculates heuristic value*/

    public double calculate(State myState){
        Expression myExp =  myState.toExpression();

        if(myExp instanceof  Literal)
            literalMap.put((Literal) myExp, 0.0);
        else{
            for(Expression literal : ((Conjunction)myExp).arguments)
                literalMap.put((Literal) literal, 0.0);
        }


        boolean isCostConstant = false;
        double value = 0.0;
        double newValue = 0.0;
        double cost = 0.0;

        while(!isCostConstant) {
            isCostConstant = true;
            for (Step step : problem.steps){
                if(step.effect instanceof  Literal){
                    value = literalMap.get(step.effect);
                    newValue = conjunctionCost(step.precondition) + 1;
                  //  System.out.println("this is heuristic calculate");

                    if(Double.compare(value, newValue) < 0){
                        literalMap.put((Literal)step.effect, newValue);
                        isCostConstant = false;
                    }
                }
                else{
                    for(Expression literal : ((Conjunction)step.effect).arguments){
                        value = literalMap.get((Literal)literal);
                        newValue = conjunctionCost(step.precondition) + 1;
                        if(Double.compare(newValue, value) < 0){
                            literalMap.put((Literal)literal, newValue);
                            isCostConstant = false;
                        }
                    }
                }
            }
        }
        cost = conjunctionCost(problem.goal);
        return cost;
    }

    private double conjunctionCost(Expression expression){

        double literalCost = 0.0;
        if(expression instanceof Literal)
            literalCost = literalMap.get(expression);
        else{
            for(Expression literal : ((Conjunction)expression).arguments){
                literalCost += literalMap.get(literal);
            if(literalCost == Double.POSITIVE_INFINITY)
             break;
            }
        }
        return literalCost;
    }

}
