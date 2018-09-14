#Implemented the heuristic for state space planning

Usage : java -jar Planning.jar <plugin> -d <domain> -p <problem> [-nl <nodes>] [-tl <millis>]

Where:
  <plugin> is a jar file containing one subclass of edu.uno.ai.planning.Planner
  <domain> and <problem> are files in PDDL-like format
  <nodes> is the optional max number of nodes to visit
  <millis> is the optional max number of milliseconds to search
