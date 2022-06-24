package org.gl.controller;


import org.gl.entity.Footprint;
import org.gl.entity.Goods;
import org.gl.service.FootprintService;
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
 * @since 2022-03-13
 */
@CrossOrigin
@RestController
@RequestMapping("/footprint")
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    @PostMapping("/addUserFootprint")
    public Integer addUserFootprint(String token,String goodsIdStr){
        return footprintService.addFootprint(token,Long.valueOf(goodsIdStr));
    }

    @PostMapping("/removeUserFootprint")
    public Map<String, Object> removeUserFootprint(String token, String... footprintIdStr){
        Long[] footprintIds=new Long[footprintIdStr.length];
        for (int i = 0; i < footprintIdStr.length; i++) {
            footprintIds[i]=Long.valueOf(footprintIdStr[i]);
        }
        return footprintService.removeFootprint(token,footprintIds);
    }

    @GetMapping("/getUserFootprint")
    public List<Footprint> getUserFootprint(String token){
        return footprintService.getUserFootprint(token);
    }

}

