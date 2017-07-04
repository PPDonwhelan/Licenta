/**
 * 
 */
package com.rng;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rng.common.services.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreApplication.class)
public class CoreApplicationTest
{
	@Autowired DataSource dataSource;
	@Autowired EmailService emailService;
	
	@Test
	public void testDummy() throws SQLException
	{
		String schema = dataSource.getConnection().getCatalog();
		assertTrue("rng".equalsIgnoreCase(schema));
	}
	
	@Test
	@Ignore
	public void testSendEmail()
	{
		emailService.sendEmail("admin@gmail.com", "JCart - Test Mail", "This is a test email from RNG");
	}
	
}
