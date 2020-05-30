import org.apache.commons.codec.binary.Base32;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;



public class Main {


    /*public static void main(String[] args) {

        Player player = new Player();
        String controlString;
        String finalString="";
        Pattern mainPattern = new Pattern();



        //todo   Choosing key by tonic value + choosing chord progression with permutation 0=I 1=V 2=VI 3=IV
        Adder adder = new Adder();
        String Strinput = adder.binNumber();
        System.out.println("bity na zaciatku "+Strinput);
        adder.createHarmony(Strinput);
        Strinput=adder.getStrinput();

        //todo   Generating random binary string



//        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
//        Pattern p1 = new Pattern();
//        Pattern p2 = new Pattern();
//        Pattern p3 = new Pattern();
//        patterns.add(p1);
//        patterns.add(p2);
//        patterns.add(p3);
        //todo Creating rhytm

        //for(int k = 0; k < 3;k++) {

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

            //todo Creating melody

            MelodyAutomaton_2 melodyAutomaton = new MelodyAutomaton_2(Strinput, mainPattern, rhytmAutomaton, adder);
            melodyAutomaton.createMelody();
            Strinput = melodyAutomaton.getStrinput();
            System.out.println(mainPattern.toString());
            System.out.println("toto zostalo z inputu "+Strinput);

            System.out.println("zakodovali sme dokopy " + (200 - Strinput.length()));
            controlString = adder.codedString(200 - Strinput.length());
            System.out.println("tieto bity sme zakodovali do rymtu a melodie "+controlString);
//            patterns.get(k).add("Rw");
//        }
//
//        mainPattern.add(patterns.get(0));
//        mainPattern.add(patterns.get(1));
//        mainPattern.add(patterns.get(2));
//        mainPattern.add(patterns.get(1));
//        mainPattern.add(patterns.get(2));
//        mainPattern.add(patterns.get(0));
//        System.out.println("it is correct");




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
        System.out.println(loadedPattern.getPattern());
        translatedPattern.add(adder.translateNotes(loadedPattern.getPattern().toString()));
        finalString=adder.getBinaryOutput();
        controlString=finalString+controlString;
        System.out.println(finalString);
        System.out.println(translatedPattern.toString());
        RhytmDecoder rhytmDecoder = new RhytmDecoder(translatedPattern.toString(),BinaryOutput);
        rhytmDecoder.decodeRhytm();
        System.out.println(rhytmDecoder.getBinaryOutput());
        finalString+=rhytmDecoder.getBinaryOutput();

        MelodyDecoder melodyDecoder = new MelodyDecoder(translatedPattern.toString(),BinaryOutput,rhytmDecoder.getGroupedParsedRhytm(),adder);
        melodyDecoder.decodeMelody();
        finalString+=melodyDecoder.getBinaryOutput();

        for(int i =0;i<finalString.length();i++){
            if(i % 4 == 0){
                System.out.print(" ");
            }
            if(finalString.toCharArray()[i]==controlString.toCharArray()[i]){
                System.out.print("\u001B[32m"+finalString.toCharArray()[i]);
            }else{
                System.out.print("\u001B[31m"+finalString.toCharArray()[i]);
            }
        }

    }*/
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


