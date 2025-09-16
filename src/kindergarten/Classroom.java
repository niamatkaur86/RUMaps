package kindergarten;

/**
 * You're a kindergarten teacher and you have many students to teach.
 * You'll supervise students when they line up, when they're in class,
 * and when they're playing musical chairs!
 * 
 * 
 * 
 * 
 */
public class Classroom {
    private SNode studentsInLine; // when students are in line - refers to the front of a singly linked list
    private SNode musicalChairs; // when students are in musical chairs: holds a reference to the LAST student in
                                 // the CLL
    private boolean[][] openSeats; // represents seat positions in the classroom
    private Student[][] studentsInSeats; // represents students in their corresponding seats

    /**
     * Constructor for classrooms. Do not edit.
     * 
     * @param inLine          passes in students in line
     * @param mChairs         passes in musical chairs
     * @param seats           passes in availability
     * @param studentsSitting passes in students sitting
     */
    public Classroom(SNode inLine, SNode mChairs, boolean[][] seats, Student[][] studentsSitting) {
        studentsInLine = inLine;
        musicalChairs = mChairs;
        openSeats = seats;
        studentsInSeats = studentsSitting;
    }

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students filing in line, before they enter the
     * classroom.
     * 
     * It does this by reading students from an input file and adding each to the
     * front
     * of the studentsInLine linked list.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. For each line of the input file:
     * 1. read a student from the file
     * 2. create a Student object with the student's info
     * 3. insert the Student to the FRONT of studentsInLine
     * 
     * Students are stored in reverse alphabetical order in the input file.
     * This method will put students in A-Z order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to front.
     * 
     * @param filename the student input file
     */
    public void enterClassroom(String filename) {
        StdIn.setFile(filename); //open file 
        int n = StdIn.readInt(); //number of students        
        studentsInLine = null;  
 
 
        for(int i = 0 ; i < n; i++){ 
            Student niamat = new Student(); 
            niamat.setFirstName(StdIn.readString()); 
            niamat.setLastName(StdIn.readString()); 
            niamat.setHeight(StdIn.readInt());
           
            SNode newNode = new SNode(); 
            newNode.setStudent(niamat);
            newNode.setNext(studentsInLine);
            studentsInLine = newNode; 
        }
 
    }//end of enterClassroom method

    /**
     * 
     * This method creates the open seats in the classroom.
     * 
     * 1. Open the file using StdIn.setFile(seatingChart);
     * 
     * 2. You will read the seating input file with the format:
     * An integer representing the number of rows in the classroom
     * An integer representing the number of columns in the classroom
     * Number of r lines, each containing c true or false values (true represents
     * that a
     * seat is present in that column)
     * 
     * 3. Initialize openSeats and studentsInSeats arrays with r rows and c columns
     * 
     * 4. Update studentsInSeats with the booleans read from the input file
     * 
     * This method does not seat students in the seats.
     * 
     * @param openSeatsFile the seating chart input file
     */
    public void createSeats(String openSeatsFile) {
        StdIn.setFile(openSeatsFile);//read file  
        int x = StdIn.readInt();
        int y = StdIn.readInt();
        studentsInSeats = new Student[x][y];
        openSeats = new boolean[x][y];
        boolean value;
 
 
        for(int r =0; r<x; r++){ 
 
            for(int c =0; c<y; c++){
 
                 value = StdIn.readBoolean(); 
                 openSeats[r][c] = value; 
 
            }//end of inner for
        }//end of outer for
    }//end of createSeats

    /**
     * 
     * This method simulates students moving from the line to their respective
     * seats.
     * 
     * Students are removed one by one from the line and inserted into
     * studentsInSeats
     * according to the seating positions in openSeats.
     * 
     * studentsInLine will then be empty at the end of this method.
     * 
     * NOTE: If the students just played musical chairs, the winner of musical
     * chairs is seated separately
     * by seatMusicalChairsWinner().
     */
    public void seatStudents() {
        int x =  studentsInSeats.length;
       int y = studentsInSeats[0].length;
      
       while (studentsInLine != null) {//while students are in line
           for(int r = 0; r<x; r++){
               for(int c = 0; c<y; c++){
                  
                   //place the students
                   if(openSeats[r][c] == true && studentsInSeats[r][c] == null && studentsInLine != null) {


                       studentsInSeats[r][c] = studentsInLine.getStudent();
                       studentsInLine = studentsInLine.getNext();

                   }
              
               }//end of inner for
           }//end of outer for 
       }//end of while
   }//end of seatStudents


