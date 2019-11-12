#pragma once
#include "Model.h"

class View
{
public:
	View();
	View(HWND h, HWND o1, HWND o2, MyQueue &que1, MyQueue &que2);
	void Create(HWND h, HWND o1, HWND o2, MyQueue &que1, MyQueue &que2);
	void Update();
	void PushError();
	void EmptyError();
	void SizeInfo(int x);
	void EmptyInfo();
	void NotEmptyInfo();
	void FrontInfo(int x);
	void BackInfo(int x);
	void MinInfo(int x);
	void MaxInfo(int x);
	void EqualInfo(bool flag);
	void NotEqualInfo(bool flag);
	~View();
private:
	HWND Out1;
	HWND Out2;
	HWND hWnd;
	MyQueue *q1;
	MyQueue *q2;
};

