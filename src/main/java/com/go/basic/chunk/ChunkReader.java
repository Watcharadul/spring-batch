package com.go.basic.chunk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class ChunkReader implements ItemReader<String>{

    private final String[] FRULT = {"Apples","Bananas","Grapes","Oranges","Mangos"};
    private Integer indx = 0;

    @Override
    public String read(){
        log.info("Reader index : "+indx);
        return (indx < FRULT.length)?FRULT[indx++]:null;
    }

}
