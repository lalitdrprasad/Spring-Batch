package com.mypkg.processor;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

public class MyProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		return "Username : " + item + "Contact : " + new Random().nextLong(400000000) + 62345678910l + "\n";
	}

}
