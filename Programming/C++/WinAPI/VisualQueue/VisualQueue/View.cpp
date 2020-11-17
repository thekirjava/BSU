#include "stdafx.h"
#include "View.h"
#include <string>
using namespace std;


View::View()
{
	hWnd = nullptr;
	Out1 = nullptr;
	Out2 = nullptr;
}

View::View(HWND h, HWND o1, HWND o2, MyQueue &que1, MyQueue &que2)
{
	hWnd = h;
	Out1 = o1;
	Out2 = o2;
	q1 = &que1;
	q2 = &que2;
}

void View::Create(HWND h, HWND o1, HWND o2, MyQueue &que1, MyQueue &que2)
{
	hWnd = h;
	Out1 = o1;
	Out2 = o2;
	q1 = &que1;
	q2 = &que2;
}

void View::Update()
{
	string s = "";
	QueIter *i = q1->createIterator();
	i->first();
	while (!(i->IsDone()))
	{
		s += to_string(*i->CurrentItem());
		s += " ";
		i->next();
	}
	const char *que1 = s.c_str();
	//SendMessage((HWND)Out1, WM_SETTEXT, NULL, (LPARAM)que1);
	SetWindowTextA(Out1, (LPCSTR)que1);
	s.clear();
	i = q2->createIterator();
	i->first();
	while (!(i->IsDone()))
	{
		s += to_string(*i->CurrentItem());
		s += " ";
		i->next();
	}
	const char *que2 = s.c_str();
	//SendMessage(*Out2, WM_SETTEXT, NULL, (LPARAM)que2);
	SetWindowTextA(Out2, (LPCSTR)que2);
}

void View::PushError()
{
	MessageBoxA(hWnd, (LPCSTR)"Вводите только целые числа", (LPCSTR)"Error", MB_OK | MB_ICONWARNING);
}

void View::EmptyError()
{
	MessageBoxA(hWnd, (LPCSTR)"Очередь пуста", (LPCSTR)"Error", MB_OK | MB_ICONWARNING);
}

void View::SizeInfo(int x)
{
	string ans;
	ans = "Размер очереди - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBoxA(hWnd, cstr, (LPCSTR)"Size", MB_OK | MB_ICONINFORMATION);
}

void View::EmptyInfo()
{
	MessageBoxA(hWnd, (LPCSTR)"Очередь пуста", (LPCSTR)"Is Empty?", MB_OK | MB_ICONINFORMATION);
}

void View::NotEmptyInfo()
{
	MessageBoxA(hWnd, (LPCSTR)"Очередь не пуста", (LPCSTR)"Is Empty?", MB_OK | MB_ICONINFORMATION);
}

void View::FrontInfo(int x)
{
	string ans;
	ans = "Передний элемент - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBoxA(hWnd, cstr, (LPCSTR)"Front", MB_OK | MB_ICONINFORMATION);
}

void View::BackInfo(int x)
{
	string ans;
	ans = "Задний элемент - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBoxA(hWnd, (LPCSTR)cstr, (LPCSTR)"Back", MB_OK | MB_ICONINFORMATION);
}

void View::MinInfo(int x)
{
	string ans;
	ans = "Минимальный элемент - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBoxA(hWnd, (LPCSTR)cstr, (LPCSTR)"Min", MB_OK | MB_ICONINFORMATION);
}

void View::MaxInfo(int x)
{
	string ans;
	ans = "Максимальный элемент - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBoxA(hWnd, (LPCSTR)cstr, (LPCSTR)"Max", MB_OK | MB_ICONINFORMATION);
}

void View::EqualInfo(bool flag)
{
	if (flag)
	{
		MessageBoxA(hWnd, (LPCSTR)"Да, очереди равны", (LPCSTR)"Equal", MB_OK | MB_ICONINFORMATION);
	}
	else
	{
		MessageBoxA(hWnd, (LPCSTR)"Нет, очереди не равны", (LPCSTR)"Equal", MB_OK | MB_ICONINFORMATION);
	}
}

void View::NotEqualInfo(bool flag)
{
	if (flag)
	{
		MessageBoxA(hWnd, (LPCSTR)"Да, очереди не равны", (LPCSTR)"Not equal", MB_OK | MB_ICONINFORMATION);
	}
	else
	{
		MessageBoxA(hWnd, (LPCSTR)"Нет, очереди равны", (LPCSTR)"Not equal", MB_OK | MB_ICONINFORMATION);
	}
}


View::~View()
{
}
