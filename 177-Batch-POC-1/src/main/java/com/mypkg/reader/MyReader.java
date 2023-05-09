package com.mypkg.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MyReader implements ItemReader<String> {

	String[] users = new String[] { "User1", "User2", "User3", "User4", "User4", "User6", "User7", "User8", "User9",
			"User10" };
	private int count = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < users.length)
			return users[count++];
		else
			return null;
	}

}
