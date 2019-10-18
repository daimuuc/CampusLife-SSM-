package com.ponmma.cl.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			// 自定义生成图片存放地址
			basePath = "D:/projectdev/campus_life";
		} else if (os.toLowerCase().startsWith("mac")){
			// 自定义生成图片存放地址
			basePath = "/Users/antony/Desktop/项目/java-web/resources/campus_life";
		} else {
			// 自定义生成图片存放地址
			basePath = "/root/Project/campus_life";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	public static String getUserImagePath(Integer personInfoId) {
		String imagePath = "/upload/images/" + personInfoId + "/";
		return imagePath.replace("/", seperator);
	}


}
