// VisualQueue.cpp : Определяет точку входа для приложения.
//

#include "stdafx.h"
#include "VisualQueue.h"
#include "Model.h"
#include "View.h"
#include <stdlib.h>
#include <string>

#define MAX_LOADSTRING 100
#define ID_PUSH1 1001
#define ID_POP1 1002
#define ID_FRONT1 1003
#define ID_BACK1 1004
#define ID_SIZE1 1005
#define ID_EMPTY1 1006
#define ID_CLEAR1 1007
#define ID_OUT1 1008
#define ID_PUSH2 1009
#define ID_POP2 1010
#define ID_FRONT2 1011
#define ID_BACK2 1012
#define ID_SIZE2 1013
#define ID_EMPTY2 1014
#define ID_CLEAR2 1015
#define ID_OUT2 1016
#define ID_SWAP 1017
#define ID_FORM1 1018
#define ID_FORM2 1019
#define ID_EQUAL 1020
#define ID_NOTEQUAL 1021
#define ID_PLUS 1022
#define ID_MIN1 1023
#define ID_MIN2 1024
#define ID_MAX1 1025
#define ID_MAX2 1026

using namespace std;

// Глобальные переменные:
HINSTANCE hInst;                                // текущий экземпляр
WCHAR szTitle[MAX_LOADSTRING];                  // Текст строки заголовка
WCHAR szWindowClass[MAX_LOADSTRING];            // имя класса главного окна

// Отправить объявления функций, включенных в этот модуль кода:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

