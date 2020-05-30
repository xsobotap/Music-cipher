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
    String binaryOutput="";


    int[] possibleRootValues={0,1,2,4,5,7,9,11};
    int[][] possibleChordProgressions={{0,1,2,3},
            {0,1,3,2},
            {0,2,1,3},
            {0,2,3,1},
            {0,3,1,2},
            {0,3,2,1},
            {1,0,2,3},
            {1,0,3,2},
            {1,2,0,3},
            {1,2,3,0},
            {1,3,0,2},
            {1,3,2,0},
            {2,0,1,3},
            {2,0,3,1},
            {2,1,0,3},
            {2,1,3,0}};

    int root=0;
    int[] chordProgression=new int[4];


    public Adder() {


    }
    public void setHarmony(int rootChoice, int progressionChoice){
        this.root=possibleRootValues[rootChoice];
        this.chordProgression=possibleChordProgressions[progressionChoice];

    }
    public void createHarmony(String strinput){
        this.Strinput=strinput;

        //TODO                                      GNERATING KEY IN WHICH THE SONG IS GOING TO BE

        Integer choice = Integer.parseInt(Strinput.substring(0,3),2);
        this.Strinput = Strinput.substring(3);

        System.out.println("vzbral som toninu"+possibleRootValues[choice]);
        this.root=possibleRootValues[choice];


        //TODO                                      GNERATING CHORD PROGRESSION OF THE SONG
        choice = Integer.parseInt(Strinput.substring(0,4),2);
        this.Strinput = Strinput.substring(4);
        System.out.println("vzbral som chord progression"+possibleChordProgressions[choice][0]+possibleChordProgressions[choice][1]+possibleChordProgressions[choice][2]+possibleChordProgressions[choice][3]);
        this.chordProgression=possibleChordProgressions[choice];
       // root =0;
        //chordProgression = possibleChordProgressions[0];

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

       /* keyChords.set(chordProgression[0],fc);
        keyChords.set(chordProgression[1],sc);
        keyChords.set(chordProgression[2],tc);
        keyChords.set(chordProgression[3],foc);*/
       //2031
       for(int i = 0; i < 4;i++){
           if(chordProgression[i]==0)
               keyChords.set(i,fc);
           if(chordProgression[i]==1)
               keyChords.set(i,sc);
           if(chordProgression[i]==2)
               keyChords.set(i,tc);
           if(chordProgression[i]==3)
               keyChords.set(i,foc);
       }

     return  keyChords;
    }

    //todo                                              THIS METHOD TRANSLATES PARSED MUSIC STRING IN LETTERS TO
    //todo                                              NUMBER VALUES REPRESENTING EACH NOTE, extracts tonic and chord progression

    public String translateNotes(String Strinput){

        int[][] fieldOfChords = new int[4][3];
        int iCounter=0;
        int jCounter=0;
        Strinput = Strinput.substring(3);   // remove V0 in the beggining
        String Stroutput="";
        boolean voiceOne = false;
        StringTokenizer st1 =
                new StringTokenizer(Strinput, " ");
        while (st1.hasMoreTokens()){
            String temp = st1.nextToken();
            if("V1".equals(temp)){
                voiceOne=true;          //here begins the voice of base line, so different approach to parsing
                continue;
            }
            if(!voiceOne){
            String compare;
            if(temp.equals("Ri")||temp.equals("Rs")){
                Stroutput+=temp + " ";
                continue;  // temp equals rest, so no other note will be tested
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
            }else{
                if(temp.charAt(0)=='@'){            //removing unnecessary symbols
                    continue;
                }
                String compare;
                for(int i = 0; i< temp.length();i++){         // extracting chords from base line
                    if(Character.isDigit(temp.charAt(i))){
                        compare = temp.substring(0,i+1);
                        for(int j =0; j<melodyNotes.length; j++){
                            if(compare.equals(melodyNotes[j])){
                                fieldOfChords[iCounter][jCounter]=j;
                                if (jCounter==2){
                                    jCounter=0;
                                    iCounter++;
                                }else{
                                    jCounter++;
                                }
                            }
                        }

                    }
                }

            }
        }
        int tonic = this.getTonicNote(Stroutput);
        this.root = tonic;
        int[] possibleChords = new int[4];

        if(tonic<7){
             possibleChords[0]=tonic+ 60;
            possibleChords[1]=tonic+67;
            possibleChords[2]=tonic+57;
            possibleChords[3]=tonic+65;
        }else{
            possibleChords[0]=tonic+60-12;
            possibleChords[1]=tonic +67-12;
            possibleChords[2]=tonic +57-12;
            possibleChords[3]=tonic +65-12;
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j<4;j++)
            {
                if(fieldOfChords[i][0]+12==possibleChords[j]){
                    this.chordProgression[i]=j;
                }

            }

        }
        // todo to binarz string possible root values + possible chord progressions

        for(int i = 0; i<possibleRootValues.length;i++){
            if (possibleRootValues[i]==root){
                if(i<2)
                    binaryOutput+="00";
                else if(i<4)
                    binaryOutput+="0";
                binaryOutput+=Integer.toBinaryString(i);
            break;
            }
        }
        boolean find;
        for(int i = 0; i< possibleChordProgressions.length; i++) {
            find = true;
            for (int j = 0; j < 4; j++) {
                if (this.chordProgression[j] != this.possibleChordProgressions[i][j]){
                    find = false;
                }
            }
            if(find){
                if(i<2)
                    binaryOutput+="000";
                else if(i<4)
                    binaryOutput+="00";
                else if(i<8)
                    binaryOutput+="0";
                binaryOutput+=Integer.toBinaryString(i);
                break;
            }
        }


        return Stroutput;
    }



    //todo                                              extracts tonic
    int getTonicNote(String Strinput){
        String lastnote="";
        int result;
        lastnote += Strinput.toCharArray()[Strinput.length()-4];
        lastnote += Strinput.toCharArray()[Strinput.length()-3];
        result = Integer.parseInt(lastnote);
        if(result>=60 && result <=66)
            return result-60;
        else if(result>=72&&result<=78)
            return result-72;
        else if(result>=55&&result<=59)
            return result-48;
        else if(result>=67&&result<=71)
            return result-60;
        return -1;
    }


    //todo                                              GENERATES RANDOM BIBNARY STRING
    public String binNumber() {
        String s="";
        for(int i=0;i<200;i++){
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

    public String getStrinput() {
        return Strinput;
    }

    public String getBinaryOutput() {
        return binaryOutput;
    }
}
