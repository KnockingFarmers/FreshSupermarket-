package org.gl.controller;


import org.gl.entity.Collect;
import org.gl.entity.Goods;
import org.gl.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
@CrossOrigin
@RestController
    @RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @GetMapping("/getUserCollectList")
    public List<Collect> getUserCollectList(String token){
        return collectService.getCollectList(token);
    }

    @PostMapping("/removeCollect")
    public Map<String, Object> removeCollect(String token,String... collectIdStr){
        Long[] collects = new Long[collectIdStr.length];
        for (int i = 0; i < collectIdStr.length; i++) {
            collects[i]=Long.valueOf(collectIdStr[i]);
        }
       return collectService.removeCollect(token,collects);
    }

    @PostMapping("/addCollect")
    public Integer addCollect(String token,String goodsIdStr){
        return collectService.addCollect(token,Long.valueOf(goodsIdStr));
    }

    @PostMapping("/userCollected")
    public String userCollected(String token,String goodsIdStr ){
        return collectService.userCollected(token,Long.valueOf(goodsIdStr));
    }

}

