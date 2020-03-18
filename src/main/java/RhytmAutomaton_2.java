import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RhytmAutomaton_2 {

    String Strinput=" ";
    public ArrayList<String[]> rhytmAtBeat;
    public ArrayList<String[]> rhytmUsed=new ArrayList<String[]>() ;
    public ArrayList<String[]> allRhytm;
    String[] beat0 ={"q"};
    //todo                                              ALL RHYTMIC VALUES PER ONE BEAT I
    //todo                                              WILL BE USING ARE PROGRAMMED HERE AND
    //todo                                              PUT TOGETHER FOR RANDOM CHOOSING


    public RhytmAutomaton_2(String strinput) {
        Strinput = strinput;

        String[] beat1 ={"i","i"};
        String[] beat2 ={"i","Ri"};
        String[] beat3 ={"i.","Rs"};
        String[] beat4 ={"i","Rs","s"};
        String[] beat5 ={"i","s","Rs"};
        String[] beat6 ={"i","s","s"};
        String[] beat7 ={"i.","s"};

        this.allRhytm=new ArrayList<String[]>(Arrays.asList(beat0,beat1,beat2,beat3,beat4,beat5,beat6,beat7));



    }


    public void createRhytm()
    //todo                                              HERE SET OF 12 RHYTM PROGRESSION IS CHOSEN
    //todo                                              FOR LATER DISTRIBUTION INTO SPECIFIC MEASURES
    //todo                                              12 IS MOST EFFICIENT
    {
        for(int i =0;i<12;i++) {
            //todo REMOVE 3 DIGITS
            int choice = Integer.parseInt(Strinput.substring(0, 3), 2);
            this.Strinput = Strinput.substring(3);


            rhytmUsed.add(this.allRhytm.get(choice));
        }
        //todo                                              DISTRIBUTION OF RHYTMIC VALUES INTO
        //todo                                              SPECIFIC BEATS AT SPECIFIC TIME IN MEASURES
        //todo                                              THEY ARE CHOSEN PURELY BY EXPERIENCE
        this.rhytmAtBeat=new ArrayList<String[]>(Arrays.asList(
                beat0,//1
                this.rhytmUsed.get(0),//2
                this.rhytmUsed.get(1),//3
                this.rhytmUsed.get(0),//4
                this.rhytmUsed.get(2),//5
                this.rhytmUsed.get(3),//6
                this.rhytmUsed.get(4),//7
                this.rhytmUsed.get(5),//8
                beat0,//9
                this.rhytmUsed.get(0),//10
                this.rhytmUsed.get(1),//11
                this.rhytmUsed.get(0),//12
                this.rhytmUsed.get(6),//13
                this.rhytmUsed.get(7),//14
                this.rhytmUsed.get(8),//15
                beat0,//16
                this.rhytmUsed.get(9),//17
                this.rhytmUsed.get(10),//18
                this.rhytmUsed.get(9),//19
                this.rhytmUsed.get(11),//20
                this.rhytmUsed.get(2),//21
                this.rhytmUsed.get(3),//22
                this.rhytmUsed.get(4),//23
                this.rhytmUsed.get(5),//24
                this.rhytmUsed.get(9),//25
                this.rhytmUsed.get(10),//26
                this.rhytmUsed.get(9),//27
                this.rhytmUsed.get(11),//28
                this.rhytmUsed.get(6),//29
                this.rhytmUsed.get(7),//30
                this.rhytmUsed.get(8),//31
                beat0//32
        ));

    }


    public ArrayList<String[]> getRhytmAtBeat() {
        return rhytmAtBeat;
    }

    public String getStrinput() {
        return Strinput;
    }
}
