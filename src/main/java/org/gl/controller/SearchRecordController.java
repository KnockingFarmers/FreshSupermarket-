package org.gl.controller;


import org.gl.entity.Goods;
import org.gl.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-16
 */
@RestController
@CrossOrigin
@RequestMapping("/searchRecord")
public class SearchRecordController {

    @Autowired
    private SearchRecordService searchRecordService;

    @GetMapping("/searchGoods")
    public List<Goods> searchGoods(String keyword, String token){
            return searchRecordService.searchGoods(keyword,token);
    }

    @GetMapping("/getSearchRecord")
    public List getSearchRecord(String token){
        return searchRecordService.getUserSearchRecord(token);
    }

    @GetMapping("/getHotSearch")
    public List getHotSearch(){
        return searchRecordService.getHotSearch();
    }
}

