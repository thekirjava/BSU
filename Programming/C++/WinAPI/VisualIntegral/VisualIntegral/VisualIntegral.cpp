// VisualIntegral.cpp : Определяет точку входа для приложения.
//

#include "stdafx.h"
#include "VisualIntegral.h"
#include <cmath>
#include <windowsx.h>
#define MAX_LOADSTRING 100
long int option = 0;
long double function(long double x)
{
	
	switch (option)
	{
	case 1:
		{
		return x*x;
		break;
		}
	case 2:
	{
		return sin(x);
		break;
	}
	case 3:
	{
		return cos(x);
		break;
	}
	}
}
void DrawGraph(long double l, long double r, int Ox, long double sz, long double left_border, long double right_border, HDC hdc, RECT rect, LPPOINT pnt)
{
	HPEN pen = CreatePen(PS_SOLID, 2, RGB(0, 0, 0));
	SelectPen(hdc, pen);
	MoveToEx(hdc, rect.left, (rect.bottom - rect.top) / 2, pnt);
	LineTo(hdc, rect.right, (rect.bottom - rect.top) / 2);
	MoveToEx(hdc, (rect.right - rect.left) / 2, rect.top, pnt);
	LineTo(hdc, (rect.right - rect.left) / 2, rect.bottom);
	int pos = 0;
	pen = CreatePen(PS_SOLID, 4, RGB(0, 0, 255));
	SelectPen(hdc, pen);
	MoveToEx(hdc, rect.left + pos, Ox - function(left_border) * sz, pnt);
	long double step = (right_border - left_border) / (rect.right - rect.left);
	for (long double i = left_border; i <= right_border; i += step)
	{
		if ((i >= l) && (i <= r))
		{
			LineTo(hdc, rect.left + pos, Ox - function(i) * sz);
		}
		else
		{
			MoveToEx(hdc, rect.left + pos, Ox - function(i) * sz, pnt);
		}
		pos++;
	}
}

// Глобальные переменные:
HINSTANCE hInst;                                // текущий экземпляр
WCHAR szTitle[MAX_LOADSTRING];                  // Текст строки заголовка
WCHAR szWindowClass[MAX_LOADSTRING];            // имя класса главного окна

// Отправить объявления функций, включенных в этот модуль кода:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
	_In_opt_ HINSTANCE hPrevInstance,
	_In_ LPWSTR    lpCmdLine,
	_In_ int       nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

	// TODO: Разместите код здесь.

	// Инициализация глобальных строк
	LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadStringW(hInstance, IDC_VISUALINTEGRAL, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// Выполнить инициализацию приложения:
	if (!InitInstance(hInstance, nCmdShow))
	{
		return FALSE;
	}

	HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_VISUALINTEGRAL));

	MSG msg;

	// Цикл основного сообщения:
	while (GetMessage(&msg, nullptr, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return (int)msg.wParam;
}



//
//  ФУНКЦИЯ: MyRegisterClass()
//
//  ЦЕЛЬ: Регистрирует класс окна.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
	WNDCLASSEXW wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);

	wcex.style = CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc = WndProc;
	wcex.cbClsExtra = 0;
	wcex.cbWndExtra = 0;
	wcex.hInstance = hInstance;
	wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_VISUALINTEGRAL));
	wcex.hCursor = LoadCursor(nullptr, IDC_ARROW);
	wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wcex.lpszMenuName = MAKEINTRESOURCEW(IDC_VISUALINTEGRAL);
	wcex.lpszClassName = szWindowClass;
	wcex.hIconSm = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

	return RegisterClassExW(&wcex);
}

//
//   ФУНКЦИЯ: InitInstance(HINSTANCE, int)
//
//   ЦЕЛЬ: Сохраняет маркер экземпляра и создает главное окно
//
//   КОММЕНТАРИИ:
//
//        В этой функции маркер экземпляра сохраняется в глобальной переменной, а также
//        создается и выводится главное окно программы.
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
	hInst = hInstance; // Сохранить маркер экземпляра в глобальной переменной

	HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
		CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, nullptr, nullptr, hInstance, nullptr);

	if (!hWnd)
	{
		return FALSE;
	}

	ShowWindow(hWnd, nCmdShow);
	UpdateWindow(hWnd);

	return TRUE;
}

