package musichours;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerator {

    private ScheduleGeneratorUtils utils = new ScheduleGeneratorUtils();

    static final Logger LOGGER = Logger.getLogger(ScheduleGenerator.class);

    private static final Integer MIN_SONGS = 14;
    private static final Integer SECONDS_PER_HOUR = 3600;
    private static final Integer HOUR_BUFFER = 60;
    private static final Integer THRESHOLD = 180;
    private static final Integer MAX_BEFORE_HOUR = 120;

    private List<Transmission> schedule = new ArrayList<>();
    private List<Transmission> bulletins = new ArrayList<>();
    private List<Transmission> originalList = new ArrayList<>();

    private Integer adjust = 0;
    private Integer runningTotalTime = 0;


    public List<Transmission> createSchedule(List<Transmission> input) throws Exception {

        LOGGER.info("Starting schedule...");

        try {
            utils.checkInputList(input);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

        originalList = input;
        Integer runningSeconds = utils.getRunningSeconds(originalList);
        Integer totalHours = Math.floorDiv(runningSeconds, SECONDS_PER_HOUR);

        bulletins = utils.findBulletinsAsList(originalList);
        originalList.removeAll(bulletins);
        addBulletinToSchedule();

        for (int hour = 0; hour <= totalHours; hour++) {
            LOGGER.info("Adding songs to Schedule hour " + (hour + 1));

            List<Transmission> minSongs = (utils.addMinimumSongs(originalList, MIN_SONGS));
            schedule.addAll(minSongs);
            originalList.removeAll(minSongs);

            if (addSongsToThreshold(hour)) {
                addTimeStamps();
                return schedule;
            }

            Integer minutesRemaining = utils.getMinutesRemainingToBeFilledInSchedule(hour, SECONDS_PER_HOUR, schedule);

            //existing list of songs has is nearing the hour, logic to add appropriate songs and bulletins
            fitSongsAndBulletinAroundHour(minutesRemaining);

            adjust = utils.getMinutesRemainingToBeFilledInSchedule(hour, SECONDS_PER_HOUR, schedule);
            LOGGER.info("Time rolled over = " + adjust);

        }

        addTimeStamps();
        return schedule;
    }

    private void addTimeStamps() {
        if (!schedule.isEmpty()) {
            LOGGER.info("Adding timestamps to final schedule...");
            for (Transmission transmission : schedule) {
                transmission.setTimeStamp(utils.formatSeconds(runningTotalTime));
                runningTotalTime += transmission.getDuration();
            }
        }
    }

    private void fitSongsAndBulletinAroundHour(Integer minutesRemaining) {
        /* Enough time for longest song and bulletin without exceeding 120 after hour time limit (long song +
        bulletin = 300 seconds*/
        if (minutesRemaining == 180) {
            addSongToSchedule();
            addBulletinToSchedule();
        /* calculate whether a song and bulletin combination exists that fits into the time remaining*/
        } else if (minutesRemaining > MAX_BEFORE_HOUR) {
            //find a song that will fit with a given bulletin, before the bulletin in scheduled
            Transmission t = utils.findSongAndAddBulletin(minutesRemaining, HOUR_BUFFER, originalList, bulletins);
            if (t != null) {
                schedule.add(t);
                originalList.remove(t);
                addBulletinToSchedule();
            } else {
                //if no combination exists, add another song. Hour will not contain a bulletin
                addSongToSchedule();
            }
            //if within 120 second before hour, add a bulletin
        } else {
            addBulletinToSchedule();
        }
    }

    private boolean addSongsToThreshold(int hour) {
        while (utils.getMinutesRemainingToBeFilledInSchedule(hour, SECONDS_PER_HOUR, schedule) > (THRESHOLD + adjust)) {
            if (!originalList.isEmpty()) {
                addSongToSchedule();
            } else {
                return true;
            }
        }
        return false;
    }

    private void addBulletinToSchedule() {
        if (!bulletins.isEmpty()) {
            Transmission bulletin = bulletins.get(0);
            LOGGER.info("Adding bulletin " + bulletin.getTitle() + " to Schedule");
            schedule.add(bulletin);
            bulletins.remove(bulletin);
        }
    }

    private void addSongToSchedule() {
        if (!originalList.isEmpty()) {
            Transmission song = originalList.get(0);
            LOGGER.info("Adding song " + song.getTitle() + " to Schedule");
            schedule.add(song);
            originalList.remove(0);
        }
    }
}