package com.exterro.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/home")
    @ResponseBody
    public String home() {
    return
    		//"ChooseBatch.html";
    		
"<br></br>\r\n" + "	<h3><a href='ChooseBatch.html'>Student feedback</a><h3><br>\r\n";
    }
    
    
	@RequestMapping("/reports")
	public String admin() {
		return "OverAllResult.html";
	}
	
	

}