    public static void main(String[] args) {

        CryptoModule cryptoModule = new CryptoModule();
        CompositionModule compositionModule = new CompositionModule();
        String input = "pavolsobota";
        String a;
        String b;
        try {
            String Strinput = cryptoModule.encryptData(input);
            SecretKey secretKey = cryptoModule.getSecretKey();
            System.out.println("toto je secretKey "+secretKey.toString());
            a= Strinput;
            compositionModule.compositionModule(Strinput);
            Strinput=compositionModule.decompositionModule();
            b=Strinput;
            for(int i = 0; i < b.length();i++){
                if(a.toCharArray()[i]==b.toCharArray()[i]){
                    System.out.print("\u001B[30m"+a.toCharArray()[i]);
                }else{
                    System.out.print("\u001B[31m"+b.toCharArray()[i]);
                }

            }
            System.out.println();

            String openText = cryptoModule.decryptData(Strinput,secretKey);
            System.out.println("Vysledny output: ");
            System.out.println(openText);
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }


       /* HashMap<Character,String> base32 = new HashMap<Character, String>() {{
            put('a', "00000");
            put('b', "00001");
            put('c', "00010");
            put('d', "00011");
            put('e', "00100");
            put('f', "00101");
            put('g', "00110");
            put('h', "00111");
            put('i', "01000");
            put('j', "01001");
            put('k', "01010");
            put('l', "01011");
            put('m', "01100");
            put('n', "01101");
            put('o', "01110");
            put('p', "01111");
            put('q', "10000");
            put('r', "10001");
            put('s', "10010");
            put('t', "10011");
            put('u', "10100");
            put('v', "10101");
            put('w', "10110");
            put('x', "10111");
            put('y', "11000");
            put('z', "11001");
            put('2', "11010");
            put('3', "11011");
            put('4', "11100");
            put('5', "11101");
            put('6', "11110");
            put('7', "11111");
        }};
 String openText = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
 String base32Text = "";
 for(int i = 0;i<openText.length();i++){
     base32Text+=base32.get(openText.charAt(i));
 }
        System.out.println(base32Text); //todo now we have 100101011
String asciiText="";
char nextChar;

for(int i = 0; i<base32Text.length(); i+=8){
    nextChar = (char)Integer.parseInt(base32Text.substring(i,i+8),2);
    asciiText+=nextChar;
}

        System.out.println(asciiText); //todo now we have ASCII .. to encode



try{
        //Adder a = new Adder(0,new int[]{0,1,2,3});
        AESCypher aes = new AESCypher("lv39eptlvuhaqqsr");
        //String s = a.binNumber();

    //System.out.println(s);
        String encdata = aes.encrypt(asciiText);
        System.out.println("enc data "+encdata);  //todo now we have encoded in ASCII

    String help="";
    for(int i = 0; i < encdata.length();i++){
        int a = (int)encdata.charAt(i);

        help += String.format("%8s",Integer.toBinaryString(a)).replace(' ','0');

    }
    System.out.println(help);

    String help2="";
    char nextChar2;

    for(int i = 0; i<help.length(); i+=8){
        nextChar2 = (char)Integer.parseInt(base32Text.substring(i,i+8),2);
        help2+=nextChar2;
    }


        String decdata = aes.decrypt(help2);
        System.out.println("dec data "+decdata);

    String help3="";
    for(int i = 0; i < decdata.length();i++){
        int a = (int)decdata.charAt(i);

        help3 += String.format("%8s",Integer.toBinaryString(a)).replace(' ','0');

    }
    System.out.println(help3);


    }catch(Exception e){
        System.out.println(e.getLocalizedMessage());
    }



        try {
            Base32 base32 = new Base32();
            byte[] a=base32.encode("test".getBytes());
            System.out.println(base32);

            AESCypher aes = new AESCypher();

            byte[] a = new byte[16];
            for(int i =0; i< 16;i++){
                a[i]=4;
            }
            byte[] encryptedData = aes.Encrypt("Socialismforever", a);

            //System.out.println(toBitString(encryptedData));

            byte[] decryptedData = aes.Decrypt("Socialismforever", encryptedData);

            //byte[] b =base32.decode(decryptedData);
           // System.out.println(new String(b));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());

        }
try {
    Base32 base32 = new Base32();
    byte[] a = base32.encode("test".getBytes());
    System.out.println(base32);


    //// TODO                               generating block of bytes of size 16B = bits
    byte[] input = new byte[16];
    for(int i =0; i< 16;i++){
        input[i]=4;
    }
    //todo                              creating cipher with parameters and key
    byte[] iv = new byte[128 / 8];
    SecureRandom srandom = new SecureRandom();
    srandom.nextBytes(iv);
    IvParameterSpec ivspec = new IvParameterSpec(iv);
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    SecretKey skey = kgen.generateKey();

    Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
    ci.init(Cipher.ENCRYPT_MODE,skey,ivspec);
    //// TODO                                   encoding input
    byte[] encoded = ci.doFinal(input);
    System.out.println();
}catch(Exception e){
    System.out.println(e.getLocalizedMessage());
}

*/
//try {
//    byte[] input = new byte[16];
//    for(int i =0; i< 16;i++){
//        input[i]=4;
//    }
//    KeyGenerator kgen = KeyGenerator.getInstance("AES");
//    SecretKey skey = kgen.generateKey();
//    Cipher encryptor = Cipher.getInstance("AES/ECB/NoPadding");
//    encryptor.init(Cipher.ENCRYPT_MODE, skey);
//    byte[] encrypted = encryptor.doFinal(input);
//    System.out.println();
//}catch(Exception e){
//    System.out.println(e.getLocalizedMessage());
//}




    }



}
