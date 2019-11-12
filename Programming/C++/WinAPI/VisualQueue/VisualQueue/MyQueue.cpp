#include "stdafx.h"
#include "MyQueue.h"
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
				//ÎØÈÁÊÀ
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
	arr = new int[100];
	space = 100;
}

MyQueue::MyQueue(MyQueue& q)
{
	size = q.size;
	space = q.space;
	arr = new int[space];
	memcpy(arr, q.arr, space);
	iter_exist = false;
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
	iter_exist = false;
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
	iter_exist = false;
}

MyQueue& MyQueue::operator=(const MyQueue& q)
{
	size = q.size;
	space = q.space;
	arr = new int[space];
	memcpy(arr, q.arr, space);
	//q.Clear();
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
	MyQueue q = *this;
	q.Push(x);
	return q;
}

MyQueue MyQueue::operator+(const MyQueue& q)
{
	MyQueue a = *this;
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
		Create_Iter();
		Last_Iter();
		int ans = Return_Iter();
		Delete_Iter();
		return ans;
	}
}

int MyQueue::Front()
{
	if (size != 0)
	{
		Create_Iter();
		First_Iter();
		int ans = Return_Iter();
		Delete_Iter();
		return ans;
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

void MyQueue::Create_Iter()
{
	iter_exist = true;
	iter_pos = 0;
}

void MyQueue::Next_Iter()
{
	iter_pos++;
	if (iter_pos == size + 1)
	{
		iter_pos--;
	}
}

void MyQueue::Prev_Iter()
{
	iter_pos--;
	if (iter_pos == -1)
	{
		iter_pos++;
	}
}

void MyQueue::First_Iter()
{
	iter_pos = 0;
}

void MyQueue::Last_Iter()
{
	iter_pos = size;
}

int MyQueue::Return_Iter()
{
	return arr[iter_pos];
}

void MyQueue::Delete_Iter()
{
	iter_exist = false;
}

void SwapQue(MyQueue &a, MyQueue &b) 
{
	swap(a.arr, b.arr);
	swap(a.size, b.size);
	swap(a.space, b.space);
}


MyQueue::~MyQueue()
{
	delete[] arr;
	size = 0;
	space = 0;
}

int MyQueue::Iter_End()
{
	return size;
}

int MyQueue::Iter_Begin()
{
	return 0;
}


