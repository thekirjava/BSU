#pragma once
#include <iostream>
#include <initializer_list>
using namespace std;

class List_iterator;

class visitor;
class visitor_sum;


class List
{
public:

	List() :amount(0), capacity(1), arr(nullptr) {}
	List(const List& l);

	List(int n) :amount(n), capacity(n)
	{
		int *ar = new int[n];
		arr = ar;
		for (int i = 0; i < n; i++)
		{
			arr[i] = 0;
		}

	}
	List(List && nl) :arr(nl.arr), amount(nl.amount), capacity(nl.capacity)
	{
		nl.Clear();
	}
	List(initializer_list<int> l) :amount(l.size()), capacity(l.size())
	{
		arr = new int[amount];
		int k = 0;
		for (auto it = l.begin(); it != l.end(); ++it)
		{
			arr[k++] = *it;
		}
	}
	~List()
	{
		if (arr != NULL)
		{
			amount = 0;
			capacity = 1;
			delete[] arr;
			arr = nullptr;
		}
		/*if (amount != 0)
		{
			amount = 0;
			capacity = 1;
			arr = nullptr;
			delete arr;
		}*/
	}

	List& operator = (List&& aList);

	bool operator ==(List const &List);

	bool operator !=(List const &List) { return !(*this == List); }
	List& operator =(List const& nl);

	List & operator+= (List const &l1);


	friend	ostream& operator << (ostream& os, List &nl);

	List operator + (List const &l1);

	int Front();

	int Back();

	bool IsEmpty() { return amount == 0; }
	void Clear();

	int  Size() { return amount; }
	void PopFront();

	void PopBack();

	void PushFront(int num);

	void PushBack(int num);

	void Swap(List &nl);

	int* GetArr() { return arr; }
	int countSum();

private:

	int capacity;
	int amount;
	int *arr;
};


class Iterator
{
public:
	virtual void First() = 0;
	virtual bool Is_done()const = 0;
	virtual int current_item()const = 0;
	virtual void next() = 0;
};


class List_iterator :public  Iterator
{
public:

	List_iterator() :fpList(nullptr), fpCur(nullptr), index(0) {}
	List_iterator(List* List) :fpList(List) {}
	~List_iterator() {}

	void First();
	bool Is_done() const
	{
		return index >= fpList->Size();
	}
	int current_item() const
	{
		return *fpCur;
	}
	void next();

private:
	int *fpCur;
	List *fpList;
	int index;
};

class visitor
{
public:
	virtual void visit(int elem) = 0;
	virtual ~visitor() = default;
};
class visitor_sum :public visitor
{
private:
	int sum = 0;
public:
	void visit(int elem)
	{
		sum += elem;
	}
	int GetSum()
	{
		return sum;
	}
};
