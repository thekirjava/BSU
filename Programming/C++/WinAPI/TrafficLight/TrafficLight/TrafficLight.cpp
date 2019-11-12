// TrafficLight.cpp : Определяет точку входа для приложения.
//

#include "stdafx.h"
#include "TrafficLight.h"
#include <Windows.h>
#include <windowsx.h>
#include <WinUser.h>

#define MAX_LOADSTRING 100

// Глобальные переменные:
HINSTANCE hInst;                                // текущий экземпляр
WCHAR szTitle[MAX_LOADSTRING];                  // Текст строки заголовка
WCHAR szWindowClass[MAX_LOADSTRING];            // имя класса главного окна
HBRUSH gray_brush = CreateSolidBrush(RGB(170, 170, 170));
HBRUSH black_brush = CreateSolidBrush(RGB(0, 0, 0));
HBRUSH red_brush = CreateSolidBrush(RGB(255, 0, 0));
HBRUSH yellow_brush = CreateSolidBrush(RGB(255, 255, 0));
HBRUSH green_brush = CreateSolidBrush(RGB(0, 255, 0));
HPEN pen = CreatePen(PS_SOLID, 5, RGB(0, 0, 0));

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
	LoadStringW(hInstance, IDC_TRAFFICLIGHT, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// Выполнить инициализацию приложения:
	if (!InitInstance(hInstance, nCmdShow))
	{
		return FALSE;
	}

	HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_TRAFFICLIGHT));

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
	wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_TRAFFICLIGHT));
	wcex.hCursor = LoadCursor(nullptr, IDC_ARROW);
	wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wcex.lpszMenuName = MAKEINTRESOURCEW(IDC_TRAFFICLIGHT);
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
	static int timer = 0;
	static int id = 0;
	static int radius = 60;
	static int periodic = 1000;
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
		case ID_SLOW:
		{
			periodic = 3000;
			KillTimer(hWnd, 42);
			id = SetTimer(hWnd, 42, periodic, NULL);
		}
		break;
		case ID_NORMAL:
		{
			periodic = 1000;
			KillTimer(hWnd, 42);
			id = SetTimer(hWnd, 42, periodic, NULL);
		}
		break;
		case ID_FAST:
		{
			periodic = 500;
			KillTimer(hWnd, 42);
			id = SetTimer(hWnd, 42, periodic, NULL);
		}
		break;
		case ID_SMALL:
		{
			radius = 40;
			InvalidateRect(hWnd, NULL, true);
		}
		break;
		case ID_MEDIUM: 
		{
			radius = 60;
			InvalidateRect(hWnd, NULL, true);
		}
		break;
		case ID_LARGE:
		{
			radius = 80;
			InvalidateRect(hWnd, NULL, true);
		}
		break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
		}
	}
	break;
	case WM_TIMER:
	{
		HDC hdc = GetDC(hWnd);
		PAINTSTRUCT ps;
		LPPOINT pnt = new POINT;
		//HDC hdc = BeginPaint(hWnd, &ps);
		GetClientRect(hWnd, &rect);
		int xmid = rect.right + rect.left;
		xmid /= 2;
		int ymid = rect.top + rect.bottom;
		ymid /= 2;
		SelectPen(hdc, pen);
		SelectBrush(hdc, gray_brush);
		SelectObject(hdc, GetStockObject(DC_BRUSH));
		SetDCBrushColor(hdc, RGB(170, 170, 170));
		Rectangle(hdc, xmid - radius - 10, ymid - (3 * radius) - 20, xmid + radius + 10, ymid + (3 * radius) + 20);
		switch (timer)
		{
		case 0:
		{
			SelectObject(hdc, red_brush);
			Ellipse(hdc, xmid - radius, ymid - (3 * radius) - 10, xmid + radius, ymid -radius - 10);
			SelectObject(hdc, black_brush);
			Ellipse(hdc, xmid - radius, ymid - radius, xmid + radius, ymid + radius);
			Ellipse(hdc, xmid - radius, ymid + radius + 10, xmid + radius, ymid + (3 * radius) + 10);
		}
		break;
		case 1:
		{
			SelectObject(hdc, yellow_brush);
			Ellipse(hdc, xmid - radius, ymid - radius, xmid + radius, ymid + radius);
			SelectObject(hdc, black_brush);
			Ellipse(hdc, xmid - radius, ymid - (3 * radius) - 10, xmid + radius, ymid - radius - 10);
			Ellipse(hdc, xmid - radius, ymid + radius + 10, xmid + radius, ymid + (3 * radius) + 10);
		}
		break;
		case 2:
		{
			SelectObject(hdc, green_brush);
			Ellipse(hdc, xmid - radius, ymid + radius + 10, xmid + radius, ymid + (3 * radius) + 10);
			SelectObject(hdc, black_brush);
			Ellipse(hdc, xmid - radius, ymid - (3 * radius) - 10, xmid + radius, ymid - radius - 10);
			Ellipse(hdc, xmid - radius, ymid - radius, xmid + radius, ymid + radius);
		}
		break;
		}
		timer++;
		timer %= 3;
		//EndPaint(hWnd, &ps);
	}
	break;
	case WM_PAINT:
	{
		PAINTSTRUCT ps;
		GetClientRect(hWnd, &rect);
		HDC hdc = BeginPaint(hWnd, &ps);
		id = SetTimer(hWnd, 42, periodic, NULL);
		ReleaseDC(hWnd, hdc);
		// TODO: Добавьте сюда любой код прорисовки, использующий HDC...
		
		EndPaint(hWnd, &ps);
	}
	break;
	case WM_DESTROY:
		KillTimer(hWnd, 42);
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
