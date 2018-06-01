package musichours;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleGeneratorUtils {

    static final Logger LOGGER = Logger.getLogger(ScheduleGeneratorUtils.class);

    protected List<Transmission> findBulletinsAsList(List<Transmission> input) {
        return input.stream().filter(transmission -> transmission.isBulletin())
                .collect(Collectors.toList());
    }

    protected Transmission findSongAndAddBulletin(Integer minutesRemaining, Integer hourBuffer, List<Transmission> originalList, List<Transmission> bulletins) {

        Integer buffered = minutesRemaining + hourBuffer;

        return originalList.stream().filter(transmission ->
                transmission.getDuration() + bulletins.get(0).getDuration() <= buffered && !transmission.isBulletin())
                .findFirst()
                .orElse(null);
    }

    protected Boolean addSongOrBulletinToSchedule(Integer minutesRemaining, Integer maxBeforeHour) {
        return minutesRemaining <= maxBeforeHour;
    }

    protected void checkInputList(List<Transmission> input) throws Exception {
        if (input.isEmpty()) {
            throw new Exception("Schedule list is empty!");
        } else {
            checkContainsBulletins(input);
            checkContainsSongs(input);
        }
    }

    protected void checkContainsBulletins(List<Transmission> input) throws Exception {
        input.stream().filter(transmission -> transmission.isBulletin())
                .findAny()
                .orElseThrow(() -> new Exception("Transmission contains no bulletins!"));
    }

    protected void checkContainsSongs(List<Transmission> input) throws Exception {
        input.stream().filter(transmission -> !transmission.isBulletin())
                .findAny()
                .orElseThrow(() -> new Exception("Transmission contains no songs!"));
    }

    protected int getMinutesRemainingToBeFilledInSchedule(int hour, Integer secondsPerHour, List<Transmission> schedule) {
        return secondsPerHour - (getRunningSeconds(schedule) - (hour * secondsPerHour));
    }

    public List<Transmission> addMinimumSongs(List<Transmission> input, Integer minSongs) {
        List<Transmission> addedSongs = new ArrayList<>();
        LOGGER.info("Adding MIN_SONGS to schedule");
        for (int j = 0; j < input.size() && j <= minSongs; j++) {
            addedSongs.add(input.get(j));
        }
        return addedSongs;
    }


    protected Integer getRunningSeconds(List<Transmission> input) {
        Integer runningSeconds = 0;

        for (Transmission transmission : input) {
            runningSeconds += transmission.getDuration();
        }
        return runningSeconds;
    }

    public  String formatSeconds(Integer timeInSeconds){

        Integer secondsLeft = timeInSeconds % 3600 % 60;
        Integer minutes = (int) Math.floor(timeInSeconds % 3600 / 60);
        Integer hours = (int) Math.floor(timeInSeconds / 3600);

        String  HH = hours < 10 ? "0" + hours : String.valueOf(hours);
        String  MM = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        String SS = secondsLeft < 10 ? "0" + secondsLeft : String.valueOf(secondsLeft);

        return HH + ":" + MM + ":" + SS;
    }
}