    /**
     * Traverses studentsInSeats row by row, column by column removing a student
     * from their seat and adding them to the END of the musical chairs CLL.
     * 
     * NOTE: musicalChairs refers to the LAST student in the CLL.
     */
    public void insertMusicalChairs() {
        musicalChairs = null;//to make sure that the CLL is remade everytime  
        SNode last = null;
 
 
        for(int r = 0; r<studentsInSeats.length; r++){
            for(int c = 0; c<studentsInSeats[0].length; c++){
               
                if(studentsInSeats[r][c]!= null) { //making sure that a student is there 
 
 
                    SNode newNode = new SNode(studentsInSeats[r][c],null);//in order to create node
 
 
                    if (musicalChairs == null) {//beginning of CLL
                        musicalChairs = newNode; 
                        last = musicalChairs;
                        musicalChairs.setNext(musicalChairs);
                    } else {
                        last.setNext(newNode); // last points to new 
                        newNode.setNext(musicalChairs);
                        last = newNode;
                    }
                    studentsInSeats[r][c] = null;//remove from seat
                }
           
            }//end of inner for
        }//end of outer for 
 
 
        
        if (last != null) {
            musicalChairs = last;
        }
 
   }//end of insertMusicalChairs


    /**
     * 
     * Removes a random student from the musicalChairs, if they can't find a seat.
     * Once eliminated students go back to the line.
     * 
     * @param size represents the number of students currently sitting in
     *             musicalChairs
     * 
     *             1. Use StdRandom.uniform(size) to pick the nth student (n=0 =
     *             student at front).
     *             2. Search for the selected student, and delete them from
     *             musicalChairs
     *             3. Call insertByName to insert the deleted student back to the
     *             line.
     * 
     *             The random value denotes the refers to the position of the
     *             student to be removed
     *             in the musicalChairs. 0 is the first student
     */
    public void moveStudentFromChairsToLine(int size) {

            int n = StdRandom.uniform(size); //generating rand size 
            SNode ptr = musicalChairs; //starting from last node 

            //base case --> if there is only one student, the winner
            if (musicalChairs == null || musicalChairs.getNext() == musicalChairs) {
                return;  //no one to eliminate, return 
            }
            
            for (int i = 1; i <= n; i++) {
                ptr = ptr.getNext();  
            }

            SNode eliminatedStudent = ptr.getNext();
            if (eliminatedStudent == musicalChairs) {
                musicalChairs = ptr;  
            }

            ptr.setNext(ptr.getNext().getNext());
            insertByName(eliminatedStudent.getStudent());//add eliminated student back into the line 
 
    }//end of moveStudentFromChairsToLine
   


    /**
     * Inserts a single student, eliminated from musical chairs, to the line
     * inserted
     * in ascending order by last name and then first name if students have the same
     * last name
     * 
     * USE compareNameTo on a student: < 0 = less than, > 0 greater than, = 0 equal
     * 
     * @param eliminatedStudent the student eliminated from chairs to insert
     */
    public void insertByName(Student eliminatedStudent) {
        SNode newNode = new SNode(eliminatedStudent, null);
        SNode ptr = studentsInLine;
 
 
        //if list is empty or eliminated student is at the fornt
        if (studentsInLine == null || studentsInLine.getStudent().compareNameTo(eliminatedStudent) > 0) {
            newNode.setNext(studentsInLine);
            studentsInLine = newNode;
            return;
        }
 
 
        while (ptr.getNext() != null && ptr.getNext().getStudent().compareNameTo(eliminatedStudent) < 0) {
            ptr = ptr.getNext();
        }
 
 
        newNode.setNext(ptr.getNext());//insert new node after ptr
        ptr.setNext(newNode);
    }//end of insertByName
 

