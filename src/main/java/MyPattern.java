import org.jfugue.pattern.Pattern;

public class MyPattern {
    Pattern pattern;
    int notesCounter;
    int leapCounter;
    String previousNote;
    int previousNotePosition;

    public MyPattern() {
        this.pattern = new Pattern();
        this.notesCounter = 0;
        this.leapCounter = 0;
    }
    String[] melodyNotes = {"C3","D3","E3","F3","G3","A3","H3","C4","D4","E4","F4","G4","A4","H4","C5","D5","E5","F5","G5","A5","H5","C6","D6","E6","F6","G6","A6","H6","C6","D6","E6","F6","G6","A6","H7"};

    //              TODO Prerobit stred melodickej sustavy na interval piatej oktavy

    void addNoteStructure(String Strinput, String previousNote){
        this.previousNote=previousNote;
        boolean ascend=false;
        //todo           tu pozriem na poslednu hranu notu ak je vyssia ako 11 melodia bude klesat ak nizsia bude stupat
        for(int i =0; i < melodyNotes.length; i++){
            if(melodyNotes[i]==previousNote)
                this.previousNotePosition=i;
                if(i<11)
                    ascend=true;
                else
                    ascend=false;
        }

        //todo              Rozhodnutie medzi leap alebo step

        Integer choice = Integer.parseInt(Strinput.substring(0,1),2);
        Strinput.substring(1);


        if(choice==0){
            this.addStep(Strinput,ascend);
        }
        else{this.addLeap(Strinput,ascend);
        }
    }

void addStep(String Strinput,boolean ascend){
        //todo              Rozhodnutie o aky typ steps ide...
        Integer choice = Integer.parseInt(Strinput.substring(0,3),2);
        Strinput.substring(3);


        switch (choice){
        case 0: //todo   quarter + quarter
            if(ascend){
                String temporary_string = this.previousNote + "q";
                this.pattern.add(temporary_string);
                this.notesCounter=this.getNotesCounter()+2;

                temporary_string = melodyNotes[previousNotePosition+1] + "q";
                this.pattern.add(temporary_string);
                this.notesCounter=this.getNotesCounter()+2;
            }else{
                String temporary_string = this.previousNote + "q";
                this.pattern.add(temporary_string);
                this.notesCounter=this.getNotesCounter()+2;
                temporary_string = melodyNotes[previousNotePosition-1] + "q";
                this.pattern.add(temporary_string);
                this.notesCounter=this.getNotesCounter()+2;
            }

            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        case 4:
            break;

        case 5:
            break;
        case 6:
            break;
        case 7:

            break;
        default:
            break;
    }




    }






        void addLeap(String Strinput,boolean ascend){
            //todo              Rozhodnutie o aku vzdialenost sa spravi leap ...
            Integer choice = Integer.parseInt(Strinput.substring(0,2),2);
            Strinput.substring(2);
            switch (choice){
                case 0:
                    if(ascend){
                        this.previousNote=melodyNotes[previousNotePosition+2];
                    }else{
                        this.previousNote=melodyNotes[previousNotePosition-2];
                    }
                    break;
                case 1:

                    if(ascend){
                        this.previousNote=melodyNotes[previousNotePosition+3];
                    }else{
                        this.previousNote=melodyNotes[previousNotePosition-3];
                    }
                    break;
                case 2:

                    if(ascend){
                        this.previousNote=melodyNotes[previousNotePosition+4];
                    }else{
                        this.previousNote=melodyNotes[previousNotePosition-4];
                    }
                    break;
                case 3:

                    if(ascend){
                        this.previousNote=melodyNotes[previousNotePosition+5];
                    }else{
                        this.previousNote=melodyNotes[previousNotePosition-5];
                    }
                    break;


                default:
                    break;
            }


        }


    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public int getNotesCounter() {
        return notesCounter;
    }

    public void setNotesCounter(int notesCounter) {
        this.notesCounter = notesCounter;
    }

    public int getLeapCounter() {
        return leapCounter;
    }

    public void setLeapCounter(int leapCounter) {
        this.leapCounter = leapCounter;
    }
}
