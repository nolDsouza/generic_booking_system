package JUnitTests;

import java.util.ArrayList;

import org.junit.Test;

import Interfaces.OpeningHours;
import Interfaces.TimeConstants;
import NWC.model.Day;
import NWC.model.PersonalTrainer;
import NWC.model.WT;

public class EmployeeTests 
implements TimeConstants {

	@Test
	public void test1() {
		PersonalTrainer employee = new PersonalTrainer("Nol");

		for (int i = 0; i < 224; i++)
		{
			System.out.print(employee.getWorkingTimes()[i]);
		}
	}
	
	@Test
	public void test2() {
		OpeningHours op = new OpeningHours();
		Day[] scheduele = op.getScheduele();
		PersonalTrainer employee = new PersonalTrainer("Nol");
		
		int wt = 0;
		int count = 0;
		for (int i=0; i<4; i++) {
			for (int j=0; j<7; j++) {
				wt++;
				System.out.print(wt +". ");
				for (int k=0; k<scheduele[j].hoursOpen(); k++) {
					System.out.print(employee.getWorkingTimes()[count]);
					count++;
				}
				System.out.print('\n');
			}
			//System.out.print(employee.getWorkingTimes()[i]);
		}
	}
	
	@Test
	public void test3() {
		int day = dayOfMonth-1;
		int month = monthOfYear;
		int y = year;
		OpeningHours op = new OpeningHours();
		Day[] scheduele = op.getScheduele();
		PersonalTrainer employee = new PersonalTrainer("Nol");
		
		String tempString = TimeConstants.toString(day, month+1, y);
		int count = 0;
		for (int i=0; i<4; i++) {
			for (int j=0; j<7; j++) {
				tempString = TimeConstants.increment(tempString);
				System.out.print(tempString +". ");
				for (int k=0; k<scheduele[j].hoursOpen(); k++) {
					System.out.print(employee.getWorkingTimes()[count]);
					count++;
				}
				System.out.print('\n');
			}
			//System.out.print(employee.getWorkingTimes()[i]);
		}
	}
	
	@Test
	public void test4() {
		PersonalTrainer employee1 = new PersonalTrainer("Avi");
		PersonalTrainer employee2 = new PersonalTrainer("Johno");

		ArrayList<WT> aArr = employee1.getWorkingDays();
		ArrayList<WT> jArr = employee2.getWorkingDays();
		
		System.out.println("\n\nAvi:");
		for (WT key : aArr) {
			System.out.println(key.getWorkingTimes());
		}
		System.out.println("\n\nJohno:");
		for (WT key : jArr) {
			System.out.println(key.getWorkingTimes());
		}
		
		for (int i=0; i<aArr.size(); i++) {
			aArr.get(i).combine(jArr.get(i).getWorkingTimes());
		}
		
		System.out.println("\n\nAvi:");
		for (WT key : aArr) {
			System.out.println(key.getWorkingTimes());
		}
		
	}

}
