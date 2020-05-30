import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CompositionModule {

    String Strinput;

    public CompositionModule() {
    }

    public void  compositionModule(String Strinput){
        this.Strinput = Strinput;
        Pattern mainPattern = new Pattern();

        //todo   Choosing key by tonic value + choosing chord progression with permutation 0=I 1=V 2=VI 3=IV
        Adder adder = new Adder();
        //System.out.println("bity na zaciatku "+Strinput);
        adder.createHarmony(Strinput);
        Strinput=adder.getStrinput();

        //todo Creating rhytm

        RhytmAutomaton_2 rhytmAutomaton = new RhytmAutomaton_2(Strinput);
        rhytmAutomaton.createRhytm();
        Strinput = rhytmAutomaton.getStrinput();

        //todo Formated Console output

        ArrayList<String[]> s = rhytmAutomaton.getRhytmAtBeat();
        int c = 1;
        for (int j = 0; j < s.size(); j++) {
            for (int i = 0; i < s.get(j).length; i++) {
                System.out.print(s.get(j)[i]);
            }
            System.out.print(" ");


            if (c % 4 == 0) {
                System.out.print("|");
            }
            c++;
        }
        System.out.println("\n");

        //todo Melody generating

        MelodyAutomaton_2 melodyAutomaton = new MelodyAutomaton_2(Strinput, mainPattern, rhytmAutomaton, adder);
        melodyAutomaton.createMelody();
        Strinput = melodyAutomaton.getStrinput();
        System.out.println(mainPattern.toString());
        System.out.println("toto zostalo z inputu "+Strinput);

        System.out.println("zakodovali sme dokopy " + (200 - Strinput.length()));
        //controlString = adder.codedString(200 - Strinput.length());
        // System.out.println("tieto bity sme zakodovali do rymtu a melodie "+controlString);

        //                         TODO SAVES PATTERN TO DESKTOP


        try {
            MidiFileManager
                    .savePatternToMidi(mainPattern, new File(/*"C:\\Users\\chcem w7\\Desktop\\*/"miusik.mid"));
        } catch (IOException ex) {
            System.out.println("Unable to create file");
        }

    }


    public String decompositionModule(){
        String finalString="";
        Adder adder = new Adder();

        //todo                      LOAD PATTERN FROM DESKTOP

        String BinaryOutput="";
        Pattern loadedPattern=null;
        Pattern translatedPattern=new Pattern();
        try {

            loadedPattern=MidiFileManager.loadPatternFromMidi(new File(/*"C:\\Users\\chcem w7\\Desktop\\*/"miusik.mid"));


        } catch (Exception e) {
            System.out.println("Unable to create file");
        }

        System.out.println(loadedPattern.getPattern());
        translatedPattern.add(adder.translateNotes(loadedPattern.getPattern().toString()));
        finalString=adder.getBinaryOutput();
        //controlString=finalString+controlString;
        System.out.println(finalString);
        System.out.println(translatedPattern.toString());
        RhytmDecoder rhytmDecoder = new RhytmDecoder(translatedPattern.toString(),BinaryOutput);
        rhytmDecoder.decodeRhytm();
        System.out.println(rhytmDecoder.getBinaryOutput());
        finalString+=rhytmDecoder.getBinaryOutput();

        MelodyDecoder melodyDecoder = new MelodyDecoder(translatedPattern.toString(),BinaryOutput,rhytmDecoder.getGroupedParsedRhytm(),adder);
        melodyDecoder.decodeMelody();
        finalString+=melodyDecoder.getBinaryOutput();

        return finalString;
    }








}
