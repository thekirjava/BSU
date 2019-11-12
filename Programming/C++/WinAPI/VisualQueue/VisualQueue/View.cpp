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
	SetWindowText(Out1, (LPCSTR)que1);
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
	SetWindowText(Out2, (LPCSTR)que2);
}

void View::PushError()
{
	MessageBox(hWnd, (LPCSTR)"������� ������ ����� �����", (LPCSTR)"Error", MB_OK | MB_ICONWARNING);
}

void View::EmptyError()
{
	MessageBox(hWnd, (LPCSTR)"������� �����", (LPCSTR)"Error", MB_OK | MB_ICONWARNING);
}

void View::SizeInfo(int x)
{
	string ans;
	ans = "������ ������� - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBox(hWnd, cstr, (LPCSTR)"Size", MB_OK | MB_ICONINFORMATION);
}

void View::EmptyInfo()
{
	MessageBox(hWnd, (LPCSTR)"������� �����", (LPCSTR)"Is Empty?", MB_OK | MB_ICONINFORMATION);
}

void View::NotEmptyInfo()
{
	MessageBox(hWnd, (LPCSTR)"������� �� �����", (LPCSTR)"Is Empty?", MB_OK | MB_ICONINFORMATION);
}

void View::FrontInfo(int x)
{
	string ans;
	ans = "�������� ������� - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBox(hWnd, cstr, (LPCSTR)"Front", MB_OK | MB_ICONINFORMATION);
}

void View::BackInfo(int x)
{
	string ans;
	ans = "������ ������� - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBox(hWnd, cstr, (LPCSTR)"Back", MB_OK | MB_ICONINFORMATION);
}

void View::MinInfo(int x)
{
	string ans;
	ans = "����������� ������� - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBox(hWnd, cstr, (LPCSTR)"Min", MB_OK | MB_ICONINFORMATION);
}

void View::MaxInfo(int x)
{
	string ans;
	ans = "������������ ������� - ";
	ans += to_string(x);
	const char* cstr = ans.c_str();
	MessageBox(hWnd, cstr, (LPCSTR)"Max", MB_OK | MB_ICONINFORMATION);
}

void View::EqualInfo(bool flag)
{
	if (flag)
	{
		MessageBox(hWnd, (LPCSTR)"��, ������� �����", (LPCSTR)"Equal", MB_OK | MB_ICONINFORMATION);
	}
	else
	{
		MessageBox(hWnd, (LPCSTR)"���, ������� �� �����", (LPCSTR)"Equal", MB_OK | MB_ICONINFORMATION);
	}
}

void View::NotEqualInfo(bool flag)
{
	if (flag)
	{
		MessageBox(hWnd, (LPCSTR)"��, ������� �� �����", (LPCSTR)"Not equal", MB_OK | MB_ICONINFORMATION);
	}
	else
	{
		MessageBox(hWnd, (LPCSTR)"���, ������� �����", (LPCSTR)"Not equal", MB_OK | MB_ICONINFORMATION);
	}
}


View::~View()
{
}
