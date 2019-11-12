// VisualCastle.cpp : Определяет точку входа для приложения.
//

#include "stdafx.h"
#include "VisualCastle.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <time.h>
#include <cstdlib>
#include <Windows.h>
#include <windowsx.h>
#include <WinUser.h>
#include <CommCtrl.h>
#include <tchar.h>"
#include<commdlg.h>
#pragma comment(lib,"ComCtl32.Lib")
#include <ctime>
#include <string>

using namespace std;
#define ID_STATUS 99
const int freespace = 5;
vector <vector <int> > castle(int& x1, int& y1, int& x2, int& y2, int& roomcnt, int& square)
{
	ifstream in("input.txt");
	ofstream out("output.txt");
	int M, N;
	in >> M >> N;
	vector < vector<int> > castle(M, vector<int>(N));
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			in >> castle[i][j];
		}
	}
	vector <vector <int> > rooms(M, vector<int>(N, -1));
	vector <int> roomsize;
	roomcnt = 0;
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			if (rooms[i][j] == -1)
			{
				roomcnt++;
				queue<pair<int, int> > way;
				way.push(make_pair(i, j));
				roomsize.push_back(0);
				rooms[i][j] = roomcnt;
				while (way.size() != 0)
				{
					roomsize[roomcnt - 1]++;
					if (castle[way.front().first][way.front().second] >= 8)
					{
						castle[way.front().first][way.front().second] -= 8;
					}
					else
					{
						if (rooms[way.front().first + 1][way.front().second] == -1)
						{
							way.push(make_pair(way.front().first + 1, way.front().second));
							rooms[way.front().first + 1][way.front().second] = roomcnt;
						}
					}
					if (castle[way.front().first][way.front().second] >= 4)
					{
						castle[way.front().first][way.front().second] -= 4;
					}
					else
					{
						if (rooms[way.front().first][way.front().second + 1] == -1)
						{
							way.push(make_pair(way.front().first, way.front().second + 1));
							rooms[way.front().first][way.front().second + 1] = roomcnt;
						}
					}
					if (castle[way.front().first][way.front().second] >= 2)
					{
						castle[way.front().first][way.front().second] -= 2;
					}
					else
					{
						if (rooms[way.front().first - 1][way.front().second] == -1)
						{
							way.push(make_pair(way.front().first - 1, way.front().second));
							rooms[way.front().first - 1][way.front().second] = roomcnt;
						}
					}
					if (castle[way.front().first][way.front().second] >= 1)
					{
						castle[way.front().first][way.front().second] -= 1;
					}
					else
					{
						if (rooms[way.front().first][way.front().second - 1] == -1)
						{
							way.push(make_pair(way.front().first, way.front().second - 1));
							rooms[way.front().first][way.front().second - 1] = roomcnt;
						}
					}
					way.pop();
				}
			}
		}
	}
	out << "Number of rooms:\n";
	out << roomcnt << '\n';
	int ans = roomsize[0];
	for (int i = 0; i < roomcnt; i++)
	{
		ans = max(ans, roomsize[i]);
	}
	out << "Biggest room square:\n";
	out << ans << '\n';
	square = ans;
	ans = 0;
	pair <int, int> room_1;
	pair <int, int> room_2;
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			if (i + 1 != M)
			{
				if (rooms[i][j] != rooms[i + 1][j])
				{
					if (roomsize[rooms[i][j] - 1] + roomsize[rooms[i + 1][j] - 1] > ans)
					{
						ans = roomsize[rooms[i][j] - 1] + roomsize[rooms[i + 1][j] - 1];
						room_1.first = i;
						room_1.second = j;
						room_2.first = i + 1;
						room_2.second = j;
					}
				}
			}
			if (j + 1 != N)
			{
				if (rooms[i][j] != rooms[i][j + 1])
				{
					if (roomsize[rooms[i][j] - 1] + roomsize[rooms[i][j + 1] - 1] > ans)
					{
						ans = roomsize[rooms[i][j] - 1] + roomsize[rooms[i][j + 1] - 1];
						room_1.first = i;
						room_1.second = j;
						room_2.first = i;
						room_2.second = j + 1;
					}
				}
			}
		}
	}
	out << "Square of two rooms and position:\n";
	x1 = room_1.first;
	y1 = room_1.second;
	x2 = room_2.first;
	y2 = room_2.second;
	out << ans << '\n' << x1 + 1 << " " << y1 + 1 << '\n' << x2 + 1 << " " << y2 + 1 << '\n';
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			rooms[i][j]--;
		}
	}
	return rooms;
}
#define MAX_LOADSTRING 100

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
    LoadStringW(hInstance, IDC_VISUALCASTLE, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Выполнить инициализацию приложения:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_VISUALCASTLE));

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

    return (int) msg.wParam;
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

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_VISUALCASTLE));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_VISUALCASTLE);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

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
	srand(time(NULL));
	LPPOINT pnt_pen = new POINT;
	static HWND hstatus;
	static int pParts[4];
    switch (message)
    {
	case WM_CREATE:
	{
		hstatus = CreateStatusWindow(WS_CHILD | WS_VISIBLE | SBARS_SIZEGRIP, L"", hWnd, ID_STATUS);
		
		

	}
	break;
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
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
	case WM_SIZE:
	{
		pParts[0] = LOWORD(lParam) / 4;
		pParts[1] = 2 * LOWORD(lParam) / 4;
		pParts[2] = 3 * LOWORD(lParam) / 4;
		pParts[3] = LOWORD(lParam) - 1;
		SendMessage(hstatus, WM_SIZE, 0, 0);
		SendMessage(hstatus, SB_SETPARTS, 4, (LPARAM)pParts);
	}
		break;
    case WM_PAINT:
        {
		SendMessage(hstatus, SB_SETTEXT, 0, (LONG)L"Количество комнат");
		SendMessage(hstatus, SB_SETTEXT, 2, (LONG)L"Площадь наибольшей");
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
			GetClientRect(hWnd, &rect);
			int x1, y1, x2, y2, roomcnt = 0;
			int square = 0;
			vector <vector <int> > rooms = castle(x1, y1, x2, y2, roomcnt, square); 
			char buf[10];
			sprintf_s(buf, "%d", roomcnt);
			SendMessage(hstatus, SB_SETTEXT, 1, (LONG)(wchar_t)buf);
			sprintf_s(buf, "%d", square);
			SendMessage(hstatus, SB_SETTEXT, 3, (LONG)(wchar_t)buf);
			int M = rooms.size();
			int N = rooms[0].size();
			int width = rect.right - rect.left - (freespace * 2);
			width /= M;
			int height = rect.bottom - rect.top - (freespace * 2);
			height /= N;
			int side = min(width, height);
			vector <COLORREF> roomclr(roomcnt, RGB(255, 255, 255));
			for (int i = 0; i < M; i++)
			{
				for (int j = 0; j < N; j++)
				{
					if (roomclr[rooms[i][j]] == RGB(255, 255, 255))
					{
						roomclr[rooms[i][j]] = RGB(rand() % 255 + 1, rand() % 255 + 1, rand() % 255 + 1);
					}
					HBRUSH brush = CreateSolidBrush(roomclr[rooms[i][j]]);
					HPEN pen = CreatePen(PS_SOLID, 1, roomclr[rooms[i][j]]);
					SelectPen(hdc, pen);
					SelectBrush(hdc, brush);
					Rectangle(hdc, (j*side) + freespace, (i*side) + freespace, ((j + 1)*side) + freespace, ((i + 1)*side) + freespace);
				}
			}
			HPEN pen = CreatePen(PS_SOLID, 5, RGB(0, 0, 0));
			SelectPen(hdc, pen);
			MoveToEx(hdc, (x2*side) + freespace, (y2*side) + freespace, pnt_pen);
			if (y1 != y2)
			{
				LineTo(hdc, (x2*side) + freespace, ((y2 + 1)*side) + freespace);
			}
			else
			{
				LineTo(hdc, ((x2 + 1)*side) + freespace, (y2*side) + freespace);
			}
            EndPaint(hWnd, &ps);
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
