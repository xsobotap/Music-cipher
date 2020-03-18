import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RhytmAutomaton {

    String Strinput=" ";
    public ArrayList<String[]> rhytmAtBeat;
    public ArrayList<String[]> rhytmUsed=new ArrayList<String[]>() ;
    public ArrayList<String[]> allRhytm;
    String[] beat0 ={"q"};

    public RhytmAutomaton(String strinput) {
        Strinput = strinput;

        String[] beat1 ={"i","i"};
        String[] beat2 ={"i","Ri"};
        String[] beat3 ={"i.","Rs"};
        String[] beat4 ={"Rs","i."};
        String[] beat5 ={"Ri","i"};
        String[] beat6 ={"i*","i*","i*"};
        String[] beat7 ={"i.","s"};

        String[] beat8 ={"i","s","Rs"};
        String[] beat9 ={"s","s","Ri"};
        String[] beat10 ={"i","s","s"};
        String[] beat11 ={"s","s","i"};
        String[] beat12 ={"i","Rs","s"};
        String[] beat13 ={"s","Rs","i"};
        String[] beat14={"s","i."};
        String[] beat15 ={"s","i","s"};
        this.allRhytm=new ArrayList<String[]>(Arrays.asList(beat0,beat1,beat2,beat3,beat4,beat5,beat6,beat7,
                                                        beat8,beat9,beat10,beat11,beat12,beat13,beat14,beat15));



    }


    public void createRhytm()

    {
        for(int i =0;i<10;i++) {
            //todo REMOVE 4 DIGITS
            int choice = Integer.parseInt(Strinput.substring(0, 4), 2);
            this.Strinput = Strinput.substring(4);


            rhytmUsed.add(this.allRhytm.get(choice));
        }

            this.rhytmAtBeat=new ArrayList<String[]>(Arrays.asList(
                    beat0,//1
                    this.rhytmUsed.get(0),//2
                    this.rhytmUsed.get(1),//3
                    this.rhytmUsed.get(0),//4
                    this.rhytmUsed.get(2),//5
                    this.rhytmUsed.get(3),//6
                    this.rhytmUsed.get(2),//7
                    this.rhytmUsed.get(4),//8
                    beat0,//9
                    this.rhytmUsed.get(0),//10
                    this.rhytmUsed.get(1),//11
                    this.rhytmUsed.get(0),//12
                    this.rhytmUsed.get(5),//13
                    this.rhytmUsed.get(6),//14
                    this.rhytmUsed.get(5),//15
                    beat0,//16
                    this.rhytmUsed.get(7),//17
                    this.rhytmUsed.get(8),//18
                    this.rhytmUsed.get(7),//19
                    this.rhytmUsed.get(9),//20
                    this.rhytmUsed.get(2),//21
                    this.rhytmUsed.get(3),//22
                    this.rhytmUsed.get(2),//23
                    this.rhytmUsed.get(4),//24
                    this.rhytmUsed.get(7),//25
                    this.rhytmUsed.get(8),//26
                    this.rhytmUsed.get(7),//27
                    this.rhytmUsed.get(9),//28
                    this.rhytmUsed.get(5),//29
                    this.rhytmUsed.get(6),//30
                    this.rhytmUsed.get(5),//31
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
