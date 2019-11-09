import java.util.*;
import javax.sound.midi.*;


public class Midi_Sequencer {

    public Midi_Sequencer() {
    }

    /*
    *
    *  kodovanie hudby v kniznici sound,midi je pomocou eventov
    *
    *
    * */
    public MidiEvent makeNewEvent(int command, int channel, int note, int velocity, int tick){
        MidiEvent event = null;

        try {

            // ShortMessage stores a note as command type, channel,
            // instrument it has to be played on and its speed.
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, note, velocity);

            // A midi event is comprised of a short message(representing
            // a note) and the tick at which that note has to be played
            event = new MidiEvent(a, tick);
        }
        catch (Exception ex) {

            ex.printStackTrace();
        }

        return event;
    }
}
