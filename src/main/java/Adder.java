import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class Adder {
    String[] melodyNotes = {"C0","C#0","D0", "Eb0", "E0" ,"F0", "F#0", "G0", "G#0", "A0", "Bb0", "B0",
            "C1","C#1","D1", "Eb1", "E1" ,"F1", "F#1", "G1", "G#1", "A1", "Bb1", "B1",
            "C2","C#2","D2", "Eb2", "E2" ,"F2", "F#2", "G2", "G#2", "A2", "Bb2", "B2",
            "C3","C#3","D3", "Eb3", "E3" ,"F3", "F#3", "G3", "G#3", "A3", "Bb3", "B3",
            "C4","C#4","D4", "Eb4", "E4" ,"F4", "F#4", "G4", "G#4", "A4", "Bb4", "B4",
            "C5","C#5","D5", "Eb5", "E5" ,"F5", "F#5", "G5", "G#5", "A5", "Bb5", "B5",
            "C6","C#6","D6", "Eb6", "E6" ,"F6", "F#6", "G6", "G#6", "A6", "Bb6", "B6",
            "C7","C#7","D7", "Eb7", "E7" ,"F7", "F#7", "G7", "G#7", "A7", "Bb7", "B7",
            "C8","C#8","D8", "Eb8", "E8" ,"F8", "F#8", "G8", "G#8", "A8", "Bb8", "B8"};
    String Strinput;
    String previousNote;
    int phrase1Counter=0;
    int phrase2Counter=0;
    int root;
    int[] chordProgression;
    ArrayList<MyPattern> patternField= new ArrayList<MyPattern>();

    public Adder(int rootValue,int[] chordProgression) {
        this.root=rootValue;
        this.chordProgression=chordProgression;
    }

    //todo                                              THIS METHOD CALCULATE 4 DIFFERENT MIDDLE NOTE VALUES
    //todo                                              AND 2 LAST NOTE VALUES BASED BY TONIC ROOT NOTE OF
    //todo                                              OUR CHOOSING


    ArrayList<Integer[]> getFrameNotes(){
        int rootValue = root;
        ArrayList<Integer []> frameNotes=new ArrayList<Integer[]>();
        ArrayList<Integer> MiddleNote=new ArrayList<Integer>();
        ArrayList<Integer> LastNote=new ArrayList<Integer>();
        if(rootValue<7) {
            MiddleNote.add(rootValue+59);
            MiddleNote.add(rootValue+64);
            MiddleNote.add(rootValue+67);
            MiddleNote.add(rootValue+71);
            LastNote.add(rootValue + 60);
            LastNote.add(rootValue + 72);
        }else{
            MiddleNote.add(rootValue+47);
            MiddleNote.add(rootValue+52);
            MiddleNote.add(rootValue+55);
            MiddleNote.add(rootValue+59);
            LastNote.add(rootValue + 48);
            LastNote.add(rootValue + 60);
        }

        Integer[] ln = new Integer[2];
        Integer[] mm = new Integer[4];

        for (int i =0; i < 4;i++){
            mm[i]=MiddleNote.get(i);
        }
        ln[0]=LastNote.get(0);
        ln[1]=LastNote.get(1);
            frameNotes.add(mm);
            frameNotes.add(ln);
        return  frameNotes;
    }

    //todo                                              THIS METHOD IS USED FOR GENERATING ALL NOTES IN PARTICULAR KEY
    //todo                                              BASED ON MODULAR ARITHMETICS OF MAJOR SCALES

    Integer[] getKeyNotes(){
        int rootValue = root;
        ArrayList<Integer> keyNotes=new ArrayList<Integer>();
        keyNotes.add(rootValue);

        for(int i = 0;i<60;i++){
           if(i % 7 ==2||i % 7 == 6){
               keyNotes.add(rootValue+=1);
           }else{
               keyNotes.add(rootValue+=2);
           }

        }
        Integer[]kn=new Integer[keyNotes.size()];
        for(int i =0; i < keyNotes.size();i++){
            kn[i]=keyNotes.get(i);
        }

        return kn;
    }
    //todo                                              GENERATING 4 NOTES IN 4 CHORDS FOR LATER DISTRIBUTION
    //todo                                              IF THE ROOT VALUE IS TOO HIGH, CHORD NOTES ARE
    //todo                                              SHIFTED ONE OCTAVE LOWER
    ArrayList<Integer[]> getKeyChords(){
        int rootValue=root;
        ArrayList<Integer[]> keyChords = new ArrayList<Integer[]>();
        ArrayList<Integer> firstchord=new ArrayList<Integer>();
        ArrayList<Integer> secondChord=new ArrayList<Integer>();
        ArrayList<Integer> thirdChord=new ArrayList<Integer>();
        ArrayList<Integer> fourthChord=new ArrayList<Integer>();
        if(rootValue<7) {
            firstchord.add(rootValue + 55);
            firstchord.add(rootValue + 60);
            firstchord.add(rootValue + 64);
            firstchord.add(rootValue + 67);
            secondChord.add(rootValue + 62);
            secondChord.add(rootValue + 67);
            secondChord.add(rootValue + 71);
            secondChord.add(rootValue + 74);
            thirdChord.add(rootValue+52);
            thirdChord.add(rootValue+57);
            thirdChord.add(rootValue+60);
            thirdChord.add(rootValue+64);
            fourthChord.add(rootValue+ 60);
            fourthChord.add(rootValue+ 65);
            fourthChord.add(rootValue+ 69);
            fourthChord.add(rootValue+72);

        }else{
            /*firstchord.add(rootValue + 43);
            firstchord.add(rootValue + 48);
            firstchord.add(rootValue + 52);
            firstchord.add(rootValue + 55);
            secondChord.add(rootValue +50);
            secondChord.add(rootValue +55);
            secondChord.add(rootValue +59);
            secondChord.add(rootValue +62);
            thirdChord.add(rootValue+52);
            thirdChord.add(rootValue+57);
            thirdChord.add(rootValue+60);
            thirdChord.add(rootValue+64);
            fourthChord.add(rootValue+48);
            fourthChord.add(rootValue+53);
            fourthChord.add(rootValue+57);
            fourthChord.add(rootValue+60);*/
            firstchord.add(rootValue + 55-12);
            firstchord.add(rootValue + 60-12);
            firstchord.add(rootValue + 64-12);
            firstchord.add(rootValue + 67-12);
            secondChord.add(rootValue + 62-12);
            secondChord.add(rootValue + 67-12);
            secondChord.add(rootValue + 71-12);
            secondChord.add(rootValue + 74-12);
            thirdChord.add(rootValue+52-12);
            thirdChord.add(rootValue+57-12);
            thirdChord.add(rootValue+60-12);
            thirdChord.add(rootValue+64-12);
            fourthChord.add(rootValue+ 60-12);
            fourthChord.add(rootValue+ 65-12);
            fourthChord.add(rootValue+ 69-12);
            fourthChord.add(rootValue+72-12);

        }
        Integer[] fc = new Integer[firstchord.size()];
        Integer[] sc = new Integer[firstchord.size()];
        Integer[] tc = new Integer[firstchord.size()];
        Integer[] foc = new Integer[firstchord.size()];
        for (int i = 0; i < 4;i++){
            fc[i]=firstchord.get(i);
            sc[i]=secondChord.get(i);
            tc[i]=thirdChord.get(i);
            foc[i]=fourthChord.get(i);
            keyChords.add(new Integer[0]);
        }
        /*keyChords.add(fc);
        keyChords.add(sc);
        keyChords.add(tc);
        keyChords.add(foc);
        */

        keyChords.set(chordProgression[0],fc);
        keyChords.set(chordProgression[1],sc);
        keyChords.set(chordProgression[2],tc);
        keyChords.set(chordProgression[3],foc);
     return  keyChords;
    }

    //todo                                              THIS METHOD TRANSLATES PARSED MUSIC STRING IN LETTERS TO
    //todo                                              NUMBER VALUES REPRESENTING EACH NOTE

    public String translateNotes(String Strinput){
        Strinput = Strinput.substring(3);
        String Stroutput="";
        StringTokenizer st1 =
                new StringTokenizer(Strinput, " ");
        while (st1.hasMoreTokens()){
            String temp = st1.nextToken();
            String compare;
            if(temp.equals("Ri")||temp.equals("Rs")){
                Stroutput+=temp + " ";
                continue;
            }
            for(int i = 0; i< temp.length();i++){
             if(Character.isDigit(temp.charAt(i))){
                    compare = temp.substring(0,i+1);
                    for(int j =0; j<melodyNotes.length; j++){
                        if(compare.equals(melodyNotes[j]))
                            Stroutput+=j;
                    }
                    Stroutput+=temp.substring(i+1);
             }
            }
            Stroutput+=" ";
        }

        return Stroutput;
    }



    //todo                                              GENERATES RANDOM BIBNARY STRING
    public String binNumber() {
        String s="";
        for(int i=0;i<6*128;i++){
        Random rg = new Random();
        int n = rg.nextInt(2);
        s=s+Integer.toBinaryString(n);
    }
        this.Strinput=s;
        return s;
    }

    public String codedString(int n){
        return Strinput.substring(0,n);
    }









}
