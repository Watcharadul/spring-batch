package com.go.basic.chunk;

import org.springframework.batch.item.ItemProcessor;

public class ChunkProcessor implements ItemProcessor<String,String>{

    @Override
    public String process(String fruit){
        return "I like "+fruit+"!";
    }

}
