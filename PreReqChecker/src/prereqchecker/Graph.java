package prereqchecker;

import java.util.*;


public class Graph {
    HashMap<String, ArrayList<String>> Courses = new HashMap<String, ArrayList<String>>();

    public Graph(String inputFile){
        StdIn.setFile(inputFile);
       int num1 = StdIn.readInt();
        String s = StdIn.readLine();
        for(int i=0;i<num1;i++){
            String name = StdIn.readLine();
            ArrayList <String> list = new ArrayList<>();
            Courses.put(name,list);
        }
        int num2 = StdIn.readInt();
        String k = StdIn.readLine();
        for(int i =0;i<num2;i++){
            String Name = StdIn.readLine();
            String [] storage = Name.split(" ");
            ArrayList <String> List = Courses.get(storage[0]);
            List.add(storage[1]);
            Courses.put(storage[0],List);
        }
        return;
       

       
    }
    public boolean CycleFinder(String visited, String destination){
        if(visited.equals(destination)){
            return true;
        }
        ArrayList<String> finalClass = Courses.get(visited);
        for(int i =0; i<finalClass.size();i++){
            String preReq = finalClass.get(i);
            if(CycleFinder(preReq,destination)){
                return true;
            }
        }
        return false;
    }
}