//
//  ФУНКЦИЯ: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  ЦЕЛЬ: Обрабатывает сообщения в главном окне.
//
//  WM_COMMAND  - обработать меню приложения
//  WM_PAINT    - Отрисовка главного окна
//  WM_DESTROY  - отправить сообщение о выходе и вернуться
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	RECT rect;
	switch (message)
	{
	case WM_COMMAND:
	{
		int wmId = LOWORD(wParam);
		// Разобрать выбор в меню:
		switch (wmId)
		{
		case IDM_ABOUT:
			DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
			break;
		case IDM_EXIT:
			DestroyWindow(hWnd);
			break;
		case ID_32772:
			option = 0;
			break;
		case ID_32773:
			option = 1;
			break;
		case ID_32774:
			option = 2;
			break;
		case ID_32775:
			option = 3;
			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
		}
	}
	break;
	case WM_PAINT:
	{
		PAINTSTRUCT ps;
		LPPOINT pnt = new POINT;
		// TODO: Добавьте сюда любой код прорисовки, использующий HDC...
		GetClientRect(hWnd, &rect);
		long double l;
		long double r;
		int x;
		x = rect.left;
		x = rect.right;
		x = rect.top;
		x = rect.bottom;
		l = -20;
		r = 20;
		int A;
		A = 3;
		long double p = 1;
		for (int i = 0; i < A; i++)
		{
			p /= 10;
		}
		long double left_border = l;
		if (-r < left_border)
		{
			left_border = -r;
		}
		long double right_border = r;
		if (-l > right_border)
		{
			right_border = -l;
		}
		long double prev = -1000000;
		long double step = r - l;
		long double ans = -1000000;
		long double delta = 1000000;
		int Ox = abs(rect.bottom - rect.top) / 2;
		long double sz = (rect.right - rect.left) / (right_border - left_border);
		long double pos_step = (r - l) * sz;
		int cnt = 5;
		//DrawGraph(l, r, min, max, Ox, sz, hdc, rect, pnt);
		if ((option >= 1) && (option <= 3))
		{

			do
			{
				/*	for (int i = rect.top; i <= rect.bottom; i++)
					{
						for (int j = rect.left; j <= rect.right; j++)
						{
							SetPixel(hdc, j, i, RGB(255, 255, 255));
						}
					}*/
				HDC hdc = BeginPaint(hWnd, &ps);
				InvalidateRect(hWnd, NULL, true);
				DrawGraph(l, r, Ox, sz, left_border, right_border, hdc, rect, pnt);
				prev = ans;
				ans = 0;
				long double pos = (rect.left + rect.right) / 2 + l * sz;
				MoveToEx(hdc, pos, Ox - function(l) / sz, pnt);
				for (long double i = l; i <= r; i += step)
				{
					HPEN pen = CreatePen(PS_SOLID, 2, RGB(155, 0, 0));
					SelectPen(hdc, pen);
					if ((i == l) || (i == r))
					{
						ans += function(i);
					}
					else
					{
						ans += 2 * function(i);
					}
					LineTo(hdc, pos, Ox - function(i) * sz);
					LineTo(hdc, pos, Ox);
					MoveToEx(hdc, pos, Ox - function(i) * sz, pnt);
					pos += pos_step;
				}
				ans *= step;
				ans /= 2;
				step /= 2;
				pos_step /= 2;
				delta = fabs(ans - prev);
				cnt--;
				EndPaint(hWnd, &ps);
				Sleep(400);
			} while ((delta > p) || (cnt >= 0));
		}
	}
	break;
	case WM_DESTROY:
		PostQuitMessage(0);
		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}

// Обработчик сообщений для окна "О программе".
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
		{
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
		}
		break;
	}
	return (INT_PTR)FALSE;
}
