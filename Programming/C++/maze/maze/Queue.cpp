#include "stdafx.h"
#include "Queue.h"

using namespace std;

Queue<class T>::Queue()
{
	s = 0;
	first = NULL;
	last = NULL;

}

template <class T> void Queue<T>::push(T a)
{
	element* e;
	e = new element;
	e.x = a;
	e->previous = NULL;
	e->next = first;
	first = e;
	s++;
}

void Queue<class T>::pop()
{
	element *e = last;
	last = last->previous;
	s--;
	delete e;
}

template <class T> T Queue<T>::front()
{
	return first->x;
}

template <class T> T Queue<T>::back()
{
	return last->x;
}

int Queue<class T>::size()
{
	return s;
}

void Queue<class T>::clear()
{
	element *i = first;
	while (i != NULL)
	{
		i = i->next;
		delete i->previous;
	}
}

Queue<class T>::~Queue()
{
	this->clear();
}