    /**
     * 
     * Removes ALL eliminated students from musical chairs and inserts those
     * students
     * back in studentsInLine in ascending name order (earliest to latest).
     * 
     * At the end of this method, all students will be in studentsInLine besides
     * the winner, who will only be in musicalChairs.
     * 
     * 1. Find the number of students currently in musicalChairs
     * 2. While there's more than 1 student in musicalChairs, call
     * moveRandomStudentFromChairsToLine()
     * --> pass the size from step (1) into the method call.
     */
    public void eliminateLosingStudents() {
        
        // get size 
        int size = 0;
        if (musicalChairs != null) {
            SNode curr = musicalChairs; 
            SNode ptr = curr.getNext();
            while (ptr != musicalChairs) {
                size++; // increment as we traverse list 
                ptr = ptr.getNext(); // move the ptr to the next node in the CLL 
            }
            size++;//for final student  
        }

        // eliminate students, only winner should remain
        while (size > 1) {
            moveStudentFromChairsToLine(size);
            size--; 
        }


} //end of elimateLosingStudents


    /*
     * If musicalChairs (CLL) contains ONLY ONE student (the winner),
     * this method removes the winner from musicalChairs and inserts that student
     * into the first available seat in studentsInSeats. musicalChairs will then be
     * empty.
     * 
     * ASSUME eliminateLosingStudents is called before this method is.
     * 
     * NOTE: This method does nothing if there is more than one student in
     * musicalChairs
     * OR if musicalChairs is empty.
     */
    public void seatMusicalChairsWinner() {
        if (musicalChairs == null || musicalChairs.getNext() != musicalChairs) {//if no winner
            return;
        }
 
 
        Student winner = musicalChairs.getStudent();//retrieve winner
        musicalChairs = null; // then remove winner
 
 
        for (int r = 0; r < studentsInSeats.length; r++) {
            for (int c = 0; c < studentsInSeats[0].length; c++) {
                if (openSeats[r][c] == true  && studentsInSeats[r][c] == null) {//if there is a seat where no one is seated
                    studentsInSeats[r][c] = winner;//seat winner and exit method
                    return;
                }
            }//end of inner for
        }//end of outer for
    }//end of seatMusicalChairsWinner method
 

    /**
     * 
     * This method simulates the game of Msical Chairs!
     * 
     * This method calls previously-written methods to remove students from
     * musicalChairs until there is only one student (the winner), then seats the
     * winner
     * and then seat the students from the studentsInline.
     * 
     * DO NOT UPDATE THIS METHOD
     */
    public void playMusicalChairs() {
        eliminateLosingStudents();
        seatMusicalChairsWinner();
        seatStudents();
    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine() {

        // Print studentsInLine
        StdOut.println("Students in Line:");
        if (studentsInLine == null) {
            StdOut.println("EMPTY");
        }

        for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print());
            if (ptr.getNext() != null) {
                StdOut.print(" -> ");
            }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents() {

        StdOut.println("Sitting Students:");

        if (studentsInSeats != null) {

            for (int i = 0; i < studentsInSeats.length; i++) {
                for (int j = 0; j < studentsInSeats[i].length; j++) {

                    String stringToPrint = "";
                    if (studentsInSeats[i][j] == null) {

                        if (openSeats[i][j] == false) {
                            stringToPrint = "X";
                        } else {
                            stringToPrint = "EMPTY";
                        }

                    } else {
                        stringToPrint = studentsInSeats[i][j].print();
                    }

                    StdOut.print(stringToPrint);

                    for (int o = 0; o < (10 - stringToPrint.length()); o++) {
                        StdOut.print(" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs() {
        StdOut.println("Students in Musical Chairs:");

        if (musicalChairs == null) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if (ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() {
        return studentsInLine;
    }

    public void setStudentsInLine(SNode l) {
        studentsInLine = l;
    }

    public SNode getMusicalChairs() {
        return musicalChairs;
    }

    public void setMusicalChairs(SNode m) {
        musicalChairs = m;
    }

    public boolean[][] getOpenSeats() {
        return openSeats;
    }

    public void setOpenSeats(boolean[][] a) {
        openSeats = a;
    }

    public Student[][] getStudentsInSeats() {
        return studentsInSeats;
    }

    public void setStudentsInSeats(Student[][] s) {
        studentsInSeats = s;
    }

}
