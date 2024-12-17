import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    public static void playDiceRollSound() {
        String filePath = "dice.wav";
        AudioPlayer.playSound(filePath);
    }

    private static void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            AudioFormat format = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(audioInputStream);

            clip.start();

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            while (clip.isRunning()) {
                Thread.sleep(100);
            }


        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
