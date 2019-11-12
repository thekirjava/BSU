#include <Windows.h>
#include "resource.h"
#include <cstdio>
#include "list.h"
#include "View.h"

INT_PTR CALLBACK Controller(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam);
int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
	_In_opt_ HINSTANCE hPrevInstance,
	_In_ LPWSTR    lpCmdLine,
	_In_ int       nCmdShow)
{
	DialogBox(hInstance, MAKEINTRESOURCE(IDD_DIALOG1), NULL, Controller);
	return 0;
}

INT_PTR CALLBACK  Controller(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	static List model;
	//static List_iterator it_list(&model);
	static View model_view(new List_iterator(&model),hDlg,IDC_LIST1);
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		switch (LOWORD(wParam)) 
		{
		case IDC_ADD_B:
			int res;
			res = GetDlgItemInt(hDlg, IDC_EDIT1, NULL, FALSE);
			model.PushFront(res);
			model_view.update();
			break;
		case  IDC_DELETE_B:
			int index;
			index=model.Back();
			model.PopBack();
			if (index != LB_ERR)
			{
				model_view.update();
			}
			break;
		case IDCANCEL:
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
			break;
		}
	}
	return (INT_PTR)FALSE;
}