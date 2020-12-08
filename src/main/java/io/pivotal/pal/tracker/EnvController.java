package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memoryLimit;
    private String cfInstanceIndex;
    private String cfInstanceAddr;

    public  EnvController(@Value("${port::NOT SET}") String port,
                          @Value("${memoryLimit::NOT SET}") String memoryLimit,
                          @Value("${cfInstanceIndex::NOT SET}") String cfInstanceindex,
                          @Value("${cfInstanceAddr::NOT SET}") String cfinstanceAddr){
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceindex;
        this.cfInstanceAddr = cfinstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> envValues = new HashMap<>();

        envValues.put("PORT",port);
        envValues.put("MEMORY_LIMIT", memoryLimit);
        envValues.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        envValues.put("CF_INSTANCE_ADDR", cfInstanceAddr);

        return envValues;
    }

}
