package com.bjss.desk_booking;

import com.bjss.desk_booking.booking.Booking;
import com.bjss.desk_booking.office.Office;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DeskBookingApplicationTests {

	private static Office office;
	private static Booking booking;

	@BeforeAll
	public static void before() {
		office = new Office();
		booking = new Booking();
	}

	@Test
	public void testGetOfficeId() {
		office.setOfficeId(2);
		assertEquals(2, office.getOfficeId());
	}

	@Test
	public void testGetOfficeName() {
		office.setOfficeName("Cardiff");
		assertEquals("Cardiff", office.getOfficeName());
	}

	@Test
	public void testGetBookingId() {
		booking.setBookingId(42);
		assertEquals(42, booking.getBookingId());
	}

}
