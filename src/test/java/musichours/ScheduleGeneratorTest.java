package musichours;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGeneratorTest {

    private ScheduleGenerator underTest = new ScheduleGenerator();
    private List<Transmission> inputList = new ArrayList<>();
    static final Logger LOGGER = Logger.getLogger(ScheduleGeneratorTest.class);

    @Before
    public void setUp() {
        inputList.add(new Transmission("song 1", 240));
        inputList.add(new Transmission("song 2", 180));
        inputList.add(new Transmission("bulletin 1", 45));
        inputList.add(new Transmission("bulletin 2", 45));
        inputList.add(new Transmission("bulletin 3", 60));
        inputList.add(new Transmission("song 3", 180));
        inputList.add(new Transmission("song 4", 190));
        inputList.add(new Transmission("song 5", 200));
        inputList.add(new Transmission("song 6", 210));
        inputList.add(new Transmission("song 7", 220));
        inputList.add(new Transmission("song 8", 230));
        inputList.add(new Transmission("song 9", 240));
        inputList.add(new Transmission("song 10", 185));
        inputList.add(new Transmission("song 11", 195));
        inputList.add(new Transmission("song 12", 205));
        inputList.add(new Transmission("song 13", 215));
        inputList.add(new Transmission("song 14", 225));
        inputList.add(new Transmission("song 15", 235));
        inputList.add(new Transmission("song 16", 183));
        inputList.add(new Transmission("song 17", 189));
        inputList.add(new Transmission("song 18", 228));
        inputList.add(new Transmission("song 19", 182));
        inputList.add(new Transmission("song 20", 180));
        inputList.add(new Transmission("song 21", 190));
        inputList.add(new Transmission("song 22", 200));
        inputList.add(new Transmission("song 23", 210));
        inputList.add(new Transmission("song 24", 220));
        inputList.add(new Transmission("song 25", 230));
        inputList.add(new Transmission("song 26", 240));
        inputList.add(new Transmission("song 27", 185));
        inputList.add(new Transmission("song 28", 195));
        inputList.add(new Transmission("song 29", 205));
        inputList.add(new Transmission("song 30", 215));
        inputList.add(new Transmission("song 31", 225));
        inputList.add(new Transmission("song 32", 235));
        inputList.add(new Transmission("song 33", 183));
        inputList.add(new Transmission("song 34", 189));
        inputList.add(new Transmission("song 35", 228));
        inputList.add(new Transmission("song 36", 182));
        inputList.add(new Transmission("song 37", 183));
        inputList.add(new Transmission("song 38", 184));
        inputList.add(new Transmission("song 40", 185));
        inputList.add(new Transmission("song 41", 186));
        inputList.add(new Transmission("song 42", 240));
        inputList.add(new Transmission("song 43", 239));
        inputList.add(new Transmission("song 44", 238));
        inputList.add(new Transmission("song 45", 235));
        inputList.add(new Transmission("song 46", 235));
        inputList.add(new Transmission("song 47", 191));
        inputList.add(new Transmission("song 48", 185));
    }

    @Test(expected = Exception.class)
    public void test_throwsException_whenListIsEmpty() throws Exception {
        List<Transmission> emptyList = new ArrayList<>();
        underTest.createSchedule(emptyList);
    }

    @Test(expected = Exception.class)
    public void test_throwsException_whenListContainsNoBulletins() throws Exception {
        List<Transmission> noBulletins = new ArrayList<>();
        noBulletins.add(new Transmission("song 1", 240));
        noBulletins.add(new Transmission("song 2", 180));
        underTest.createSchedule(noBulletins);
    }

    @Test(expected = Exception.class)
    public void test_throwsException_whenListContainsNoSongs() throws Exception {
        List<Transmission> noSongsList = new ArrayList<>();
        noSongsList.add(new Transmission("bulletin 1", 45));
        underTest.createSchedule(noSongsList);
    }

    @Test
    public void test_throwsNoExceptionWhenListContainsSongsAndBulletins() throws Exception {
        underTest.createSchedule(inputList);
    }

    @Test
    public void creatScheduleTest() throws Exception {
        LOGGER.info("Starting createScheduleTest");
        int totalSecs = 0;
        List<Transmission> completeSchedule = underTest.createSchedule(inputList);


        for (Transmission transmission : completeSchedule) {

            System.out.println(transmission.getTitle() + " " + transmission.getDuration() + " "
                    + transmission.getTimeStamp());
            totalSecs = totalSecs + transmission.getDuration();
        }
    }
}