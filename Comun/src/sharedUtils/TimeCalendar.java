package sharedUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimeCalendar implements Serializable{

	public Calendar date;

	public TimeCalendar() {
		date = Calendar.getInstance();

	}
	public TimeCalendar(Timestamp stamp){
		 
		Date dateS = new Date(stamp.getTime());
	
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(dateS);
		 date=cal;

	}

	public int getDay() {
		return date.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth() {
		return date.get(Calendar.MONTH);
	}

	public int getYear() {
		return date.get(Calendar.YEAR);
	}

	public int getHour() {
		return date.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinutes() {
		return date.get(Calendar.MINUTE);
	}

	public int getSeconds() {
		return date.get(Calendar.SECOND);
	}
	public int getTotalDaysOfYear() {
		return date.get(Calendar.DAY_OF_YEAR);
	}


	public static String parseo(TimeCalendar t){
		int daysOfYear=365;
		int daysOfMonth=30;
		int diasCreado=t.date.get(Calendar.DAY_OF_YEAR);
		TimeCalendar tc = new TimeCalendar();
		int diasActual=tc.date.get(Calendar.DAY_OF_YEAR);
		if(diasActual-diasCreado==0) {
			return ("Today at "+Integer.toString(t.getHour())+":"+(Integer.toString(t.getMinutes())));
		}else if(diasActual-diasCreado==1) {
			return ("Yesterday");
		}else if(diasActual-diasCreado<daysOfMonth) {
			return (Integer.toString(diasActual-diasCreado)+" days ago");
		}
		if(diasActual-diasCreado>=daysOfMonth) {
			if((diasActual-diasCreado)%daysOfMonth==1) {
				return (Integer.toString((diasActual-diasCreado)%daysOfMonth)+" month ago");	
			}else {
				return (Integer.toString((diasActual-diasCreado)%daysOfMonth)+" months ago");	
			}
		}
		if(((((tc.getYear()-t.getYear())*daysOfYear)-diasCreado+diasActual)%daysOfYear)==1) {
			return Integer.toString(((((tc.getYear()-t.getYear())*daysOfYear)-diasCreado+diasActual)%daysOfYear))+" year ago";
		}
		if(((((tc.getYear()-t.getYear())*daysOfYear)-diasCreado+diasActual)%daysOfYear)>1) {
			return Integer.toString(((((tc.getYear()-t.getYear())*daysOfYear)-diasCreado+diasActual)%daysOfYear))+" years ago";
		}
		
//		if(t.getYear()==tc.getYear()&&tc.getMonth()==t.getMonth()){
//			if(t.getDay()==tc.getDay()){
//				return ("Today at "+Integer.toString(t.getHour())+":"+(Integer.toString(t.getMinutes())));
//			}
//			if(tc.getDay()-t.getDay()==1){
//				return ("Yesterday");
//			}
//			return ((Integer.toString(tc.getDay()-t.getDay())+" days ago"));
//			
//		}
//		if(tc.getMonth()!=t.getMonth()){
//			if(tc.getMonth()-t.getMonth()==1){
//				return ((Integer.toString(tc.getMonth()-t.getMonth())+" month ago"));
//			}
//			return ((Integer.toString(tc.getMonth()-t.getMonth())+" months ago"));
//
//		}
//		if(tc.getYear()!=t.getYear()){
//			if(tc.getYear()-t.getYear()==1){
//				return ((Integer.toString(tc.getYear()-t.getYear())+" year ago"));
//			}
//			return ((Integer.toString(tc.getYear()-t.getYear())+" years ago"));
//
//		}
		return null;
		
	}

}
