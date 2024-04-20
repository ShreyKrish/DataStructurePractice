package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                System.exit(1);
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }
    
    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /**
     * Reads a given text file character by character, and returns an arraylist
     * of CharFreq objects with frequency > 0, sorted by frequency
     * 
     * @param filename The text file to read from
     * @return Arraylist of CharFreq objects, sorted by frequency
     */
    public static ArrayList<CharFreq> makeSortedList(String filename) {
        StdIn.setFile(filename);
        /* Your code goes here */
        ArrayList<CharFreq> list = new ArrayList<>();
        int [] arr = new int [128];
        int c  = 0;
       

        while( StdIn.hasNextChar()){
            arr[StdIn.readChar()]++;
            c++;
        }
        for(int i = 0;i<128;i++){
            if(arr[i]!=0){
                list.add(new CharFreq((char)i, (double) arr[i]/c));
            }
        }
        if(list.size()== 1){
           char a = list.get(0).getCharacter();
            if(a < 127){
                list.add(new CharFreq((char)(a+1), 0));
            }else if (a == 127){
                list.add(new CharFreq((char)(0), 0));
            }
        }

        Collections.sort(list);
        return list; // Delete this line
    }

    /**
     * Uses a given sorted arraylist of CharFreq objects to build a huffman coding tree
     * 
     * @param sortedList The arraylist of CharFreq objects to build the tree from
     * @return A TreeNode representing the root of the huffman coding tree
     */
    public static TreeNode makeTree(ArrayList<CharFreq> sortedList) {
        /* Your code goes here */
    //     Queue<TreeNode> source = new Queue<TreeNode>();
    //     Queue<TreeNode> target = new Queue<TreeNode>();

    //     for(int i = 0; i < sortedList.size(); i++){
    //         source.enqueue(new TreeNode(sortedList.get(i), null, null));
    //     }

       
    //     while(source.size()!=0 || target.size()!=1){
    //         TreeNode node = new TreeNode();
    //         TreeNode left = new TreeNode();
    //         TreeNode right = new TreeNode();
           
    //         if(target.isEmpty() && !source.isEmpty()){
    //            // target.size()==0
    //             left = source.dequeue();
    //             right = source.dequeue();
                
    //         }else if ( !target.isEmpty() && source.isEmpty()){
    //             left = target.dequeue();
    //             right = target.dequeue();
               
    //         }
    //         else if(!source.isEmpty() && !target.isEmpty() && target.peek().getData().getProbOccurrence()> source.peek().getData().getProbOccurrence() ){
    //            left = source.dequeue();//target.peek().getData().getProbOccurrence()> source.peek().getData().getProbOccurrence()
    //            if(!source.isEmpty() && target.peek().getData().getProbOccurrence()> source.peek().getData().getProbOccurrence() ){
    //                right = source.dequeue();
    //            }else{
    //                right = target.dequeue();
    //            }
              
    //         }else{
    //             left = target.dequeue();
    //             if(!source.isEmpty() && !target.isEmpty() && target.peek().getData().getProbOccurrence()> source.peek().getData().getProbOccurrence() ){
    //                 right = source.dequeue();
    //             }else if(!target.isEmpty()){
    //                 right = target.dequeue();
    //             }else{
    //               right=source.dequeue();
    //             }
                
    //         }
    //         node.setLeft(left);
    //         node.setRight(right);
    //         target.enqueue(node);
    //         CharFreq a = new CharFreq(null, left.getData().getProbOccurrence()+ right.getData().getProbOccurrence());
    //          node.setData(a);
    //         System.out.println(node.getLeft().getData());
    //         System.out.println(node.getRight().getData());
    //     }
    
    //     return target.peek(); // Delete this line
    // }

    // /**
    //  * Uses a given huffman coding tree to create a string array of size 128, where each
    //  * index in the array contains that ASCII character's bitstring encoding. Characters not
    //  * present in the huffman coding tree should have their spots in the array left null
    //  * 
    //  * @param root The root of the given huffman coding tree
    //  * @return Array of strings containing only 1's and 0's representing character encodings
    //  */
    // public static String[] makeEncodings(TreeNode root) {
    //     /* Your code goes here */
    //     String [] s = new String[128];
    //     findEncoding(root,s,"");

    //     return s; // Delete this line
    // }
    // private static String[] findEncoding(TreeNode t, String[]a, String chain){
    //     if(t.getLeft() == null && t.getRight() == null){
    //         a[t.getData().getCharacter()] = chain;
    //     }
    //     else{
    //         findEncoding(t.getLeft(),a,chain+"0");
    //         findEncoding(t.getRight(),a,chain + "1");
    //     }
    //     return a;
    // }
    Queue <TreeNode> source = new Queue<>();
        for (int  i = 0; i < sortedList.size(); i++) {
            TreeNode temp = new TreeNode(sortedList.get(i), null, null);
            source.enqueue(temp);
        }

        Queue <TreeNode> target = new Queue<>();
        while (source.size() != 0 || target.size() != 1) {
            double s11 = 2;
            double t11 = 2;
            double firstVal = 0;
            TreeNode leftTemp = new TreeNode();
            if (source.size() != 0) {
                TreeNode s111 = source.peek();
                CharFreq s1 = s111.getData();
                s11 = s1.getProbOccurrence();
            }
            if (target.size() != 0) {
                TreeNode t111 = target.peek();
                CharFreq t1 = t111.getData();
                t11 = t1.getProbOccurrence();
            }

            if (t11 < s11) {
                TreeNode lTemp = target.dequeue();
                CharFreq lTemps = lTemp.getData();
                TreeNode llc = lTemp.getLeft();
                TreeNode lrc = lTemp.getRight();
                leftTemp.setData(lTemps);
                leftTemp.setLeft(llc);
                leftTemp.setRight(lrc);
                firstVal = t11;
            } else {
                TreeNode lTemp = source.dequeue();
                CharFreq lTemps = lTemp.getData();
                TreeNode llc = lTemp.getLeft();
                TreeNode lrc = lTemp.getRight();
                leftTemp.setData(lTemps);
                leftTemp.setLeft(llc);
                leftTemp.setRight(lrc);
                firstVal = s11;
            }

            double s22 = 2;
            double t22 = 2;
            double secondVal = 0;
            TreeNode rightTemp = new TreeNode();
            if (source.size() != 0) {
                TreeNode s222 = source.peek();
                CharFreq s2 = s222.getData();
                s22 = s2.getProbOccurrence();
            }
            if (target.size() != 0) {
                TreeNode t222 = target.peek();
                CharFreq t2 = t222.getData();
                t22 = t2.getProbOccurrence();
            }

            if (t22 < s22) {
                TreeNode rTemp = target.dequeue();
                CharFreq rTemps = rTemp.getData();
                TreeNode rlc = rTemp.getLeft();
                TreeNode rrc = rTemp.getRight();
                rightTemp.setData(rTemps);
                rightTemp.setLeft(rlc);
                rightTemp.setRight(rrc);
                secondVal = t22;
            } else {
                TreeNode rTemp = source.dequeue();
                CharFreq rTemps = rTemp.getData();
                TreeNode rlc = rTemp.getLeft();
                TreeNode rrc = rTemp.getRight();
                rightTemp.setData(rTemps);
                rightTemp.setLeft(rlc);
                rightTemp.setRight(rrc);
                secondVal = s22;
            }

            double val = firstVal + secondVal;
            CharFreq tempTemp = new CharFreq(null, val);
            TreeNode temps = new TreeNode(tempTemp, leftTemp, rightTemp);
            target.enqueue(temps);

        }

        return target.peek();


    }

  

    /**
     * Using a given string array of encodings, a given text file, and a file name to encode into,
     * this method makes use of the writeBitString method to write the final encoding of 1's and
     * 0's to the encoded file.
     * 
     * @param encodings The array containing binary string encodings for each ASCII character
     * @param textFile The text file which is to be encoded
     * @param encodedFile The file name into which the text file is to be encoded
     */
   
     public static void encodeFromArray(String[] encodings, String textFile, String encodedFile) {
        StdIn.setFile(textFile);
        /* Your code goes here */
      
       String a = "";
       char b;
        while(StdIn.hasNextChar()){
            b = StdIn.readChar() ;
            a = a+encodings[b];
        }
    writeBitString(encodedFile, a);

    }
  
    
    
    /**
     * Using a given encoded file name and a huffman coding tree, this method makes use of the 
     * readBitString method to convert the file into a bit string, then decodes the bit string
     * using the tree, and writes it to a file.
     * 
     * @param encodedFile The file which contains the encoded text we want to decode
     * @param root The root of your Huffman Coding tree
     * @param decodedFile The file which you want to decode into
     */
    public static void decode(String encodedFile, TreeNode root, String decodedFile) {
        StdOut.setFile(decodedFile);
        /* Your code goes here */
        String a = readBitString(encodedFile);
        TreeNode ptr = root;
        for(char b: a.toCharArray()){
         if(ptr.getData().getCharacter() != null){
            StdOut.print(ptr.getData().getCharacter());
            ptr = root;
         }
            if(b == '0'){
                ptr = ptr.getLeft();
            }else{
                ptr = ptr.getRight();
            }
           
        }
        if(ptr.getData().getCharacter() != null){
               StdOut.print(ptr.getData().getCharacter());
            }
        // traversal(a,root,root,c);
        
    }
    // private static void traversal(String decoded,TreeNode x, TreeNode y, String code){
    //     if(!StdIn.hasNextChar()){
    //          System.out.println("non");
    //          return;
    //     }  
    //     if(x.getLeft() == null && x.getRight() == null){
    //         code += x.getData().getCharacter();
    //         traversal(decoded,x,y,code);
           
    //     }
    //     while(StdIn.hasNextChar()){
    //         char d = StdIn.readChar();
    //         if(d=='0'){
    //             traversal(decoded.substring(1, decoded.length()), x.getLeft(),y,code);
               
    //         }else{
    //             traversal(decoded, x.getRight(),y,code);
               
    //         }
    //     }
    //    return;
    // } 
}
