import org.jfugue.pattern.Pattern;

import java.util.ArrayList;

public class MelodyAutomaton_2 {
    String Strinput="";
    Pattern MyPattern;
    RhytmAutomaton_2 rhytmAutomaton;
    //String[] melodyNotes = {"C3","D3","E3","F3","G3","A3","B3","C4","D4","E4","F4","G4","A4","B4","C5","D5","E5","F5","G5","A5","B5","C6","D6","E6","F6","G6","A6","B6","C7","D7","E7","F7","G7","A7","B7"};
     Integer[] melodyNotes;
     Integer[] firstChord;
     Integer[] secondChord;
     Integer[] thirdChord;
     Integer[] fourthChord;
     Integer[] MiddleNote;
     Integer[] LastNote;

    /*String[] CmajChord = {"G4","C5","E5","G5"};
    String[] GmajChord = {"B4","G5","B5","D5"};
    String[] AminChord = {"C5","E5","A5","C6"};
    String[] FmajChord = {"A4","C5","F5","A5"};
    String[] MiddleNote = {"B4","E5","G5","B5"};
    String[] LastNote = {"C5","C6"};*/

    ArrayList<String[]> rhytmAtBeat;
    ArrayList<String> measure1=new ArrayList<String>();
    ArrayList<String> measure2=new ArrayList<String>();
    String ControlString="";
    int previousNotePosition=0;
    Integer previousNote=0;
    int beatCounter = 0;

    public MelodyAutomaton_2(String strinput, Pattern myPattern, RhytmAutomaton_2 rhytmAutomaton, Adder adder) {
        Strinput = strinput;
        MyPattern=myPattern;
        this.rhytmAutomaton = rhytmAutomaton;
        rhytmAtBeat = this.rhytmAutomaton.getRhytmAtBeat();
        melodyNotes= adder.getKeyNotes();   //todo      debug
        ArrayList<Integer[]> framenotes=adder.getFrameNotes();
        ArrayList<Integer[]> chordnotes=adder.getKeyChords();
        firstChord=chordnotes.get(0);
        secondChord=chordnotes.get(1);
        thirdChord=chordnotes.get(2);
        fourthChord=chordnotes.get(3);
        MiddleNote=framenotes.get(0);
        LastNote=framenotes.get(1);

    }

public void createMelody(){

        addMelody(8,firstChord);

        //todo ///////////////////////////////////////////////////////////////

    addCopiedMeasure(measure1,firstChord,secondChord);


    //todo ///////////////////////////////////////////////////////////////

        addMelody(14,secondChord);

        //todo ///////////////////////////////////////////////////////////////

    addFrameNote(MiddleNote);

    //todo ///////////////////////////////////////////////////////////////-------------------------
    addMelody(24,thirdChord);

    //todo ///////////////////////////////////////////////////////////////
    addCopiedMeasure(measure2,thirdChord,fourthChord);

    //todo ///////////////////////////////////////////////////////////////
    addMelody(30,fourthChord);

    //todo ///////////////////////////////////////////////////////////////
    addFrameNote(LastNote);

    System.out.println(getControlString());
}

private void addMelody(int b, Integer[] playedChord){
        while(beatCounter<b){
            for (int i = 0; i < rhytmAtBeat.get(beatCounter).length;i++) {
                if (beatCounter % 2 == 0&&i==0) {

                    Integer choice = Integer.parseInt(Strinput.substring(0, 2), 2);

                    this.Strinput = Strinput.substring(2);
                    MyPattern.add(playedChord[choice] + rhytmAtBeat.get(beatCounter)[i] + " ");
                    ControlString+=playedChord[choice] + rhytmAtBeat.get(beatCounter)[i] + " ";
                    System.out.println("chord note: " + playedChord[choice]+" choice "+choice );             //todo debug
                    previousNote=playedChord[choice];
                    if(beatCounter<4){
                        measure1.add(playedChord[choice]+rhytmAtBeat.get(beatCounter)[i]);
                    }else if(beatCounter>15&&beatCounter<20){
                        measure2.add(playedChord[choice]+rhytmAtBeat.get(beatCounter)[i]);
                    }

                }else{
                    if((!rhytmAtBeat.get(beatCounter)[i].equals("Ri"))&&(!rhytmAtBeat.get(beatCounter)[i].equals("Rs"))){
                        boolean ascend=this.isMelodyAscending();
                        //TODO ROZHODNUTIE STEP 0 ALEBO LEAP 1
                        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
                        this.Strinput = Strinput.substring(1);
                        System.out.println("step/leap "+choice);                                            //todo DEBUG
                        if(choice==0){
                            //TODO ADD STEP
                            this.addStep(ascend, beatCounter, i );
                        }
                        else{
                            //TODO ADD LEAP
                            this.addLeap(ascend,beatCounter, i);
                        }
                    }else{      //TODO ADD REST
                        MyPattern.add(rhytmAtBeat.get(beatCounter)[i]+" ");
                        ControlString+=rhytmAtBeat.get(beatCounter)[i]+" ";
                        System.out.println("add rest "+rhytmAtBeat.get(beatCounter)[i]);                        //todo debug

                        if(beatCounter<4){
                            measure1.add(rhytmAtBeat.get(beatCounter)[i]);
                        }else if(beatCounter>15&&beatCounter<20){
                            measure2.add(rhytmAtBeat.get(beatCounter)[i]);
                        }
                    }
                }
            }
            if((beatCounter+1)%4==0){
                MyPattern.add(" | ");
                ControlString += " | ";
            }
            beatCounter++;
        }

}
    private boolean isMelodyAscending(){
        boolean ascend=false;

        for(int j =0; j < melodyNotes.length; j++){

            if(melodyNotes[j]<55)
                ascend=true;
            else if(melodyNotes[j]>79)
                ascend=false;

            if(melodyNotes[j].equals(previousNote)){
                previousNotePosition=j;
                break;
            }
        }

        if(previousNote>=55&&previousNote<=79){
            Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
            //todo                      REMOVE 1 DIGITS
            this.Strinput = Strinput.substring(1);
            if(choice==0){
                ascend=false;
            }else{
                ascend=true;
            }
            System.out.println("melody in middle range "+choice);                           //todo debug
        }
        System.out.println("ASCEND ?? "+ascend);
        return  ascend;
    }


