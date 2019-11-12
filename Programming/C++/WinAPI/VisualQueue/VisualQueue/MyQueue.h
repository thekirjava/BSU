#pragma once
#include <iostream>
#include <initializer_list>
using namespace std;

class MyQueue
{
	friend istream& operator>>(istream& input, MyQueue& q);
	friend ostream& operator<<(ostream& output, const MyQueue& p);
public:
	MyQueue();
	MyQueue(MyQueue& q);
	MyQueue(initializer_list <int> l);
	MyQueue(int n, int a, ...);
	MyQueue& operator=(const MyQueue& q);
	bool operator==(const MyQueue& q);
	bool operator!=(const MyQueue& q);
	MyQueue operator+(const int& x);
	MyQueue operator+(const MyQueue& q);
	friend MyQueue operator+(const int& x, const MyQueue& q);
	MyQueue& operator+=(const int& x);
	MyQueue& operator+=(const MyQueue& x);
	bool IsEmpty();
	int Size();
	void Clear();
	int Front();
	int Back();
	void Push(int x);
	void Pop();
	friend void SwapQue(MyQueue &a, MyQueue &b);
	void Create_Iter();
	void Next_Iter();
	void Prev_Iter();
	void First_Iter();
	void Last_Iter();
	int Return_Iter();
	void Delete_Iter();
	~MyQueue();
private:
	int size;
	int space;
	int* arr;
	int iter_pos;
	bool iter_exist;
	int Iter_End();
	int Iter_Begin();
	
};

