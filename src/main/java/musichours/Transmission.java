package musichours;

import org.apache.log4j.Logger;

public class Transmission {

    private String title;
    private Integer duration;
    private Boolean isBulletin;
    private String timeStamp;

    private static final Integer MIN_SONG_LENGTH = 180;
    private static final Integer MAX_SONG_LENGTH = 240;

    private static final Integer MIN_BULLETIN_DURATION = 45;
    private static final Integer MAX_BULLETIN_DURATION = 60;

    public Transmission(String title, Integer duration) throws Exception {
        this.title = title;
        this.duration = duration;
        setBulletin();
        try {
            sanitiseInput();
        } catch (Exception e){
            throw e;
        }

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

    private void sanitiseInput() throws Exception {
        if (!isBulletin && duration > MAX_SONG_LENGTH) {
            throw new Exception("Song length is too long!");
        } else if (!isBulletin && duration < MIN_SONG_LENGTH) {
            throw new Exception("Song length is too short!");
        } else if (isBulletin && duration < MIN_BULLETIN_DURATION) {
            throw new Exception("Bulletin length too short!");
        } else if (isBulletin && duration > MAX_BULLETIN_DURATION) {
            throw new Exception("Bulletin length is too long");
        }
    }
}
