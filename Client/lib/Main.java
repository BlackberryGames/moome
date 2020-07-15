import com.moome.MoomeGame;
import com.moome.leveleditor.LevelEditor;
import com.moome.easteregg.EasterEgg;
import javax.swing.SwingUtilities;
import java.lang.Runnable;
import javax.swing.JFrame;

public class Main {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(args.length > 0 && args[0].equalsIgnoreCase("-leveleditor")) {
                    LevelEditor levelEditor = new LevelEditor();
                } else if(args.length > 0 && args[0].equalsIgnoreCase("-easteregg")) {
                    new EasterEgg();
                } else {
                    MoomeGame m = new MoomeGame();
                }
            }
        });
    }
}
