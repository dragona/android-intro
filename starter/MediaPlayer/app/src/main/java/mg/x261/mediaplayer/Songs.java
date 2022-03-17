package mg.x261.mediaplayer;

public class Songs {

    private String title;
    private String audioFile;

    Songs(String title, String audioFile) {
        this.title = title;
        this.audioFile = audioFile;
    }


    public String getTitle() {
        return title;
    }

    public String getAudioFile() {
        return audioFile;
    }
}
