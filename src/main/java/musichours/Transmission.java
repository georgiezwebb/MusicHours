package musichours;

public class Transmission {

    private String title;
    private Integer duration;
    private Boolean isBulletin;
    private String timeStamp;

    private static final Integer MIN_SONG_LENGTH = 180;

    public Transmission(String title, Integer duration) {
        this.title = title;
        this.duration = duration;
        setBulletin();
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public Boolean isBulletin() {
        return isBulletin;
    }

    private void setBulletin() {
        isBulletin = duration < MIN_SONG_LENGTH;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
