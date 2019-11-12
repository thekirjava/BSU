#pragma once
#include <iostream>
#include <initializer_list>

using namespace std;

class Visitor
{
public:
	virtual void Visit(int *ref) = 0;
};

class Element
{
	virtual void accept(class Visitor &v) = 0;
};

class Min : public Visitor
{
public:
	Min();
	void Visit(int *ref);
	int ReturnAns(bool clr);
private:
	int ans;
};

class Max : public Visitor
{
public:
	Max();
	void Visit(int *ref);
	int ReturnAns(bool clr);
private:
	int ans;
};

class MyQueue:public Element
{
	friend istream& operator>>(istream& input, MyQueue& q);
	friend ostream& operator<<(ostream& output, const MyQueue& p);
public:
	friend class QueIter;
	MyQueue();
	MyQueue(MyQueue&& q);
	MyQueue(initializer_list <int> l);
	MyQueue(int n, int a, ...);
	MyQueue& operator=(MyQueue&& q);
	bool operator==(const MyQueue& q);
	bool operator!=(const MyQueue& q);
	MyQueue operator+(const int& x);
	MyQueue operator+(const MyQueue& q);
	friend MyQueue operator+(const int& x, const MyQueue& q);
	void accept(class Visitor &v);
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
	QueIter *createIterator() const;
	~MyQueue();
private:
	int size;
	int space;
	int* arr;
	const int STARTSIZE = 100;
};

class QueIter
{
public:
	QueIter(const class MyQueue *q);
	void first();
	void next();
	bool IsDone();
	int* CurrentItem();
private:
	const MyQueue *que;
	int index;
};