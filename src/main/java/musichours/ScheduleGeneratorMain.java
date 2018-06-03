package musichours;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScheduleGeneratorMain {

    public static void main(String[] args) throws Exception {
        ScheduleGenerator scheduleGenerator = new ScheduleGenerator();
        String file = new File(args[0]).getAbsolutePath();
        String everything = null;

        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }

        List<String> input = Arrays.asList(everything.split("\\s*,\n\\s*"));

        List<Transmission> transmissions = new ArrayList<>();
        input.forEach(item -> {
            String title = item.split("\\s*,\\s*")[0].toString();
            Integer duration = Integer.valueOf(item.split("\\s*,\\s*")[1]);
            transmissions.add(new Transmission(title,duration));
        });

        List<Transmission> schedule = scheduleGenerator.createSchedule(transmissions);
        schedule.forEach(sched -> {
            System.out.println(sched.getTitle() + " " + sched.getDuration() +" "+ sched.getTimeStamp());
        });
    }

}
