package com.peoce.wesee.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.peoce.wesee.constant.Const;
import com.peoce.wesee.model.User;
import com.peoce.wesee.utils.UserUtil;

/**
 * User login servlet
 * @author wangxm
 *
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 0x0000L;
	private  int ERROR_CODE = Const.error_code.SUCCESS;
	private User u = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		int id = 0;
		ERROR_CODE = Const.error_code.SUCCESS;
		String phoneNumber = null;
		String nickName = null;
		String password = null;

		phoneNumber = req.getParameter(Const.users.USERS_PHONENUMBER);
		nickName = req.getParameter(Const.users.USERS_NICKNAME);
		password = req.getParameter(Const.users.USERS_PASSWORD);

		if (phoneNumber == null && nickName == null) {
			String strId = req.getParameter(Const.users.USERS_ID);
			if (password == null) {
				ERROR_CODE = Const.error_code.PASSWORD_MISSED_ERROT;
				System.out.println("password missed !!!");
				write(ERROR_CODE, resp);
			} else if (strId == null) {
				ERROR_CODE = Const.error_code.PARAM_MISSED_ERROT;
				System.out.println("parameters missed !!!");
				write(ERROR_CODE, resp);
			} else if (strId != null) {
				try {
					id = Integer.valueOf(strId);
				} catch (NumberFormatException e) {
					ERROR_CODE = Const.error_code.PARAM_MALE_FORMAT_ERROT;
					System.out.println("should enter a valid id number!!!");
					write(ERROR_CODE, resp);
					e.printStackTrace();
				}
			}
		}
		if (password != null) {
			if (id != 0) {
				u = UserUtil.getInstance().loginWithId(id, password);
			} else if (phoneNumber != null) {
				u = UserUtil.getInstance()
						.loginWithPhone(phoneNumber, password);
			} else if (nickName != null) {
				u = UserUtil.getInstance().loginWithNick(nickName, password);
			}
			write(ERROR_CODE, resp);
		}else {
			ERROR_CODE = Const.error_code.PASSWORD_MISSED_ERROT;
			System.out.println("password missed !!!");
			write(ERROR_CODE, resp);
		}

	}

	private void write(int error_code, HttpServletResponse resp) {
		PrintWriter pw = null;
		try {
			pw = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Const.error_code.SUCCESS == error_code && null != u) {
			String respJson = JSON.toJSONString(u);
			pw.write(respJson);
		} else {
			pw.write("{errorcode:" + ERROR_CODE + "}");
		}
		pw.flush();
		pw.close();
	}
}
