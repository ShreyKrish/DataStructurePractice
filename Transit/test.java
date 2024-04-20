public class test {
    public static void main (String [] args){
        
        int trainStations [] = {0,3,7,13,19};
        int busStop[] = {0,2,3,5,7,11,13,17,19,23};
        busStop[1] = trainStations[0];
        System.out.println(busStop[1]);
       }
    }

