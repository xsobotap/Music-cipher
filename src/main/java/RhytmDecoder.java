import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RhytmDecoder {


    //todo                                              RHYTM THAT IS USED AND IS GOING TO BE
    //todo                                              COMPARED TO PARSED STRING
    public ArrayList<String[]> allRhytm;

        String[] beat0 ={"q",null,null};
        String[] beat1 ={"i","i",null};
        String[] beat2 ={"i","Ri",null};
        String[] beat3 ={"i.","Rs",null};
        String[] beat4 ={"i","Rs","s"};
        String[] beat5 ={"i","s","Rs"};
        String[] beat6 ={"i","s","s"};
        String[] beat7 ={"i.","s",null};





    String Strinput;
    String BinaryOutput;
    ArrayList<String> parsedRhytm=new ArrayList<String>();
    ArrayList<String[]> groupedParsedRhytm = new ArrayList<String[]>();
    public RhytmDecoder(String strinput, String binaryOutput) {
        Strinput = strinput;
        BinaryOutput=binaryOutput;
    }

    public void decodeRhytm(){
        this.parseRhytm();
        this.compareRhytm();
    }
    //todo                                              METHOD THAT PARSES STRING BY " " AND ISOLATES
    //todo                                              RHYTM LETTERS FROM EACH NOTES
    public void parseRhytm(){
        StringTokenizer st1 = new StringTokenizer(Strinput, " ");
        while (st1.hasMoreTokens()){
            String temp = st1.nextToken();
            if(temp.equals("Rs")||temp.equals("Ri")){
                parsedRhytm.add(temp);
                continue;
            }
            temp = temp.substring(2);
            parsedRhytm.add(temp);
        }
        int beatCounter=0;
        String[] str=new String[3];
        int strcounter = 0;
        for(int i =0; i< parsedRhytm.size();i++) {

            if(beatCounter==0){
                this.groupedParsedRhytm.add(new String[3]);
                strcounter=0;
            }

            if(parsedRhytm.get(i).equals("q"))
            beatCounter=4;
            if(parsedRhytm.get(i).equals("i."))
                beatCounter+=3;
            if(parsedRhytm.get(i).equals("i")||parsedRhytm.get(i).equals("Ri"))
                beatCounter+=2;
            if(parsedRhytm.get(i).equals("s")||parsedRhytm.get(i).equals("Rs"))
                beatCounter+=1;

            if (beatCounter == 4) {
                groupedParsedRhytm.get(groupedParsedRhytm.size()-1)[strcounter]=parsedRhytm.get(i);
                strcounter++;
                beatCounter=0;
            }else{
                groupedParsedRhytm.get(groupedParsedRhytm.size()-1)[strcounter]=parsedRhytm.get(i);
                strcounter++;
            }

        }

    }
    //todo                                              COMPARES PARSED RHYTM IN DISTINCT MEASURES
    //todo                                              AND DECODES IT BACK TO BINARY STRING
    public void compareRhytm(){
        allRhytm=new ArrayList<String[]>(Arrays.asList(beat0,beat1,beat2,beat3,beat4,beat5,beat6,beat7));
        Integer[] indexes = {1,2,4,5,6,7,12,13,14,16,17,19};
        ArrayList<Integer> decimal = new ArrayList<Integer>();
        for(int i = 0; i< indexes.length;i++){
            for(int j =0; j < allRhytm.size();j++){
                boolean b =true;
                for(int k=0;k<3;k++){
                    if(allRhytm.get(j)[k]!=null){
                    if (!allRhytm.get(j)[k].equals(groupedParsedRhytm.get(indexes[i])[k]))
                    b=false;
                    }
                }
                if(b)
                    decimal.add(new Integer(j));
            }

        }
        for(int i = 0; i < decimal.size();i++){
            if(decimal.get(i)<2)
                BinaryOutput+="00";
            else if(decimal.get(i)<4)
                BinaryOutput+="0";
            BinaryOutput+=Integer.toBinaryString(decimal.get(i));
        }

    }

    public String getBinaryOutput() {
        return BinaryOutput;
    }
}
