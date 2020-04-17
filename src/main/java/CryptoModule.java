import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class CryptoModule {
    HashMap<Character,String> base32 = new HashMap<Character, String>() {{
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
    SecretKey secretKey = null;

    public CryptoModule() {
    }

    public String encryptData(String openText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] openDataBytes= new byte[80];
        int openBytesCounter = 0;
        String base32Text = "";
        String binaryStringOutput="";

        //todo                   This part is responsible for encoding open text into base32 byte array of length suitable for AES encoding

        for(int i = 0;i<openText.length();i++){
            base32Text+=base32.get(openText.charAt(i));
        }
        System.out.println(base32Text);
        while (base32Text.length()%8!=0){
            base32Text+="0";
        }


        while (base32Text.length()!=0){
            Integer tempinteger = Integer.parseInt(base32Text.substring(0,8), 2);
            byte b = tempinteger.byteValue();
            base32Text=base32Text.substring(8);
            openDataBytes[openBytesCounter]=b;
            openBytesCounter++;
        }
        while(openBytesCounter!=80){
            byte b =0;
            openDataBytes[openBytesCounter]=b;
            openBytesCounter++;
        }

        //todo                   This part is responsible for AES cipher encrypting

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        this.secretKey = kgen.generateKey();
        Cipher encryptor = Cipher.getInstance("AES/ECB/NoPadding");
        encryptor.init(Cipher.ENCRYPT_MODE, this.secretKey);
        byte[] encrypted = encryptor.doFinal(openDataBytes);

        //todo                   This part turns encrypted byte array into desired binary string used in Music composition module

        for(int i = 0; i < encrypted.length;i++){
            binaryStringOutput+=String.format("%8s", Integer.toBinaryString(encrypted[i] & 0xFF)).replace(' ', '0');
        }
        return binaryStringOutput;
    }

    public String decryptData(String binaryString,SecretKey skey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encryptedData=new byte[80];
        int encryptedDataCounter = 0;
        String binaryDecrypted="";
        String openText="";

        //todo                 This part turns binary string into byte array for deciphering with AES

        while (binaryString.length()%8!=0){
            binaryString+="0";
        }

        while (binaryString.length()!=0){
            Integer tempinteger = Integer.parseInt(binaryString.substring(0,8), 2);
                byte b = tempinteger.byteValue();

            binaryString=binaryString.substring(8);
            encryptedData[encryptedDataCounter]=b;
            encryptedDataCounter++;
        }

        while(encryptedDataCounter!=80){
            byte b =0;
            encryptedData[encryptedDataCounter]=b;
            encryptedDataCounter++;
        }

        //todo                  This part uses AES to decipher encrypted byte array

        Cipher decryptor = Cipher.getInstance("AES/ECB/NoPadding");
        decryptor.init(Cipher.DECRYPT_MODE, skey);
        byte[] decrypted = decryptor.doFinal(encryptedData);

        //todo                  This part decode decrypted data with Base32 and outputs them
        for(int i = 0; i < decrypted.length;i++){
            binaryDecrypted+=String.format("%8s", Integer.toBinaryString(decrypted[i] & 0xFF)).replace(' ', '0');
        }
        while (binaryDecrypted.length()%5!=0){
            binaryDecrypted+="0";
        }
        while (binaryDecrypted.length()!=0){
            for (HashMap.Entry<Character, String> entry : base32.entrySet()) {
                if (binaryDecrypted.substring(0,5).equals(entry.getValue())) {
                    openText += entry.getKey();
                    binaryDecrypted = binaryDecrypted.substring(5);
                    break;
                }
            }
        }

        return openText;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
