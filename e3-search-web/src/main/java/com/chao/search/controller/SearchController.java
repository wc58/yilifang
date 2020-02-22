/**
 * 
 */
package com.chao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.pojo.SearchResult;
import com.chao.search.service.ISearchItemService;
import com.chao.search.service.ISearchService;
import com.chao.utils.E3Result;

/**
 * Title: SearchController.java
 * 
 * @author ChaoSir
 * @date 2019年10月1日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class SearchController {

	@Autowired
	private ISearchService iSearchService;

	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;

	@RequestMapping("/search")
	public String search(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page,
			Model model) {

		try {
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		用于测试全局异常	
//		int i = 1/0;
		
		SearchResult searchResult = iSearchService.searchResult(keyword, page, SEARCH_RESULT_ROWS);
		// 把结果传递给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecordCount());
		model.addAttribute("itemList", searchResult.getItemList());
		return "search";
	}

}
