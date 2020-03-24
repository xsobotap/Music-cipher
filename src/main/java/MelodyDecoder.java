import org.jfugue.pattern.Pattern;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

public class MelodyDecoder {
    String Strinput="";
    String binaryOutput;

    Adder adder;
    Integer[] melodyNotes;
    Integer[] firstChord;
    Integer[] secondChord;
    Integer[] thirdChord;
    Integer[] fourthChord;
    Integer[] MiddleNote;
    Integer[] LastNote;
    Integer firstChordNote;
    Integer secondChordNote;
//    ArrayList<Integer> decimalOutput=new ArrayList<Integer>();

    ArrayList<String[]> rhytm;
    ArrayList<Integer> melody=new ArrayList<Integer>();
    int melodyCounter = 0;


    int previousNotePosition=0;
    Integer previousNote=0;
    int beatCounter = 0;

    public MelodyDecoder(String strinput, String binaryOutput, ArrayList<String[]> parsedrhytm) {
        Strinput = strinput;
        this.binaryOutput=binaryOutput;
        this.rhytm=parsedrhytm;

    }


    //todo                              MAIN METHOD, CHRONOLOGICALY CALLS ALL METHODS
    public void decodeMelody(){
         this.adder=new Adder(this.getTonicNote(),new int[]{0,1,2,3});

        melodyNotes= adder.getKeyNotes();   //todo      debug
        ArrayList<Integer[]> framenotes=adder.getFrameNotes();
        ArrayList<Integer[]> chordnotes=adder.getKeyChords();
        firstChord=chordnotes.get(0);
        secondChord=chordnotes.get(1);
        thirdChord=chordnotes.get(2);
        fourthChord=chordnotes.get(3);
        MiddleNote=framenotes.get(0);
        LastNote=framenotes.get(1);
        this.tokenizeAndParse();
        //01000101000000011100100100100110011101101110101110001010010101100101100101110
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.mainDecoder(8,firstChord);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.copiedMeasureDecoder(firstChordNote,firstChord,secondChord);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.mainDecoder(14,secondChord);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.frameNoteDecoder(MiddleNote);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.mainDecoder(24,thirdChord);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.copiedMeasureDecoder(secondChordNote ,thirdChord,fourthChord);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.mainDecoder(30,fourthChord);
        //todo XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.frameNoteDecoder(LastNote);
    }
//TODO                                  PARSES LAST NOTE FROM MELODY AND RETURNS THE VALUE OF TONIC NOTE FOR GENERATING MELODYNOTES
    int getTonicNote(){
        String lastnote="";
        int result;
        lastnote += Strinput.toCharArray()[Strinput.length()-4];
        lastnote += Strinput.toCharArray()[Strinput.length()-3];
        result = Integer.parseInt(lastnote);
        if(result<72){
            return result-60;
        }else{
            return result-12-60;
        }
    }

//TODO                                      DECODES MAIN MELODY AUTOMATON - CHANGING PITCH ASCENDING + STEPS/LEAPS CHOICES
   public void mainDecoder(int b, Integer[] playedChord){

        while(beatCounter<b){
            for(int i =0; i < rhytm.get(beatCounter).length;i++)
            {
                if(rhytm.get(beatCounter)[i]!=null) {
                    if ((!rhytm.get(beatCounter)[i].equals("Ri")) && (!rhytm.get(beatCounter)[i].equals("Rs"))) {

                        //TODO                                   DECODE HARMONIC NOTE AT EVERY FIRST AND THIRD BEAT AT FIRST NOTE PLAYED
                        if (beatCounter % 2 == 0 && i == 0) {
                            for (int j = 0; j < playedChord.length; j++) {
                                if (playedChord[j] == melody.get(melodyCounter)) {
                                    if(j<2){
                                        binaryOutput+="0";
                                        binaryOutput+=Integer.toBinaryString(j);
                                    }else{
                                        binaryOutput+=Integer.toBinaryString(j);
                                    }
                                    //todo      SAVE FOR LATER COMPARISON
                                    if(beatCounter==0)
                                        firstChordNote=melody.get(melodyCounter);
                                    if(beatCounter==16)
                                        secondChordNote=melody.get(melodyCounter);

                                    previousNote = melody.get(melodyCounter);
                                    melodyCounter++;
                                    break;
                                }
                            }
                            //TODO                              DECODE EVERY OTHER NOTE, ENCODED WITH AUTOMATON
                        } else {
                            //// TODO                           WAS MELODY ASCENDING OR DESCENDING
                            if (previousNote >= 55 && previousNote <= 79)
                                if (previousNote < melody.get(melodyCounter))
                                    binaryOutput+="1";
                                else
                                    binaryOutput+="0";

                            //todo                                 WAS IT STEP OR LEAP
                            for (int j = 0; j < melodyNotes.length; j++) {
                                if (melodyNotes[j].equals(previousNote)) {
                                    previousNotePosition = j;
                                    break;
                                }
                            }
                            for (int j = 0; j < melodyNotes.length; j++) {
                                if (melodyNotes[j].equals(melody.get(melodyCounter))) {
                                    previousNote=melodyNotes[j];
                                    int difference = abs(j - previousNotePosition);
                                    if (difference > 1) {
                                        binaryOutput+="1";
                                        binaryOutput+=new Integer(difference - 2).toString();
                                    } else
                                        binaryOutput+="0";
                                    break;
                                }
                            }
                            melodyCounter++;
                        }
                    }
                }
            }
            beatCounter++;
        }

   }
//TODO                                      DECODES COPIED MEASURES, BY COMPARING PITCHES OF FIRST NOTE OF ORIGINAL AND COPIED
public void copiedMeasureDecoder(Integer chordNote,Integer[]previousChord, Integer[] playedChord){
   /* int c=0;
    int a=0;
    int b=0;
    int x0=0;
    int x1=0;*/
    int d1=0;
    int d2=0;
    //todo


    for (int i=0;i<melodyNotes.length;i++){
        if(melodyNotes[i]==chordNote)
            d1=i;
        if(melodyNotes[i]==melody.get(melodyCounter))
            d2=i;
       /* if(melodyNotes[i]==playedChord[1])
            a=i;
        if(melodyNotes[i]==previousChord[1])
            b=i;*/
    }
    /*if(a<b){
        x0=a-b;
        x1=7+x1;
    }else{
        x0=a-b;
        x1=x0-7;
    }*/
    if(d1>d2)
        binaryOutput+="1";
    else
        binaryOutput+="0";

    for(int i = 0;i<4;i++){
        for(int j =0; j < rhytm.get(beatCounter).length;j++){
            if(rhytm.get(beatCounter)[j]!=null)
                if ((!rhytm.get(beatCounter)[j].equals("Ri")) && (!rhytm.get(beatCounter)[j].equals("Rs"))) {
                    melodyCounter++;
                }
        }
        beatCounter++;
    }


}
//TODO                                      DECODES FRAME NOTES, MIDDLE AND LAST BY SIMPLY COMPARING
public void frameNoteDecoder(Integer[] frameNotes){
    //for(int i = 0;i<1;i++){
        for(int j =0; j < rhytm.get(beatCounter).length;j++){
            if(rhytm.get(beatCounter)[j]!=null)
                if ((!rhytm.get(beatCounter)[j].equals("Ri")) && (!rhytm.get(beatCounter)[j].equals("Rs"))) {
                    melodyCounter++;
                }
        }
        beatCounter++;
    //}

    if(beatCounter==15) {
        for (int i = 0; i < frameNotes.length; i++) {
            if (melody.get(melodyCounter) == frameNotes[i]) {
                if (i < 2) {
                    binaryOutput += "0";
                    binaryOutput += Integer.toBinaryString(i);
                } else {
                    binaryOutput += Integer.toBinaryString(i);
                }
            }
        }
    }
    if(beatCounter==31) {
        for (int i = 0; i < frameNotes.length; i++) {
            if (melody.get(melodyCounter) == frameNotes[i]) {
                    binaryOutput += Integer.toBinaryString(i);
            }
        }
    }
        beatCounter++;
        melodyCounter++;


}
//TODO                                      EXTRACTS ONLY MELODY FROM WHOLE PERIOD
   public void tokenizeAndParse(){
       StringTokenizer st1 = new StringTokenizer(Strinput, " ");
       while (st1.hasMoreTokens()){
           String temp = st1.nextToken();
           if(!temp.equals("Rs")&&!temp.equals("Ri")){
               melody.add(Integer.decode(temp.substring(0,2)));
           }

       }

   }

    public String getBinaryOutput() {
        return binaryOutput;
    }
}


