package com.tenco.blog.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SitemapController {

	@GetMapping("/_sitemap/sitemap")
	public String sitemap() {
		return "_sitemap/sitemap";
	}
}
