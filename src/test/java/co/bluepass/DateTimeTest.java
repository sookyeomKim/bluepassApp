package co.bluepass;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeTest {

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMM");
		DateTime monthStartDate = formatter.parseDateTime("1502");
		DateTime monthEndDate = monthStartDate.withTime(23, 59, 59, 59);
		monthEndDate = monthEndDate.dayOfMonth().withMaximumValue();
		
		System.out.println(monthStartDate);
		System.out.println(monthEndDate);
	}

}
