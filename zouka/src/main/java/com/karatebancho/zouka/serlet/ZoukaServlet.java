/**
 * 
 */
package com.karatebancho.zouka.serlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.karatebancho.zouka.impl.RomanFilter;
import com.karatebancho.zouka.tree.TrieTree;

/**
 * @author ayataro
 * 
 */
public class ZoukaServlet extends HttpServlet {
	protected static TrieTree<String> root;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		RomanFilter filter = new RomanFilter();
		InputStream is = this.getClass().getResourceAsStream("/data.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str;
		root = new TrieTree<>();
		try {
			int i = 0;
			while ((str = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(str);
				String val = tokenizer.nextToken();
				String key = tokenizer.nextToken();
				root.put(filter.filter(key), val);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String key = req.getParameter("key");
		List<String> candidates = root.findValues(key);
		resp.addHeader("content-type", "application/json;charset=utf-8");
		resp.getWriter().print("{");
		resp.getWriter().print("\"candidate\":[");
		int cnt = 0;
		for (String candidate : candidates) {
			if (cnt++ > 0) {
				resp.getWriter().print(",");
			}
			resp.getWriter().print("\"" + candidate + "\"");
		}
		resp.getWriter().print("]");
		resp.getWriter().print("}");
	}
}
