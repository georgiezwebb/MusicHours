package musichours;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ScheduleGeneratorUtilsTest {

    private ScheduleGeneratorUtils underTest = new ScheduleGeneratorUtils();

    private static final Integer MIN_SONGS = 14;
    private static final Integer SECONDS_PER_HOUR = 3600;
    private static final Integer HOUR_BUFFER = 60;
    private static final Integer THRESHOLD = 180;
    private static final Integer MAX_BEFORE_HOUR = 120;
    private static final Integer MAX_TIME_AVAILABLE = 300;
    private static final Integer MAX_BULLETIN_TIME = 60;

    private List<Transmission> inputList = new ArrayList<>();
    private List<Transmission> bulletins = new ArrayList<>();
    private static final Integer TOTAL_RUNNING_SECONDS = 9895;

    private Transmission bulletin1 = new Transmission("bulletin 1", 45);
    private Transmission bulletin2 = new Transmission("bulletin 2", 45);
    private Transmission bulletin3 = new Transmission("bulletin 3", 60);

    @Before
    public void setUp() {
        inputList.add(new Transmission("song 1", 240));
        inputList.add(new Transmission("song 2", 180));
        inputList.add(bulletin1);
        inputList.add(bulletin2);
        inputList.add(bulletin3);
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

        bulletins.add(bulletin1);
        bulletins.add(bulletin2);
        bulletins.add(bulletin3);
    }

    @Test
    public void test_getRunningSeconds_returnsCorrectTotal() {
        Integer runningSeconds = underTest.getRunningSeconds(inputList);
        assertEquals(runningSeconds, TOTAL_RUNNING_SECONDS);
    }

    @Test
    public void test_findBulletins_returnsNewListContainingOnlyBulletins_ofCorrectSize() {
        List<Transmission> returned = underTest.findBulletinsAsList(inputList);
        assertEquals(returned.size(), 3);
    }

    @Test
    public void test_findBulletins_returnsNewListContainingOnlyBulletins() {
        List<Transmission> returned = underTest.findBulletinsAsList(inputList);
        returned.forEach(transmission -> {
            assertTrue(transmission.isBulletin());
        });
    }

    @Test
    public void test_findBulletinsAsList_returnsEmptyList_ifNoBulletins() {
        List<Transmission> returned = underTest.findBulletinsAsList(new ArrayList<>());
        assertTrue(returned.isEmpty());
    }

    @Test
    public void test_findSongAndAddBulletin_returnsSongWhenTimeForSongAndBulletin_maxTimeAvailable() {
        Transmission returned = underTest.findSongAndAddBulletin(179, HOUR_BUFFER, inputList, bulletins);
        assertTrue(returned.getDuration() + MAX_BULLETIN_TIME <= MAX_TIME_AVAILABLE);
    }

    @Test
    public void test_findSongAndAddBulletin_returnsNull_whenNoSongShortEnoughToFitWithBulletin() {
        Transmission returned = underTest.findSongAndAddBulletin(121, HOUR_BUFFER, inputList, bulletins);
        assertEquals(returned, null);
    }

    @Test
    public void testFormatSeconds_returnsCorrectOutput() {

        assertEquals(underTest.formatSeconds(MAX_TIME_AVAILABLE), "00:05:00");
        assertEquals(underTest.formatSeconds(MAX_BULLETIN_TIME), "00:01:00");
        assertEquals(underTest.formatSeconds(7362), "02:02:42");
        assertEquals(underTest.formatSeconds(0), "00:00:00");
        assertEquals(underTest.formatSeconds(5), "00:00:05");
        assertEquals(underTest.formatSeconds(SECONDS_PER_HOUR), "01:00:00");
    }
}