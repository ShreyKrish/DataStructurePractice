package prereqchecker;
import java.util.*;
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);

        HashMap<String, ArrayList<String>> coursesPrereqs = new HashMap<>();

        int numCourses = StdIn.readInt();
        for (int i = 0; i < numCourses; i++) {
            String course = StdIn.readString();
            coursesPrereqs.put(course, new ArrayList<String>());
        }
        
        int numEdges = StdIn.readInt();
        for (int i = 0; i < numEdges; i++) {
            String course = StdIn.readString();
            String prereq = StdIn.readString();
            coursesPrereqs.get(course).add(prereq);
        }

        StdOut.setFile(args[1]);
        for (String course : coursesPrereqs.keySet()) {
            StdOut.print(course);
            for (String prereq : coursesPrereqs.get(course)) {
                StdOut.print(" " + prereq);
            }
            StdOut.println();
        }
    
//     Graph graph = new Graph(filename);
//    StdOut.setFile(outputFile);

//    for(String first: graph.keySet()){
//     StdOut.print(first);
//     StdOut.print(" ");
//     Set<String> value = graph.get(first);
//     for(String second: value){
//         StdOut.print(" ");
//     }
//     StdOut.print("\n");
//    }
//    StdOut.close();
   
        
    }
   
}