    private void addStep(boolean ascend, int beatCounter,int i ){
        int c=0;
        //todo zero step ??
//        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
//        this.Strinput = Strinput.substring(1);
//        if(choice==0){
//            c=0;
//            System.out.println("add  a zero step");
//        }
//        else{
        if(ascend){
            c=1;
        }else {
            c=-1;
        }
        //}
        MyPattern.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ");
        previousNote=melodyNotes[previousNotePosition+c];
        ControlString+=melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ";
        System.out.println("add a step "+c+" "+melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ");       //todo debug

        if(beatCounter<4){
            measure1.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
        }else if(beatCounter>15&&beatCounter<20){
            measure2.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
        }
    }


    private void addLeap(boolean ascend, int beatCounter, int i){
        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
        this.Strinput = Strinput.substring(1);

        int c;
        if(ascend){
            c=2+choice;
        }else {
            c=-2-choice;
        }

        MyPattern.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ");
        previousNote=melodyNotes[previousNotePosition+c];
        ControlString+=melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ";
        System.out.println("add a leap "+c+" "+melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ");       //todo debug

        if(beatCounter<4){
            measure1.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
        }else if(beatCounter>15&&beatCounter<20){
            measure2.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
        }
    }

    private void addCopiedMeasure(ArrayList<String> theMeasure,Integer[] previousChord, Integer[] playedChord) {
        int c=0;
        int a=0;
        int b=0;
        int x0=0;
        int x1=0;
        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
        this.Strinput = Strinput.substring(1);
        //todo


        for (int i=0;i<melodyNotes.length;i++){
        if(melodyNotes[i]==playedChord[1])
            a=i;
        if(melodyNotes[i]==previousChord[1])
            b=i;
        }
        if(a<b){
            x0=a-b;
            x1=7+x1;
        }else{
            x0=a-b;
            x1=x0-7;
        }
        if(choice==0)
            c=x0;
            else
            c=x1;


        for(int i = 0; i < theMeasure.size();i++){
            if(!theMeasure.get(i).equals("Ri")&&!theMeasure.get(i).equals("Rs")){
                for(int j =0; j < melodyNotes.length; j++){
                    if(melodyNotes[j].toString().equals(theMeasure.get(i).substring(0,2)) ){
                        previousNotePosition=j;
                        break;
                    }
                }
                MyPattern.add(melodyNotes[previousNotePosition+c]+theMeasure.get(i).substring(2)+" ");
                ControlString+=melodyNotes[previousNotePosition+c]+theMeasure.get(i).substring(2)+" ";
            }
            else{
                MyPattern.add(theMeasure.get(i)+" ");
                ControlString +=theMeasure.get(i)+" ";
            }
        }
        beatCounter+=4;
        MyPattern.add("| ");
        ControlString+="| ";

    }

    public void addFrameNote(Integer [] frameNotes){

        Integer choice=0;
        if(frameNotes.length==4) {
             choice = Integer.parseInt(Strinput.substring(0, 2), 2);
            this.Strinput = Strinput.substring(2);
        }else{
             choice = Integer.parseInt(Strinput.substring(0, 1), 2);
            this.Strinput = Strinput.substring(1);
        }


        Integer frameNote=frameNotes[choice];
        System.out.println("my frame note is "+frameNote);                      //todo DEbug
        int c,mnPos=0;

        for(int j =0; j < melodyNotes.length; j++){
            if(melodyNotes[j].equals(previousNote)){
                previousNotePosition=j;

            }else if(melodyNotes[j].equals(frameNote)){
                mnPos=j;
            }
        }
        if(previousNotePosition>mnPos)
            c=-1;
        else
            c=+1;

        for (int i=0;i<rhytmAtBeat.get(beatCounter).length;i++){
            if((!rhytmAtBeat.get(beatCounter)[i].equals("Ri"))&&(!rhytmAtBeat.get(beatCounter)[i].equals("Rs"))) {
                MyPattern.add(melodyNotes[previousNotePosition + c] + rhytmAtBeat.get(beatCounter)[i] + " ");
                ControlString += melodyNotes[previousNotePosition + c] + rhytmAtBeat.get(beatCounter)[i] + " ";
                previousNotePosition += c;
            }else{
                MyPattern.add(rhytmAtBeat.get(beatCounter)[i]+" ");
                ControlString+=rhytmAtBeat.get(beatCounter)[i]+" ";
            }
        }
        beatCounter++;
        MyPattern.add(frameNote + rhytmAtBeat.get(beatCounter)[0] + " ");
        ControlString+=frameNote + rhytmAtBeat.get(beatCounter)[0] + " ";
        beatCounter++;
        MyPattern.add("| ");
        ControlString+="| \n";

    }


    public String getControlString() {
        return ControlString;
    }

    public String getStrinput() {
        return Strinput;
    }
}
