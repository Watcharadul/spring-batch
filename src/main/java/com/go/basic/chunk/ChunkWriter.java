package com.go.basic.chunk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class ChunkWriter implements ItemWriter<String>{

    @Override
    public void write(List<? extends String> list){
        for(String msg : list){
            log.info(msg);
        }

    }

}
