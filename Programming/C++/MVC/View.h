#pragma once
#include <Windows.h>
#include"list.h"
#include <cstdio>
class View
{
private:
	Iterator* model;
	HWND hdlg;
	int idc_list;
public:
	View(Iterator* it, HWND hwnd,int idc) : model(it), hdlg(hwnd),idc_list(idc) {}
	void update()
	{
		SendDlgItemMessage(hdlg, idc_list, LB_RESETCONTENT, 0, 0);
		//List_iterator it(model);
		for (model->First();!model->Is_done();model->next())
		{
			char buf[100];
			sprintf_s(buf, 100, "%d",model->current_item());
			SendDlgItemMessage(hdlg, idc_list, LB_ADDSTRING, 0, (LPARAM)buf);
		}
	}
};