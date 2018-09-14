package edu.uno.ai.planning.bfs;

import edu.uno.ai.planning.ss.StateSpacePlanner;
import edu.uno.ai.planning.ss.StateSpaceProblem;
import edu.uno.ai.planning.ss.StateSpaceSearch;

public class SearchPlanner extends StateSpacePlanner {

    public SearchPlanner(){
        super("sdhunga2");
    }

    @Override
    protected StateSpaceSearch makeStateSpaceSearch(StateSpaceProblem problem){
        return new Search(problem);
    }

}
