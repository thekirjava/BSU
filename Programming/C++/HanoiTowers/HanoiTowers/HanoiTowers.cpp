#include "pch.h"
#include <iostream>
#include <stdlib.h>
#include <vector>
#include <windows.h>

using namespace std;

vector <int> st_1;
vector <int> st_2;
vector <int> st_3;
int l;
int N;

void output()
{
	int u1 = 0;
	int u2 = 0;
	int u3 = 0;
	system("cls");
	for (int i = 0; i < N; i++)
	{
		cout << " ";
		if (st_1.size() + i >= N)
		{
			for (int j = 0; j < (l - st_1[u1]) / 2; j++)
			{
				cout << " ";
			}
			for (int j = 0; j < st_1[u1]; j++) 
			{
				cout << "*";
			}
			for (int j = 0; j < (l - st_1[u1]) / 2; j++)
			{
				cout << " ";
			}
			u1++;
		}
		else
		{
			for (int j = 0; j < l / 2; j++) 
			{
				cout << " ";
			}
			cout << "|";
			for (int j = 0; j < l / 2; j++)
			{
				cout << " ";
			}
		}

		cout << " ";

		if (st_2.size() + i >= N)
		{
			for (int j = 0; j < (l - st_2[u2]) / 2; j++)
			{
				cout << " ";
			}
			for (int j = 0; j < st_2[u2]; j++)
			{
				cout << "*";
			}
			for (int j = 0; j < (l - st_2[u2]) / 2; j++)
			{
				cout << " ";
			}
			u2++;
		}
		else
		{
			for (int j = 0; j < l / 2; j++) 
			{
				cout << " ";
			}
			cout << "|";
			for (int j = 0; j < l / 2; j++)
			{
				cout << " ";
			}
		}

		cout << " ";

		if (st_3.size() + i >= N)
		{
			for (int j = 0; j < (l - st_3[u3]) / 2; j++)
			{
				cout << " ";
			}
			for (int j = 0; j < st_3[u3]; j++)
			{
				cout << "*";
			}
			for (int j = 0; j < (l - st_3[u3]) / 2; j++)
			{
				cout << " ";
			}
			u3++;
		}
		else
		{
			for (int j = 0; j < l / 2; j++) {
				cout << " ";
			}
			cout << "|";
			for (int j = 0; j < l / 2; j++)
			{
				cout << " ";
			}
		}
		cout << '\n';
	}
	Sleep(2400);
}

void transfer(int from, int to)
{
	if (from == 1) {
		if (to == 2)
		{
			st_2.insert(st_2.begin(), st_1[0]);
		}
		else
		{
			st_3.insert(st_3.begin(), st_1[0]);
		}
		st_1.erase(st_1.begin());
	}
	else
	{
		if (from == 2)
		{
			if (to == 1)
			{
				st_1.insert(st_1.begin(), st_2[0]);
			}
			else
			{
				st_3.insert(st_3.begin(), st_2[0]);
			}
			st_2.erase(st_2.begin());
		}
		else
		{
			if (to == 1)
			{
				st_1.insert(st_1.begin(), st_3[0]);
			}
			else
			{
				st_2.insert(st_2.begin(), st_3[0]);
			}
			st_3.erase(st_3.begin());
		}
	}
}
void hanoi(int K, int from, int to, int buf)
{
	if (K != 0) 
	{
		hanoi(K - 1, from, buf, to);
		transfer(from, to);
		output();
		hanoi(K - 1, buf, to, from);
	}
}

int main()
{
	cout << "Enter the number of discs ";
	cin >> N;
	for (int i = 0; i < N; i++) 
	{
		st_1.push_back((i * 2) + 1);
	}
	l = (N * 2) - 1;
	output();
	hanoi(N, 1, 3, 2);
	return 0;
}