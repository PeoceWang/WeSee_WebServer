package com.peoce.wesee.servlet.content;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.peoce.wesee.constant.Const;
import com.peoce.wesee.model.Pic;
import com.peoce.wesee.utils.ContentUtil;

/**
 * return current hot Pic
 * 
 * @author wangxm
 * 
 */
public class WhatnewServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private int error_code = Const.error_code.SUCCESS;
	ArrayList<Pic> list_pic;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");

		error_code = Const.error_code.SUCCESS;
		list_pic = new ArrayList<Pic>();

		String start = req.getParameter(Const.page.PARA_START);
		String off = req.getParameter(Const.page.PARA_OFF);
		if (start == null || off == null) {
			list_pic = ContentUtil.getInstance().getWhatNew();
		} else {
			list_pic = ContentUtil.getInstance().getWhatNew(
					Integer.valueOf(start), Integer.valueOf(off));
		}

		PrintWriter pw = null;
		try {
			pw = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Const.error_code.SUCCESS == error_code) {
			String respJson = JSON.toJSONString(list_pic);
			pw.write(respJson);
		} else {
			pw.write("{errorcode:" + error_code + "}");
		}

		pw.flush();
		pw.close();

	}

}
