import org.jfugue.pattern.Pattern;

import java.util.ArrayList;

public class MelodyAutomaton {
    String Strinput="";
    Pattern MyPattern;
    RhytmAutomaton rhytmAutomaton;
    String[] melodyNotes = {"C3","D3","E3","F3","G3","A3","B3","C4","D4","E4","F4","G4","A4","B4","C5","D5","E5","F5","G5","A5","B5","C6","D6","E6","F6","G6","A6","B6","C7","D7","E7","F7","G7","A7","B7"};
    ArrayList<String[]> rhytmAtBeat;
    ArrayList<String> measure1=new ArrayList<String>();
    ArrayList<String> measure2=new ArrayList<String>();
    String ControlString="";
    int previousNotePosition=0;
    String previousNote="";
    int beatCounter = 0;

    public MelodyAutomaton(String strinput, Pattern myPattern, RhytmAutomaton rhytmAutomaton) {
        Strinput = strinput;
        MyPattern=myPattern;
        this.rhytmAutomaton = rhytmAutomaton;
        rhytmAtBeat = this.rhytmAutomaton.getRhytmAtBeat();
    }


    public void createMelody(){
        //todo Adding First Note
        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
        //todo                      REMOVE 1 DIGITS
        this.Strinput = Strinput.substring(1);
        //System.out.println("1st"+Strinput);

        choice = 60 + choice*12;
        if(choice==60){
            previousNote = "C5";
            ControlString += "C5q ";
            measure1.add("C5"+rhytmAtBeat.get(beatCounter)[0]);
        }else{
            previousNote = "C6";
            ControlString += "C6q ";
            measure1.add("C6"+rhytmAtBeat.get(beatCounter)[0]);
        }

        //// TODO:         "C5"/"C6"                              "q"
        MyPattern.add(choice.toString()+rhytmAtBeat.get(beatCounter)[0]+" ");

        beatCounter++;

// todo ///////////////////////////////////////////////////////////////////////////////

        this.melodyAdder(8);

// todo ///////////////////////////////////////////////////////////////////////////////


        this.addCopiedMeasure(measure1);
        beatCounter+=4;
        MyPattern.add("| ");
        ControlString+="| ";


// todo ///////////////////////////////////////////////////////////////////////////////
        this.melodyAdder(14);
// todo ///////////////////////////////////////////////////////////////////////////////


        for(int i = 0 ; i<2;i++){
            for (int j = 0; j < rhytmAtBeat.get(beatCounter).length;j++) {
                MyPattern.add(melodyNotes[previousNotePosition] + rhytmAtBeat.get(beatCounter)[j] + " ");
                ControlString+=melodyNotes[previousNotePosition] + rhytmAtBeat.get(beatCounter)[j] + " ";
            }
            beatCounter++;
        }
        MyPattern.add("| ");
        ControlString+="| \n";

// todo ///////////////////////////////////////////////////////////////////////////////

    this.melodyAdder(24);


// todo ///////////////////////////////////////////////////////////////////////////////
        this.addCopiedMeasure(measure2);
        beatCounter+=4;


        MyPattern.add("| ");
        ControlString+="| ";

        System.out.println(ControlString);
    }


    public String getStrinput() {
        return Strinput;
    }

    private boolean isMelodyAscending(){
        boolean ascend=false;

        for(int j =0; j < melodyNotes.length; j++){

            if(j<11)
                ascend=true;
            else if(j>25)
                ascend=false;

            if(melodyNotes[j].equals(previousNote)){
                previousNotePosition=j;
                break;
            }
        }

        if(previousNotePosition>=11&&previousNotePosition<=25){
            Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
            //todo                      REMOVE 1 DIGITS
            this.Strinput = Strinput.substring(1);
            if(choice==0){
                ascend=false;
            }else{
                ascend=true;
            }
        }

        return  ascend;
    }

    private void addStep(boolean ascend, int beatCounter,int i ){
        int c;
        if(ascend){
            c=1;
        }else {
            c=-1;
        }
            MyPattern.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ");
            previousNote=melodyNotes[previousNotePosition+c];
            ControlString+=melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ";

            if(beatCounter<4){
                measure1.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
            }else if(beatCounter>15&&beatCounter<20){
                measure2.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
            }
    }

    private void addLeap(boolean ascend, int beatCounter, int i){
        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
        this.Strinput = Strinput.substring(1);
        //System.out.println("leap?"+Strinput);
        int c;
        if(ascend){
            c=2+choice;
        }else {
            c=-2-choice;
        }

            MyPattern.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ");
            previousNote=melodyNotes[previousNotePosition+c];
            ControlString+=melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]+" ";


            if(beatCounter<4){
                measure1.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
            }else if(beatCounter>15&&beatCounter<20){
                measure2.add(melodyNotes[previousNotePosition+c]+rhytmAtBeat.get(beatCounter)[i]);
            }
    }

    private void addCopiedMeasure(ArrayList<String> theMeasure){
        int c;
        Integer choice = Integer.parseInt(Strinput.substring(0,3),2);
        this.Strinput = Strinput.substring(3);
        //todo                  0,1,2,3 // -1 -2 -3 -4
        if(choice<4){
        c=+1+choice;
        }else{  //TODO      4,5,6,7 // +1 +2 +3 +4
        c=-1-choice;
        }
            for(int i = 0; i < theMeasure.size();i++){
                if(!theMeasure.get(i).equals("Ri")&&!theMeasure.get(i).equals("Rs")){
                    for(int j =0; j < melodyNotes.length; j++){
                        if(melodyNotes[j].equals(theMeasure.get(i).substring(0,2)) ){
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
    }

    private void melodyAdder(int b){
        while (beatCounter<b){
            for (int i = 0; i < rhytmAtBeat.get(beatCounter).length;i++){
                // todo PRIDAVANIE MELODIE SA DEJE LEN AK V RYTME NIE JE POMLCKA / REST         preskakuje podmienku
                String Ri="Ri";
                String Rs="Rs";
                if((!rhytmAtBeat.get(beatCounter)[i].equals(Ri))&&(!rhytmAtBeat.get(beatCounter)[i].equals(Rs))){

                    //todo           ROZHODNUTIE KLESA VS STUPA
                    boolean ascend=this.isMelodyAscending();
                    //TODO ROZHODNUTIE STEP 0 ALEBO LEAP 1
                    Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
                    this.Strinput = Strinput.substring(1);
                    // System.out.println("s or l"+Strinput);

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

                    if(beatCounter<4){
                        measure1.add(rhytmAtBeat.get(beatCounter)[i]);
                    }else if(beatCounter>15&&beatCounter<20){
                        measure2.add(rhytmAtBeat.get(beatCounter)[i]);
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


}
