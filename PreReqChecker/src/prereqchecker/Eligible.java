package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void saveCompletedCourses(String course, HashMap<String, ArrayList<String>> coursesPrereqs, HashSet<String> completedCourses) {
        if (completedCourses.contains(course))
            return;
        completedCourses.add(course);
        ArrayList<String> prereqs = coursesPrereqs.get(course);
        for (String prereq : prereqs)
            saveCompletedCourses(prereq, coursesPrereqs, completedCourses);
    }
    public static boolean elegibleCourse(String course, HashMap<String, ArrayList<String>> coursesPrereqs, HashSet<String> completedCourses) {
        if (completedCourses.contains(course))
            return false;
        for (String prereq : coursesPrereqs.get(course))
            if (!completedCourses.contains(prereq))
                return false;
        return true;
    }
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
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

        StdIn.setFile(args[1]);

        HashSet<String> completedCourses = new HashSet<>();
        
        int numCompletedCourses = StdIn.readInt();
        for (int i = 0; i < numCompletedCourses; i++) {
            String course = StdIn.readString();
            saveCompletedCourses(course, coursesPrereqs, completedCourses);
        }

        StdOut.setFile(args[2]);
        
        for (String course : coursesPrereqs.keySet())
            if (elegibleCourse(course, coursesPrereqs, completedCourses))
                StdOut.println(course);
    }
}