MyQueue q1;
MyQueue q2;
View v;
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
    LoadStringW(hInstance, IDC_VISUALQUEUE, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Выполнить инициализацию приложения:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_VISUALQUEUE));

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
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_VISUALQUEUE));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_VISUALQUEUE);
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
	static HWND Push1;
	static HWND Form1;
	static HWND Pop1;
	static HWND Size1;
	static HWND IsEmpty1;
	static HWND Front1;
	static HWND Back1;
	static HWND Clear1;
	static HWND Out1;
	static HWND Push2;
	static HWND Form2;
	static HWND Pop2;
	static HWND Size2;
	static HWND IsEmpty2;
	static HWND Front2;
	static HWND Back2;
	static HWND Clear2;
	static HWND Out2;
	static HWND Swap;
	static HWND Equal;
	static HWND NotEqual;
	static HWND Plus;
	static HWND Minimal1;
	static HWND Minimal2;
	static HWND Maximal1;
	static HWND Maximal2;
	
    switch (message)
    {
	case WM_CREATE:
		{
			Push1 = CreateWindow("button", "Push", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 20, 80, 30, hWnd, (HMENU)ID_PUSH1, hInst, NULL);
			Form1 = CreateWindow("Edit", NULL, WS_EX_CLIENTEDGE | WS_BORDER | WS_CHILD | WS_VISIBLE, 1080, 20, 80, 30, hWnd, (HMENU)ID_FORM1, hInst, 0);
			Pop1 = CreateWindow("button", "Pop", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 60, 80, 30, hWnd, (HMENU)ID_POP1, hInst, NULL);
			Clear1 = CreateWindow("button", "Clear", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 60, 80, 30, hWnd, (HMENU)ID_CLEAR1, hInst, NULL);
			Size1 = CreateWindow("button", "Size", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 100, 80, 30, hWnd, (HMENU)ID_SIZE1, hInst, NULL);
			IsEmpty1 = CreateWindow("button", "Is Empty", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 100, 80, 30, hWnd, (HMENU)ID_EMPTY1, hInst, NULL);
			Front1 = CreateWindow("button", "Front", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 140, 80, 30, hWnd, (HMENU)ID_FRONT1, hInst, NULL);
			Back1 = CreateWindow("button", "Back", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 140, 80, 30, hWnd, (HMENU)ID_BACK1, hInst, NULL);
			Out1 = CreateWindow("Edit", NULL, WS_EX_CLIENTEDGE | WS_BORDER | WS_CHILD | WS_VISIBLE | WS_HSCROLL | ES_MULTILINE, 10, 70, 880, 70, hWnd, (HMENU)ID_OUT1, hInst, 0);
			SendMessage(Out1, EM_SETREADONLY, TRUE, 0);
			Minimal1 = CreateWindow("button", "Min", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 180, 80, 30, hWnd, (HMENU)ID_MIN1, hInst, NULL);
			Maximal1 = CreateWindow("button", "Max", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 180, 80, 30, hWnd, (HMENU)ID_MAX1, hInst, NULL);
			Push2 = CreateWindow("button", "Push", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 300, 80, 30, hWnd, (HMENU)ID_PUSH2, hInst, NULL);
			Form2 = CreateWindow("Edit", NULL, WS_EX_CLIENTEDGE | WS_BORDER | WS_CHILD | WS_VISIBLE, 1080, 300, 80, 30, hWnd, (HMENU)ID_FORM2, hInst, 0);
			Pop2 = CreateWindow("button", "Pop", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 340, 80, 30, hWnd, (HMENU)ID_POP2, hInst, NULL);
			Clear2 = CreateWindow("button", "Clear", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 340, 80, 30, hWnd, (HMENU)ID_CLEAR2, hInst, NULL);
			Size2 = CreateWindow("button", "Size", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 380, 80, 30, hWnd, (HMENU)ID_SIZE2, hInst, NULL);
			IsEmpty2 = CreateWindow("button", "Is Empty", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 380, 80, 30, hWnd, (HMENU)ID_EMPTY2, hInst, NULL);
			Front2 = CreateWindow("button", "Front", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 420, 80, 30, hWnd, (HMENU)ID_FRONT2, hInst, NULL);
			Back2 = CreateWindow("button", "Back", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 420, 80, 30, hWnd, (HMENU)ID_BACK2, hInst, NULL);
			Out2 = CreateWindow("Edit", NULL, WS_EX_CLIENTEDGE | WS_BORDER | WS_CHILD | WS_VISIBLE | WS_HSCROLL | ES_MULTILINE, 10, 360, 880, 70, hWnd, (HMENU)ID_OUT2, hInst, 0);
			SendMessage(Out2, EM_SETREADONLY, TRUE, 0);
			Minimal2 = CreateWindow("button", "Min", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 990, 460, 80, 30, hWnd, (HMENU)ID_MIN2, hInst, NULL);
			Maximal2 = CreateWindow("button", "Max", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 1080, 460, 80, 30, hWnd, (HMENU)ID_MAX2, hInst, NULL);
			Equal = CreateWindow("button", "==", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 415, 530, 80, 30, hWnd, (HMENU)ID_EQUAL, hInst, NULL);
			Swap = CreateWindow("button", "Swap", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 505, 530, 80, 30, hWnd, (HMENU)ID_SWAP, hInst, NULL);
			Plus = CreateWindow("button", "q1 += q2", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 595, 530, 80, 30, hWnd, (HMENU)ID_PLUS, hInst, NULL);
			NotEqual = CreateWindow("button", "!=", WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON, 685, 530, 80, 30, hWnd, (HMENU)ID_NOTEQUAL, hInst, NULL);
			v.Create(hWnd, Out1, Out2, q1, q2);		
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
			case ID_PUSH1:
			{
				char *cstr = new char[100];
				int x;
				SendMessage(Form1, WM_GETTEXT, 100, (LPARAM)cstr);
				x = atoi(cstr);
				string s1 = to_string(x);
				string s2 = cstr;
				if (s1 == s2)
				{
					q1.Push(x);

				}
				else
				{
					v.PushError();
				}
				SendMessage(Form1, WM_SETTEXT, NULL, NULL);
			}
			break;
			case ID_POP1:
			{
				if (q1.Size() != 0)
				{
					q1.Pop();
				}
				else
				{
					v.EmptyError();
				}
			}
			break;
			case ID_CLEAR1:
			{
				if (q1.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					q1.Clear();
				}
			}
			break;
			case ID_SIZE1:
			{
				int x = q1.Size();
				v.SizeInfo(x);
			}
			break;
			case ID_EMPTY1:
			{
				bool flag = q1.IsEmpty();
				if (flag)
				{
					v.EmptyInfo();
				}
				else
				{
					v.NotEmptyInfo();
				}
			}
			break;
			case ID_FRONT1:
			{
				if (q1.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					int x = q1.Front();
					v.FrontInfo(x);
				}
			}
			break;
			case ID_BACK1:
			{
				if (q1.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					int x = q1.Back();
					v.BackInfo(x);
				}
			}
			break;
			case ID_MIN1:
			{
				if (q1.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					Min m;
					q1.accept(m);
					int x = m.ReturnAns(true);
					v.MinInfo(x);
				}
			}
			break;
			case ID_MAX1:
			{
				if (q1.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					Max m;
					q1.accept(m);
					int x = m.ReturnAns(true);
					v.MaxInfo(x);
				}
			}
			break;
			case ID_PUSH2:
			{
				char *cstr = new char[100];
				int x;
				SendMessage(Form2, WM_GETTEXT, 100, (LPARAM)cstr);
				x = atoi(cstr);
				string s1 = to_string(x);
				string s2 = cstr;
				if (s1 == s2)
				{
					q2.Push(x);

				}
				else
				{
					v.PushError();
				}
				SendMessage(Form2, WM_SETTEXT, NULL, NULL);
			}
			break;
			case ID_POP2:
			{
				if (q2.Size() != 0)
				{
					q2.Pop();
				}
				else
				{
					v.EmptyError();
				}
			}
			break;
			case ID_CLEAR2:
			{
				if (q2.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					q2.Clear();
				}
			}
			break;
			case ID_SIZE2:
			{
				int x = q2.Size();
				v.SizeInfo(x);
			}
			break;
			case ID_EMPTY2:
			{
				bool flag = q2.IsEmpty();
				if (flag)
				{
					v.EmptyInfo();
				}
				else
				{
					v.NotEmptyInfo();
				}
			}
			break;
			case ID_FRONT2:
			{
				if (q2.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					int x = q2.Front();
					v.FrontInfo(x);
				}
			}
			break;
			case ID_BACK2:
			{
				if (q2.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					int x = q2.Back();
					v.BackInfo(x);
				}
			}
			break;
			case ID_MIN2:
			{
				if (q2.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					Min m;
					q2.accept(m);
					int x = m.ReturnAns(true);
					v.MinInfo(x);
				}
			}
			break;
			case ID_MAX2:
			{
				if (q2.IsEmpty())
				{
					v.EmptyError();
				}
				else
				{
					Max m;
					q2.accept(m);
					int x = m.ReturnAns(true);
					v.MaxInfo(x);
				}
			}
			break;
			case ID_SWAP:
			{
				SwapQue(q1, q2);
			}
			break;
			case ID_EQUAL:
			{
				bool flag = (q1 == q2);
				v.EqualInfo(flag);
			}
			break;
			case ID_NOTEQUAL:
			{
				bool flag = (q1 != q2);
				v.NotEqualInfo(flag);
			}
			break;
			case ID_PLUS:
			{
				q1 += q2;
			}
			break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
			// Вывод перевернуть и нормально преобразовывать число  
			//ГОТОВО
			/*string s="";
			int N = q1.Size();
			for (int i = 0; i < N; i++)
			{
				s += to_string(q1.Front());
				s += " ";
				q1.Push(q1.Front());
				q1.Pop();
			}
			const char *que1 = s.c_str();
			SendMessage(Out1, WM_SETTEXT, NULL, (LPARAM)que1);
			s.clear();
			N = q2.Size();
			for (int i = 0; i < N; i++)
			{
				s += to_string(q2.Front());
				s += " ";
				q2.Push(q2.Front());
				q2.Pop();
			}
			const char *que2 = s.c_str();
			SendMessage(Out2, WM_SETTEXT, NULL, (LPARAM)que2);*/
			v.Update();
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            // TODO: Добавьте сюда любой код прорисовки, использующий HDC...
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
