package com.karatebancho.zouka.servlet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ZoukaServletTest extends ZoukaServlet {

	@Test
	public void getCjkPrefix() {
		String str = getCjkPrefix("東京とっきょk");
		assertThat(str, is("東京"));

	}

}
