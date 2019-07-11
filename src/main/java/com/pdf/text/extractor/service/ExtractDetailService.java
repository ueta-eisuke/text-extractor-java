package com.pdf.text.extractor.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.pdf.text.extractor.config.DetailConfig;

public interface ExtractDetailService {

	/**
	 * Extract text from file
	 * 
	 * @param config
	 * @param file
	 * @return
	 */
	public List<Map<String, String>> execute(DetailConfig config, InputStream file);

}
