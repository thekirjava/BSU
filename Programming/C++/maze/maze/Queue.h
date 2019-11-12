#pragma once

using namespace std;

template <class T>
struct element
{
	T x;
	element *next;
	element *previous;
};

template <class T>
class Queue
{
public:
	Queue();
	~Queue();
	/*template <class T>*/ void push(T a);
	void pop();
	/*template <class T>*/ T front();
	/*template <class T>*/ T back();
	int size();
	void clear();
private:
	
	element <T> *first;
	element <T> *last;
	int s;
};

