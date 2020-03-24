import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParserListener;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.Note;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Main {


    public static void main(String[] args) {
        Player player = new Player();
        String controlString;
        String finalString="";
        Pattern mainPattern = new Pattern();
        //todo   Choosing key by tonic value + choosing chord progression with permutation 0=I 1=V 2=VI 3=IV
        Adder adder = new Adder(0,new int[]{0,1,2,3});
        //todo   Generating random binary string
        String Strinput = adder.binNumber();

        System.out.println(Strinput);



        //todo Creating rhytm

        RhytmAutomaton_2 rhytmAutomaton = new RhytmAutomaton_2(Strinput);
        rhytmAutomaton.createRhytm();
        Strinput=rhytmAutomaton.getStrinput();

        //todo Formated Console output

        ArrayList<String[]>  s=rhytmAutomaton.getRhytmAtBeat();
        int c=1;
        for (int j = 0; j<s.size();j++){
            for(int i = 0; i < s.get(j).length; i++){
                System.out.print(s.get(j)[i]);
            }
            System.out.print(" ");


            if(c % 4 ==0){
                System.out.print("|");
            }
            c++;
        }
        System.out.println("\n");

        //todo Creating melody

        MelodyAutomaton_2 melodyAutomaton = new MelodyAutomaton_2(Strinput, mainPattern, rhytmAutomaton,adder);
        melodyAutomaton.createMelody();
        Strinput = melodyAutomaton.getStrinput();
        System.out.println(mainPattern.toString());
        System.out.println(Strinput);

        System.out.println("zakodovali sme dokopy "+(200-Strinput.length()));
        controlString=adder.codedString(200-Strinput.length());
        System.out.println(controlString);

        //
//        for(int i =0; i < 8; i++){
//            mainPattern.add(patternField.get(i).getPattern());
//
//        }
        


        //                         TODO SAVES PATTERN TO DESKTOP


        try {
            MidiFileManager
                    .savePatternToMidi(mainPattern, new File("C:\\Users\\chcem w7\\Desktop\\miusik.mid"));
        } catch (IOException ex) {
            System.out.println("Unable to create file");
        }
        //player.play(mainPattern);

        //todo                      LOAD PATTERN FROM DESKTOP

        String BinaryOutput="";
Pattern loadedPattern=null;
Pattern translatedPattern=new Pattern();
        try {

            loadedPattern=MidiFileManager.loadPatternFromMidi(new File("C:\\Users\\chcem w7\\Desktop\\miusik.mid"));


        } catch (Exception e) {
            System.out.println("Unable to create file");
        }
         translatedPattern.add(adder.translateNotes(loadedPattern.getPattern().toString()));
        System.out.println(translatedPattern.toString());
        RhytmDecoder rhytmDecoder = new RhytmDecoder(translatedPattern.toString(),BinaryOutput);
        rhytmDecoder.decodeRhytm();
        System.out.println(rhytmDecoder.getBinaryOutput());
        finalString+=rhytmDecoder.getBinaryOutput();

        MelodyDecoder melodyDecoder = new MelodyDecoder(translatedPattern.toString(),BinaryOutput,rhytmDecoder.getGroupedParsedRhytm());
        melodyDecoder.decodeMelody();
        finalString+=melodyDecoder.getBinaryOutput();

        for(int i =0;i<finalString.length();i++){
            if(finalString.toCharArray()[i]==controlString.toCharArray()[i]){
                System.out.print("\u001B[32m"+finalString.toCharArray()[i]);
            }else{
                System.out.print("\u001B[31m"+finalString.toCharArray()[i]);
            }
        }

    }

}
