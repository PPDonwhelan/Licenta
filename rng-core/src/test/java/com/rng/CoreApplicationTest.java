/**
 * 
 */
package com.rng;

import com.rng.common.services.EmailService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreApplication.class)
public class CoreApplicationTest
{
	@SuppressWarnings("SpringJavaAutowiringInspection")
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
