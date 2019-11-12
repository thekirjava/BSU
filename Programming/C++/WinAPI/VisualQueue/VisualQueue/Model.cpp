#include "stdafx.h"
#include "Model.h"
#include <string>

using namespace std;

istream& operator>>(istream& input, MyQueue& q)
{
	string s;
	input >> s;
	int k = 0;
	for (int i = 0; i < s.size(); i++)
	{
		if ((s[i] >= '0') && (s[i] <= '9'))
		{
			k *= 10;
			k += s[i] - '0';
		}
		else
		{
			if (s[i] == ' ')
			{
				q.Push(k);
				k = 0;
			}
			else
			{
				//ÎÁÎÇÍÀ×ÈÒÜ ÎØÈÁÊÓ
				q.Clear();
				return input;
			}
		}
	}
	q.Push(k);
	return input;
}

ostream& operator<<(ostream& output, const MyQueue& q)
{
	for (int i = q.size - 1; i >= 0; i--)
	{
		output << q.arr[i] << " ";
	}
	return output;
}

MyQueue::MyQueue()
{
	size = 0;
	arr = new int[STARTSIZE];
	space = STARTSIZE;
}

MyQueue::MyQueue(MyQueue&& q) //Äâà àìïåðñàíäà!! ÃÎÒÎÂÎ
{
	size = q.size;
	space = q.space;
	arr = new int[space];
	memcpy(arr, q.arr, space);
	q.Clear();
}

MyQueue::MyQueue(initializer_list<int> l)
{
	size = l.size();
	space = size * 2;
	arr = new int[space];
	int k = 0;
	for (initializer_list<int>::iterator i = l.begin(); i != l.end(); i++)
	{
		arr[k] = *i;
	}
}

MyQueue::MyQueue(int n, int a, ...)
{
	int *ptr = &a;
	size = n;
	arr = new int[n * 2];
	space = n * 2;
	for (int i = 0; i < n; i++)
	{
		arr[i] = *ptr;
		ptr++;
	}
}

MyQueue& MyQueue::operator=(MyQueue&& q)
{
	size = q.size;
	space = q.space;
	arr = new int[space];
	memcpy(arr, q.arr, space);
	q.Clear();
	return *this;
}

bool MyQueue::operator==(const MyQueue& q)
{
	if (this->size == q.size)
	{
		for (int i = 0; i < q.size; i++)
		{
			if (this->arr[i] != q.arr[i])
			{
				return false;
			}
		}
		return true;
	}
	return false;
}

bool MyQueue::operator!=(const MyQueue& q)
{
	if (this->size == q.size)
	{
		for (int i = 0; i < q.size; i++)
		{
			if (this->arr[i] != q.arr[i])
			{
				return true;
			}
		}
		return false;
	}
	return true;
}

MyQueue MyQueue::operator+(const int& x)
{
	MyQueue q;
	q.size = size;
	q.space = space;
	q.arr = new int[space];
	memcpy(q.arr, arr, q.space);
	q.Push(x);
	return q;
}

MyQueue MyQueue::operator+(const MyQueue& q)
{
	MyQueue a;
	a.size = size;
	a.space = space;
	a.arr = new int[space];
	memcpy(a.arr, arr, a.space);
	for (int i = 0; i < q.size; i++)
	{
		a.Push(q.arr[i]);
	}
	return a;
}


MyQueue operator+(const int& x, const MyQueue& q)
{
	MyQueue a;
	a.size = q.size;
	a.space = q.space;
	memcpy(a.arr, q.arr, a.space);
	a.Push(x);
	return a;
}

void MyQueue::accept(Visitor &v)
{
	QueIter* it = this->createIterator();
	it->first();
	while (!(it->IsDone()))
	{
		v.Visit((it->CurrentItem()));
		it->next();
	}
	
}

MyQueue& MyQueue::operator+=(const int& x)
{
	this->Push(x);
	return *this;
}

MyQueue& MyQueue::operator+=(const MyQueue& q)
{
	for (int i = 0; i < q.size; i++)
	{
		this->Push(q.arr[i]);
	}
	return *this;
}

bool MyQueue::IsEmpty()
{
	if (size == 0)
	{
		return true;
	}
	else
	{
		return false;
	}
}

int MyQueue::Size()
{
	return size;
}

void MyQueue::Clear()
{
	size = 0;
}

int MyQueue::Back()
{
	if (size != 0)
	{
		return arr[size - 1];
	}
}

int MyQueue::Front()
{
	if (size != 0)
	{
		return arr[0];
	}
}

void MyQueue::Push(int x)
{
	if (size >= space)
	{
		int* q = arr;
		space *= 2;
		arr = new int[space];
		memcpy(arr, q, space / 2);
	}
	arr[size] = x;
	size++;
}

void MyQueue::Pop()
{
	if (size != 0)
	{
		for (int i = 1; i < size; i++)
		{
			arr[i - 1] = arr[i];
		}
		size--;
	}
	
}

void SwapQue(MyQueue &a, MyQueue &b) 
{
	swap(a.arr, b.arr);
	swap(a.size, b.size);
	swap(a.space, b.space);
}


QueIter * MyQueue::createIterator() const
{
	return new QueIter(this);
}

MyQueue::~MyQueue()
{
	delete[] arr;
	size = 0;
	space = 0;
}


Min::Min()
{
	ans = INT32_MAX;
}

void Min::Visit(int *ref)
{
		ans = min(ans, *ref);
}

int Min::ReturnAns(bool clr)
{
	int x = this->ans;
	if (clr)
	{
		this->ans = INT32_MAX;
	}
	return x;
}

Max::Max()
{
	ans = INT32_MIN;
}

void Max::Visit(int *ref)
{
	ans = max(ans, *ref);
}

int Max::ReturnAns(bool clr)
{
	int x = this->ans;
	if (clr)
	{
		this->ans = INT32_MIN;
	}
	return x;
}

QueIter::QueIter(const MyQueue * q)
{
	que = q;
}

void QueIter::first()
{
	index = 0;
}

void QueIter::next()
{
	index++;
}

bool QueIter::IsDone()
{
	return (index == que->size);
}

int* QueIter::CurrentItem()
{
	return &que->arr[index];
}
