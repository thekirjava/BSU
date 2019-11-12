#include "list.h"
#include"list.h"
List::List(const List & l)
{
	amount = l.amount;
	capacity = l.capacity;
	int *temp = new int[amount];
	for (int i = 0; i < amount; ++i)
	{
		temp[i] = l.arr[i];
	}
	arr = temp;
}

List & List::operator=(List && aList)
{
	if (this == &aList)
		return *this;

	if (!aList.IsEmpty())
		aList.Swap(*this);
	aList.Clear();
	return *this;
}

bool List::operator==(List const & List)
{
	if (amount == List.amount)
	{
		for (int i = 0; i < amount; ++i)
		{
			if (arr[i] != List.arr[i])
				return false;
		}
		return true;
	}
	return false;
}

List & List::operator=(List const & nl)
{
	amount = nl.amount;
	capacity = nl.capacity;
	for (int i = 0; i < amount; ++i)
	{
		arr[i] = nl.arr[i];
	}
	return *this;
}

List & List::operator+=(List const & l1)
{
	int *n_ar = new int[amount + l1.amount];
	for (int i = 0; i < amount; ++i)
	{
		n_ar[i] = arr[i];
	}


	for (int i = 0; i < l1.amount; ++i)
	{
		n_ar[i + amount] = l1.arr[i];
	}
	amount += l1.amount;
	capacity += l1.capacity;
	delete[] arr;

	arr = n_ar;

	return *this;
}

List List::operator+(List const & l1)
{
	List nl(*this);
	nl += l1;
	return nl;

}

int List::Front()
{
	if (amount != 0)
		return arr[0];
	else
		return -1;
}

int List::Back()
{
	if (amount != 0)
		return arr[amount - 1];
	else
		return -1;
}

void List::Clear()
{
	amount = 0;
	capacity = 1;
	delete[] arr;
	arr = nullptr;
}

void List::PopFront()
{
	if (amount != 0)
	{
		amount--;
		capacity = amount;
		int* n_ar = new int[amount];
		for (int i = 0; i < amount; ++i)
		{
			n_ar[i] = arr[i + 1];
		}
		delete[] arr;
		arr = n_ar;
	}




}

void List::PopBack()
{
	if (amount != 0)
		amount--;
}

void List::PushFront(int num)
{
	amount++;
	capacity = amount;
	int *n_ar = new int[amount];
	n_ar[0] = num;
	for (int i = 0; i < amount - 1; ++i)
	{
		n_ar[i + 1] = arr[i];
	}
	delete[] arr;
	arr = n_ar;

}

void List::PushBack(int num)
{
	amount++;
	if (amount >= capacity)
	{
		capacity *= 2;
		int *n_ar = new int[capacity];

		for (int i = 0; i < amount - 1; ++i)
		{
			n_ar[i] = arr[i];
		}
		n_ar[amount - 1] = num;
		delete[] arr;
		arr = n_ar;
	}
	else
	{
		arr[amount - 1] = num;
	}

}

void List::Swap(List & nl)
{
	swap(amount, nl.amount);
	swap(capacity, nl.capacity);
	swap(arr, nl.arr);
}

ostream & operator<<(ostream & os, List & nl)
{
	if (!nl.IsEmpty())
	{
		for (int i = 0; i < nl.amount; i++)
		{
			os << nl.arr[i] << "; ";
		}
		os << endl;
	}
	else
		os << "Empty" << endl;
	return os;
}


void List_iterator::First()
{
	index = 0;
	fpCur = fpList->GetArr();
}

void List_iterator::next()
{
	if (!Is_done())
	{
		fpCur = fpCur + 1;
		index++;
	}
}

int List::countSum()
{
	{
		visitor_sum vis_sum;
		List_iterator it(this);
		for (it.First(); !it.Is_done(); it.next())
		{
			vis_sum.visit(it.current_item());
		}
		return vis_sum.GetSum();
	}
}