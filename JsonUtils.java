package cn.xiedacon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 * <h1>JSON工具类</h1>
 * <h3>依赖：</h3>
 * <ul>
 * <li>jackson</li>
 * <li>commons-io</li>
 * </ul>
 * 
 * @author xiedacon
 * @version v0.0.0
 *
 */
public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T parse(String json, Class<T> c) {
		try {
			return objectMapper.readValue(json, c);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> parseList(byte[] jsonBytes, Class<T> c) {
		return JsonUtils.parseCollection(jsonBytes, List.class, c);
	}

	public static <T> List<T> parseList(URL jsonURL, Class<T> c) {
		return JsonUtils.parseCollection(jsonURL, List.class, c);
	}

	public static <T> List<T> parseList(String json, Class<T> c) {
		return JsonUtils.parseCollection(json, List.class, c);
	}

	public static <T> List<T> parseList(File jsonFile, Class<T> c) {
		return JsonUtils.parseCollection(jsonFile, List.class, c);
	}

	public static <T> List<T> parseList(Reader in, Class<T> c) {
		return JsonUtils.parseCollection(in, List.class, c);
	}

	public static <T> List<T> parseList(InputStream in, Class<T> c) {
		return JsonUtils.parseCollection(in, List.class, c);
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static <T> List<T> parseCollection(byte[] jsonBytes, Class<? extends Collection> collectionType,
			Class<T> elementType) {
		try {
			return objectMapper.readValue(jsonBytes, TypeFactory.collectionType(collectionType, elementType));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static <T> List<T> parseCollection(URL jsonURL, Class<? extends Collection> collectionType,
			Class<T> elementType) {
		try {
			return parseCollection(jsonURL.openStream(), collectionType, elementType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> List<T> parseCollection(String json, Class<? extends Collection> collectionType,
			Class<T> elementType) {
		return parseCollection(new StringReader(json), collectionType, elementType);
	}

	@SuppressWarnings("rawtypes")
	public static <T> List<T> parseCollection(File jsonFile, Class<? extends Collection> collectionType,
			Class<T> elementType) {
		try (FileInputStream in = new FileInputStream(jsonFile)) {
			return parseCollection(in, collectionType, elementType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> List<T> parseCollection(Reader in, Class<? extends Collection> collectionType,
			Class<T> elementType) {
		try {
			return parseCollection(IOUtils.toByteArray(in), collectionType, elementType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static <T> List<T> parseCollection(InputStream in, Class<? extends Collection> collectionType,
			Class<T> elementType) {
		try {
			return parseCollection(IOUtils.toByteArray(in), collectionType, elementType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String stringify(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
