import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParserListener;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.Note;

import javax.xml.bind.SchemaOutputResolver;
import java.awt.event.PaintEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
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


        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        Pattern p1 = new Pattern();
        Pattern p2 = new Pattern();
        Pattern p3 = new Pattern();
        patterns.add(p1);
        patterns.add(p2);
        patterns.add(p3);
        //todo Creating rhytm

        for(int k = 0; k < 3;k++) {

            RhytmAutomaton_2 rhytmAutomaton = new RhytmAutomaton_2(Strinput,k);
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

            //todo Creating melody

            MelodyAutomaton_2 melodyAutomaton = new MelodyAutomaton_2(Strinput, patterns.get(k), rhytmAutomaton, adder);
            melodyAutomaton.createMelody();
            Strinput = melodyAutomaton.getStrinput();
            System.out.println(mainPattern.toString());
            System.out.println(Strinput);

            System.out.println("zakodovali sme dokopy " + (200 - Strinput.length()));
           // controlString = adder.codedString(200 - Strinput.length());
            //System.out.println(controlString);
            patterns.get(k).add("Rw");
        }

        mainPattern.add(patterns.get(0));
        mainPattern.add(patterns.get(1));
        mainPattern.add(patterns.get(2));
        mainPattern.add(patterns.get(1));
        mainPattern.add(patterns.get(2));
        mainPattern.add(patterns.get(0));
        System.out.println("it is correct");
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

       /* String BinaryOutput="";
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
        }*/

    }
    public static String toBitString(final byte[] b) {
        final char[] bits = new char[8 * b.length];
        for(int i = 0; i < b.length; i++) {
            final byte byteval = b[i];
            int bytei = i << 3;
            int mask = 0x1;
            for(int j = 7; j >= 0; j--) {
                final int bitval = byteval & mask;
                if(bitval == 0) {
                    bits[bytei + j] = '0';
                } else {
                    bits[bytei + j] = '1';
                }
                mask <<= 1;
            }
        }
        return String.valueOf(bits);
    }
    /*public static void main(String[] args){

        try {
            AESCypher aes = new AESCypher();
            Adder a = new Adder(0,new int[]{0,1,2,3});
            String b = a.binNumber();
            System.out.println(b);
            byte[] openData= new BigInteger(b, 2).toByteArray();
            byte[] encryptedData=aes.Encrypt("123",openData);

            System.out.println(toBitString(encryptedData));

            byte[] decryptedData = aes.Decrypt("123",encryptedData);

            String transformedData= toBitString(decryptedData);
            if(decryptedData[0]==0){
                transformedData=transformedData.substring(8);
            }

            System.out.println(transformedData);

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());

        }




        *//*try{
        Adder a = new Adder(0,new int[]{0,1,2,3});
        AESCypher aes = new AESCypher("lv39eptlvuhaqqsr");
        String s = a.binNumber();
        System.out.println(s);
        String encdata = aes.encrypt(s);
        System.out.println("enc data "+encdata);
        String decdata = aes.decrypt(encdata);
        System.out.println("dec data "+decdata);
    }catch(Exception e){
        System.out.println(e.getCause());
    }*//*




    }*/
}
