package com.mypkg.processor;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

public class MyProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		return "Username : " + item + "Contact : " + new Random().nextLong(6234578910l, 9999999999l) + "\n";
	}

}
