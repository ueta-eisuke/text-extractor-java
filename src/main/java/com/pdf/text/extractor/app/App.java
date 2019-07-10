package com.pdf.text.extractor.app;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdf.text.extractor.config.ExtractConfig;
import com.pdf.text.extractor.config.ExtractResult;
import com.pdf.text.extractor.service.ExtractDetailService;
import com.pdf.text.extractor.service.ExtractDetailServiceImpl;
import com.pdf.text.extractor.service.ExtractHeaderService;
import com.pdf.text.extractor.service.ExtractHeaderServiceImpl;

/**
 * Hello world!
 *
 */
public class App {

	final static String FILE_PATH = System.getenv("FILE_PATH");
	final static String JSON_PATH = System.getenv("JSON_PATH");

	public static void main(String[] args) {

		final File file = new File(FILE_PATH);

		// Build configuration
		ExtractConfig config = getExtractConfigFromJSON();
//		config.getHeaderConfig().setDebug(true);

		// Extract header items
		ExtractHeaderService extractHeaderService = new ExtractHeaderServiceImpl();
		final Map<String, String> headerItems = extractHeaderService.execute(config.getHeaderConfig(), file);

		// Extract detail items
		ExtractDetailService extractDetailService = new ExtractDetailServiceImpl();
		final List<Map<String, String>> detailItems = extractDetailService.execute(config.getDetailConfig(), file);

		// Build result
		final ExtractResult result = new ExtractResult();
		result.setHeaderItems(headerItems);
		result.setDetailItems(detailItems);

		// Output result
		for (Map.Entry<String, String> entry : result.getHeaderItems().entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		for (Map<String, String> details : result.getDetailItems()) {
			System.out.print(details.get("detail_no") + ", ");
			System.out.print(details.get("detail_name_1") + ", ");
			System.out.print(details.get("detail_name_2") + ", ");
			System.out.print(details.get("detail_spec_1") + ", ");
			System.out.print(details.get("detail_spec_2"));
			System.out.println("");
		}

	}

	private static ExtractConfig getExtractConfigFromJSON() {
		ObjectMapper mapper = new ObjectMapper();
		ExtractConfig config = null;
		try {
			File file = new File(JSON_PATH);
			config = mapper.readValue(file, ExtractConfig.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

}