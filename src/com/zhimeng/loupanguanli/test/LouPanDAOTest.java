package com.zhimeng.loupanguanli.test;

import android.test.AndroidTestCase;

import com.zhimeng.loupanguanli.dao.LouPanDAO;
import com.zhimeng.loupanguanli.entity.LouPan;

public class LouPanDAOTest extends AndroidTestCase {
	private LouPanDAO lpdao;

	public void insertTest() {
		lpdao = new LouPanDAO(this.getContext());
		lpdao.insert(new LouPan("sss", "asdasdasd", "gfjnhfghjfghjfg", "poooo"));
		for (Integer i = 1; i <= 30; i++) {
			lpdao.insert(new LouPan("山水人家" + String.valueOf(i), "XXX市XX大道XX地段",
					"XXXXXXXXXXXXXXXX", ""));

		}
	}

	public void updateTest() {

	}
}
