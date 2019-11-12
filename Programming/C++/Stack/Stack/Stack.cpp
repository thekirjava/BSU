#include "pch.h"
#include <iostream>

using namespace std;

struct stack
{
	int *x;
	int size_Stack;
	int MaxSize;
};

void Init(stack &st, int sizest)
{
	st.size_Stack = 0;
	st.MaxSize = sizest;
	st.x =new int [st.MaxSize];
}

void push(stack &st, int a)
{
	if (st.size_Stack < st.MaxSize)
	{
		st.x[st.size_Stack] = a;
		st.size_Stack++;
	}
	else
	{
		cout << "Error! Array is already full\n";
	}
}

void pop(stack &st)
{
	if (st.size_Stack != 0)
	{
		st.size_Stack--;
	}
	else
	{
		cout << "Error! Array is already empty\n";
	}
}

int top(stack &st)
{
	if (st.size_Stack != 0)
	{
		return st.x[st.size_Stack - 1];
	}
	else
	{
		cout << "Error! Array is empty, there is nothing to return.\n";
		return -1;
	}
}

int StackSize(stack& st)
{
	return st.size_Stack;
}
void help()
{
	cout << "List of commands\n";
	cout << "1 x: Insert x\n";
	cout << "2: Pop\n";
	cout << "3: Print top\n";
	cout << "4: Stack size\n";
	cout << "0: Finish execution of programm\n";
}

int main()
{
	stack st;
	Init(st, 1000000);
	int command = -1;
	help();
	while (command != 0)
	{
		cin >> command;
		switch (command)
		{
		case 1:
		{
			int x;
			cin >> x;
			push(st, x);
			break;
		}
		case 2:
		{
			pop(st);
			break;
		}
		case 3:
		{
			cout << top(st) << '\n';
			break;
		}
		case 4:
		{
			cout << StackSize(st) << '\n';
			break;
		}
		case 0:
		{
			return 0;
			break;
		}
		default:
		{
			cout << "Incorrect command!\nTry again\n";
			help();
			break;
		}
		}
	}
}
